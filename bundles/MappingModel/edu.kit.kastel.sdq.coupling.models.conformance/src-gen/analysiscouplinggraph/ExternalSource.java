/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>External Source</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.ExternalSource#getName <em>Name</em>}</li>
 *   <li>{@link analysiscouplinggraph.ExternalSource#getOutputs <em>Outputs</em>}</li>
 * </ul>
 *
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getExternalSource()
 * @model
 * @generated
 */
public interface ExternalSource extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getExternalSource_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.ExternalSource#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.ProvidedInterface}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getExternalSource_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedInterface> getOutputs();

} // ExternalSource
