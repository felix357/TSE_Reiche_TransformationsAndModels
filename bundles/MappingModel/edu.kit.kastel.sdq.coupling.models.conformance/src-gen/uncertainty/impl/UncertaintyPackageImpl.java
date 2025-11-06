/**
 */
package uncertainty.impl;

import ReferenceMetamodel.ReferenceMetamodelPackage;

import ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl;

import analysiscouplinggraph.AnalysiscouplinggraphPackage;

import analysiscouplinggraph.impl.AnalysiscouplinggraphPackageImpl;

import mapping.MappingPackage;

import mapping.impl.MappingPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import uncertainty.ImpactOnAccuracy;
import uncertainty.Location;
import uncertainty.Manageability;
import uncertainty.OriginType;
import uncertainty.ReducibilityByADD;
import uncertainty.Relation;
import uncertainty.ResolutionTime;
import uncertainty.SeverityOfImpact;
import uncertainty.UncertaintyFactory;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyPackage;
import uncertainty.UncertaintyScenario;
import uncertainty.UncertaintySource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyPackageImpl extends EPackageImpl implements UncertaintyPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uncertaintyLabelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass relationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum uncertaintySourceEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum severityOfImpactEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum resolutionTimeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum reducibilityByADDEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum originTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum manageabilityEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum locationEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum impactOnAccuracyEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum uncertaintyScenarioEEnum = null;

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
	 * @see uncertainty.UncertaintyPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UncertaintyPackageImpl() {
		super(eNS_URI, UncertaintyFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link UncertaintyPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UncertaintyPackage init() {
		if (isInited) return (UncertaintyPackage)EPackage.Registry.INSTANCE.getEPackage(UncertaintyPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredUncertaintyPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		UncertaintyPackageImpl theUncertaintyPackage = registeredUncertaintyPackage instanceof UncertaintyPackageImpl ? (UncertaintyPackageImpl)registeredUncertaintyPackage : new UncertaintyPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(AnalysiscouplinggraphPackage.eNS_URI);
		AnalysiscouplinggraphPackageImpl theAnalysiscouplinggraphPackage = (AnalysiscouplinggraphPackageImpl)(registeredPackage instanceof AnalysiscouplinggraphPackageImpl ? registeredPackage : AnalysiscouplinggraphPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ReferenceMetamodelPackage.eNS_URI);
		ReferenceMetamodelPackageImpl theReferenceMetamodelPackage = (ReferenceMetamodelPackageImpl)(registeredPackage instanceof ReferenceMetamodelPackageImpl ? registeredPackage : ReferenceMetamodelPackage.eINSTANCE);
		registeredPackage = EPackage.Registry.INSTANCE.getEPackage(MappingPackage.eNS_URI);
		MappingPackageImpl theMappingPackage = (MappingPackageImpl)(registeredPackage instanceof MappingPackageImpl ? registeredPackage : MappingPackage.eINSTANCE);

		// Create package meta-data objects
		theUncertaintyPackage.createPackageContents();
		theAnalysiscouplinggraphPackage.createPackageContents();
		theReferenceMetamodelPackage.createPackageContents();
		theMappingPackage.createPackageContents();

		// Initialize created meta-data
		theUncertaintyPackage.initializePackageContents();
		theAnalysiscouplinggraphPackage.initializePackageContents();
		theReferenceMetamodelPackage.initializePackageContents();
		theMappingPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUncertaintyPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UncertaintyPackage.eNS_URI, theUncertaintyPackage);
		return theUncertaintyPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getUncertaintyLabel() {
		return uncertaintyLabelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Source() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Severity() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_ResolutionTime() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Reducability() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_OriginType() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Manageability() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Location() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_Impact() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getUncertaintyLabel_Relations() {
		return (EReference)uncertaintyLabelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getUncertaintyLabel_UncertaintyScenario() {
		return (EAttribute)uncertaintyLabelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRelation() {
		return relationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelation_Key() {
		return (EAttribute)relationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getRelation_Value() {
		return (EAttribute)relationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getUncertaintySource() {
		return uncertaintySourceEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getSeverityOfImpact() {
		return severityOfImpactEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getResolutionTime() {
		return resolutionTimeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getReducibilityByADD() {
		return reducibilityByADDEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getOriginType() {
		return originTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getManageability() {
		return manageabilityEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getLocation() {
		return locationEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getImpactOnAccuracy() {
		return impactOnAccuracyEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EEnum getUncertaintyScenario() {
		return uncertaintyScenarioEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyFactory getUncertaintyFactory() {
		return (UncertaintyFactory)getEFactoryInstance();
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
		uncertaintyLabelEClass = createEClass(UNCERTAINTY_LABEL);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__SOURCE);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__SEVERITY);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__RESOLUTION_TIME);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__REDUCABILITY);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__ORIGIN_TYPE);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__MANAGEABILITY);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__LOCATION);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__IMPACT);
		createEReference(uncertaintyLabelEClass, UNCERTAINTY_LABEL__RELATIONS);
		createEAttribute(uncertaintyLabelEClass, UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO);

		relationEClass = createEClass(RELATION);
		createEAttribute(relationEClass, RELATION__KEY);
		createEAttribute(relationEClass, RELATION__VALUE);

		// Create enums
		uncertaintySourceEEnum = createEEnum(UNCERTAINTY_SOURCE);
		severityOfImpactEEnum = createEEnum(SEVERITY_OF_IMPACT);
		resolutionTimeEEnum = createEEnum(RESOLUTION_TIME);
		reducibilityByADDEEnum = createEEnum(REDUCIBILITY_BY_ADD);
		originTypeEEnum = createEEnum(ORIGIN_TYPE);
		manageabilityEEnum = createEEnum(MANAGEABILITY);
		locationEEnum = createEEnum(LOCATION);
		impactOnAccuracyEEnum = createEEnum(IMPACT_ON_ACCURACY);
		uncertaintyScenarioEEnum = createEEnum(UNCERTAINTY_SCENARIO);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(uncertaintyLabelEClass, UncertaintyLabel.class, "UncertaintyLabel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUncertaintyLabel_Source(), this.getUncertaintySource(), "source", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_Severity(), this.getSeverityOfImpact(), "severity", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_ResolutionTime(), this.getResolutionTime(), "resolutionTime", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_Reducability(), this.getReducibilityByADD(), "reducability", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_OriginType(), this.getOriginType(), "originType", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_Manageability(), this.getManageability(), "manageability", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_Location(), this.getLocation(), "location", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_Impact(), this.getImpactOnAccuracy(), "impact", null, 0, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUncertaintyLabel_Relations(), this.getRelation(), null, "relations", null, 0, -1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUncertaintyLabel_UncertaintyScenario(), this.getUncertaintyScenario(), "uncertaintyScenario", null, 1, 1, UncertaintyLabel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(relationEClass, Relation.class, "Relation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRelation_Key(), this.getUncertaintySource(), "key", null, 0, 1, Relation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRelation_Value(), ecorePackage.getEString(), "value", null, 0, 1, Relation.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(uncertaintySourceEEnum, UncertaintySource.class, "UncertaintySource");
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.INPUT_DATA_INDUCED);
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.METHODOLOGY_INDUCED);
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.MODELING_INDUCED);
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.OUTPUT_DATA_INDUCED);
		addEEnumLiteral(uncertaintySourceEEnum, UncertaintySource.ORCHESTRATION_DECISION_INDUCED);

		initEEnum(severityOfImpactEEnum, SeverityOfImpact.class, "SeverityOfImpact");
		addEEnumLiteral(severityOfImpactEEnum, SeverityOfImpact.NONE);
		addEEnumLiteral(severityOfImpactEEnum, SeverityOfImpact.LOW);
		addEEnumLiteral(severityOfImpactEEnum, SeverityOfImpact.HIGH);

		initEEnum(resolutionTimeEEnum, ResolutionTime.class, "ResolutionTime");
		addEEnumLiteral(resolutionTimeEEnum, ResolutionTime.REQUIREMENTS_TIME);
		addEEnumLiteral(resolutionTimeEEnum, ResolutionTime.DESIGN_TIME);
		addEEnumLiteral(resolutionTimeEEnum, ResolutionTime.REALIZATION_TIME);
		addEEnumLiteral(resolutionTimeEEnum, ResolutionTime.RUNTIME);
		addEEnumLiteral(resolutionTimeEEnum, ResolutionTime.UNRESOLVABLE);

		initEEnum(reducibilityByADDEEnum, ReducibilityByADD.class, "ReducibilityByADD");
		addEEnumLiteral(reducibilityByADDEEnum, ReducibilityByADD.YES);
		addEEnumLiteral(reducibilityByADDEEnum, ReducibilityByADD.NO);

		initEEnum(originTypeEEnum, OriginType.class, "OriginType");
		addEEnumLiteral(originTypeEEnum, OriginType.SINGLE_ANALYSIS);
		addEEnumLiteral(originTypeEEnum, OriginType.COUPLING_INDUCED);

		initEEnum(manageabilityEEnum, Manageability.class, "Manageability");
		addEEnumLiteral(manageabilityEEnum, Manageability.FULLY_REDUCIBLE);
		addEEnumLiteral(manageabilityEEnum, Manageability.PARTIALLY_REDUCIBLE);
		addEEnumLiteral(manageabilityEEnum, Manageability.IRREDUCIBLE);

		initEEnum(locationEEnum, Location.class, "Location");
		addEEnumLiteral(locationEEnum, Location.EXTERNAL_INPUT);
		addEEnumLiteral(locationEEnum, Location.INTERNAL_ANALYSIS);
		addEEnumLiteral(locationEEnum, Location.COUPLING_OUTPUT);

		initEEnum(impactOnAccuracyEEnum, ImpactOnAccuracy.class, "ImpactOnAccuracy");
		addEEnumLiteral(impactOnAccuracyEEnum, ImpactOnAccuracy.DIRECT);
		addEEnumLiteral(impactOnAccuracyEEnum, ImpactOnAccuracy.INDIRECT);
		addEEnumLiteral(impactOnAccuracyEEnum, ImpactOnAccuracy.NONE);

		initEEnum(uncertaintyScenarioEEnum, UncertaintyScenario.class, "UncertaintyScenario");
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.IMPRECISE_INPUT_DATA);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.INCORRECT_INPUT_DATA);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.CORRECT_INPUT_DATA);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.SCENARIO_DEFINITION_INCORRECT);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.SCENARIO_DEFINITION_CORRECT);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.METHODOLOGY_ABSTRACTION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.METHODOLOGY_APPROXIMATION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.METHODOLOGY_OVER_SIMPLIFICATION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.METHODOLOGY_CORRECT);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.MODEL_UNDER_SPECIFICATION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.MODEL_ABSTRACTION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.MODEL_DISCREPANCY);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.MODEL_CORRECT);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.OUTPUT_ERROR);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.OUTPUT_IMPRECISION);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.OUTPUT_CORRECT);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.ORCHESTRATION_NOT_FINAL);
		addEEnumLiteral(uncertaintyScenarioEEnum, UncertaintyScenario.ORCHESTRATION_FINAL);

		// Create resource
		createResource(eNS_URI);
	}

} //UncertaintyPackageImpl
