/**
 */
package uncertainty;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Relation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uncertainty.Relation#getKey <em>Key</em>}</li>
 *   <li>{@link uncertainty.Relation#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see uncertainty.UncertaintyPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends EObject {
	/**
	 * Returns the value of the '<em><b>Key</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.UncertaintySource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Key</em>' attribute.
	 * @see uncertainty.UncertaintySource
	 * @see #setKey(UncertaintySource)
	 * @see uncertainty.UncertaintyPackage#getRelation_Key()
	 * @model
	 * @generated
	 */
	UncertaintySource getKey();

	/**
	 * Sets the value of the '{@link uncertainty.Relation#getKey <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Key</em>' attribute.
	 * @see uncertainty.UncertaintySource
	 * @see #getKey()
	 * @generated
	 */
	void setKey(UncertaintySource value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see uncertainty.UncertaintyPackage#getRelation_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link uncertainty.Relation#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

} // Relation
