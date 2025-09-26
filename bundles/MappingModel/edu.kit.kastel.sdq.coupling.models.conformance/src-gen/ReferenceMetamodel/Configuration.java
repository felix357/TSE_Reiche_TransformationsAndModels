/**
 */
package ReferenceMetamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.Configuration#getSecurityPolicy <em>Security Policy</em>}</li>
 *   <li>{@link ReferenceMetamodel.Configuration#getSecurityCharacteristics <em>Security Characteristics</em>}</li>
 *   <li>{@link ReferenceMetamodel.Configuration#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link ReferenceMetamodel.Configuration#getSystemElements <em>System Elements</em>}</li>
 * </ul>
 *
 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getConfiguration()
 * @model
 * @generated
 */
public interface Configuration extends EObject {
	/**
	 * Returns the value of the '<em><b>Security Policy</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Policy</em>' reference.
	 * @see #setSecurityPolicy(SecurityPolicy)
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getConfiguration_SecurityPolicy()
	 * @model required="true"
	 * @generated
	 */
	SecurityPolicy getSecurityPolicy();

	/**
	 * Sets the value of the '{@link ReferenceMetamodel.Configuration#getSecurityPolicy <em>Security Policy</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Policy</em>' reference.
	 * @see #getSecurityPolicy()
	 * @generated
	 */
	void setSecurityPolicy(SecurityPolicy value);

	/**
	 * Returns the value of the '<em><b>Security Characteristics</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.SecurityCharacteristic}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Characteristics</em>' reference list.
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getConfiguration_SecurityCharacteristics()
	 * @model required="true"
	 * @generated
	 */
	EList<SecurityCharacteristic> getSecurityCharacteristics();

	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' reference list.
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getConfiguration_Annotations()
	 * @model required="true"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

	/**
	 * Returns the value of the '<em><b>System Elements</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.SystemElement}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>System Elements</em>' reference list.
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getConfiguration_SystemElements()
	 * @model required="true"
	 * @generated
	 */
	EList<SystemElement> getSystemElements();

} // Configuration
