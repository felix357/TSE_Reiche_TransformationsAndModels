/**
 */
package mapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mapping.MappingDefinition#getClassMappings <em>Class Mappings</em>}</li>
 * </ul>
 *
 * @see mapping.MappingPackage#getMappingDefinition()
 * @model
 * @generated
 */
public interface MappingDefinition extends EObject {
	/**
	 * Returns the value of the '<em><b>Class Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link mapping.ClassMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Mappings</em>' containment reference list.
	 * @see mapping.MappingPackage#getMappingDefinition_ClassMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClassMapping> getClassMappings();

} // MappingDefinition
