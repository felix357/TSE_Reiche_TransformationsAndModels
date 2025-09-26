/**
 */
package ReferenceMetamodel.impl;

import ReferenceMetamodel.Annotation;
import ReferenceMetamodel.Configuration;
import ReferenceMetamodel.ReferenceMetamodelPackage;
import ReferenceMetamodel.SecurityCharacteristic;
import ReferenceMetamodel.SecurityPolicy;
import ReferenceMetamodel.SystemElement;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.impl.ConfigurationImpl#getSecurityPolicy <em>Security Policy</em>}</li>
 *   <li>{@link ReferenceMetamodel.impl.ConfigurationImpl#getSecurityCharacteristics <em>Security Characteristics</em>}</li>
 *   <li>{@link ReferenceMetamodel.impl.ConfigurationImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link ReferenceMetamodel.impl.ConfigurationImpl#getSystemElements <em>System Elements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConfigurationImpl extends MinimalEObjectImpl.Container implements Configuration {
	/**
	 * The cached value of the '{@link #getSecurityPolicy() <em>Security Policy</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecurityPolicy()
	 * @generated
	 * @ordered
	 */
	protected SecurityPolicy securityPolicy;

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
	 * The cached value of the '{@link #getAnnotations() <em>Annotations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotations()
	 * @generated
	 * @ordered
	 */
	protected EList<Annotation> annotations;

	/**
	 * The cached value of the '{@link #getSystemElements() <em>System Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSystemElements()
	 * @generated
	 * @ordered
	 */
	protected EList<SystemElement> systemElements;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferenceMetamodelPackage.Literals.CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SecurityPolicy getSecurityPolicy() {
		if (securityPolicy != null && securityPolicy.eIsProxy()) {
			InternalEObject oldSecurityPolicy = (InternalEObject)securityPolicy;
			securityPolicy = (SecurityPolicy)eResolveProxy(oldSecurityPolicy);
			if (securityPolicy != oldSecurityPolicy) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY, oldSecurityPolicy, securityPolicy));
			}
		}
		return securityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityPolicy basicGetSecurityPolicy() {
		return securityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSecurityPolicy(SecurityPolicy newSecurityPolicy) {
		SecurityPolicy oldSecurityPolicy = securityPolicy;
		securityPolicy = newSecurityPolicy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY, oldSecurityPolicy, securityPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SecurityCharacteristic> getSecurityCharacteristics() {
		if (securityCharacteristics == null) {
			securityCharacteristics = new EObjectResolvingEList<SecurityCharacteristic>(SecurityCharacteristic.class, this, ReferenceMetamodelPackage.CONFIGURATION__SECURITY_CHARACTERISTICS);
		}
		return securityCharacteristics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectResolvingEList<Annotation>(Annotation.class, this, ReferenceMetamodelPackage.CONFIGURATION__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SystemElement> getSystemElements() {
		if (systemElements == null) {
			systemElements = new EObjectResolvingEList<SystemElement>(SystemElement.class, this, ReferenceMetamodelPackage.CONFIGURATION__SYSTEM_ELEMENTS);
		}
		return systemElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY:
				if (resolve) return getSecurityPolicy();
				return basicGetSecurityPolicy();
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_CHARACTERISTICS:
				return getSecurityCharacteristics();
			case ReferenceMetamodelPackage.CONFIGURATION__ANNOTATIONS:
				return getAnnotations();
			case ReferenceMetamodelPackage.CONFIGURATION__SYSTEM_ELEMENTS:
				return getSystemElements();
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
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY:
				setSecurityPolicy((SecurityPolicy)newValue);
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_CHARACTERISTICS:
				getSecurityCharacteristics().clear();
				getSecurityCharacteristics().addAll((Collection<? extends SecurityCharacteristic>)newValue);
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__SYSTEM_ELEMENTS:
				getSystemElements().clear();
				getSystemElements().addAll((Collection<? extends SystemElement>)newValue);
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
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY:
				setSecurityPolicy((SecurityPolicy)null);
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_CHARACTERISTICS:
				getSecurityCharacteristics().clear();
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case ReferenceMetamodelPackage.CONFIGURATION__SYSTEM_ELEMENTS:
				getSystemElements().clear();
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
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_POLICY:
				return securityPolicy != null;
			case ReferenceMetamodelPackage.CONFIGURATION__SECURITY_CHARACTERISTICS:
				return securityCharacteristics != null && !securityCharacteristics.isEmpty();
			case ReferenceMetamodelPackage.CONFIGURATION__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case ReferenceMetamodelPackage.CONFIGURATION__SYSTEM_ELEMENTS:
				return systemElements != null && !systemElements.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ConfigurationImpl
