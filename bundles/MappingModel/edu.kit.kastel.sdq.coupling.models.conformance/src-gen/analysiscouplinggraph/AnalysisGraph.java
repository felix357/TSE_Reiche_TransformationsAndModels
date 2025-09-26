/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Analysis Graph</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.AnalysisGraph#getComponents <em>Components</em>}</li>
 *   <li>{@link analysiscouplinggraph.AnalysisGraph#getConnections <em>Connections</em>}</li>
 *   <li>{@link analysiscouplinggraph.AnalysisGraph#getExternalSources <em>External Sources</em>}</li>
 * </ul>
 *
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisGraph()
 * @model
 * @generated
 */
public interface AnalysisGraph extends EObject {
	/**
	 * Returns the value of the '<em><b>Components</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.AnalysisComponent}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Components</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisGraph_Components()
	 * @model containment="true"
	 * @generated
	 */
	EList<AnalysisComponent> getComponents();

	/**
	 * Returns the value of the '<em><b>Connections</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.Connection}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Connections</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisGraph_Connections()
	 * @model containment="true"
	 * @generated
	 */
	EList<Connection> getConnections();

	/**
	 * Returns the value of the '<em><b>External Sources</b></em>' containment reference list.
	 * The list contents are of type {@link analysiscouplinggraph.ExternalSource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Sources</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getAnalysisGraph_ExternalSources()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExternalSource> getExternalSources();

} // AnalysisGraph
