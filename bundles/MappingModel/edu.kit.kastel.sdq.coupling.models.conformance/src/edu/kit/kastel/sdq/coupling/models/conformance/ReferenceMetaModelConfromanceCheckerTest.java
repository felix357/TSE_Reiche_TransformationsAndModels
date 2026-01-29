package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import mapping.MappingDefinition;
import mapping.MappingPackage;

public class ReferenceMetaModelConfromanceCheckerTest {

	@Test
	public void testValidMappingConforms() throws Exception {
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		String codeqlOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping.xmi";
		MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

		String edfaInputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMappingTest.xmi";
		MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);

		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		assertTrue(codeqlInputConforms);
		assertTrue(codeqlOutputConforms);
		assertTrue(edfaInputConforms);
	}
	
	@Test
	public void testValidMappingConformsJoanaEDFA() throws Exception {
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		String joanaMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaInputMapping.xmi";
		MappingDefinition joanaInputMapping = loadMapping(resSet, joanaMappingPath);

		String joanaOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaOutputMapping.xmi";
		MappingDefinition joanaOutputMapping = loadMapping(resSet, joanaOutputMappingPath);

		String edfaInputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMappingTest.xmi";
		MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);

		EcoreUtil.resolveAll(resSet);

		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		assertTrue(joanaInputConforms);
		assertTrue(joanaOutputConforms);
		assertTrue(edfaInputConforms);
	}

	@Test
	public void testIncompleteCodeqlInputMappingFailsConformance() throws Exception {
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");
		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping_incomplete.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		assertFalse(codeqlInputConforms);
	}

	@Test
	public void testWrongMappingFailsConformance() throws Exception {
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");
		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping_wrong.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		assertFalse(codeqlInputConforms);
	}

	private ResourceSet createResourceSet() {
		// Create ResourceSet
		ResourceSet resSet = new ResourceSetImpl();
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());

		// Register MappingPackage
		MappingPackage.eINSTANCE.eClass();
		resSet.getPackageRegistry().put(MappingPackage.eNS_URI, MappingPackage.eINSTANCE);

		// Load relevant EPackages		
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(
			    resSet,
			    "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/identifier.ecore"
			));
		
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(
			    resSet,
			    "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/java.ecore"
			));

		
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/DataDictionaryCharacterized.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/pcm.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeql.ecore"));
		
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joana.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/parameterannotation.ecore"));
		return resSet;
	}

	private static EPackage loadAndRegisterEPackage(ResourceSet resSet, String path) {
		try {
			Resource res = resSet.getResource(URI.createFileURI(path), true);
			res.load(Collections.EMPTY_MAP);
			EPackage pkg = (EPackage) res.getContents().get(0);
			registerEPackageRecursively(resSet, pkg);
			System.out.println("EPackage geladen: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
			return pkg;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fehler beim Laden des EPackage: " + path);
			return null;
		}
	}

//	private static void registerEPackageRecursively(ResourceSet resSet, EPackage pkg) {
//		if (pkg == null)
//			return;
//		resSet.getPackageRegistry().put(pkg.getNsURI(), pkg);
//		System.out.println("EPackage registriert: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
//		for (EPackage subPkg : pkg.getESubpackages()) {
//			registerEPackageRecursively(resSet, subPkg);
//		}
//	}
	
	private static void registerEPackageRecursively(ResourceSet resSet, EPackage pkg) {
	    if (pkg == null) return;
	    
	    // Registriere das aktuelle Paket
	    resSet.getPackageRegistry().put(pkg.getNsURI(), pkg);
	    
	    // WICHTIG: Rekursion für alle Unterpakete (z.B. java -> members)
	    for (EPackage subPkg : pkg.getESubpackages()) {
	        registerEPackageRecursively(resSet, subPkg);
	    }
	}

	private static MappingDefinition loadMapping(ResourceSet resSet, String mappingFilePath) {
		try {
			Resource mappingResource = resSet.getResource(URI.createFileURI(mappingFilePath), true);
			mappingResource.load(Collections.EMPTY_MAP);
			MappingDefinition mapping = (MappingDefinition) mappingResource.getContents().get(0);
			System.out.println("Mapping XMI erfolgreich geladen: " + mappingFilePath);
			return mapping;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fehler beim Laden der Mapping XMI-Datei: " + mappingFilePath);
			return null;
		}
	}
}
