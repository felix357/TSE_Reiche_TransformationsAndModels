/**
 */
package analysiscouplinggraph.impl;

import ReferenceMetamodel.ReferenceMetamodelPackage;

import ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.AnalysiscouplinggraphFactory;
import analysiscouplinggraph.AnalysiscouplinggraphPackage;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ExternalSource;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;

import mapping.MappingPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import uncertainty.UncertaintyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AnalysiscouplinggraphPackageImpl extends EPackageImpl implements AnalysiscouplinggraphPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass analysisComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredInterfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providedInterfaceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass connectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalSourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass analysisGraphEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AnalysiscouplinggraphPackageImpl() {
		super(eNS_URI, AnalysiscouplinggraphFactory.eINSTANCE);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>This method is used to initialize {@link AnalysiscouplinggraphPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AnalysiscouplinggraphPackage init() {
		if (isInited) return (AnalysiscouplinggraphPackage)EPackage.Registry.INSTANCE.getEPackage(AnalysiscouplinggraphPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredAnalysiscouplinggraphPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		AnalysiscouplinggraphPackageImpl theAnalysiscouplinggraphPackage = registeredAnalysiscouplinggraphPackage instanceof AnalysiscouplinggraphPackageImpl ? (AnalysiscouplinggraphPackageImpl)registeredAnalysiscouplinggraphPackage : new AnalysiscouplinggraphPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		MappingPackage.eINSTANCE.eClass();
		UncertaintyPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ReferenceMetamodelPackage.eNS_URI);
		ReferenceMetamodelPackageImpl theReferenceMetamodelPackage = (ReferenceMetamodelPackageImpl)(registeredPackage instanceof ReferenceMetamodelPackageImpl ? registeredPackage : ReferenceMetamodelPackage.eINSTANCE);

		// Create package meta-data objects
		theAnalysiscouplinggraphPackage.createPackageContents();
		theReferenceMetamodelPackage.createPackageContents();

		// Initialize created meta-data
		theAnalysiscouplinggraphPackage.initializePackageContents();
		theReferenceMetamodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAnalysiscouplinggraphPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AnalysiscouplinggraphPackage.eNS_URI, theAnalysiscouplinggraphPackage);
		return theAnalysiscouplinggraphPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnalysisComponent() {
		return analysisComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAnalysisComponent_Name() {
		return (EAttribute)analysisComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisComponent_Inputs() {
		return (EReference)analysisComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisComponent_Outputs() {
		return (EReference)analysisComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisComponent_UncertaintyLabels() {
		return (EReference)analysisComponentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRequiredInterface() {
		return requiredInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRequiredInterface_UncertaintyLabel() {
		return (EReference)requiredInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRequiredInterface_Owner() {
		return (EReference)requiredInterfaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRequiredInterface_Annotations() {
		return (EReference)requiredInterfaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRequiredInterface_MappingModel() {
		return (EReference)requiredInterfaceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getProvidedInterface() {
		return providedInterfaceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProvidedInterface_UncertaintyLabel() {
		return (EReference)providedInterfaceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProvidedInterface_Owner() {
		return (EReference)providedInterfaceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProvidedInterface_Annotations() {
		return (EReference)providedInterfaceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getProvidedInterface_MappingModel() {
		return (EReference)providedInterfaceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConnection() {
		return connectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnection_From() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConnection_To() {
		return (EReference)connectionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getExternalSource() {
		return externalSourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getExternalSource_Name() {
		return (EAttribute)externalSourceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getExternalSource_Outputs() {
		return (EReference)externalSourceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAnalysisGraph() {
		return analysisGraphEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisGraph_Components() {
		return (EReference)analysisGraphEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisGraph_Connections() {
		return (EReference)analysisGraphEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAnalysisGraph_ExternalSources() {
		return (EReference)analysisGraphEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnalysiscouplinggraphFactory getAnalysiscouplinggraphFactory() {
		return (AnalysiscouplinggraphFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		analysisComponentEClass = createEClass(ANALYSIS_COMPONENT);
		createEAttribute(analysisComponentEClass, ANALYSIS_COMPONENT__NAME);
		createEReference(analysisComponentEClass, ANALYSIS_COMPONENT__INPUTS);
		createEReference(analysisComponentEClass, ANALYSIS_COMPONENT__OUTPUTS);
		createEReference(analysisComponentEClass, ANALYSIS_COMPONENT__UNCERTAINTY_LABELS);

		requiredInterfaceEClass = createEClass(REQUIRED_INTERFACE);
		createEReference(requiredInterfaceEClass, REQUIRED_INTERFACE__UNCERTAINTY_LABEL);
		createEReference(requiredInterfaceEClass, REQUIRED_INTERFACE__OWNER);
		createEReference(requiredInterfaceEClass, REQUIRED_INTERFACE__ANNOTATIONS);
		createEReference(requiredInterfaceEClass, REQUIRED_INTERFACE__MAPPING_MODEL);

		providedInterfaceEClass = createEClass(PROVIDED_INTERFACE);
		createEReference(providedInterfaceEClass, PROVIDED_INTERFACE__UNCERTAINTY_LABEL);
		createEReference(providedInterfaceEClass, PROVIDED_INTERFACE__OWNER);
		createEReference(providedInterfaceEClass, PROVIDED_INTERFACE__ANNOTATIONS);
		createEReference(providedInterfaceEClass, PROVIDED_INTERFACE__MAPPING_MODEL);

		connectionEClass = createEClass(CONNECTION);
		createEReference(connectionEClass, CONNECTION__FROM);
		createEReference(connectionEClass, CONNECTION__TO);

		externalSourceEClass = createEClass(EXTERNAL_SOURCE);
		createEAttribute(externalSourceEClass, EXTERNAL_SOURCE__NAME);
		createEReference(externalSourceEClass, EXTERNAL_SOURCE__OUTPUTS);

		analysisGraphEClass = createEClass(ANALYSIS_GRAPH);
		createEReference(analysisGraphEClass, ANALYSIS_GRAPH__COMPONENTS);
		createEReference(analysisGraphEClass, ANALYSIS_GRAPH__CONNECTIONS);
		createEReference(analysisGraphEClass, ANALYSIS_GRAPH__EXTERNAL_SOURCES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		UncertaintyPackage theUncertaintyPackage = (UncertaintyPackage)EPackage.Registry.INSTANCE.getEPackage(UncertaintyPackage.eNS_URI);
		ReferenceMetamodelPackage theReferenceMetamodelPackage = (ReferenceMetamodelPackage)EPackage.Registry.INSTANCE.getEPackage(ReferenceMetamodelPackage.eNS_URI);
		MappingPackage theMappingPackage = (MappingPackage)EPackage.Registry.INSTANCE.getEPackage(MappingPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(analysisComponentEClass, AnalysisComponent.class, "AnalysisComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnalysisComponent_Name(), ecorePackage.getEString(), "name", null, 0, 1, AnalysisComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnalysisComponent_Inputs(), this.getRequiredInterface(), null, "inputs", null, 0, -1, AnalysisComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnalysisComponent_Outputs(), this.getProvidedInterface(), null, "outputs", null, 0, -1, AnalysisComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnalysisComponent_UncertaintyLabels(), theUncertaintyPackage.getUncertaintyLabel(), null, "uncertaintyLabels", null, 0, -1, AnalysisComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requiredInterfaceEClass, RequiredInterface.class, "RequiredInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRequiredInterface_UncertaintyLabel(), theUncertaintyPackage.getUncertaintyLabel(), null, "uncertaintyLabel", null, 0, -1, RequiredInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequiredInterface_Owner(), this.getAnalysisComponent(), null, "owner", null, 1, 1, RequiredInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequiredInterface_Annotations(), theReferenceMetamodelPackage.getAnnotation(), null, "annotations", null, 1, -1, RequiredInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequiredInterface_MappingModel(), theMappingPackage.getMappingDefinition(), null, "mappingModel", null, 1, 1, RequiredInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providedInterfaceEClass, ProvidedInterface.class, "ProvidedInterface", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProvidedInterface_UncertaintyLabel(), theUncertaintyPackage.getUncertaintyLabel(), null, "uncertaintyLabel", null, 0, -1, ProvidedInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProvidedInterface_Owner(), this.getAnalysisComponent(), null, "owner", null, 1, 1, ProvidedInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProvidedInterface_Annotations(), theReferenceMetamodelPackage.getAnnotation(), null, "annotations", null, 1, -1, ProvidedInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProvidedInterface_MappingModel(), theMappingPackage.getMappingDefinition(), null, "mappingModel", null, 1, 1, ProvidedInterface.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(connectionEClass, Connection.class, "Connection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConnection_From(), this.getProvidedInterface(), null, "from", null, 1, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConnection_To(), this.getRequiredInterface(), null, "to", null, 1, 1, Connection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(externalSourceEClass, ExternalSource.class, "ExternalSource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalSource_Name(), ecorePackage.getEString(), "name", null, 1, 1, ExternalSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getExternalSource_Outputs(), this.getProvidedInterface(), null, "outputs", null, 0, -1, ExternalSource.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(analysisGraphEClass, AnalysisGraph.class, "AnalysisGraph", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAnalysisGraph_Components(), this.getAnalysisComponent(), null, "components", null, 0, -1, AnalysisGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnalysisGraph_Connections(), this.getConnection(), null, "connections", null, 0, -1, AnalysisGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAnalysisGraph_ExternalSources(), this.getExternalSource(), null, "externalSources", null, 0, -1, AnalysisGraph.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //AnalysiscouplinggraphPackageImpl
