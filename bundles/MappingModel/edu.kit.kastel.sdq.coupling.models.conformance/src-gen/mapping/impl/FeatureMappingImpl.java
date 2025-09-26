/**
 */
package mapping.impl;

import mapping.FeatureMapping;
import mapping.MappingPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mapping.impl.FeatureMappingImpl#getSourceFeatureURI <em>Source Feature URI</em>}</li>
 *   <li>{@link mapping.impl.FeatureMappingImpl#getTargetFeatureURI <em>Target Feature URI</em>}</li>
 *   <li>{@link mapping.impl.FeatureMappingImpl#getTransformationRule <em>Transformation Rule</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FeatureMappingImpl extends MinimalEObjectImpl.Container implements FeatureMapping {
	/**
	 * The default value of the '{@link #getSourceFeatureURI() <em>Source Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFeatureURI()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_FEATURE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceFeatureURI() <em>Source Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFeatureURI()
	 * @generated
	 * @ordered
	 */
	protected String sourceFeatureURI = SOURCE_FEATURE_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getTargetFeatureURI() <em>Target Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetFeatureURI()
	 * @generated
	 * @ordered
	 */
	protected static final String TARGET_FEATURE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTargetFeatureURI() <em>Target Feature URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetFeatureURI()
	 * @generated
	 * @ordered
	 */
	protected String targetFeatureURI = TARGET_FEATURE_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getTransformationRule() <em>Transformation Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationRule()
	 * @generated
	 * @ordered
	 */
	protected static final String TRANSFORMATION_RULE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTransformationRule() <em>Transformation Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransformationRule()
	 * @generated
	 * @ordered
	 */
	protected String transformationRule = TRANSFORMATION_RULE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.FEATURE_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getSourceFeatureURI() {
		return sourceFeatureURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceFeatureURI(String newSourceFeatureURI) {
		String oldSourceFeatureURI = sourceFeatureURI;
		sourceFeatureURI = newSourceFeatureURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.FEATURE_MAPPING__SOURCE_FEATURE_URI, oldSourceFeatureURI, sourceFeatureURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTargetFeatureURI() {
		return targetFeatureURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetFeatureURI(String newTargetFeatureURI) {
		String oldTargetFeatureURI = targetFeatureURI;
		targetFeatureURI = newTargetFeatureURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.FEATURE_MAPPING__TARGET_FEATURE_URI, oldTargetFeatureURI, targetFeatureURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getTransformationRule() {
		return transformationRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTransformationRule(String newTransformationRule) {
		String oldTransformationRule = transformationRule;
		transformationRule = newTransformationRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.FEATURE_MAPPING__TRANSFORMATION_RULE, oldTransformationRule, transformationRule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MappingPackage.FEATURE_MAPPING__SOURCE_FEATURE_URI:
				return getSourceFeatureURI();
			case MappingPackage.FEATURE_MAPPING__TARGET_FEATURE_URI:
				return getTargetFeatureURI();
			case MappingPackage.FEATURE_MAPPING__TRANSFORMATION_RULE:
				return getTransformationRule();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MappingPackage.FEATURE_MAPPING__SOURCE_FEATURE_URI:
				setSourceFeatureURI((String)newValue);
				return;
			case MappingPackage.FEATURE_MAPPING__TARGET_FEATURE_URI:
				setTargetFeatureURI((String)newValue);
				return;
			case MappingPackage.FEATURE_MAPPING__TRANSFORMATION_RULE:
				setTransformationRule((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case MappingPackage.FEATURE_MAPPING__SOURCE_FEATURE_URI:
				setSourceFeatureURI(SOURCE_FEATURE_URI_EDEFAULT);
				return;
			case MappingPackage.FEATURE_MAPPING__TARGET_FEATURE_URI:
				setTargetFeatureURI(TARGET_FEATURE_URI_EDEFAULT);
				return;
			case MappingPackage.FEATURE_MAPPING__TRANSFORMATION_RULE:
				setTransformationRule(TRANSFORMATION_RULE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case MappingPackage.FEATURE_MAPPING__SOURCE_FEATURE_URI:
				return SOURCE_FEATURE_URI_EDEFAULT == null ? sourceFeatureURI != null : !SOURCE_FEATURE_URI_EDEFAULT.equals(sourceFeatureURI);
			case MappingPackage.FEATURE_MAPPING__TARGET_FEATURE_URI:
				return TARGET_FEATURE_URI_EDEFAULT == null ? targetFeatureURI != null : !TARGET_FEATURE_URI_EDEFAULT.equals(targetFeatureURI);
			case MappingPackage.FEATURE_MAPPING__TRANSFORMATION_RULE:
				return TRANSFORMATION_RULE_EDEFAULT == null ? transformationRule != null : !TRANSFORMATION_RULE_EDEFAULT.equals(transformationRule);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (sourceFeatureURI: ");
		result.append(sourceFeatureURI);
		result.append(", targetFeatureURI: ");
		result.append(targetFeatureURI);
		result.append(", transformationRule: ");
		result.append(transformationRule);
		result.append(')');
		return result.toString();
	}

} //FeatureMappingImpl
