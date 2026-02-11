package propagationscalabilityevaluation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.AnalysiscouplinggraphFactory;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemUnderEval;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import propagation.AnalysisType;
import propagation.RoundRobinUncertaintyController;
import propagation.UncertaintyAnnotator;

/**
 * Evaluates the scalability of uncertainty propagation in coupled model-based
 * analyses for the CoCoMe System with a particular focus on the coupling
 * between Joana and EDFA.
 * 
 * Specifically, it evaluates the scalability of the uncertainty propagation by
 * measuring the runtime of propagation runs on coupling graphs with 2, 10, and
 * 100 analysis nodes.
 * 
 * The evaluation is based on CoCoMe system, documented in the results of
 * the uncertainty propagation evaluation.
 */
public class CoCoMeJoanaEDFAPropagationScalabilityEvaluation {

	// This test measures the runtime scalability of the uncertainty propagation
	// when applied to a coupling graph consisting of two analysis nodes (Joana
	// and EDFA) for the CoCoMe system.
	@Test
	public void testUncertaintyPropagationRuntimeTwoNodeCouplingGraph() throws Exception {

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		assertEquals(graph.getComponents().size(), 2);

		long startTime = System.nanoTime();
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		controller.propagateWithComponentInfo();
		long endTime = System.nanoTime();

		long runtimeNs = endTime - startTime;
		long runtimeMs = runtimeNs / 1_000_000;

		System.out.println("Uncertainty propagation runtime (2-node coupling graph): " + runtimeMs + " ms");
	}

	// This test measures the runtime scalability of the uncertainty propagation
	// on a ten node coupling graph created by repeating the same Joana and EDFA
	// analysis nodes
	// for the CoCoMe system.
	@Test
	public void testUncertaintyPropagationRuntimeTenNodeCouplingGraph() throws Exception {

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		// Build a graph with 10 nodes (5 repetitions of Joana + EDFA)
		AnalysisGraph g = buildRepeatedAnalysisGraph(graph, AnalysisType.JOANA, AnalysisType.EDFA, 4);

		assertEquals(g.getComponents().size(), 10);

		long startTime = System.nanoTime();

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		for (int i = 1; i < g.getComponents().size(); i += 1) {
			AnalysisComponent comp = g.getComponents().get(i);
			for (RequiredInterface req : comp.getInputs()) {
				annotator.annotateInterface(req, true);
			}
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(g);

		controller.propagateWithComponentInfo();

		long endTime = System.nanoTime();

		long runtimeNs = endTime - startTime;
		long runtimeMs = runtimeNs / 1_000_000;

		System.out.println("Uncertainty propagation runtime (10-node coupling graph): " + runtimeMs + " ms");
	}

	// This test measures the runtime scalability of the uncertainty propagation
	// on a 100-node coupling graph created by repeating the same Joana and EDFA
	// analysis nodes
	// for the CoCoMe system.
	@Test
	public void testUncertaintyPropagationRuntimeHundredNodeCouplingGraph() throws Exception {

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

		// Build the base 2-node graph
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		// Build a graph with 100 nodes (50 repetitions of Joana + EDFA)
		AnalysisGraph g = buildRepeatedAnalysisGraph(graph, AnalysisType.JOANA, AnalysisType.EDFA, 49);

		assertEquals(g.getComponents().size(), 100);

		long startTime = System.nanoTime();

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		// Annotate the second node of each 2-node pair
		for (int i = 1; i < g.getComponents().size(); i += 1) {
			AnalysisComponent comp = g.getComponents().get(i);
			for (RequiredInterface req : comp.getInputs()) {
				annotator.annotateInterface(req, true);
			}
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(g);

		controller.propagateWithComponentInfo();

		long endTime = System.nanoTime();

		long runtimeNs = endTime - startTime;
		long runtimeMs = runtimeNs / 1_000_000;

		System.out.println("Uncertainty propagation runtime (100-node coupling graph): " + runtimeMs + " ms");
	}
	
	private AnalysisGraph buildRepeatedAnalysisGraph(AnalysisGraph graph, AnalysisType nodeType1,
	        AnalysisType nodeType2, int repetitions) throws Exception {

	    AnalysisGraph baseGraph = buildAnalysisGraph(nodeType1, nodeType2);

	    AnalysisComponent lastNode = graph.getComponents().get(graph.getComponents().size() - 1);

	    for (int i = 0; i < repetitions; i++) {
	        AnalysisComponent copyA = EcoreUtil.copy(baseGraph.getComponents().get(0));
	        AnalysisComponent copyB = EcoreUtil.copy(baseGraph.getComponents().get(1));

	        graph.getComponents().add(copyA);
	        graph.getComponents().add(copyB);

	        Connection internalConn = EcoreUtil.copy(baseGraph.getConnections().get(0));
	        internalConn.setFrom(copyA.getOutputs().get(0));
	        internalConn.setTo(copyB.getInputs().get(0));
	        graph.getConnections().add(internalConn);

	        if (lastNode != null) {
	            Connection crossConn = AnalysiscouplinggraphFactory.eINSTANCE.createConnection();
	            crossConn.setFrom(lastNode.getOutputs().get(0));
	            crossConn.setTo(copyA.getInputs().get(0));
	            graph.getConnections().add(crossConn);
	        }

	        lastNode = copyB;
	    }

	    return graph;
	}


	public AnalysisGraph buildAnalysisGraph(AnalysisType sourceCodeAnalysis, AnalysisType architecturalAnalysis)
			throws Exception {
		AnalysiscouplinggraphFactory graphFactory = AnalysiscouplinggraphFactory.eINSTANCE;

		// ---------------------------
		// ResourceSet erstellen
		// ---------------------------
		ResourceSet resSet = new ResourceSetImpl();
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());

		// ---------------------------
		// MappingPackage registrieren
		// ---------------------------
		MappingPackage.eINSTANCE.eClass();
		resSet.getPackageRegistry().put(MappingPackage.eNS_URI, MappingPackage.eINSTANCE);

		// ---------------------------
		// Relevante EPackages laden
		// ---------------------------
		List<String> ecorePaths = List.of(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.identifier/model/identifier.ecore",
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.java/model/java.ecore",
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/DataDictionaryCharacterized.ecore",
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/pcm.ecore",
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore",
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore",
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.codeql/model/codeql.ecore",
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.extension.dataflowanalysis.parameterannotation/model/parameterannotation.ecore");

		for (String path : ecorePaths) {
			registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet, path));
		}

		// ---------------------------
		// Mapping laden basierend auf Enum
		// ---------------------------
		MappingDefinition sourceCodeInputMapping = loadMapping(resSet, sourceCodeAnalysis.getInputMappingPath());
		MappingDefinition sourceCodeOutputMapping = loadMapping(resSet, sourceCodeAnalysis.getOutputMappingPath());

		MappingDefinition architecturalMapping = loadMapping(resSet, architecturalAnalysis.getInputMappingPath());

		// ---------------------------
		// Proxies auflösen
		// ---------------------------
		EcoreUtil.resolveAll(resSet);

		// ---------------------------
		// AnalysisGraph bauen
		// ---------------------------
		AnalysisGraph graph = graphFactory.createAnalysisGraph();

		// Source code analysis component
		AnalysisComponent sourceCodeComp = graphFactory.createAnalysisComponent();
		sourceCodeComp.setName(sourceCodeAnalysis.name());

		ProvidedInterface sourceCodeProvided = graphFactory.createProvidedInterface();
		sourceCodeProvided.setOwner(sourceCodeComp);
		sourceCodeProvided.setMappingModel(sourceCodeOutputMapping);

		RequiredInterface sourceCodeRequired = graphFactory.createRequiredInterface();
		sourceCodeRequired.setOwner(sourceCodeComp);
		sourceCodeRequired.setMappingModel(sourceCodeInputMapping);

		sourceCodeComp.getOutputs().add(sourceCodeProvided);
		sourceCodeComp.getInputs().add(sourceCodeRequired);

		// Architectural analysis component
		AnalysisComponent archComp = graphFactory.createAnalysisComponent();
		archComp.setName(architecturalAnalysis.name());

		RequiredInterface archRequired = graphFactory.createRequiredInterface();
		archRequired.setOwner(archComp);
		archRequired.setMappingModel(architecturalMapping);

		ProvidedInterface archProvided = graphFactory.createProvidedInterface();
		archProvided.setOwner(archComp);

		archComp.getInputs().add(archRequired);
		archComp.getOutputs().add(archProvided);

		// Connection
		Connection conn = graphFactory.createConnection();
		conn.setFrom(sourceCodeProvided);
		conn.setTo(archRequired);

		graph.getComponents().add(sourceCodeComp);
		graph.getComponents().add(archComp);
		graph.getConnections().add(conn);

		return graph;
	}

	private static EPackage loadAndRegisterEPackage(ResourceSet resSet, String path) {
		try {
			Resource res = resSet.getResource(URI.createFileURI(path), true);
			res.load(Collections.EMPTY_MAP);
			EPackage pkg = (EPackage) res.getContents().get(0);
			registerEPackageRecursively(resSet, pkg);
			System.out.println("EPackage geladen: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
			return pkg;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fehler beim Laden des EPackage: " + path);
			return null;
		}
	}

	private static MappingDefinition loadMapping(ResourceSet resSet, String mappingFilePath) {
		try {
			Resource mappingResource = resSet.getResource(URI.createFileURI(mappingFilePath), true);
			mappingResource.load(Collections.EMPTY_MAP);
			MappingDefinition mapping = (MappingDefinition) mappingResource.getContents().get(0);
			System.out.println("Mapping XMI erfolgreich geladen: " + mappingFilePath);
			return mapping;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Fehler beim Laden der Mapping XMI-Datei: " + mappingFilePath);
			return null;
		}
	}

	private static void registerEPackageRecursively(ResourceSet resSet, EPackage pkg) {
		if (pkg == null)
			return;
		resSet.getPackageRegistry().put(pkg.getNsURI(), pkg);
		System.out.println("EPackage registriert: " + pkg.getName() + " (nsURI=" + pkg.getNsURI() + ")");
		for (EPackage subPkg : pkg.getESubpackages()) {
			registerEPackageRecursively(resSet, subPkg);
		}
	}

	private ResourceSet createResourceSet() {
		// Create ResourceSet
		ResourceSet resSet = new ResourceSetImpl();
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());

		// Register MappingPackage
		MappingPackage.eINSTANCE.eClass();
		resSet.getPackageRegistry().put(MappingPackage.eNS_URI, MappingPackage.eINSTANCE);

		// Load relevant EPackages
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.identifier/model/identifier.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.java/model/java.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/DataDictionaryCharacterized.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/pcm.ecore"));

		// Input-Referencemetamodell
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");
		registerEPackageRecursively(resSet, inputRefMeta);

		// Output-Referencemetamodell
		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");
		registerEPackageRecursively(resSet, outputRefMeta);

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.codeql/model/codeql.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.extension.dataflowanalysis.parameterannotation/model/parameterannotation.ecore"));
		return resSet;
	}
}
