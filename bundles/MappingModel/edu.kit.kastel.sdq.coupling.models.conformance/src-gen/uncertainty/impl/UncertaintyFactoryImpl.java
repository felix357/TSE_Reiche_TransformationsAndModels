/**
 */
package uncertainty.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import uncertainty.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UncertaintyFactoryImpl extends EFactoryImpl implements UncertaintyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static UncertaintyFactory init() {
		try {
			UncertaintyFactory theUncertaintyFactory = (UncertaintyFactory)EPackage.Registry.INSTANCE.getEFactory(UncertaintyPackage.eNS_URI);
			if (theUncertaintyFactory != null) {
				return theUncertaintyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new UncertaintyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case UncertaintyPackage.UNCERTAINTY_LABEL: return createUncertaintyLabel();
			case UncertaintyPackage.RELATION: return createRelation();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case UncertaintyPackage.UNCERTAINTY_SOURCE:
				return createUncertaintySourceFromString(eDataType, initialValue);
			case UncertaintyPackage.SEVERITY_OF_IMPACT:
				return createSeverityOfImpactFromString(eDataType, initialValue);
			case UncertaintyPackage.RESOLUTION_TIME:
				return createResolutionTimeFromString(eDataType, initialValue);
			case UncertaintyPackage.REDUCIBILITY_BY_ADD:
				return createReducibilityByADDFromString(eDataType, initialValue);
			case UncertaintyPackage.ORIGIN_TYPE:
				return createOriginTypeFromString(eDataType, initialValue);
			case UncertaintyPackage.MANAGEABILITY:
				return createManageabilityFromString(eDataType, initialValue);
			case UncertaintyPackage.LOCATION:
				return createLocationFromString(eDataType, initialValue);
			case UncertaintyPackage.IMPACT_ON_ACCURACY:
				return createImpactOnAccuracyFromString(eDataType, initialValue);
			case UncertaintyPackage.UNCERTAINTY_SCENARIO:
				return createUncertaintyScenarioFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case UncertaintyPackage.UNCERTAINTY_SOURCE:
				return convertUncertaintySourceToString(eDataType, instanceValue);
			case UncertaintyPackage.SEVERITY_OF_IMPACT:
				return convertSeverityOfImpactToString(eDataType, instanceValue);
			case UncertaintyPackage.RESOLUTION_TIME:
				return convertResolutionTimeToString(eDataType, instanceValue);
			case UncertaintyPackage.REDUCIBILITY_BY_ADD:
				return convertReducibilityByADDToString(eDataType, instanceValue);
			case UncertaintyPackage.ORIGIN_TYPE:
				return convertOriginTypeToString(eDataType, instanceValue);
			case UncertaintyPackage.MANAGEABILITY:
				return convertManageabilityToString(eDataType, instanceValue);
			case UncertaintyPackage.LOCATION:
				return convertLocationToString(eDataType, instanceValue);
			case UncertaintyPackage.IMPACT_ON_ACCURACY:
				return convertImpactOnAccuracyToString(eDataType, instanceValue);
			case UncertaintyPackage.UNCERTAINTY_SCENARIO:
				return convertUncertaintyScenarioToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyLabel createUncertaintyLabel() {
		UncertaintyLabelImpl uncertaintyLabel = new UncertaintyLabelImpl();
		return uncertaintyLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Relation createRelation() {
		RelationImpl relation = new RelationImpl();
		return relation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintySource createUncertaintySourceFromString(EDataType eDataType, String initialValue) {
		UncertaintySource result = UncertaintySource.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUncertaintySourceToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityOfImpact createSeverityOfImpactFromString(EDataType eDataType, String initialValue) {
		SeverityOfImpact result = SeverityOfImpact.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertSeverityOfImpactToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResolutionTime createResolutionTimeFromString(EDataType eDataType, String initialValue) {
		ResolutionTime result = ResolutionTime.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResolutionTimeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReducibilityByADD createReducibilityByADDFromString(EDataType eDataType, String initialValue) {
		ReducibilityByADD result = ReducibilityByADD.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertReducibilityByADDToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OriginType createOriginTypeFromString(EDataType eDataType, String initialValue) {
		OriginType result = OriginType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertOriginTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Manageability createManageabilityFromString(EDataType eDataType, String initialValue) {
		Manageability result = Manageability.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertManageabilityToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location createLocationFromString(EDataType eDataType, String initialValue) {
		Location result = Location.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertLocationToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpactOnAccuracy createImpactOnAccuracyFromString(EDataType eDataType, String initialValue) {
		ImpactOnAccuracy result = ImpactOnAccuracy.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertImpactOnAccuracyToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UncertaintyScenario createUncertaintyScenarioFromString(EDataType eDataType, String initialValue) {
		UncertaintyScenario result = UncertaintyScenario.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUncertaintyScenarioToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyPackage getUncertaintyPackage() {
		return (UncertaintyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static UncertaintyPackage getPackage() {
		return UncertaintyPackage.eINSTANCE;
	}

} //UncertaintyFactoryImpl
