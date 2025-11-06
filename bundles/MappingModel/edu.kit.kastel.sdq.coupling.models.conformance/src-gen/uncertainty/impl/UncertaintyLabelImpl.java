/**
 */
package uncertainty.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import uncertainty.ImpactOnAccuracy;
import uncertainty.Location;
import uncertainty.Manageability;
import uncertainty.OriginType;
import uncertainty.ReducibilityByADD;
import uncertainty.Relation;
import uncertainty.ResolutionTime;
import uncertainty.SeverityOfImpact;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyPackage;
import uncertainty.UncertaintyScenario;
import uncertainty.UncertaintySource;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getSource <em>Source</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getResolutionTime <em>Resolution Time</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getReducability <em>Reducability</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getOriginType <em>Origin Type</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getManageability <em>Manageability</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getImpact <em>Impact</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getRelations <em>Relations</em>}</li>
 *   <li>{@link uncertainty.impl.UncertaintyLabelImpl#getUncertaintyScenario <em>Uncertainty Scenario</em>}</li>
 * </ul>
 *
 * @generated
 */
public class UncertaintyLabelImpl extends MinimalEObjectImpl.Container implements UncertaintyLabel {
	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final UncertaintySource SOURCE_EDEFAULT = UncertaintySource.INPUT_DATA_INDUCED;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected UncertaintySource source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final SeverityOfImpact SEVERITY_EDEFAULT = SeverityOfImpact.NONE;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected SeverityOfImpact severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getResolutionTime() <em>Resolution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolutionTime()
	 * @generated
	 * @ordered
	 */
	protected static final ResolutionTime RESOLUTION_TIME_EDEFAULT = ResolutionTime.REQUIREMENTS_TIME;

	/**
	 * The cached value of the '{@link #getResolutionTime() <em>Resolution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResolutionTime()
	 * @generated
	 * @ordered
	 */
	protected ResolutionTime resolutionTime = RESOLUTION_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #getReducability() <em>Reducability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReducability()
	 * @generated
	 * @ordered
	 */
	protected static final ReducibilityByADD REDUCABILITY_EDEFAULT = ReducibilityByADD.YES;

	/**
	 * The cached value of the '{@link #getReducability() <em>Reducability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReducability()
	 * @generated
	 * @ordered
	 */
	protected ReducibilityByADD reducability = REDUCABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOriginType() <em>Origin Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginType()
	 * @generated
	 * @ordered
	 */
	protected static final OriginType ORIGIN_TYPE_EDEFAULT = OriginType.SINGLE_ANALYSIS;

	/**
	 * The cached value of the '{@link #getOriginType() <em>Origin Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOriginType()
	 * @generated
	 * @ordered
	 */
	protected OriginType originType = ORIGIN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getManageability() <em>Manageability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManageability()
	 * @generated
	 * @ordered
	 */
	protected static final Manageability MANAGEABILITY_EDEFAULT = Manageability.FULLY_REDUCIBLE;

	/**
	 * The cached value of the '{@link #getManageability() <em>Manageability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getManageability()
	 * @generated
	 * @ordered
	 */
	protected Manageability manageability = MANAGEABILITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final Location LOCATION_EDEFAULT = Location.EXTERNAL_INPUT;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected Location location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getImpact() <em>Impact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpact()
	 * @generated
	 * @ordered
	 */
	protected static final ImpactOnAccuracy IMPACT_EDEFAULT = ImpactOnAccuracy.DIRECT;

	/**
	 * The cached value of the '{@link #getImpact() <em>Impact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImpact()
	 * @generated
	 * @ordered
	 */
	protected ImpactOnAccuracy impact = IMPACT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRelations() <em>Relations</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRelations()
	 * @generated
	 * @ordered
	 */
	protected EList<Relation> relations;

	/**
	 * The default value of the '{@link #getUncertaintyScenario() <em>Uncertainty Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyScenario()
	 * @generated
	 * @ordered
	 */
	protected static final UncertaintyScenario UNCERTAINTY_SCENARIO_EDEFAULT = UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE;

	/**
	 * The cached value of the '{@link #getUncertaintyScenario() <em>Uncertainty Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUncertaintyScenario()
	 * @generated
	 * @ordered
	 */
	protected UncertaintyScenario uncertaintyScenario = UNCERTAINTY_SCENARIO_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UncertaintyLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return UncertaintyPackage.Literals.UNCERTAINTY_LABEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintySource getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSource(UncertaintySource newSource) {
		UncertaintySource oldSource = source;
		source = newSource == null ? SOURCE_EDEFAULT : newSource;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SeverityOfImpact getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeverity(SeverityOfImpact newSeverity) {
		SeverityOfImpact oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__SEVERITY, oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResolutionTime getResolutionTime() {
		return resolutionTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setResolutionTime(ResolutionTime newResolutionTime) {
		ResolutionTime oldResolutionTime = resolutionTime;
		resolutionTime = newResolutionTime == null ? RESOLUTION_TIME_EDEFAULT : newResolutionTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__RESOLUTION_TIME, oldResolutionTime, resolutionTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReducibilityByADD getReducability() {
		return reducability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReducability(ReducibilityByADD newReducability) {
		ReducibilityByADD oldReducability = reducability;
		reducability = newReducability == null ? REDUCABILITY_EDEFAULT : newReducability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__REDUCABILITY, oldReducability, reducability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public OriginType getOriginType() {
		return originType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setOriginType(OriginType newOriginType) {
		OriginType oldOriginType = originType;
		originType = newOriginType == null ? ORIGIN_TYPE_EDEFAULT : newOriginType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__ORIGIN_TYPE, oldOriginType, originType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Manageability getManageability() {
		return manageability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setManageability(Manageability newManageability) {
		Manageability oldManageability = manageability;
		manageability = newManageability == null ? MANAGEABILITY_EDEFAULT : newManageability;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__MANAGEABILITY, oldManageability, manageability));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Location getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocation(Location newLocation) {
		Location oldLocation = location;
		location = newLocation == null ? LOCATION_EDEFAULT : newLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__LOCATION, oldLocation, location));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ImpactOnAccuracy getImpact() {
		return impact;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImpact(ImpactOnAccuracy newImpact) {
		ImpactOnAccuracy oldImpact = impact;
		impact = newImpact == null ? IMPACT_EDEFAULT : newImpact;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__IMPACT, oldImpact, impact));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Relation> getRelations() {
		if (relations == null) {
			relations = new EObjectResolvingEList<Relation>(Relation.class, this, UncertaintyPackage.UNCERTAINTY_LABEL__RELATIONS);
		}
		return relations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UncertaintyScenario getUncertaintyScenario() {
		return uncertaintyScenario;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUncertaintyScenario(UncertaintyScenario newUncertaintyScenario) {
		UncertaintyScenario oldUncertaintyScenario = uncertaintyScenario;
		uncertaintyScenario = newUncertaintyScenario == null ? UNCERTAINTY_SCENARIO_EDEFAULT : newUncertaintyScenario;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, UncertaintyPackage.UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO, oldUncertaintyScenario, uncertaintyScenario));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case UncertaintyPackage.UNCERTAINTY_LABEL__SOURCE:
				return getSource();
			case UncertaintyPackage.UNCERTAINTY_LABEL__SEVERITY:
				return getSeverity();
			case UncertaintyPackage.UNCERTAINTY_LABEL__RESOLUTION_TIME:
				return getResolutionTime();
			case UncertaintyPackage.UNCERTAINTY_LABEL__REDUCABILITY:
				return getReducability();
			case UncertaintyPackage.UNCERTAINTY_LABEL__ORIGIN_TYPE:
				return getOriginType();
			case UncertaintyPackage.UNCERTAINTY_LABEL__MANAGEABILITY:
				return getManageability();
			case UncertaintyPackage.UNCERTAINTY_LABEL__LOCATION:
				return getLocation();
			case UncertaintyPackage.UNCERTAINTY_LABEL__IMPACT:
				return getImpact();
			case UncertaintyPackage.UNCERTAINTY_LABEL__RELATIONS:
				return getRelations();
			case UncertaintyPackage.UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO:
				return getUncertaintyScenario();
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
			case UncertaintyPackage.UNCERTAINTY_LABEL__SOURCE:
				setSource((UncertaintySource)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__SEVERITY:
				setSeverity((SeverityOfImpact)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RESOLUTION_TIME:
				setResolutionTime((ResolutionTime)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__REDUCABILITY:
				setReducability((ReducibilityByADD)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__ORIGIN_TYPE:
				setOriginType((OriginType)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__MANAGEABILITY:
				setManageability((Manageability)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__LOCATION:
				setLocation((Location)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__IMPACT:
				setImpact((ImpactOnAccuracy)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RELATIONS:
				getRelations().clear();
				getRelations().addAll((Collection<? extends Relation>)newValue);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO:
				setUncertaintyScenario((UncertaintyScenario)newValue);
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
			case UncertaintyPackage.UNCERTAINTY_LABEL__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RESOLUTION_TIME:
				setResolutionTime(RESOLUTION_TIME_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__REDUCABILITY:
				setReducability(REDUCABILITY_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__ORIGIN_TYPE:
				setOriginType(ORIGIN_TYPE_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__MANAGEABILITY:
				setManageability(MANAGEABILITY_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__LOCATION:
				setLocation(LOCATION_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__IMPACT:
				setImpact(IMPACT_EDEFAULT);
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RELATIONS:
				getRelations().clear();
				return;
			case UncertaintyPackage.UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO:
				setUncertaintyScenario(UNCERTAINTY_SCENARIO_EDEFAULT);
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
			case UncertaintyPackage.UNCERTAINTY_LABEL__SOURCE:
				return source != SOURCE_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RESOLUTION_TIME:
				return resolutionTime != RESOLUTION_TIME_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__REDUCABILITY:
				return reducability != REDUCABILITY_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__ORIGIN_TYPE:
				return originType != ORIGIN_TYPE_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__MANAGEABILITY:
				return manageability != MANAGEABILITY_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__LOCATION:
				return location != LOCATION_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__IMPACT:
				return impact != IMPACT_EDEFAULT;
			case UncertaintyPackage.UNCERTAINTY_LABEL__RELATIONS:
				return relations != null && !relations.isEmpty();
			case UncertaintyPackage.UNCERTAINTY_LABEL__UNCERTAINTY_SCENARIO:
				return uncertaintyScenario != UNCERTAINTY_SCENARIO_EDEFAULT;
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
		result.append(" (source: ");
		result.append(source);
		result.append(", severity: ");
		result.append(severity);
		result.append(", resolutionTime: ");
		result.append(resolutionTime);
		result.append(", reducability: ");
		result.append(reducability);
		result.append(", originType: ");
		result.append(originType);
		result.append(", manageability: ");
		result.append(manageability);
		result.append(", location: ");
		result.append(location);
		result.append(", impact: ");
		result.append(impact);
		result.append(", uncertaintyScenario: ");
		result.append(uncertaintyScenario);
		result.append(')');
		return result.toString();
	}

} //UncertaintyLabelImpl
