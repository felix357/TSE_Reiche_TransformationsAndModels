/**
 */
package ReferenceMetamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Security Policy</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.SecurityPolicy#getSecurityCharacteristics <em>Security Characteristics</em>}</li>
 * </ul>
 *
 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getSecurityPolicy()
 * @model
 * @generated
 */
public interface SecurityPolicy extends EObject {
	/**
	 * Returns the value of the '<em><b>Security Characteristics</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.SecurityCharacteristic}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Characteristics</em>' reference list.
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getSecurityPolicy_SecurityCharacteristics()
	 * @model required="true"
	 * @generated
	 */
	EList<SecurityCharacteristic> getSecurityCharacteristics();

} // SecurityPolicy
