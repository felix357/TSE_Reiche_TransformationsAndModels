/**
 */
package mapping.impl;

import java.util.Collection;

import mapping.ClassMapping;
import mapping.MappingDefinition;
import mapping.MappingPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link mapping.impl.MappingDefinitionImpl#getClassMappings <em>Class Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingDefinitionImpl extends MinimalEObjectImpl.Container implements MappingDefinition {
	/**
	 * The cached value of the '{@link #getClassMappings() <em>Class Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<ClassMapping> classMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MappingDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MappingPackage.Literals.MAPPING_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ClassMapping> getClassMappings() {
		if (classMappings == null) {
			classMappings = new EObjectContainmentEList<ClassMapping>(ClassMapping.class, this, MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS);
		}
		return classMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS:
				return ((InternalEList<?>)getClassMappings()).basicRemove(otherEnd, msgs);
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
			case MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS:
				return getClassMappings();
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
			case MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS:
				getClassMappings().clear();
				getClassMappings().addAll((Collection<? extends ClassMapping>)newValue);
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
			case MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS:
				getClassMappings().clear();
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
			case MappingPackage.MAPPING_DEFINITION__CLASS_MAPPINGS:
				return classMappings != null && !classMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MappingDefinitionImpl
