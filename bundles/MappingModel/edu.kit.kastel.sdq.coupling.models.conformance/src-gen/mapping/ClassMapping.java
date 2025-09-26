/**
 */
package mapping;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Class Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mapping.ClassMapping#getSourceClass <em>Source Class</em>}</li>
 *   <li>{@link mapping.ClassMapping#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link mapping.ClassMapping#getFeatureMappings <em>Feature Mappings</em>}</li>
 * </ul>
 *
 * @see mapping.MappingPackage#getClassMapping()
 * @model
 * @generated
 */
public interface ClassMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Class</em>' reference.
	 * @see #setSourceClass(EClass)
	 * @see mapping.MappingPackage#getClassMapping_SourceClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getSourceClass();

	/**
	 * Sets the value of the '{@link mapping.ClassMapping#getSourceClass <em>Source Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Class</em>' reference.
	 * @see #getSourceClass()
	 * @generated
	 */
	void setSourceClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Target Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Class</em>' reference.
	 * @see #setTargetClass(EClass)
	 * @see mapping.MappingPackage#getClassMapping_TargetClass()
	 * @model required="true"
	 * @generated
	 */
	EClass getTargetClass();

	/**
	 * Sets the value of the '{@link mapping.ClassMapping#getTargetClass <em>Target Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Class</em>' reference.
	 * @see #getTargetClass()
	 * @generated
	 */
	void setTargetClass(EClass value);

	/**
	 * Returns the value of the '<em><b>Feature Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link mapping.FeatureMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Feature Mappings</em>' containment reference list.
	 * @see mapping.MappingPackage#getClassMapping_FeatureMappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<FeatureMapping> getFeatureMappings();

} // ClassMapping
