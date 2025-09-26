/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.Connection#getFrom <em>From</em>}</li>
 *   <li>{@link analysiscouplinggraph.Connection#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getConnection()
 * @model
 * @generated
 */
public interface Connection extends EObject {
	/**
	 * Returns the value of the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' reference.
	 * @see #setFrom(ProvidedInterface)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getConnection_From()
	 * @model required="true"
	 * @generated
	 */
	ProvidedInterface getFrom();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.Connection#getFrom <em>From</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(ProvidedInterface value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' reference.
	 * @see #setTo(RequiredInterface)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getConnection_To()
	 * @model required="true"
	 * @generated
	 */
	RequiredInterface getTo();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.Connection#getTo <em>To</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(RequiredInterface value);

} // Connection
