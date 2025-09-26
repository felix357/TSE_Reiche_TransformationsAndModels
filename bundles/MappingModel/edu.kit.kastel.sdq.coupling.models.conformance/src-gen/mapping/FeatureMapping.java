/**
 */
package mapping;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link mapping.FeatureMapping#getSourceFeatureURI <em>Source Feature URI</em>}</li>
 *   <li>{@link mapping.FeatureMapping#getTargetFeatureURI <em>Target Feature URI</em>}</li>
 *   <li>{@link mapping.FeatureMapping#getTransformationRule <em>Transformation Rule</em>}</li>
 * </ul>
 *
 * @see mapping.MappingPackage#getFeatureMapping()
 * @model
 * @generated
 */
public interface FeatureMapping extends EObject {
	/**
	 * Returns the value of the '<em><b>Source Feature URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source Feature URI</em>' attribute.
	 * @see #setSourceFeatureURI(String)
	 * @see mapping.MappingPackage#getFeatureMapping_SourceFeatureURI()
	 * @model required="true"
	 * @generated
	 */
	String getSourceFeatureURI();

	/**
	 * Sets the value of the '{@link mapping.FeatureMapping#getSourceFeatureURI <em>Source Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source Feature URI</em>' attribute.
	 * @see #getSourceFeatureURI()
	 * @generated
	 */
	void setSourceFeatureURI(String value);

	/**
	 * Returns the value of the '<em><b>Target Feature URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target Feature URI</em>' attribute.
	 * @see #setTargetFeatureURI(String)
	 * @see mapping.MappingPackage#getFeatureMapping_TargetFeatureURI()
	 * @model required="true"
	 * @generated
	 */
	String getTargetFeatureURI();

	/**
	 * Sets the value of the '{@link mapping.FeatureMapping#getTargetFeatureURI <em>Target Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Target Feature URI</em>' attribute.
	 * @see #getTargetFeatureURI()
	 * @generated
	 */
	void setTargetFeatureURI(String value);

	/**
	 * Returns the value of the '<em><b>Transformation Rule</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transformation Rule</em>' attribute.
	 * @see #setTransformationRule(String)
	 * @see mapping.MappingPackage#getFeatureMapping_TransformationRule()
	 * @model
	 * @generated
	 */
	String getTransformationRule();

	/**
	 * Sets the value of the '{@link mapping.FeatureMapping#getTransformationRule <em>Transformation Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transformation Rule</em>' attribute.
	 * @see #getTransformationRule()
	 * @generated
	 */
	void setTransformationRule(String value);

} // FeatureMapping
