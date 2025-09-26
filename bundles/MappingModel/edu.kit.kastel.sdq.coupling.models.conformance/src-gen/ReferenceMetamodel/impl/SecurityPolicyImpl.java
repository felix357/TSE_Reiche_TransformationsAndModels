/**
 */
package ReferenceMetamodel.impl;

import ReferenceMetamodel.ReferenceMetamodelPackage;
import ReferenceMetamodel.SecurityCharacteristic;
import ReferenceMetamodel.SecurityPolicy;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Security Policy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.impl.SecurityPolicyImpl#getSecurityCharacteristics <em>Security Characteristics</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SecurityPolicyImpl extends MinimalEObjectImpl.Container implements SecurityPolicy {
	/**
	 * The cached value of the '{@link #getSecurityCharacteristics() <em>Security Characteristics</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurityCharacteristics()
	 * @generated
	 * @ordered
	 */
	protected EList<SecurityCharacteristic> securityCharacteristics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityPolicyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferenceMetamodelPackage.Literals.SECURITY_POLICY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SecurityCharacteristic> getSecurityCharacteristics() {
		if (securityCharacteristics == null) {
			securityCharacteristics = new EObjectResolvingEList<SecurityCharacteristic>(SecurityCharacteristic.class, this, ReferenceMetamodelPackage.SECURITY_POLICY__SECURITY_CHARACTERISTICS);
		}
		return securityCharacteristics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferenceMetamodelPackage.SECURITY_POLICY__SECURITY_CHARACTERISTICS:
				return getSecurityCharacteristics();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ReferenceMetamodelPackage.SECURITY_POLICY__SECURITY_CHARACTERISTICS:
				getSecurityCharacteristics().clear();
				getSecurityCharacteristics().addAll((Collection<? extends SecurityCharacteristic>)newValue);
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
			case ReferenceMetamodelPackage.SECURITY_POLICY__SECURITY_CHARACTERISTICS:
				getSecurityCharacteristics().clear();
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
			case ReferenceMetamodelPackage.SECURITY_POLICY__SECURITY_CHARACTERISTICS:
				return securityCharacteristics != null && !securityCharacteristics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //SecurityPolicyImpl
