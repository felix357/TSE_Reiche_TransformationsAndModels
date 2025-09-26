/**
 */
package analysiscouplinggraph.impl;

import analysiscouplinggraph.*;

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
public class AnalysiscouplinggraphFactoryImpl extends EFactoryImpl implements AnalysiscouplinggraphFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AnalysiscouplinggraphFactory init() {
		try {
			AnalysiscouplinggraphFactory theAnalysiscouplinggraphFactory = (AnalysiscouplinggraphFactory)EPackage.Registry.INSTANCE.getEFactory(AnalysiscouplinggraphPackage.eNS_URI);
			if (theAnalysiscouplinggraphFactory != null) {
				return theAnalysiscouplinggraphFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AnalysiscouplinggraphFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnalysiscouplinggraphFactoryImpl() {
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
			case AnalysiscouplinggraphPackage.ANALYSIS_COMPONENT: return createAnalysisComponent();
			case AnalysiscouplinggraphPackage.REQUIRED_INTERFACE: return createRequiredInterface();
			case AnalysiscouplinggraphPackage.PROVIDED_INTERFACE: return createProvidedInterface();
			case AnalysiscouplinggraphPackage.CONNECTION: return createConnection();
			case AnalysiscouplinggraphPackage.EXTERNAL_SOURCE: return createExternalSource();
			case AnalysiscouplinggraphPackage.ANALYSIS_GRAPH: return createAnalysisGraph();
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
	public AnalysisComponent createAnalysisComponent() {
		AnalysisComponentImpl analysisComponent = new AnalysisComponentImpl();
		return analysisComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RequiredInterface createRequiredInterface() {
		RequiredInterfaceImpl requiredInterface = new RequiredInterfaceImpl();
		return requiredInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ProvidedInterface createProvidedInterface() {
		ProvidedInterfaceImpl providedInterface = new ProvidedInterfaceImpl();
		return providedInterface;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Connection createConnection() {
		ConnectionImpl connection = new ConnectionImpl();
		return connection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ExternalSource createExternalSource() {
		ExternalSourceImpl externalSource = new ExternalSourceImpl();
		return externalSource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnalysisGraph createAnalysisGraph() {
		AnalysisGraphImpl analysisGraph = new AnalysisGraphImpl();
		return analysisGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public AnalysiscouplinggraphPackage getAnalysiscouplinggraphPackage() {
		return (AnalysiscouplinggraphPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AnalysiscouplinggraphPackage getPackage() {
		return AnalysiscouplinggraphPackage.eINSTANCE;
	}

} //AnalysiscouplinggraphFactoryImpl
