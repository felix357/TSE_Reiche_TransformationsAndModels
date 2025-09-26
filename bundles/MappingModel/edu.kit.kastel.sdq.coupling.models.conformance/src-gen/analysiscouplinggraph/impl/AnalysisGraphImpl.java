/**
 */
package analysiscouplinggraph.impl;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.AnalysiscouplinggraphPackage;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ExternalSource;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Analysis Graph</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisGraphImpl#getComponents <em>Components</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisGraphImpl#getConnections <em>Connections</em>}</li>
 *   <li>{@link analysiscouplinggraph.impl.AnalysisGraphImpl#getExternalSources <em>External Sources</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnalysisGraphImpl extends MinimalEObjectImpl.Container implements AnalysisGraph {
	/**
	 * The cached value of the '{@link #getComponents() <em>Components</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponents()
	 * @generated
	 * @ordered
	 */
	protected EList<AnalysisComponent> components;

	/**
	 * The cached value of the '{@link #getConnections() <em>Connections</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<Connection> connections;

	/**
	 * The cached value of the '{@link #getExternalSources() <em>External Sources</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExternalSources()
	 * @generated
	 * @ordered
	 */
	protected EList<ExternalSource> externalSources;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AnalysisGraphImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AnalysiscouplinggraphPackage.Literals.ANALYSIS_GRAPH;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<AnalysisComponent> getComponents() {
		if (components == null) {
			components = new EObjectContainmentEList<AnalysisComponent>(AnalysisComponent.class, this, AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS);
		}
		return components;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Connection> getConnections() {
		if (connections == null) {
			connections = new EObjectContainmentEList<Connection>(Connection.class, this, AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS);
		}
		return connections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ExternalSource> getExternalSources() {
		if (externalSources == null) {
			externalSources = new EObjectContainmentEList<ExternalSource>(ExternalSource.class, this, AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES);
		}
		return externalSources;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS:
				return ((InternalEList<?>)getComponents()).basicRemove(otherEnd, msgs);
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS:
				return ((InternalEList<?>)getConnections()).basicRemove(otherEnd, msgs);
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES:
				return ((InternalEList<?>)getExternalSources()).basicRemove(otherEnd, msgs);
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
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS:
				return getComponents();
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS:
				return getConnections();
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES:
				return getExternalSources();
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
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS:
				getComponents().clear();
				getComponents().addAll((Collection<? extends AnalysisComponent>)newValue);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS:
				getConnections().clear();
				getConnections().addAll((Collection<? extends Connection>)newValue);
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES:
				getExternalSources().clear();
				getExternalSources().addAll((Collection<? extends ExternalSource>)newValue);
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
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS:
				getComponents().clear();
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS:
				getConnections().clear();
				return;
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES:
				getExternalSources().clear();
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
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__COMPONENTS:
				return components != null && !components.isEmpty();
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__CONNECTIONS:
				return connections != null && !connections.isEmpty();
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH__EXTERNAL_SOURCES:
				return externalSources != null && !externalSources.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //AnalysisGraphImpl
