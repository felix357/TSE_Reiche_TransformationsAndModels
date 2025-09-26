/**
 */
package mapping.impl;

import java.util.Collection;

import mapping.ClassMapping;
import mapping.FeatureMapping;
import mapping.MappingPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Class Mapping</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mapping.impl.ClassMappingImpl#getSourceClass <em>Source Class</em>}</li>
 *   <li>{@link mapping.impl.ClassMappingImpl#getTargetClass <em>Target Class</em>}</li>
 *   <li>{@link mapping.impl.ClassMappingImpl#getFeatureMappings <em>Feature Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ClassMappingImpl extends MinimalEObjectImpl.Container implements ClassMapping {
	/**
	 * The cached value of the '{@link #getSourceClass() <em>Source Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceClass()
	 * @generated
	 * @ordered
	 */
	protected EClass sourceClass;

	/**
	 * The cached value of the '{@link #getTargetClass() <em>Target Class</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTargetClass()
	 * @generated
	 * @ordered
	 */
	protected EClass targetClass;

	/**
	 * The cached value of the '{@link #getFeatureMappings() <em>Feature Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFeatureMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<FeatureMapping> featureMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ClassMappingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.CLASS_MAPPING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSourceClass() {
		if (sourceClass != null && sourceClass.eIsProxy()) {
			InternalEObject oldSourceClass = (InternalEObject)sourceClass;
			sourceClass = (EClass)eResolveProxy(oldSourceClass);
			if (sourceClass != oldSourceClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MappingPackage.CLASS_MAPPING__SOURCE_CLASS, oldSourceClass, sourceClass));
			}
		}
		return sourceClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetSourceClass() {
		return sourceClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSourceClass(EClass newSourceClass) {
		EClass oldSourceClass = sourceClass;
		sourceClass = newSourceClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.CLASS_MAPPING__SOURCE_CLASS, oldSourceClass, sourceClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getTargetClass() {
		if (targetClass != null && targetClass.eIsProxy()) {
			InternalEObject oldTargetClass = (InternalEObject)targetClass;
			targetClass = (EClass)eResolveProxy(oldTargetClass);
			if (targetClass != oldTargetClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, MappingPackage.CLASS_MAPPING__TARGET_CLASS, oldTargetClass, targetClass));
			}
		}
		return targetClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetTargetClass() {
		return targetClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTargetClass(EClass newTargetClass) {
		EClass oldTargetClass = targetClass;
		targetClass = newTargetClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MappingPackage.CLASS_MAPPING__TARGET_CLASS, oldTargetClass, targetClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<FeatureMapping> getFeatureMappings() {
		if (featureMappings == null) {
			featureMappings = new EObjectContainmentEList<FeatureMapping>(FeatureMapping.class, this, MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS);
		}
		return featureMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS:
				return ((InternalEList<?>)getFeatureMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MappingPackage.CLASS_MAPPING__SOURCE_CLASS:
				if (resolve) return getSourceClass();
				return basicGetSourceClass();
			case MappingPackage.CLASS_MAPPING__TARGET_CLASS:
				if (resolve) return getTargetClass();
				return basicGetTargetClass();
			case MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS:
				return getFeatureMappings();
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
			case MappingPackage.CLASS_MAPPING__SOURCE_CLASS:
				setSourceClass((EClass)newValue);
				return;
			case MappingPackage.CLASS_MAPPING__TARGET_CLASS:
				setTargetClass((EClass)newValue);
				return;
			case MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS:
				getFeatureMappings().clear();
				getFeatureMappings().addAll((Collection<? extends FeatureMapping>)newValue);
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
			case MappingPackage.CLASS_MAPPING__SOURCE_CLASS:
				setSourceClass((EClass)null);
				return;
			case MappingPackage.CLASS_MAPPING__TARGET_CLASS:
				setTargetClass((EClass)null);
				return;
			case MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS:
				getFeatureMappings().clear();
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
			case MappingPackage.CLASS_MAPPING__SOURCE_CLASS:
				return sourceClass != null;
			case MappingPackage.CLASS_MAPPING__TARGET_CLASS:
				return targetClass != null;
			case MappingPackage.CLASS_MAPPING__FEATURE_MAPPINGS:
				return featureMappings != null && !featureMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ClassMappingImpl
