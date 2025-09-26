/**
 */
package analysiscouplinggraph.impl;

import ReferenceMetamodel.Annotation;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysiscouplinggraphPackage;
import analysiscouplinggraph.ProvidedInterface;

import java.util.Collection;

import mapping.MappingDefinition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import uncertainty.UncertaintyLabel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provided Interface</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl#getUncertaintyLabel <em>Uncertainty Label</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.ProvidedInterfaceImpl#getMappingModel <em>Mapping Model</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProvidedInterfaceImpl extends MinimalEObjectImpl.Container implements ProvidedInterface {
	/**
	 * The cached value of the '{@link #getUncertaintyLabel() <em>Uncertainty Label</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyLabel()
	 * @generated
	 * @ordered
	 */
	protected EList<UncertaintyLabel> uncertaintyLabel;

	/**
	 * The cached value of the '{@link #getOwner() <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwner()
	 * @generated
	 * @ordered
	 */
	protected AnalysisComponent owner;

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
	 * The cached value of the '{@link #getMappingModel() <em>Mapping Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappingModel()
	 * @generated
	 * @ordered
	 */
	protected MappingDefinition mappingModel;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProvidedInterfaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnalysiscouplinggraphPackage.Literals.PROVIDED_INTERFACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UncertaintyLabel> getUncertaintyLabel() {
		if (uncertaintyLabel == null) {
			uncertaintyLabel = new EObjectContainmentEList<UncertaintyLabel>(UncertaintyLabel.class, this, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL);
		}
		return uncertaintyLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnalysisComponent getOwner() {
		if (owner != null && owner.eIsProxy()) {
			InternalEObject oldOwner = (InternalEObject)owner;
			owner = (AnalysisComponent)eResolveProxy(oldOwner);
			if (owner != oldOwner) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER, oldOwner, owner));
			}
		}
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnalysisComponent basicGetOwner() {
		return owner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOwner(AnalysisComponent newOwner) {
		AnalysisComponent oldOwner = owner;
		owner = newOwner;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER, oldOwner, owner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Annotation> getAnnotations() {
		if (annotations == null) {
			annotations = new EObjectResolvingEList<Annotation>(Annotation.class, this, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__ANNOTATIONS);
		}
		return annotations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public MappingDefinition getMappingModel() {
		if (mappingModel != null && mappingModel.eIsProxy()) {
			InternalEObject oldMappingModel = (InternalEObject)mappingModel;
			mappingModel = (MappingDefinition)eResolveProxy(oldMappingModel);
			if (mappingModel != oldMappingModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL, oldMappingModel, mappingModel));
			}
		}
		return mappingModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappingDefinition basicGetMappingModel() {
		return mappingModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMappingModel(MappingDefinition newMappingModel) {
		MappingDefinition oldMappingModel = mappingModel;
		mappingModel = newMappingModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL, oldMappingModel, mappingModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL:
				return ((InternalEList<?>)getUncertaintyLabel()).basicRemove(otherEnd, msgs);
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
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL:
				return getUncertaintyLabel();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER:
				if (resolve) return getOwner();
				return basicGetOwner();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__ANNOTATIONS:
				return getAnnotations();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL:
				if (resolve) return getMappingModel();
				return basicGetMappingModel();
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
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL:
				getUncertaintyLabel().clear();
				getUncertaintyLabel().addAll((Collection<? extends UncertaintyLabel>)newValue);
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER:
				setOwner((AnalysisComponent)newValue);
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends Annotation>)newValue);
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL:
				setMappingModel((MappingDefinition)newValue);
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
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL:
				getUncertaintyLabel().clear();
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER:
				setOwner((AnalysisComponent)null);
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL:
				setMappingModel((MappingDefinition)null);
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
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__UNCERTAINTY_LABEL:
				return uncertaintyLabel != null && !uncertaintyLabel.isEmpty();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__OWNER:
				return owner != null;
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__ANNOTATIONS:
				return annotations != null && !annotations.isEmpty();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE__MAPPING_MODEL:
				return mappingModel != null;
		}
		return super.eIsSet(featureID);
	}

} //ProvidedInterfaceImpl
