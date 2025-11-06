/**
 */
package uncertainty;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Label</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link uncertainty.UncertaintyLabel#getSource <em>Source</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getSeverity <em>Severity</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getResolutionTime <em>Resolution Time</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getReducability <em>Reducability</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getOriginType <em>Origin Type</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getManageability <em>Manageability</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getLocation <em>Location</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getImpact <em>Impact</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getRelations <em>Relations</em>}</li>
 *   <li>{@link uncertainty.UncertaintyLabel#getUncertaintyScenario <em>Uncertainty Scenario</em>}</li>
 * </ul>
 *
 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel()
 * @model
 * @generated
 */
public interface UncertaintyLabel extends EObject {
	/**
	 * Returns the value of the '<em><b>Source</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.UncertaintySource}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Source</em>' attribute.
	 * @see uncertainty.UncertaintySource
	 * @see #setSource(UncertaintySource)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Source()
	 * @model
	 * @generated
	 */
	UncertaintySource getSource();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getSource <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Source</em>' attribute.
	 * @see uncertainty.UncertaintySource
	 * @see #getSource()
	 * @generated
	 */
	void setSource(UncertaintySource value);

	/**
	 * Returns the value of the '<em><b>Severity</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.SeverityOfImpact}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Severity</em>' attribute.
	 * @see uncertainty.SeverityOfImpact
	 * @see #setSeverity(SeverityOfImpact)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Severity()
	 * @model
	 * @generated
	 */
	SeverityOfImpact getSeverity();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getSeverity <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Severity</em>' attribute.
	 * @see uncertainty.SeverityOfImpact
	 * @see #getSeverity()
	 * @generated
	 */
	void setSeverity(SeverityOfImpact value);

	/**
	 * Returns the value of the '<em><b>Resolution Time</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.ResolutionTime}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resolution Time</em>' attribute.
	 * @see uncertainty.ResolutionTime
	 * @see #setResolutionTime(ResolutionTime)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_ResolutionTime()
	 * @model
	 * @generated
	 */
	ResolutionTime getResolutionTime();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getResolutionTime <em>Resolution Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resolution Time</em>' attribute.
	 * @see uncertainty.ResolutionTime
	 * @see #getResolutionTime()
	 * @generated
	 */
	void setResolutionTime(ResolutionTime value);

	/**
	 * Returns the value of the '<em><b>Reducability</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.ReducibilityByADD}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reducability</em>' attribute.
	 * @see uncertainty.ReducibilityByADD
	 * @see #setReducability(ReducibilityByADD)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Reducability()
	 * @model
	 * @generated
	 */
	ReducibilityByADD getReducability();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getReducability <em>Reducability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reducability</em>' attribute.
	 * @see uncertainty.ReducibilityByADD
	 * @see #getReducability()
	 * @generated
	 */
	void setReducability(ReducibilityByADD value);

	/**
	 * Returns the value of the '<em><b>Origin Type</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.OriginType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Origin Type</em>' attribute.
	 * @see uncertainty.OriginType
	 * @see #setOriginType(OriginType)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_OriginType()
	 * @model
	 * @generated
	 */
	OriginType getOriginType();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getOriginType <em>Origin Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Origin Type</em>' attribute.
	 * @see uncertainty.OriginType
	 * @see #getOriginType()
	 * @generated
	 */
	void setOriginType(OriginType value);

	/**
	 * Returns the value of the '<em><b>Manageability</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.Manageability}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Manageability</em>' attribute.
	 * @see uncertainty.Manageability
	 * @see #setManageability(Manageability)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Manageability()
	 * @model
	 * @generated
	 */
	Manageability getManageability();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getManageability <em>Manageability</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Manageability</em>' attribute.
	 * @see uncertainty.Manageability
	 * @see #getManageability()
	 * @generated
	 */
	void setManageability(Manageability value);

	/**
	 * Returns the value of the '<em><b>Location</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.Location}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' attribute.
	 * @see uncertainty.Location
	 * @see #setLocation(Location)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Location()
	 * @model
	 * @generated
	 */
	Location getLocation();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getLocation <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' attribute.
	 * @see uncertainty.Location
	 * @see #getLocation()
	 * @generated
	 */
	void setLocation(Location value);

	/**
	 * Returns the value of the '<em><b>Impact</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.ImpactOnAccuracy}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Impact</em>' attribute.
	 * @see uncertainty.ImpactOnAccuracy
	 * @see #setImpact(ImpactOnAccuracy)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Impact()
	 * @model
	 * @generated
	 */
	ImpactOnAccuracy getImpact();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getImpact <em>Impact</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Impact</em>' attribute.
	 * @see uncertainty.ImpactOnAccuracy
	 * @see #getImpact()
	 * @generated
	 */
	void setImpact(ImpactOnAccuracy value);

	/**
	 * Returns the value of the '<em><b>Relations</b></em>' reference list.
	 * The list contents are of type {@link uncertainty.Relation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Relations</em>' reference list.
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_Relations()
	 * @model
	 * @generated
	 */
	EList<Relation> getRelations();

	/**
	 * Returns the value of the '<em><b>Uncertainty Scenario</b></em>' attribute.
	 * The literals are from the enumeration {@link uncertainty.UncertaintyScenario}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Scenario</em>' attribute.
	 * @see uncertainty.UncertaintyScenario
	 * @see #setUncertaintyScenario(UncertaintyScenario)
	 * @see uncertainty.UncertaintyPackage#getUncertaintyLabel_UncertaintyScenario()
	 * @model required="true"
	 * @generated
	 */
	UncertaintyScenario getUncertaintyScenario();

	/**
	 * Sets the value of the '{@link uncertainty.UncertaintyLabel#getUncertaintyScenario <em>Uncertainty Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uncertainty Scenario</em>' attribute.
	 * @see uncertainty.UncertaintyScenario
	 * @see #getUncertaintyScenario()
	 * @generated
	 */
	void setUncertaintyScenario(UncertaintyScenario value);

} // UncertaintyLabel
