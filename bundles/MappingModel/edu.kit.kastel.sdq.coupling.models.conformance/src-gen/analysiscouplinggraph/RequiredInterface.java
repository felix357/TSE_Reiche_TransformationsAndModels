/**
 */
package analysiscouplinggraph;

import ReferenceMetamodel.Annotation;

import mapping.MappingDefinition;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import uncertainty.UncertaintyLabel;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Required Interface</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link analysiscouplinggraph.RequiredInterface#getUncertaintyLabel <em>Uncertainty Label</em>}</li>
 *   <li>{@link analysiscouplinggraph.RequiredInterface#getOwner <em>Owner</em>}</li>
 *   <li>{@link analysiscouplinggraph.RequiredInterface#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link analysiscouplinggraph.RequiredInterface#getMappingModel <em>Mapping Model</em>}</li>
 * </ul>
 *
 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getRequiredInterface()
 * @model
 * @generated
 */
public interface RequiredInterface extends EObject {
	/**
	 * Returns the value of the '<em><b>Uncertainty Label</b></em>' containment reference list.
	 * The list contents are of type {@link uncertainty.UncertaintyLabel}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uncertainty Label</em>' containment reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getRequiredInterface_UncertaintyLabel()
	 * @model containment="true"
	 * @generated
	 */
	EList<UncertaintyLabel> getUncertaintyLabel();

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' reference.
	 * @see #setOwner(AnalysisComponent)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getRequiredInterface_Owner()
	 * @model required="true"
	 * @generated
	 */
	AnalysisComponent getOwner();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.RequiredInterface#getOwner <em>Owner</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' reference.
	 * @see #getOwner()
	 * @generated
	 */
	void setOwner(AnalysisComponent value);

	/**
	 * Returns the value of the '<em><b>Annotations</b></em>' reference list.
	 * The list contents are of type {@link ReferenceMetamodel.Annotation}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Annotations</em>' reference list.
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getRequiredInterface_Annotations()
	 * @model required="true"
	 * @generated
	 */
	EList<Annotation> getAnnotations();

	/**
	 * Returns the value of the '<em><b>Mapping Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapping Model</em>' reference.
	 * @see #setMappingModel(MappingDefinition)
	 * @see analysiscouplinggraph.AnalysiscouplinggraphPackage#getRequiredInterface_MappingModel()
	 * @model required="true"
	 * @generated
	 */
	MappingDefinition getMappingModel();

	/**
	 * Sets the value of the '{@link analysiscouplinggraph.RequiredInterface#getMappingModel <em>Mapping Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapping Model</em>' reference.
	 * @see #getMappingModel()
	 * @generated
	 */
	void setMappingModel(MappingDefinition value);

} // RequiredInterface
