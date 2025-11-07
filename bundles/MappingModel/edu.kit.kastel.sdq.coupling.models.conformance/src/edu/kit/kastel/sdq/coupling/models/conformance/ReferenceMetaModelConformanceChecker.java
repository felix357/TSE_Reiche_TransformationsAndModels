package edu.kit.kastel.sdq.coupling.models.conformance;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;

import mapping.MappingDefinition;

/**
 * Utility class to check the conformance of a mapping model to a reference
 * metamodel. Implements recursive reference checking based on Definition 4 of
 * https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=11082015
 */
public class ReferenceMetaModelConformanceChecker {

	/**
	 * Checks recursively whether a given mapping class correctly references all
	 * necessary classes in the reference class.
	 * 
	 * @param mappingClass   the source class from the mapping
	 * @param referenceClass the target class in the reference metamodel
	 * @param mapping        the mapping definition
	 * @return true if all required references are present and conform, false
	 *         otherwise
	 */
	private static boolean conformingReferences(EClass mappingClass, EClass referenceClass, MappingDefinition mapping) {
		for (EStructuralFeature refFeature : referenceClass.getEStructuralFeatures()) {

			if (refFeature instanceof EReference) {
				EReference ref = (EReference) refFeature;
				EClass referenceTargetClass = ref.getEReferenceType();
				System.out.println(ref.getName() + " references class: " + referenceTargetClass.getName());

				// 1. Find mapping classes that map to the reference target class
				Set<String> mappedTargetClasses = new HashSet<>();
				for (var cm : mapping.getClassMappings()) {
					EClass mappedClass = cm.getTargetClass();
					if (mappedClass != null) {
				        if (mappedClass.getName().equals(referenceTargetClass.getName())
				                && mappedClass.getEPackage().getNsURI().equals(referenceTargetClass.getEPackage().getNsURI())) {
				            mappedTargetClasses.add(cm.getSourceClass().getName());
				            System.out.println("Mapped source class: " + cm.getSourceClass().getName());
				        }
				    }
				}

				// 2. Collect all EReferences of mappingClass and its super types
				Set<EReference> allMappingRefs = new HashSet<>(mappingClass.getEReferences());

				// only needed for input mapping of codeql (maybe remove in final version)
				collectSuperTypeReferences(mappingClass, allMappingRefs);

				boolean found = false;

				// 3. Check if any reference points to a mapped target class
				for (EReference mappingRef : allMappingRefs) {
					if (referencesTargetClassRecursively(mappingRef, mappedTargetClasses, new HashSet<>())) {
						found = true;
						break;
					}
				}

				if (!found) {
					System.err.println("Mapping class " + mappingClass.getName()
							+ " (including super types) does not reference any mapped class for "
							+ referenceTargetClass.getName());
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Recursively checks whether the given EReference or any of its target references
	 * (following the reference tree) are contained in the set of mapped target classes.
	 * 
	 * The method performs the following checks:
	 * 1. Direct hit: returns true if the target class of the reference is in the mapped target classes.
	 * 2. Target mapping: recursively checks if any of the EReferences of the target class are mapped.
	 * 3. Subtype check: for all subtypes of the target class, returns true only if every subtype has
	 *    it a reference recursively mapping to valid target classes. If any subtype fails this check,
	 *    the method returns false.
	 * 
	 * @param mappingRef the reference to check
	 * @param mappedTargetClasses the names of classes considered valid targets
	 * @param visited a set of already visited classes to prevent cycles
	 * @return true if a valid mapping is found according to the rules above, false otherwise
	 */
	private static boolean referencesTargetClassRecursively(EReference mappingRef, Set<String> mappedTargetClasses,
			Set<EClass> visited) {
		EClass target = mappingRef.getEReferenceType();
		if (target == null || visited.contains(target)) {
			return false;
		}
		visited.add(target);

		// 1. Direct hit?
		if (mappedTargetClasses.contains(target.getName())) {
			return true;
		}

		// 2. Check if the target class itself is mapped
		for (EReference subRef : target.getEReferences()) {
			if (referencesTargetClassRecursively(subRef, mappedTargetClasses, visited)) {
				return true;
			}
		}

		// 3. Check if all subtypes have a valid mapping
		Set<EClass> subTypes = getAllSubTypes(target.getEPackage(), target);
		if (!subTypes.isEmpty()) {
			for (EClass subType : subTypes) {
				boolean allSubRefsValid = true;
				for (EReference subRef : subType.getEReferences()) {
					if (!referencesTargetClassRecursively(subRef, mappedTargetClasses, new HashSet<>(visited))) {
						allSubRefsValid = false;
						break;
					}
				}
				// at least one subtype does not have a valid mapping → false
				if (!allSubRefsValid) {
					return false;
				}
			}
			return true; // all subtypes have valid references
		}

		return false;
	}

	/**
	 * Recursively collects all subtypes of a given EClass within a package and its subpackages.
	 * A subtype is any class that directly or indirectly inherits from the specified superClass.
	 * 
	 * @param rootPackage the root EPackage to search for subtypes
	 * @param superClass  the EClass whose subtypes are to be found
	 * @return a set of all EClass instances that are subtypes of the given superClass
	 */
	private static Set<EClass> getAllSubTypes(EPackage rootPackage, EClass superClass) {
		Set<EClass> result = new HashSet<>();
		for (var classifier : rootPackage.getEClassifiers()) {
			if (classifier instanceof EClass ec) {
				if (ec.getEAllSuperTypes().contains(superClass)) {
					result.add(ec);
				}
			}
		}
		// Recursively search in subpackages
		for (EPackage sub : rootPackage.getESubpackages()) {
			result.addAll(getAllSubTypes(sub, superClass));
		}
		return result;
	}

	/**
	 * Recursively collects all EReferences from super types of a given EClass into
	 * the provided set.
	 * 
	 * @param clazz  the class whose super type references are to be collected
	 * @param refSet the set to store all collected references
	 */
	private static void collectSuperTypeReferences(EClass clazz, Set<EReference> refSet) {
		for (EClass superType : clazz.getESuperTypes()) {
			refSet.addAll(superType.getEReferences());
			collectSuperTypeReferences(superType, refSet);
		}
	}

	/**
	 * Checks whether the mapping conforms to the reference metamodel according to
	 * Definition 4 in
	 * https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=11082015. Ensures
	 * that for each reference class at least one mapping class conforms.
	 * 
	 * @param mapping            the mapping definition
	 * @param referenceMetamodel the reference metamodel package
	 * @return true if the mapping conforms to the reference metamodel, false
	 *         otherwise
	 */
	public static boolean conformsToReferenceMetamodel(MappingDefinition mapping, EPackage referenceMetamodel) {
		Set<EClass> allReferenceClasses = new HashSet<>();
		referenceMetamodel.getEClassifiers().forEach(c -> {
			if (c instanceof EClass ec) {
				allReferenceClasses.add(ec);
			}
		});

		boolean allConform = true;

		for (EClass referenceClass : allReferenceClasses) {
			String refName = referenceClass.getName();
			System.out.println("Checking reference class: " + refName);
			
			List<EClass> mappingClassesForRef = mapping.getClassMappings().stream()
				    .filter(cm -> {
				        if (cm.getTargetClass() == null) return false;
				        String targetName = cm.getTargetClass().getName();
				        String targetNsUri = cm.getTargetClass().getEPackage() != null ? cm.getTargetClass().getEPackage().getNsURI() : null;
				        return targetName != null
				            && targetName.equals(referenceClass.getName())
				            && referenceClass.getEPackage().getNsURI().equals(targetNsUri);
				    })
				    .map(cm -> cm.getSourceClass())
				    .toList();


			if (mappingClassesForRef.isEmpty()) {
				System.err.println("No mapping class found for reference class " + refName);
				allConform = false;
				continue;
			}

			// Check: at least one mapping class must conform.
			boolean anyConforms = false;
			for (EClass mappingClass : mappingClassesForRef) {
				boolean conforms = ReferenceMetaModelConformanceChecker.conformingReferences(mappingClass,
						referenceClass, mapping);

				if (conforms) {
					System.out.println("Mapping class " + mappingClass.getName() + " conforms to reference class "
							+ referenceClass.getName());
					anyConforms = true;
					break;
				} else {
					System.err.println("Mapping class " + mappingClass.getName()
							+ " does NOT conform to reference class " + referenceClass.getName());
				}
			}

			if (!anyConforms) {
				allConform = false;
			}
		}

		return allConform;
	}
}