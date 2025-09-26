/**
 */
package ReferenceMetamodel;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see ReferenceMetamodel.ReferenceMetamodelFactory
 * @model kind="package"
 * @generated
 */
public interface ReferenceMetamodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "ReferenceMetamodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.kit.edu/coupling/referencemetamodel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "crm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ReferenceMetamodelPackage eINSTANCE = ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link ReferenceMetamodel.impl.SecurityCharacteristicImpl <em>Security Characteristic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ReferenceMetamodel.impl.SecurityCharacteristicImpl
	 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSecurityCharacteristic()
	 * @generated
	 */
	int SECURITY_CHARACTERISTIC = 0;

	/**
	 * The number of structural features of the '<em>Security Characteristic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CHARACTERISTIC_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Security Characteristic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_CHARACTERISTIC_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ReferenceMetamodel.impl.AnnotationImpl <em>Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ReferenceMetamodel.impl.AnnotationImpl
	 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getAnnotation()
	 * @generated
	 */
	int ANNOTATION = 1;

	/**
	 * The feature id for the '<em><b>Annotates</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__ANNOTATES = 0;

	/**
	 * The feature id for the '<em><b>Characteristics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION__CHARACTERISTICS = 1;

	/**
	 * The number of structural features of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ReferenceMetamodel.impl.SystemElementImpl <em>System Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ReferenceMetamodel.impl.SystemElementImpl
	 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSystemElement()
	 * @generated
	 */
	int SYSTEM_ELEMENT = 2;

	/**
	 * The number of structural features of the '<em>System Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>System Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYSTEM_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ReferenceMetamodel.impl.ConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ReferenceMetamodel.impl.ConfigurationImpl
	 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getConfiguration()
	 * @generated
	 */
	int CONFIGURATION = 3;

	/**
	 * The feature id for the '<em><b>Security Policy</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__SECURITY_POLICY = 0;

	/**
	 * The feature id for the '<em><b>Security Characteristics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__SECURITY_CHARACTERISTICS = 1;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__ANNOTATIONS = 2;

	/**
	 * The feature id for the '<em><b>System Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION__SYSTEM_ELEMENTS = 3;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link ReferenceMetamodel.impl.SecurityPolicyImpl <em>Security Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see ReferenceMetamodel.impl.SecurityPolicyImpl
	 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSecurityPolicy()
	 * @generated
	 */
	int SECURITY_POLICY = 4;

	/**
	 * The feature id for the '<em><b>Security Characteristics</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_POLICY__SECURITY_CHARACTERISTICS = 0;

	/**
	 * The number of structural features of the '<em>Security Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_POLICY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Security Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SECURITY_POLICY_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link ReferenceMetamodel.SecurityCharacteristic <em>Security Characteristic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Characteristic</em>'.
	 * @see ReferenceMetamodel.SecurityCharacteristic
	 * @generated
	 */
	EClass getSecurityCharacteristic();

	/**
	 * Returns the meta object for class '{@link ReferenceMetamodel.Annotation <em>Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotation</em>'.
	 * @see ReferenceMetamodel.Annotation
	 * @generated
	 */
	EClass getAnnotation();

	/**
	 * Returns the meta object for the reference '{@link ReferenceMetamodel.Annotation#getAnnotates <em>Annotates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Annotates</em>'.
	 * @see ReferenceMetamodel.Annotation#getAnnotates()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Annotates();

	/**
	 * Returns the meta object for the reference list '{@link ReferenceMetamodel.Annotation#getCharacteristics <em>Characteristics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Characteristics</em>'.
	 * @see ReferenceMetamodel.Annotation#getCharacteristics()
	 * @see #getAnnotation()
	 * @generated
	 */
	EReference getAnnotation_Characteristics();

	/**
	 * Returns the meta object for class '{@link ReferenceMetamodel.SystemElement <em>System Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>System Element</em>'.
	 * @see ReferenceMetamodel.SystemElement
	 * @generated
	 */
	EClass getSystemElement();

	/**
	 * Returns the meta object for class '{@link ReferenceMetamodel.Configuration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see ReferenceMetamodel.Configuration
	 * @generated
	 */
	EClass getConfiguration();

	/**
	 * Returns the meta object for the reference '{@link ReferenceMetamodel.Configuration#getSecurityPolicy <em>Security Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Security Policy</em>'.
	 * @see ReferenceMetamodel.Configuration#getSecurityPolicy()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_SecurityPolicy();

	/**
	 * Returns the meta object for the reference list '{@link ReferenceMetamodel.Configuration#getSecurityCharacteristics <em>Security Characteristics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Security Characteristics</em>'.
	 * @see ReferenceMetamodel.Configuration#getSecurityCharacteristics()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_SecurityCharacteristics();

	/**
	 * Returns the meta object for the reference list '{@link ReferenceMetamodel.Configuration#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Annotations</em>'.
	 * @see ReferenceMetamodel.Configuration#getAnnotations()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_Annotations();

	/**
	 * Returns the meta object for the reference list '{@link ReferenceMetamodel.Configuration#getSystemElements <em>System Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>System Elements</em>'.
	 * @see ReferenceMetamodel.Configuration#getSystemElements()
	 * @see #getConfiguration()
	 * @generated
	 */
	EReference getConfiguration_SystemElements();

	/**
	 * Returns the meta object for class '{@link ReferenceMetamodel.SecurityPolicy <em>Security Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Security Policy</em>'.
	 * @see ReferenceMetamodel.SecurityPolicy
	 * @generated
	 */
	EClass getSecurityPolicy();

	/**
	 * Returns the meta object for the reference list '{@link ReferenceMetamodel.SecurityPolicy#getSecurityCharacteristics <em>Security Characteristics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Security Characteristics</em>'.
	 * @see ReferenceMetamodel.SecurityPolicy#getSecurityCharacteristics()
	 * @see #getSecurityPolicy()
	 * @generated
	 */
	EReference getSecurityPolicy_SecurityCharacteristics();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ReferenceMetamodelFactory getReferenceMetamodelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link ReferenceMetamodel.impl.SecurityCharacteristicImpl <em>Security Characteristic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ReferenceMetamodel.impl.SecurityCharacteristicImpl
		 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSecurityCharacteristic()
		 * @generated
		 */
		EClass SECURITY_CHARACTERISTIC = eINSTANCE.getSecurityCharacteristic();

		/**
		 * The meta object literal for the '{@link ReferenceMetamodel.impl.AnnotationImpl <em>Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ReferenceMetamodel.impl.AnnotationImpl
		 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getAnnotation()
		 * @generated
		 */
		EClass ANNOTATION = eINSTANCE.getAnnotation();

		/**
		 * The meta object literal for the '<em><b>Annotates</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__ANNOTATES = eINSTANCE.getAnnotation_Annotates();

		/**
		 * The meta object literal for the '<em><b>Characteristics</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATION__CHARACTERISTICS = eINSTANCE.getAnnotation_Characteristics();

		/**
		 * The meta object literal for the '{@link ReferenceMetamodel.impl.SystemElementImpl <em>System Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ReferenceMetamodel.impl.SystemElementImpl
		 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSystemElement()
		 * @generated
		 */
		EClass SYSTEM_ELEMENT = eINSTANCE.getSystemElement();

		/**
		 * The meta object literal for the '{@link ReferenceMetamodel.impl.ConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ReferenceMetamodel.impl.ConfigurationImpl
		 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getConfiguration()
		 * @generated
		 */
		EClass CONFIGURATION = eINSTANCE.getConfiguration();

		/**
		 * The meta object literal for the '<em><b>Security Policy</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__SECURITY_POLICY = eINSTANCE.getConfiguration_SecurityPolicy();

		/**
		 * The meta object literal for the '<em><b>Security Characteristics</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__SECURITY_CHARACTERISTICS = eINSTANCE.getConfiguration_SecurityCharacteristics();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__ANNOTATIONS = eINSTANCE.getConfiguration_Annotations();

		/**
		 * The meta object literal for the '<em><b>System Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION__SYSTEM_ELEMENTS = eINSTANCE.getConfiguration_SystemElements();

		/**
		 * The meta object literal for the '{@link ReferenceMetamodel.impl.SecurityPolicyImpl <em>Security Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ReferenceMetamodel.impl.SecurityPolicyImpl
		 * @see ReferenceMetamodel.impl.ReferenceMetamodelPackageImpl#getSecurityPolicy()
		 * @generated
		 */
		EClass SECURITY_POLICY = eINSTANCE.getSecurityPolicy();

		/**
		 * The meta object literal for the '<em><b>Security Characteristics</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SECURITY_POLICY__SECURITY_CHARACTERISTICS = eINSTANCE.getSecurityPolicy_SecurityCharacteristics();

	}

} //ReferenceMetamodelPackage
