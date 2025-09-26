/**
 */
package ReferenceMetamodel.impl;

import ReferenceMetamodel.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ReferenceMetamodelFactoryImpl extends EFactoryImpl implements ReferenceMetamodelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ReferenceMetamodelFactory init() {
		try {
			ReferenceMetamodelFactory theReferenceMetamodelFactory = (ReferenceMetamodelFactory)EPackage.Registry.INSTANCE.getEFactory(ReferenceMetamodelPackage.eNS_URI);
			if (theReferenceMetamodelFactory != null) {
				return theReferenceMetamodelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ReferenceMetamodelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReferenceMetamodelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ReferenceMetamodelPackage.SECURITY_CHARACTERISTIC: return createSecurityCharacteristic();
			case ReferenceMetamodelPackage.ANNOTATION: return createAnnotation();
			case ReferenceMetamodelPackage.SYSTEM_ELEMENT: return createSystemElement();
			case ReferenceMetamodelPackage.CONFIGURATION: return createConfiguration();
			case ReferenceMetamodelPackage.SECURITY_POLICY: return createSecurityPolicy();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SecurityCharacteristic createSecurityCharacteristic() {
		SecurityCharacteristicImpl securityCharacteristic = new SecurityCharacteristicImpl();
		return securityCharacteristic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Annotation createAnnotation() {
		AnnotationImpl annotation = new AnnotationImpl();
		return annotation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SystemElement createSystemElement() {
		SystemElementImpl systemElement = new SystemElementImpl();
		return systemElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Configuration createConfiguration() {
		ConfigurationImpl configuration = new ConfigurationImpl();
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SecurityPolicy createSecurityPolicy() {
		SecurityPolicyImpl securityPolicy = new SecurityPolicyImpl();
		return securityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ReferenceMetamodelPackage getReferenceMetamodelPackage() {
		return (ReferenceMetamodelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ReferenceMetamodelPackage getPackage() {
		return ReferenceMetamodelPackage.eINSTANCE;
	}

} //ReferenceMetamodelFactoryImpl
