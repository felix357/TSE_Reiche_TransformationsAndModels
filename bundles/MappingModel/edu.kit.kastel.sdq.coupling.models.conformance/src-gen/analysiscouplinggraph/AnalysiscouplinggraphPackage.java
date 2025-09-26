/**
 */
package analysiscouplinggraph;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see analysiscouplinggraph.AnalysiscouplinggraphFactory
 * @model kind="package"
 * @generated
 */
public interface AnalysiscouplinggraphPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "analysiscouplinggraph";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://example.org/analysiscouplinggraph";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "acg";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AnalysiscouplinggraphPackage eINSTANCE = analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl.init();

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.AnalysisComponentImpl <em>Analysis Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.AnalysisComponentImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getAnalysisComponent()
	 * @generated
	 */
	int ANALYSIS_COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Inputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT__INPUTS = 1;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT__OUTPUTS = 2;

	/**
	 * The feature id for the '<em><b>Uncertainty Labels</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT__UNCERTAINTY_LABELS = 3;

	/**
	 * The number of structural features of the '<em>Analysis Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Analysis Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_COMPONENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.RequiredInterfaceImpl <em>Required Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.RequiredInterfaceImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getRequiredInterface()
	 * @generated
	 */
	int REQUIRED_INTERFACE = 1;

	/**
	 * The feature id for the '<em><b>Uncertainty Label</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE__UNCERTAINTY_LABEL = 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE__OWNER = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE__ANNOTATIONS = 2;

	/**
	 * The feature id for the '<em><b>Mapping Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE__MAPPING_MODEL = 3;

	/**
	 * The number of structural features of the '<em>Required Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Required Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_INTERFACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl <em>Provided Interface</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.ProvidedInterfaceImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getProvidedInterface()
	 * @generated
	 */
	int PROVIDED_INTERFACE = 2;

	/**
	 * The feature id for the '<em><b>Uncertainty Label</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE__UNCERTAINTY_LABEL = 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE__OWNER = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE__ANNOTATIONS = 2;

	/**
	 * The feature id for the '<em><b>Mapping Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE__MAPPING_MODEL = 3;

	/**
	 * The number of structural features of the '<em>Provided Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Provided Interface</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_INTERFACE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.ConnectionImpl <em>Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.ConnectionImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getConnection()
	 * @generated
	 */
	int CONNECTION = 3;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION__TO = 1;

	/**
	 * The number of structural features of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONNECTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.ExternalSourceImpl <em>External Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.ExternalSourceImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getExternalSource()
	 * @generated
	 */
	int EXTERNAL_SOURCE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SOURCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Outputs</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SOURCE__OUTPUTS = 1;

	/**
	 * The number of structural features of the '<em>External Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SOURCE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>External Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_SOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link analysiscouplinggraph.impl.AnalysisGraphImpl <em>Analysis Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see analysiscouplinggraph.impl.AnalysisGraphImpl
	 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getAnalysisGraph()
	 * @generated
	 */
	int ANALYSIS_GRAPH = 5;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_GRAPH__COMPONENTS = 0;

	/**
	 * The feature id for the '<em><b>Connections</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_GRAPH__CONNECTIONS = 1;

	/**
	 * The feature id for the '<em><b>External Sources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_GRAPH__EXTERNAL_SOURCES = 2;

	/**
	 * The number of structural features of the '<em>Analysis Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_GRAPH_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Analysis Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANALYSIS_GRAPH_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.AnalysisComponent <em>Analysis Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Analysis Component</em>'.
	 * @see analysiscouplinggraph.AnalysisComponent
	 * @generated
	 */
	EClass getAnalysisComponent();

	/**
	 * Returns the meta object for the attribute '{@link analysiscouplinggraph.AnalysisComponent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see analysiscouplinggraph.AnalysisComponent#getName()
	 * @see #getAnalysisComponent()
	 * @generated
	 */
	EAttribute getAnalysisComponent_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisComponent#getInputs <em>Inputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Inputs</em>'.
	 * @see analysiscouplinggraph.AnalysisComponent#getInputs()
	 * @see #getAnalysisComponent()
	 * @generated
	 */
	EReference getAnalysisComponent_Inputs();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisComponent#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see analysiscouplinggraph.AnalysisComponent#getOutputs()
	 * @see #getAnalysisComponent()
	 * @generated
	 */
	EReference getAnalysisComponent_Outputs();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisComponent#getUncertaintyLabels <em>Uncertainty Labels</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncertainty Labels</em>'.
	 * @see analysiscouplinggraph.AnalysisComponent#getUncertaintyLabels()
	 * @see #getAnalysisComponent()
	 * @generated
	 */
	EReference getAnalysisComponent_UncertaintyLabels();

	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.RequiredInterface <em>Required Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Interface</em>'.
	 * @see analysiscouplinggraph.RequiredInterface
	 * @generated
	 */
	EClass getRequiredInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.RequiredInterface#getUncertaintyLabel <em>Uncertainty Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncertainty Label</em>'.
	 * @see analysiscouplinggraph.RequiredInterface#getUncertaintyLabel()
	 * @see #getRequiredInterface()
	 * @generated
	 */
	EReference getRequiredInterface_UncertaintyLabel();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.RequiredInterface#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see analysiscouplinggraph.RequiredInterface#getOwner()
	 * @see #getRequiredInterface()
	 * @generated
	 */
	EReference getRequiredInterface_Owner();

	/**
	 * Returns the meta object for the reference list '{@link analysiscouplinggraph.RequiredInterface#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotations</em>'.
	 * @see analysiscouplinggraph.RequiredInterface#getAnnotations()
	 * @see #getRequiredInterface()
	 * @generated
	 */
	EReference getRequiredInterface_Annotations();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.RequiredInterface#getMappingModel <em>Mapping Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Model</em>'.
	 * @see analysiscouplinggraph.RequiredInterface#getMappingModel()
	 * @see #getRequiredInterface()
	 * @generated
	 */
	EReference getRequiredInterface_MappingModel();

	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.ProvidedInterface <em>Provided Interface</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Interface</em>'.
	 * @see analysiscouplinggraph.ProvidedInterface
	 * @generated
	 */
	EClass getProvidedInterface();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.ProvidedInterface#getUncertaintyLabel <em>Uncertainty Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uncertainty Label</em>'.
	 * @see analysiscouplinggraph.ProvidedInterface#getUncertaintyLabel()
	 * @see #getProvidedInterface()
	 * @generated
	 */
	EReference getProvidedInterface_UncertaintyLabel();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.ProvidedInterface#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see analysiscouplinggraph.ProvidedInterface#getOwner()
	 * @see #getProvidedInterface()
	 * @generated
	 */
	EReference getProvidedInterface_Owner();

	/**
	 * Returns the meta object for the reference list '{@link analysiscouplinggraph.ProvidedInterface#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotations</em>'.
	 * @see analysiscouplinggraph.ProvidedInterface#getAnnotations()
	 * @see #getProvidedInterface()
	 * @generated
	 */
	EReference getProvidedInterface_Annotations();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.ProvidedInterface#getMappingModel <em>Mapping Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Mapping Model</em>'.
	 * @see analysiscouplinggraph.ProvidedInterface#getMappingModel()
	 * @see #getProvidedInterface()
	 * @generated
	 */
	EReference getProvidedInterface_MappingModel();

	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.Connection <em>Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Connection</em>'.
	 * @see analysiscouplinggraph.Connection
	 * @generated
	 */
	EClass getConnection();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.Connection#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see analysiscouplinggraph.Connection#getFrom()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_From();

	/**
	 * Returns the meta object for the reference '{@link analysiscouplinggraph.Connection#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see analysiscouplinggraph.Connection#getTo()
	 * @see #getConnection()
	 * @generated
	 */
	EReference getConnection_To();

	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.ExternalSource <em>External Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Source</em>'.
	 * @see analysiscouplinggraph.ExternalSource
	 * @generated
	 */
	EClass getExternalSource();

	/**
	 * Returns the meta object for the attribute '{@link analysiscouplinggraph.ExternalSource#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see analysiscouplinggraph.ExternalSource#getName()
	 * @see #getExternalSource()
	 * @generated
	 */
	EAttribute getExternalSource_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.ExternalSource#getOutputs <em>Outputs</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Outputs</em>'.
	 * @see analysiscouplinggraph.ExternalSource#getOutputs()
	 * @see #getExternalSource()
	 * @generated
	 */
	EReference getExternalSource_Outputs();

	/**
	 * Returns the meta object for class '{@link analysiscouplinggraph.AnalysisGraph <em>Analysis Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Analysis Graph</em>'.
	 * @see analysiscouplinggraph.AnalysisGraph
	 * @generated
	 */
	EClass getAnalysisGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisGraph#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see analysiscouplinggraph.AnalysisGraph#getComponents()
	 * @see #getAnalysisGraph()
	 * @generated
	 */
	EReference getAnalysisGraph_Components();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisGraph#getConnections <em>Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Connections</em>'.
	 * @see analysiscouplinggraph.AnalysisGraph#getConnections()
	 * @see #getAnalysisGraph()
	 * @generated
	 */
	EReference getAnalysisGraph_Connections();

	/**
	 * Returns the meta object for the containment reference list '{@link analysiscouplinggraph.AnalysisGraph#getExternalSources <em>External Sources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>External Sources</em>'.
	 * @see analysiscouplinggraph.AnalysisGraph#getExternalSources()
	 * @see #getAnalysisGraph()
	 * @generated
	 */
	EReference getAnalysisGraph_ExternalSources();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AnalysiscouplinggraphFactory getAnalysiscouplinggraphFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.AnalysisComponentImpl <em>Analysis Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.AnalysisComponentImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getAnalysisComponent()
		 * @generated
		 */
		EClass ANALYSIS_COMPONENT = eINSTANCE.getAnalysisComponent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANALYSIS_COMPONENT__NAME = eINSTANCE.getAnalysisComponent_Name();

		/**
		 * The meta object literal for the '<em><b>Inputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_COMPONENT__INPUTS = eINSTANCE.getAnalysisComponent_Inputs();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_COMPONENT__OUTPUTS = eINSTANCE.getAnalysisComponent_Outputs();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Labels</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_COMPONENT__UNCERTAINTY_LABELS = eINSTANCE.getAnalysisComponent_UncertaintyLabels();

		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.RequiredInterfaceImpl <em>Required Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.RequiredInterfaceImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getRequiredInterface()
		 * @generated
		 */
		EClass REQUIRED_INTERFACE = eINSTANCE.getRequiredInterface();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Label</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_INTERFACE__UNCERTAINTY_LABEL = eINSTANCE.getRequiredInterface_UncertaintyLabel();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_INTERFACE__OWNER = eINSTANCE.getRequiredInterface_Owner();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_INTERFACE__ANNOTATIONS = eINSTANCE.getRequiredInterface_Annotations();

		/**
		 * The meta object literal for the '<em><b>Mapping Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_INTERFACE__MAPPING_MODEL = eINSTANCE.getRequiredInterface_MappingModel();

		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl <em>Provided Interface</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.ProvidedInterfaceImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getProvidedInterface()
		 * @generated
		 */
		EClass PROVIDED_INTERFACE = eINSTANCE.getProvidedInterface();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Label</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDED_INTERFACE__UNCERTAINTY_LABEL = eINSTANCE.getProvidedInterface_UncertaintyLabel();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDED_INTERFACE__OWNER = eINSTANCE.getProvidedInterface_Owner();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDED_INTERFACE__ANNOTATIONS = eINSTANCE.getProvidedInterface_Annotations();

		/**
		 * The meta object literal for the '<em><b>Mapping Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDED_INTERFACE__MAPPING_MODEL = eINSTANCE.getProvidedInterface_MappingModel();

		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.ConnectionImpl <em>Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.ConnectionImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getConnection()
		 * @generated
		 */
		EClass CONNECTION = eINSTANCE.getConnection();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__FROM = eINSTANCE.getConnection_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONNECTION__TO = eINSTANCE.getConnection_To();

		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.ExternalSourceImpl <em>External Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.ExternalSourceImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getExternalSource()
		 * @generated
		 */
		EClass EXTERNAL_SOURCE = eINSTANCE.getExternalSource();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_SOURCE__NAME = eINSTANCE.getExternalSource_Name();

		/**
		 * The meta object literal for the '<em><b>Outputs</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_SOURCE__OUTPUTS = eINSTANCE.getExternalSource_Outputs();

		/**
		 * The meta object literal for the '{@link analysiscouplinggraph.impl.AnalysisGraphImpl <em>Analysis Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see analysiscouplinggraph.impl.AnalysisGraphImpl
		 * @see analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl#getAnalysisGraph()
		 * @generated
		 */
		EClass ANALYSIS_GRAPH = eINSTANCE.getAnalysisGraph();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_GRAPH__COMPONENTS = eINSTANCE.getAnalysisGraph_Components();

		/**
		 * The meta object literal for the '<em><b>Connections</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_GRAPH__CONNECTIONS = eINSTANCE.getAnalysisGraph_Connections();

		/**
		 * The meta object literal for the '<em><b>External Sources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANALYSIS_GRAPH__EXTERNAL_SOURCES = eINSTANCE.getAnalysisGraph_ExternalSources();

	}

} //AnalysiscouplinggraphPackage
