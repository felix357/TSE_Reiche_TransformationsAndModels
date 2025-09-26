/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import uncertainty.UncertaintyLabel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Analysis Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.AnalysisComponent#getName <em>Name</em>}</li>
 *   <li>{@link analysiscouplinggraph.AnalysisComponent#getInputs <em>Inputs</em>}</li>
 *   <li>{@link analysiscouplinggraph.AnalysisComponent#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link analysiscouplinggraph.AnalysisComponent#getUncertaintyLabels <em>Uncertainty Labels</em>}</li>
 * </ul>
 *
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisComponent()
 * @model
 * @generated
 */
public interface AnalysisComponent extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisComponent_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.AnalysisComponent#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Inputs</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.RequiredInterface}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Inputs</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisComponent_Inputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredInterface> getInputs();

	/**
	 * Returns the value of the '<em><b>Outputs</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.ProvidedInterface}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Outputs</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisComponent_Outputs()
	 * @model containment="true"
	 * @generated
	 */
	EList<ProvidedInterface> getOutputs();

	/**
	 * Returns the value of the '<em><b>Uncertainty Labels</b></em>' containment reference list.
	 * The list contents are of type {@link uncertainty.UncertaintyLabel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Labels</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisComponent_UncertaintyLabels()
	 * @model containment="true"
	 * @generated
	 */
	EList<UncertaintyLabel> getUncertaintyLabels();

} // AnalysisComponent
