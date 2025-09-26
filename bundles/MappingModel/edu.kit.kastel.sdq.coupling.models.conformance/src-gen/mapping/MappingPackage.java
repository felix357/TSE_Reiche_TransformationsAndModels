/**
 */
package mapping;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see mapping.MappingFactory
 * @model kind="package"
 * @generated
 */
public interface MappingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "mapping";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.example.org/mapping";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "map";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MappingPackage eINSTANCE = mapping.impl.MappingPackageImpl.init();

	/**
	 * The meta object id for the '{@link mapping.impl.MappingDefinitionImpl <em>Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mapping.impl.MappingDefinitionImpl
	 * @see mapping.impl.MappingPackageImpl#getMappingDefinition()
	 * @generated
	 */
	int MAPPING_DEFINITION = 0;

	/**
	 * The feature id for the '<em><b>Class Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_DEFINITION__CLASS_MAPPINGS = 0;

	/**
	 * The number of structural features of the '<em>Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_DEFINITION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_DEFINITION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link mapping.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mapping.impl.ClassMappingImpl
	 * @see mapping.impl.MappingPackageImpl#getClassMapping()
	 * @generated
	 */
	int CLASS_MAPPING = 1;

	/**
	 * The feature id for the '<em><b>Source Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__SOURCE_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Target Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__TARGET_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Feature Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING__FEATURE_MAPPINGS = 2;

	/**
	 * The number of structural features of the '<em>Class Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Class Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLASS_MAPPING_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link mapping.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see mapping.impl.FeatureMappingImpl
	 * @see mapping.impl.MappingPackageImpl#getFeatureMapping()
	 * @generated
	 */
	int FEATURE_MAPPING = 2;

	/**
	 * The feature id for the '<em><b>Source Feature URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__SOURCE_FEATURE_URI = 0;

	/**
	 * The feature id for the '<em><b>Target Feature URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__TARGET_FEATURE_URI = 1;

	/**
	 * The feature id for the '<em><b>Transformation Rule</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING__TRANSFORMATION_RULE = 2;

	/**
	 * The number of structural features of the '<em>Feature Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Feature Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_MAPPING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link mapping.MappingDefinition <em>Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Definition</em>'.
	 * @see mapping.MappingDefinition
	 * @generated
	 */
	EClass getMappingDefinition();

	/**
	 * Returns the meta object for the containment reference list '{@link mapping.MappingDefinition#getClassMappings <em>Class Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Class Mappings</em>'.
	 * @see mapping.MappingDefinition#getClassMappings()
	 * @see #getMappingDefinition()
	 * @generated
	 */
	EReference getMappingDefinition_ClassMappings();

	/**
	 * Returns the meta object for class '{@link mapping.ClassMapping <em>Class Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Class Mapping</em>'.
	 * @see mapping.ClassMapping
	 * @generated
	 */
	EClass getClassMapping();

	/**
	 * Returns the meta object for the reference '{@link mapping.ClassMapping#getSourceClass <em>Source Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source Class</em>'.
	 * @see mapping.ClassMapping#getSourceClass()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_SourceClass();

	/**
	 * Returns the meta object for the reference '{@link mapping.ClassMapping#getTargetClass <em>Target Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target Class</em>'.
	 * @see mapping.ClassMapping#getTargetClass()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_TargetClass();

	/**
	 * Returns the meta object for the containment reference list '{@link mapping.ClassMapping#getFeatureMappings <em>Feature Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Feature Mappings</em>'.
	 * @see mapping.ClassMapping#getFeatureMappings()
	 * @see #getClassMapping()
	 * @generated
	 */
	EReference getClassMapping_FeatureMappings();

	/**
	 * Returns the meta object for class '{@link mapping.FeatureMapping <em>Feature Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Mapping</em>'.
	 * @see mapping.FeatureMapping
	 * @generated
	 */
	EClass getFeatureMapping();

	/**
	 * Returns the meta object for the attribute '{@link mapping.FeatureMapping#getSourceFeatureURI <em>Source Feature URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source Feature URI</em>'.
	 * @see mapping.FeatureMapping#getSourceFeatureURI()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EAttribute getFeatureMapping_SourceFeatureURI();

	/**
	 * Returns the meta object for the attribute '{@link mapping.FeatureMapping#getTargetFeatureURI <em>Target Feature URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target Feature URI</em>'.
	 * @see mapping.FeatureMapping#getTargetFeatureURI()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EAttribute getFeatureMapping_TargetFeatureURI();

	/**
	 * Returns the meta object for the attribute '{@link mapping.FeatureMapping#getTransformationRule <em>Transformation Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Transformation Rule</em>'.
	 * @see mapping.FeatureMapping#getTransformationRule()
	 * @see #getFeatureMapping()
	 * @generated
	 */
	EAttribute getFeatureMapping_TransformationRule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MappingFactory getMappingFactory();

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
		 * The meta object literal for the '{@link mapping.impl.MappingDefinitionImpl <em>Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mapping.impl.MappingDefinitionImpl
		 * @see mapping.impl.MappingPackageImpl#getMappingDefinition()
		 * @generated
		 */
		EClass MAPPING_DEFINITION = eINSTANCE.getMappingDefinition();

		/**
		 * The meta object literal for the '<em><b>Class Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_DEFINITION__CLASS_MAPPINGS = eINSTANCE.getMappingDefinition_ClassMappings();

		/**
		 * The meta object literal for the '{@link mapping.impl.ClassMappingImpl <em>Class Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mapping.impl.ClassMappingImpl
		 * @see mapping.impl.MappingPackageImpl#getClassMapping()
		 * @generated
		 */
		EClass CLASS_MAPPING = eINSTANCE.getClassMapping();

		/**
		 * The meta object literal for the '<em><b>Source Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__SOURCE_CLASS = eINSTANCE.getClassMapping_SourceClass();

		/**
		 * The meta object literal for the '<em><b>Target Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__TARGET_CLASS = eINSTANCE.getClassMapping_TargetClass();

		/**
		 * The meta object literal for the '<em><b>Feature Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLASS_MAPPING__FEATURE_MAPPINGS = eINSTANCE.getClassMapping_FeatureMappings();

		/**
		 * The meta object literal for the '{@link mapping.impl.FeatureMappingImpl <em>Feature Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see mapping.impl.FeatureMappingImpl
		 * @see mapping.impl.MappingPackageImpl#getFeatureMapping()
		 * @generated
		 */
		EClass FEATURE_MAPPING = eINSTANCE.getFeatureMapping();

		/**
		 * The meta object literal for the '<em><b>Source Feature URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MAPPING__SOURCE_FEATURE_URI = eINSTANCE.getFeatureMapping_SourceFeatureURI();

		/**
		 * The meta object literal for the '<em><b>Target Feature URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MAPPING__TARGET_FEATURE_URI = eINSTANCE.getFeatureMapping_TargetFeatureURI();

		/**
		 * The meta object literal for the '<em><b>Transformation Rule</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_MAPPING__TRANSFORMATION_RULE = eINSTANCE.getFeatureMapping_TransformationRule();

	}

} //MappingPackage
