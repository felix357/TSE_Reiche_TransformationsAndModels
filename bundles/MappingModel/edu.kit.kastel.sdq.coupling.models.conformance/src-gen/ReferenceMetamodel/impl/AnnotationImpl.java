/**
 */
package ReferenceMetamodel.impl;

import ReferenceMetamodel.Annotation;
import ReferenceMetamodel.ReferenceMetamodelPackage;
import ReferenceMetamodel.SecurityCharacteristic;
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
 * An implementation of the model object '<em><b>Annotation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link ReferenceMetamodel.impl.AnnotationImpl#getAnnotates <em>Annotates</em>}</li>
 *   <li>{@link ReferenceMetamodel.impl.AnnotationImpl#getCharacteristics <em>Characteristics</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnnotationImpl extends MinimalEObjectImpl.Container implements Annotation {
	/**
	 * The cached value of the '{@link #getAnnotates() <em>Annotates</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAnnotates()
	 * @generated
	 * @ordered
	 */
	protected SystemElement annotates;

	/**
	 * The cached value of the '{@link #getCharacteristics() <em>Characteristics</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharacteristics()
	 * @generated
	 * @ordered
	 */
	protected EList<SecurityCharacteristic> characteristics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnnotationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ReferenceMetamodelPackage.Literals.ANNOTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemElement getAnnotates() {
		if (annotates != null && annotates.eIsProxy()) {
			InternalEObject oldAnnotates = (InternalEObject)annotates;
			annotates = (SystemElement)eResolveProxy(oldAnnotates);
			if (annotates != oldAnnotates) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ReferenceMetamodelPackage.ANNOTATION__ANNOTATES, oldAnnotates, annotates));
			}
		}
		return annotates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SystemElement basicGetAnnotates() {
		return annotates;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setAnnotates(SystemElement newAnnotates) {
		SystemElement oldAnnotates = annotates;
		annotates = newAnnotates;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ReferenceMetamodelPackage.ANNOTATION__ANNOTATES, oldAnnotates, annotates));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<SecurityCharacteristic> getCharacteristics() {
		if (characteristics == null) {
			characteristics = new EObjectResolvingEList<SecurityCharacteristic>(SecurityCharacteristic.class, this, ReferenceMetamodelPackage.ANNOTATION__CHARACTERISTICS);
		}
		return characteristics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ReferenceMetamodelPackage.ANNOTATION__ANNOTATES:
				if (resolve) return getAnnotates();
				return basicGetAnnotates();
			case ReferenceMetamodelPackage.ANNOTATION__CHARACTERISTICS:
				return getCharacteristics();
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
			case ReferenceMetamodelPackage.ANNOTATION__ANNOTATES:
				setAnnotates((SystemElement)newValue);
				return;
			case ReferenceMetamodelPackage.ANNOTATION__CHARACTERISTICS:
				getCharacteristics().clear();
				getCharacteristics().addAll((Collection<? extends SecurityCharacteristic>)newValue);
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
			case ReferenceMetamodelPackage.ANNOTATION__ANNOTATES:
				setAnnotates((SystemElement)null);
				return;
			case ReferenceMetamodelPackage.ANNOTATION__CHARACTERISTICS:
				getCharacteristics().clear();
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
			case ReferenceMetamodelPackage.ANNOTATION__ANNOTATES:
				return annotates != null;
			case ReferenceMetamodelPackage.ANNOTATION__CHARACTERISTICS:
				return characteristics != null && !characteristics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AnnotationImpl
