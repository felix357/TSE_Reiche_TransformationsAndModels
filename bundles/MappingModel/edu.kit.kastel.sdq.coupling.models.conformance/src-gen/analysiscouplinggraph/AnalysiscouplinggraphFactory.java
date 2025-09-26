/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage
 * @generated
 */
public interface AnalysiscouplinggraphFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnalysiscouplinggraphFactory eINSTANCE = analysiscouplinggraph.impl.AnalysiscouplinggraphFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Analysis Component</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Analysis Component</em>'.
	 * @generated
	 */
	AnalysisComponent createAnalysisComponent();

	/**
	 * Returns a new object of class '<em>Required Interface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Required Interface</em>'.
	 * @generated
	 */
	RequiredInterface createRequiredInterface();

	/**
	 * Returns a new object of class '<em>Provided Interface</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Provided Interface</em>'.
	 * @generated
	 */
	ProvidedInterface createProvidedInterface();

	/**
	 * Returns a new object of class '<em>Connection</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Connection</em>'.
	 * @generated
	 */
	Connection createConnection();

	/**
	 * Returns a new object of class '<em>External Source</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>External Source</em>'.
	 * @generated
	 */
	ExternalSource createExternalSource();

	/**
	 * Returns a new object of class '<em>Analysis Graph</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Analysis Graph</em>'.
	 * @generated
	 */
	AnalysisGraph createAnalysisGraph();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	AnalysiscouplinggraphPackage getAnalysiscouplinggraphPackage();

} //AnalysiscouplinggraphFactory
