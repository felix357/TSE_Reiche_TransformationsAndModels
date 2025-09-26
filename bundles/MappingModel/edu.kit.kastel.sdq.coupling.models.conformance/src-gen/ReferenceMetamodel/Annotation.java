/**
 */
package ReferenceMetamodel;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.Annotation#getAnnotates <em>Annotates</em>}</li>
 *   <li>{@link ReferenceMetamodel.Annotation#getCharacteristics <em>Characteristics</em>}</li>
 * </ul>
 *
 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getAnnotation()
 * @model
 * @generated
 */
public interface Annotation extends EObject {
	/**
	 * Returns the value of the '<em><b>Annotates</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotates</em>' reference.
	 * @see #setAnnotates(SystemElement)
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getAnnotation_Annotates()
	 * @model required="true"
	 * @generated
	 */
	SystemElement getAnnotates();

	/**
	 * Sets the value of the '{@link ReferenceMetamodel.Annotation#getAnnotates <em>Annotates</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Annotates</em>' reference.
	 * @see #getAnnotates()
	 * @generated
	 */
	void setAnnotates(SystemElement value);

	/**
	 * Returns the value of the '<em><b>Characteristics</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.SecurityCharacteristic}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Characteristics</em>' reference list.
	 * @see ReferenceMetamodel.ReferenceMetamodelPackage#getAnnotation_Characteristics()
	 * @model required="true"
	 * @generated
	 */
	EList<SecurityCharacteristic> getCharacteristics();

} // Annotation
