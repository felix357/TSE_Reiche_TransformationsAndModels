/**
 */
package uncertainty;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see uncertainty.UncertaintyFactory
 * @model kind="package"
 * @generated
 */
public interface UncertaintyPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "uncertainty";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://extendeddataflow.uncertainty/uncertainty";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "uncertainty";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UncertaintyPackage eINSTANCE = uncertainty.impl.UncertaintyPackageImpl.init();

	/**
	 * The meta object id for the '{@link uncertainty.impl.UncertaintyLabelImpl <em>Label</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.impl.UncertaintyLabelImpl
	 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintyLabel()
	 * @generated
	 */
	int UNCERTAINTY_LABEL = 0;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__SEVERITY = 1;

	/**
	 * The feature id for the '<em><b>Resolution Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__RESOLUTION_TIME = 2;

	/**
	 * The feature id for the '<em><b>Reducability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__REDUCABILITY = 3;

	/**
	 * The feature id for the '<em><b>Origin Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__ORIGIN_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Manageability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__MANAGEABILITY = 5;

	/**
	 * The feature id for the '<em><b>Location</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__LOCATION = 6;

	/**
	 * The feature id for the '<em><b>Impact</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__IMPACT = 7;

	/**
	 * The feature id for the '<em><b>Relations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__RELATIONS = 8;

	/**
	 * The feature id for the '<em><b>Uncertainty Scenario</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO = 9;

	/**
	 * The number of structural features of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Label</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNCERTAINTY_LABEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link uncertainty.impl.RelationImpl <em>Relation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.impl.RelationImpl
	 * @see uncertainty.impl.UncertaintyPackageImpl#getRelation()
	 * @generated
	 */
	int RELATION = 1;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Relation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RELATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link uncertainty.UncertaintySource <em>Source</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.UncertaintySource
	 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintySource()
	 * @generated
	 */
	int UNCERTAINTY_SOURCE = 2;

	/**
	 * The meta object id for the '{@link uncertainty.SeverityOfImpact <em>Severity Of Impact</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.SeverityOfImpact
	 * @see uncertainty.impl.UncertaintyPackageImpl#getSeverityOfImpact()
	 * @generated
	 */
	int SEVERITY_OF_IMPACT = 3;

	/**
	 * The meta object id for the '{@link uncertainty.ResolutionTime <em>Resolution Time</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.ResolutionTime
	 * @see uncertainty.impl.UncertaintyPackageImpl#getResolutionTime()
	 * @generated
	 */
	int RESOLUTION_TIME = 4;

	/**
	 * The meta object id for the '{@link uncertainty.ReducibilityByADD <em>Reducibility By ADD</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.ReducibilityByADD
	 * @see uncertainty.impl.UncertaintyPackageImpl#getReducibilityByADD()
	 * @generated
	 */
	int REDUCIBILITY_BY_ADD = 5;

	/**
	 * The meta object id for the '{@link uncertainty.OriginType <em>Origin Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.OriginType
	 * @see uncertainty.impl.UncertaintyPackageImpl#getOriginType()
	 * @generated
	 */
	int ORIGIN_TYPE = 6;

	/**
	 * The meta object id for the '{@link uncertainty.Manageability <em>Manageability</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.Manageability
	 * @see uncertainty.impl.UncertaintyPackageImpl#getManageability()
	 * @generated
	 */
	int MANAGEABILITY = 7;

	/**
	 * The meta object id for the '{@link uncertainty.Location <em>Location</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.Location
	 * @see uncertainty.impl.UncertaintyPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 8;

	/**
	 * The meta object id for the '{@link uncertainty.ImpactOnAccuracy <em>Impact On Accuracy</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.ImpactOnAccuracy
	 * @see uncertainty.impl.UncertaintyPackageImpl#getImpactOnAccuracy()
	 * @generated
	 */
	int IMPACT_ON_ACCURACY = 9;

	/**
	 * The meta object id for the '{@link uncertainty.UncertaintyScenario <em>Scenario</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see uncertainty.UncertaintyScenario
	 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintyScenario()
	 * @generated
	 */
	int UNCERTAINTY_SCENARIO = 10;


	/**
	 * Returns the meta object for class '{@link uncertainty.UncertaintyLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Label</em>'.
	 * @see uncertainty.UncertaintyLabel
	 * @generated
	 */
	EClass getUncertaintyLabel();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see uncertainty.UncertaintyLabel#getSource()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Source();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see uncertainty.UncertaintyLabel#getSeverity()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Severity();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getResolutionTime <em>Resolution Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resolution Time</em>'.
	 * @see uncertainty.UncertaintyLabel#getResolutionTime()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_ResolutionTime();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getReducability <em>Reducability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Reducability</em>'.
	 * @see uncertainty.UncertaintyLabel#getReducability()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Reducability();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getOriginType <em>Origin Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Origin Type</em>'.
	 * @see uncertainty.UncertaintyLabel#getOriginType()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_OriginType();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getManageability <em>Manageability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Manageability</em>'.
	 * @see uncertainty.UncertaintyLabel#getManageability()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Manageability();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location</em>'.
	 * @see uncertainty.UncertaintyLabel#getLocation()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Location();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getImpact <em>Impact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Impact</em>'.
	 * @see uncertainty.UncertaintyLabel#getImpact()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_Impact();

	/**
	 * Returns the meta object for the reference list '{@link uncertainty.UncertaintyLabel#getRelations <em>Relations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Relations</em>'.
	 * @see uncertainty.UncertaintyLabel#getRelations()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EReference getUncertaintyLabel_Relations();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.UncertaintyLabel#getUncertaintyScenario <em>Uncertainty Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Uncertainty Scenario</em>'.
	 * @see uncertainty.UncertaintyLabel#getUncertaintyScenario()
	 * @see #getUncertaintyLabel()
	 * @generated
	 */
	EAttribute getUncertaintyLabel_UncertaintyScenario();

	/**
	 * Returns the meta object for class '{@link uncertainty.Relation <em>Relation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Relation</em>'.
	 * @see uncertainty.Relation
	 * @generated
	 */
	EClass getRelation();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.Relation#getKey <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see uncertainty.Relation#getKey()
	 * @see #getRelation()
	 * @generated
	 */
	EAttribute getRelation_Key();

	/**
	 * Returns the meta object for the attribute '{@link uncertainty.Relation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see uncertainty.Relation#getValue()
	 * @see #getRelation()
	 * @generated
	 */
	EAttribute getRelation_Value();

	/**
	 * Returns the meta object for enum '{@link uncertainty.UncertaintySource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Source</em>'.
	 * @see uncertainty.UncertaintySource
	 * @generated
	 */
	EEnum getUncertaintySource();

	/**
	 * Returns the meta object for enum '{@link uncertainty.SeverityOfImpact <em>Severity Of Impact</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity Of Impact</em>'.
	 * @see uncertainty.SeverityOfImpact
	 * @generated
	 */
	EEnum getSeverityOfImpact();

	/**
	 * Returns the meta object for enum '{@link uncertainty.ResolutionTime <em>Resolution Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Resolution Time</em>'.
	 * @see uncertainty.ResolutionTime
	 * @generated
	 */
	EEnum getResolutionTime();

	/**
	 * Returns the meta object for enum '{@link uncertainty.ReducibilityByADD <em>Reducibility By ADD</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Reducibility By ADD</em>'.
	 * @see uncertainty.ReducibilityByADD
	 * @generated
	 */
	EEnum getReducibilityByADD();

	/**
	 * Returns the meta object for enum '{@link uncertainty.OriginType <em>Origin Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Origin Type</em>'.
	 * @see uncertainty.OriginType
	 * @generated
	 */
	EEnum getOriginType();

	/**
	 * Returns the meta object for enum '{@link uncertainty.Manageability <em>Manageability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Manageability</em>'.
	 * @see uncertainty.Manageability
	 * @generated
	 */
	EEnum getManageability();

	/**
	 * Returns the meta object for enum '{@link uncertainty.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Location</em>'.
	 * @see uncertainty.Location
	 * @generated
	 */
	EEnum getLocation();

	/**
	 * Returns the meta object for enum '{@link uncertainty.ImpactOnAccuracy <em>Impact On Accuracy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Impact On Accuracy</em>'.
	 * @see uncertainty.ImpactOnAccuracy
	 * @generated
	 */
	EEnum getImpactOnAccuracy();

	/**
	 * Returns the meta object for enum '{@link uncertainty.UncertaintyScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scenario</em>'.
	 * @see uncertainty.UncertaintyScenario
	 * @generated
	 */
	EEnum getUncertaintyScenario();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UncertaintyFactory getUncertaintyFactory();

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
		 * The meta object literal for the '{@link uncertainty.impl.UncertaintyLabelImpl <em>Label</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.impl.UncertaintyLabelImpl
		 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintyLabel()
		 * @generated
		 */
		EClass UNCERTAINTY_LABEL = eINSTANCE.getUncertaintyLabel();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__SOURCE = eINSTANCE.getUncertaintyLabel_Source();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__SEVERITY = eINSTANCE.getUncertaintyLabel_Severity();

		/**
		 * The meta object literal for the '<em><b>Resolution Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__RESOLUTION_TIME = eINSTANCE.getUncertaintyLabel_ResolutionTime();

		/**
		 * The meta object literal for the '<em><b>Reducability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__REDUCABILITY = eINSTANCE.getUncertaintyLabel_Reducability();

		/**
		 * The meta object literal for the '<em><b>Origin Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__ORIGIN_TYPE = eINSTANCE.getUncertaintyLabel_OriginType();

		/**
		 * The meta object literal for the '<em><b>Manageability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__MANAGEABILITY = eINSTANCE.getUncertaintyLabel_Manageability();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__LOCATION = eINSTANCE.getUncertaintyLabel_Location();

		/**
		 * The meta object literal for the '<em><b>Impact</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__IMPACT = eINSTANCE.getUncertaintyLabel_Impact();

		/**
		 * The meta object literal for the '<em><b>Relations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNCERTAINTY_LABEL__RELATIONS = eINSTANCE.getUncertaintyLabel_Relations();

		/**
		 * The meta object literal for the '<em><b>Uncertainty Scenario</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO = eINSTANCE.getUncertaintyLabel_UncertaintyScenario();

		/**
		 * The meta object literal for the '{@link uncertainty.impl.RelationImpl <em>Relation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.impl.RelationImpl
		 * @see uncertainty.impl.UncertaintyPackageImpl#getRelation()
		 * @generated
		 */
		EClass RELATION = eINSTANCE.getRelation();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATION__KEY = eINSTANCE.getRelation_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RELATION__VALUE = eINSTANCE.getRelation_Value();

		/**
		 * The meta object literal for the '{@link uncertainty.UncertaintySource <em>Source</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.UncertaintySource
		 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintySource()
		 * @generated
		 */
		EEnum UNCERTAINTY_SOURCE = eINSTANCE.getUncertaintySource();

		/**
		 * The meta object literal for the '{@link uncertainty.SeverityOfImpact <em>Severity Of Impact</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.SeverityOfImpact
		 * @see uncertainty.impl.UncertaintyPackageImpl#getSeverityOfImpact()
		 * @generated
		 */
		EEnum SEVERITY_OF_IMPACT = eINSTANCE.getSeverityOfImpact();

		/**
		 * The meta object literal for the '{@link uncertainty.ResolutionTime <em>Resolution Time</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.ResolutionTime
		 * @see uncertainty.impl.UncertaintyPackageImpl#getResolutionTime()
		 * @generated
		 */
		EEnum RESOLUTION_TIME = eINSTANCE.getResolutionTime();

		/**
		 * The meta object literal for the '{@link uncertainty.ReducibilityByADD <em>Reducibility By ADD</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.ReducibilityByADD
		 * @see uncertainty.impl.UncertaintyPackageImpl#getReducibilityByADD()
		 * @generated
		 */
		EEnum REDUCIBILITY_BY_ADD = eINSTANCE.getReducibilityByADD();

		/**
		 * The meta object literal for the '{@link uncertainty.OriginType <em>Origin Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.OriginType
		 * @see uncertainty.impl.UncertaintyPackageImpl#getOriginType()
		 * @generated
		 */
		EEnum ORIGIN_TYPE = eINSTANCE.getOriginType();

		/**
		 * The meta object literal for the '{@link uncertainty.Manageability <em>Manageability</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.Manageability
		 * @see uncertainty.impl.UncertaintyPackageImpl#getManageability()
		 * @generated
		 */
		EEnum MANAGEABILITY = eINSTANCE.getManageability();

		/**
		 * The meta object literal for the '{@link uncertainty.Location <em>Location</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.Location
		 * @see uncertainty.impl.UncertaintyPackageImpl#getLocation()
		 * @generated
		 */
		EEnum LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '{@link uncertainty.ImpactOnAccuracy <em>Impact On Accuracy</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.ImpactOnAccuracy
		 * @see uncertainty.impl.UncertaintyPackageImpl#getImpactOnAccuracy()
		 * @generated
		 */
		EEnum IMPACT_ON_ACCURACY = eINSTANCE.getImpactOnAccuracy();

		/**
		 * The meta object literal for the '{@link uncertainty.UncertaintyScenario <em>Scenario</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see uncertainty.UncertaintyScenario
		 * @see uncertainty.impl.UncertaintyPackageImpl#getUncertaintyScenario()
		 * @generated
		 */
		EEnum UNCERTAINTY_SCENARIO = eINSTANCE.getUncertaintyScenario();

	}

} //UncertaintyPackage
