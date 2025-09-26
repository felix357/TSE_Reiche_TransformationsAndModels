/**
 */
package analysiscouplinggraph.impl;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysiscouplinggraphPackage;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import uncertainty.UncertaintyLabel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Analysis Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisComponentImpl#getName <em>Name</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisComponentImpl#getInputs <em>Inputs</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisComponentImpl#getOutputs <em>Outputs</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisComponentImpl#getUncertaintyLabels <em>Uncertainty Labels</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnalysisComponentImpl extends MinimalEObjectImpl.Container implements AnalysisComponent {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInputs() <em>Inputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputs()
	 * @generated
	 * @ordered
	 */
	protected EList<RequiredInterface> inputs;

	/**
	 * The cached value of the '{@link #getOutputs() <em>Outputs</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputs()
	 * @generated
	 * @ordered
	 */
	protected EList<ProvidedInterface> outputs;

	/**
	 * The cached value of the '{@link #getUncertaintyLabels() <em>Uncertainty Labels</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyLabels()
	 * @generated
	 * @ordered
	 */
	protected EList<UncertaintyLabel> uncertaintyLabels;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnalysisComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnalysiscouplinggraphPackage.Literals.ANALYSIS_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RequiredInterface> getInputs() {
		if (inputs == null) {
			inputs = new EObjectContainmentEList<RequiredInterface>(RequiredInterface.class, this, AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS);
		}
		return inputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ProvidedInterface> getOutputs() {
		if (outputs == null) {
			outputs = new EObjectContainmentEList<ProvidedInterface>(ProvidedInterface.class, this, AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS);
		}
		return outputs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<UncertaintyLabel> getUncertaintyLabels() {
		if (uncertaintyLabels == null) {
			uncertaintyLabels = new EObjectContainmentEList<UncertaintyLabel>(UncertaintyLabel.class, this, AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS);
		}
		return uncertaintyLabels;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS:
				return ((InternalEList<?>)getInputs()).basicRemove(otherEnd, msgs);
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS:
				return ((InternalEList<?>)getOutputs()).basicRemove(otherEnd, msgs);
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS:
				return ((InternalEList<?>)getUncertaintyLabels()).basicRemove(otherEnd, msgs);
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
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__NAME:
				return getName();
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS:
				return getInputs();
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS:
				return getOutputs();
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS:
				return getUncertaintyLabels();
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
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__NAME:
				setName((String)newValue);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS:
				getInputs().clear();
				getInputs().addAll((Collection<? extends RequiredInterface>)newValue);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS:
				getOutputs().clear();
				getOutputs().addAll((Collection<? extends ProvidedInterface>)newValue);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS:
				getUncertaintyLabels().clear();
				getUncertaintyLabels().addAll((Collection<? extends UncertaintyLabel>)newValue);
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
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS:
				getInputs().clear();
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS:
				getOutputs().clear();
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS:
				getUncertaintyLabels().clear();
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
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__INPUTS:
				return inputs != null && !inputs.isEmpty();
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__OUTPUTS:
				return outputs != null && !outputs.isEmpty();
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT__UNCERTAINTY_LABELS:
				return uncertaintyLabels != null && !uncertaintyLabels.isEmpty();
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
		result.append(" (name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //AnalysisComponentImpl
