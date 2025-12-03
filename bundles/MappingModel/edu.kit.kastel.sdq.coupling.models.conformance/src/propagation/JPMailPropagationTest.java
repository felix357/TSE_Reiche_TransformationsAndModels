package propagation;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
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
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.ReferenceMetaModelConformanceChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemUnderEval;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import uncertainty.UncertaintySource;

/**
 * Evaluates the accuracy of uncertainty propagation in coupled model-based
 * analyses. Specifically, it assesses whether the computed impact set
 * accurately reflects the uncertainties present in the affected set, measuring
 * both the precision and recall of the propagation results.
 * 
 * The evaluation is based on representative example uncertainties identified
 * for the JPMail system, as documented in the results of the uncertainty
 * propagation evaluation.
 */
public class JPMailPropagationTest {

	@Test
	public void graphWithNoUncertaintiesTest() throws Exception {
		AnalysisGraph graph = buildAnalysisGraph();

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> uncertainties = controller
				.propagateWithComponentInfo();

		assertTrue(uncertainties.isEmpty());
	}

	// Tests Case 1 for (IC1) uncertainty propagation evaluation:
	// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
	// EDFA.
	@Test
	public void graphWithIC1MappingValidTest() throws Exception {

		// first case mapping valid -> Uncertainty Scenario: correct input data
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker modelChecker = new IC1MChecker(cfg);

		IC1IChecker instanceChecker = new IC1IChecker(cfg);

		AnalysisGraph graph = buildAnalysisGraph();

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC1ModelChecker(modelChecker)
				.withIC1InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> result = controller.propagateWithComponentInfo();

		List<String> impactSet = result.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC1) uncertainty propagation evaluation:
	// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
	// EDFA.
	@Test
	public void graphWithIC1MappingInValidTest() throws Exception {
		// second case mapping invalid -> Uncertainty Scenario: Non-conformance to input
		// interface
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		cfg.overrideModelCorrespondence("correspondences.edfacodeqlcorrespondences_invalid_structure");

		IC1MChecker modelChecker = new IC1MChecker(cfg);

		IC1IChecker instanceChecker = new IC1IChecker(cfg);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC1ModelChecker(modelChecker)
				.withIC1InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");
		assertEquals(expectedImpactSet, impactSet);
		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for (IC1) uncertainty propagation evaluation:
	// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
	// EDFA.
	@Test
	public void graphWithIC1ImpreciseInputDataTest() throws Exception {
		// third case imprecise input data represented in codeqlresults -> Uncertainty
		// Scenario: imprecise input data
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		cfg.overrideCodeQL("codeql4extendeddataflow_impre.codeql");

		IC1MChecker modelChecker = new IC1MChecker(cfg);

		IC1IChecker instanceChecker = new IC1IChecker(cfg);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC1ModelChecker(modelChecker)
				.withIC1InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for (IC2) uncertainty propagation evaluation:
	// (IC2) Missing or inconsistent code–architecture correspondences.
	@Test
	public void graphWithIC2CodeArchcorrespondencesValidTest() throws Exception {

		// first case correspondences valid -> Uncertainty Scenario: correct input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker modelChecker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);

		IC2IChecker instanceChecker = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC2ModelChecker(modelChecker)
				.withIC2InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC2) uncertainty propagation evaluation:
	// (IC2) Missing or inconsistent code–architecture correspondences.
	@Test
	public void graphWithIC2CodeArchcorrespondencesInValidTest() throws Exception {

		// second case correspondences invalid -> Uncertainty Scenario: Non-conformance
		// to input interface
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence_invalid";

		IC2MChecker modelChecker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);

		IC2IChecker instanceChecker = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC2ModelChecker(modelChecker)
				.withIC2InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for (IC2) uncertainty propagation evaluation:
	// (IC2) Missing or inconsistent code–architecture correspondences.
	@Test
	public void graphWithIC2CodeArchcorrespondencesImPreciseInputDataTest() throws Exception {

		// Third case correspondences invalid -> Uncertainty Scenario: Imprecise input
		// data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences_imprecise.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker modelChecker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);

		IC2IChecker instanceChecker = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC2ModelChecker(modelChecker)
				.withIC2InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for (IC3) uncertainty propagation evaluation:
	// (IC3) Uncertainty about the existence and consistency of security annotations
	// in the annotated source code model.
	@Test
	public void graphWithIC3SecurityAnnoationsConsistetInputDataTest() throws Exception {

		// First case security annoations consistet -> Uncertainty Scenario: correct
		// input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker checker1 = new IC1MChecker(cfg);

		checker1.runCheck();

		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker checker2 = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker modelChecker = new IC3MChecker(basePath,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/codeql4extendeddataflow.codeql",
				secLiterals, systemElementsFromIC2, configurationsFromIC2);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker instanceChecker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap,
				sysElements, configs);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC3ModelChecker(modelChecker)
				.withIC3InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC3) uncertainty propagation evaluation:
	// (IC3) Uncertainty about the existence and consistency of security annotations
	// in the annotated source code model.
	@Test
	public void graphWithIC3SecurityAnnoationsInConsistetInputDataTest() throws Exception {

		// Second case security annoations inconsistet -> Uncertainty Scenario:
		// Non-conformance to input interface
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);
		cfg.overrideCodeQL("codeql4extendeddataflow_invalidSecurityLevels.codeql");
		IC1MChecker checker1 = new IC1MChecker(cfg);

		checker1.runCheck();

		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker checker2 = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker modelChecker = new IC3MChecker(basePath,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/codeql4extendeddataflow.codeql",
				secLiterals, systemElementsFromIC2, configurationsFromIC2);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker instanceChecker = new IC3IChecker(basePath, "codeql4extendeddataflow_invalidSecurityLevels.codeql",
				codeqlRivMap, sysElements, configs);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC3ModelChecker(modelChecker)
				.withIC3InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for (IC3) uncertainty propagation evaluation:
	// (IC3) Uncertainty about the existence and consistency of security annotations
	// in the annotated source code model.
	@Test
	public void graphWithIC3SecurityAnnoationsimpreciseInputDataTest() throws Exception {

		// 3. case security annoations imprecise -> Uncertainty Scenario:
		// Imprecise input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);
		cfg.overrideCodeQL("codeql4extendeddataflow_impre.codeql");
		IC1MChecker checker1 = new IC1MChecker(cfg);

		checker1.runCheck();

		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker checker2 = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker modelChecker = new IC3MChecker(basePath,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/codeql4extendeddataflow.codeql",
				secLiterals, systemElementsFromIC2, configurationsFromIC2);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker instanceChecker = new IC3IChecker(basePath, "codeql4extendeddataflow_impre.codeql", codeqlRivMap,
				sysElements, configs);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC3ModelChecker(modelChecker)
				.withIC3InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for (IC4) uncertainty propagation evaluation:
	// (IC4) Uncertainty if linkages between security policies and security
	// characteristics are incorrect or missing.
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsValidInputDataTest()
			throws Exception {
		// first case valid -> Uncertainty Scenario: correct input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		IC4MChecker modelChecker = new IC4MChecker(basePath, "jpmail.pddc", "correspondences.edfacodeqlcorrespondences",
				"codeql4extendeddataflow.codeql", "codeql4extendeddataflow.configurationrepresentation");

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);
		
		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker instanceChecker = new IC4IChecker(basePath, "codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap, rivValuesMap);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC4ModelChecker(modelChecker)
				.withIC4InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC4) uncertainty propagation evaluation:
	// (IC4) Uncertainty if linkages between security policies and security
	// characteristics are incorrect or missing.
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsInValidInputDataTest()
			throws Exception {
		// second case valid -> Uncertainty Scenario: Non-conformance to input interface
		// (LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsInValid)
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		IC4MChecker modelChecker = new IC4MChecker(basePath, "jpmail.pddc", "correspondences.edfacodeqlcorrespondences",
				"codeql4extendeddataflow.codeql", "codeql4extendeddataflow.configurationrepresentation");

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);
		
		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_incorrect");
		
		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker instanceChecker = new IC4IChecker(basePath, "codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap, rivValuesMap);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC4ModelChecker(modelChecker)
				.withIC4InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for (IC4) uncertainty propagation evaluation:
	// (IC4) Uncertainty if linkages between security policies and security
	// characteristics are incorrect or missing.
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsImpreciseInputDataTest()
			throws Exception {
		// third case valid -> Uncertainty Scenario: Imprecise input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";

		IC4MChecker modelChecker = new IC4MChecker(basePath, "jpmail.pddc", "correspondences.edfacodeqlcorrespondences",
				"codeql4extendeddataflow.codeql", "codeql4extendeddataflow.configurationrepresentation");
		
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);
		
		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_imprecise");
		
		IC1IChecker c1 = new IC1IChecker(cfg);
		
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker instanceChecker = new IC4IChecker(basePath, "codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap, rivValuesMap);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withIC4ModelChecker(modelChecker)
				.withIC4InstanceChecker(instanceChecker).withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToApproximationInCodeQlTest() throws Exception {
		// first case -> Uncertainty Scenario: correct analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: METHODOLOGY_APPROXIMATION",
				"CodeQL: METHODOLOGY_OVER_SIMPLIFICATION", "CodeQL: METHODOLOGY_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "CodeQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: METHODOLOGY_CORRECT", "CodeQL: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithLossOfAccuracyDueToApproximationInCodeQlTest() throws Exception {
		// Second case -> Uncertainty Scenario: approximation in analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: METHODOLOGY_APPROXIMATION",
				"CodeQL: METHODOLOGY_OVER_SIMPLIFICATION", "CodeQL: METHODOLOGY_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "CodeQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: METHODOLOGY_APPROXIMATION", "CodeQL: OUTPUT_IMPRECISION",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithLossOfAccuracyDueToOverSimplificationInCodeQlTest() throws Exception {
		// Third case -> Uncertainty Scenario: over simplification in analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: METHODOLOGY_APPROXIMATION",
				"CodeQL: METHODOLOGY_OVER_SIMPLIFICATION", "CodeQL: METHODOLOGY_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "CodeQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: METHODOLOGY_OVER_SIMPLIFICATION", "CodeQL: OUTPUT_ERROR",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Scenario-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToScenarioInCodeQlTest() throws Exception {
		// first case -> Uncertainty Scenario: Scenario definition of analysis correct

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: SCENARIO_DEFINITION_CORRECT",
				"CodeQL: SCENARIO_DEFINITION_INCORRECT", "CodeQL: OUTPUT_CORRECT", "CodeQL: OUTPUT_ERROR",
				"CodeQL: OUTPUT_IMPRECISION", "EDFA: CORRECT_INPUT_DATA", "EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_IMPRECISION");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: SCENARIO_DEFINITION_CORRECT", "CodeQL: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// Scenario-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithLossOfAccuracyDueToScenarioInCodeQlTest() throws Exception {
		// second case -> Uncertainty Scenario: Scenario definition of analysis correct

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: SCENARIO_DEFINITION_CORRECT",
				"CodeQL: SCENARIO_DEFINITION_INCORRECT", "CodeQL: OUTPUT_CORRECT", "CodeQL: OUTPUT_ERROR",
				"CodeQL: OUTPUT_IMPRECISION", "EDFA: CORRECT_INPUT_DATA", "EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_IMPRECISION");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: SCENARIO_DEFINITION_INCORRECT", "CodeQL: OUTPUT_ERROR",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToModelingInCodeQlTest() throws Exception {
		// first case -> Uncertainty Scenario: correct model

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: MODEL_CORRECT", "CodeQL: MODEL_UNDER_SPECIFICATION",
				"CodeQL: MODEL_DISCREPANCY", "CodeQL: OUTPUT_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: MODEL_CORRECT", "CodeQL: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithLossOfAccuracyDueToModelingInCodeQlTest() throws Exception {
		// second case -> Uncertainty Scenario: model under specification

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: MODEL_CORRECT", "CodeQL: MODEL_UNDER_SPECIFICATION",
				"CodeQL: MODEL_DISCREPANCY", "CodeQL: OUTPUT_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: MODEL_UNDER_SPECIFICATION", "CodeQL: OUTPUT_IMPRECISION",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Source Code Analysis
	@Test
	public void graphWithLossOfAccuracyDueToModelingDiscInCodeQlTest() throws Exception {
		// third case -> Uncertainty Scenario: discrapencies between model and
		// implementation

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: MODEL_CORRECT", "CodeQL: MODEL_UNDER_SPECIFICATION",
				"CodeQL: MODEL_DISCREPANCY", "CodeQL: OUTPUT_CORRECT", "CodeQL: OUTPUT_IMPRECISION",
				"CodeQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: MODEL_DISCREPANCY", "CodeQL: OUTPUT_ERROR",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToApproximationInEDFATest() throws Exception {
		// first case -> Uncertainty Scenario: correct analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: METHODOLOGY_APPROXIMATION",
				"EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: METHODOLOGY_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: METHODOLOGY_CORRECT", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithLossOfAccuracyDueToApproximationInEDFATest() throws Exception {
		// second case -> Uncertainty Scenario: approximation in analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: METHODOLOGY_APPROXIMATION",
				"EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: METHODOLOGY_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: METHODOLOGY_APPROXIMATION", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for Uncertainty if loss of accuracy occurs due to
	// methodology-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithLossOfAccuracyDueToOverSimplificationInEDFATest() throws Exception {
		// third case -> Uncertainty Scenario: over simplification in analysis

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: METHODOLOGY_APPROXIMATION",
				"EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: METHODOLOGY_CORRECT", "EDFA: OUTPUT_IMPRECISION",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_CORRECT");
		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Scenario-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToScenarioInEDFATest() throws Exception {
		// first case -> Uncertainty Scenario: Scenario definition of analysis correct

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: SCENARIO_DEFINITION_CORRECT",
				"EDFA: SCENARIO_DEFINITION_INCORRECT", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_ERROR",
				"EDFA: OUTPUT_IMPRECISION");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: SCENARIO_DEFINITION_CORRECT", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// Scenario-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithLossOfAccuracyDueToScenarioInEDFATest() throws Exception {
		// second case -> Uncertainty Scenario: Scenario definition of analysis
		// incorrect

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: SCENARIO_DEFINITION_CORRECT",
				"EDFA: SCENARIO_DEFINITION_INCORRECT", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_ERROR",
				"EDFA: OUTPUT_IMPRECISION");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: SCENARIO_DEFINITION_INCORRECT", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithNoLossOfAccuracyDueToModelingInEDFATest() throws Exception {
		// first case -> Uncertainty Scenario: correct model

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: MODEL_CORRECT", "EDFA: MODEL_UNDER_SPECIFICATION",
				"EDFA: MODEL_DISCREPANCY", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: MODEL_CORRECT", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithLossOfAccuracyDueToModelingUnderSpecInEDFATest() throws Exception {
		// second case -> Uncertainty Scenario: model under specification

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: MODEL_CORRECT", "EDFA: MODEL_UNDER_SPECIFICATION",
				"EDFA: MODEL_DISCREPANCY", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: MODEL_UNDER_SPECIFICATION", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for Uncertainty if loss of accuracy occurs due to
	// Modeling-induced uncertainty in Architectural Analysis
	@Test
	public void graphWithLossOfAccuracyDueToModelingDiscrapancyInEDFATest() throws Exception {
		// third case -> Uncertainty Scenario: discrapencies between model and
		// implementation

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateAnalysisComponent(eDFAAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA: MODEL_CORRECT", "EDFA: MODEL_UNDER_SPECIFICATION",
				"EDFA: MODEL_DISCREPANCY", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA: MODEL_DISCREPANCY", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Orchestration-decision-induced uncertainty in Coupling graph.
	@Test
	public void graphWithNoLossOfAccuracyDueToOrchestrationTest() throws Exception {
		// first case -> Uncertainty scenario: final analysis orchestration

		AnalysisGraph graph = buildAnalysisGraph();

		RequiredInterface eDFAReq = graph.getComponents().get(1).getInputs().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateInterfaceWithUncertaintyAnnoation(eDFAReq, UncertaintySource.ORCHESTRATION_DECISION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA input: ORCHESTRATION_NOT_FINAL",
				"EDFA input: ORCHESTRATION_FINAL", "EDFA output: ORCHESTRATION_NOT_FINAL",
				"EDFA output: ORCHESTRATION_FINAL");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA input: ORCHESTRATION_FINAL", "EDFA output: ORCHESTRATION_FINAL");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for Uncertainty if loss of accuracy occurs due to
	// Orchestration-decision-induced uncertainty in Coupling graph.
	@Test
	public void graphWithLossOfAccuracyDueToOrchestrationTest() throws Exception {
		// second case -> Uncertainty scenario: Not final analysis orchestration

		AnalysisGraph graph = buildAnalysisGraph();

		RequiredInterface eDFAReq = graph.getComponents().get(1).getInputs().get(0);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder().withInputReferenceConformance(true)
				.withOutputReferenceConformance(true).build();

		annotator.annotateInterfaceWithUncertaintyAnnoation(eDFAReq, UncertaintySource.ORCHESTRATION_DECISION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("EDFA input: ORCHESTRATION_NOT_FINAL",
				"EDFA input: ORCHESTRATION_FINAL", "EDFA output: ORCHESTRATION_NOT_FINAL",
				"EDFA output: ORCHESTRATION_FINAL");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("EDFA input: ORCHESTRATION_NOT_FINAL",
				"EDFA output: ORCHESTRATION_NOT_FINAL");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithCompleteReferenceMetamodelMappingTest() throws Exception {

		// Case 1: All Reference-Class Mappings valid (edfaInputConforms,
		// codeqlInputConforms and codeqlOutputConforms are true)
		// first case mapping valid -> Uncertainty Scenario: correct input data
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		String codeqlOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping.xmi";
		MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

		String edfaInputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMappingTest.xmi";
		MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		AnalysisGraph graph = buildAnalysisGraph();

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
		annotatorCodeQLInput.annotateInterface(codeQlReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("CodeQL: IMPRECISE_INPUT_DATA", "CodeQL: CORRECT_INPUT_DATA",
				"CodeQL: OUTPUT_IMPRECISION", "CodeQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: CORRECT_INPUT_DATA", "CodeQL: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithIncompleteReferenceMetamodelMappingCodeQlInputTest() throws Exception {

		// Case 2: CodeQL input incomplete mapping to reference metamodel.
		// second case mapping invalid -> Uncertainty Scenario: Non-conformance to input
		// interface
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping_incomplete.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		String codeqlOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping.xmi";
		MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

		String edfaInputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMappingTest.xmi";
		MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		AnalysisGraph graph = buildAnalysisGraph();

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
		annotatorCodeQLInput.annotateInterface(codeQlReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CodeQL: IMPRECISE_INPUT_DATA",
				"CodeQL: NON_CONFORMANCE_TO_INPUT_INTERFACE", "CodeQL: OUTPUT_IMPRECISION", "CodeQL: OUTPUT_ERROR",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: CORRECT_INPUT_DATA", "EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE",
				"EDFA: OUTPUT_IMPRECISION", "EDFA: OUTPUT_CORRECT", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		List<String> affectedSet = List.of("CodeQL: NON_CONFORMANCE_TO_INPUT_INTERFACE", "CodeQL: OUTPUT_ERROR",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 3 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithIncompleteReferenceMetamodelMappingCodeQlOutputandEDFAInputTest() throws Exception {

		// Case 3: CodeQL output and EDFA input have incomplete mapping to reference
		// metamodel.
		// third case mapping incomplete -> Uncertainty Scenario: Non-conformance to
		// input interface
		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		String codeqlOutputMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping_Incomplete.xmi";
		MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

		String edfaInputMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/EDFAInputMapping_Incomplete.xmi";
		MappingDefinition edfaInputMapping = loadMapping(resSet, edfaInputMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		AnalysisGraph graph = buildAnalysisGraph();
		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
		annotatorCodeQLInput.annotateInterface(codeQlReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("CodeQL: CORRECT_INPUT_DATA", "CodeQL: OUTPUT_CORRECT",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertTrue(codeqlInputConforms);
		assertFalse(codeqlOutputConforms);
		assertFalse(edfaInputConforms);
		assertNotEquals(affectedSet, impactSet);
	}

	public AnalysisGraph buildAnalysisGraph() {
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
		// Alle relevanten EPackages laden
		// ---------------------------
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.identifier/model/identifier.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.java/model/java.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/DataDictionaryCharacterized.ecore"));

		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/pcm.ecore"));

		// Input-Referenzmetamodell
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");
		registerEPackageRecursively(resSet, inputRefMeta);

		// Output-Referenzmetamodell
		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");
		registerEPackageRecursively(resSet, outputRefMeta);

		// CodeQL-spezifische Ecore
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.codeql/model/codeql.ecore"));

		// parameterannotation-spezifische Ecore
		registerEPackageRecursively(resSet, loadAndRegisterEPackage(resSet,
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels/bundles/Metamodels/edu.kit.kastel.sdq.coupling.models.extension.dataflowanalysis.parameterannotation/model/parameterannotation.ecore"));

		// ---------------------------
		// Mapping laden
		// ---------------------------
		String codeqlMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		String codeqlOutputMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlOutputMapping.xmi";
		MappingDefinition codeqlOutputMapping = loadMapping(resSet, codeqlOutputMappingPath);

		// ---------------------------
		// Proxies auflösen
		// ---------------------------
		EcoreUtil.resolveAll(resSet);

		// Debug: prüfen ob TargetClasses korrekt aufgelöst wurden
		codeqlInputMapping.getClassMappings().forEach(cm -> {
			EClass target = cm.getTargetClass();
			if (target != null && !target.eIsProxy()) {
				System.out.println("[DEBUG] Target class resolved: " + target.getName());
			} else {
				System.err.println("[DEBUG] Target class unresolved: " + EcoreUtil.getURI(target));
			}
		});

		// ---------------------------
		// AnalysisGraph bauen
		// ---------------------------
		AnalysisComponent codeql = graphFactory.createAnalysisComponent();
		codeql.setName("CodeQL");

		AnalysisComponent edfa = graphFactory.createAnalysisComponent();
		edfa.setName("EDFA");

		ProvidedInterface codeqlProvided = graphFactory.createProvidedInterface();
		codeqlProvided.setOwner(codeql);
		codeqlProvided.setMappingModel(codeqlOutputMapping);

		RequiredInterface codeqlRequired = graphFactory.createRequiredInterface();
		codeqlRequired.setOwner(codeql);
		codeqlRequired.setMappingModel(codeqlInputMapping);

		RequiredInterface edfaRequired = graphFactory.createRequiredInterface();
		edfaRequired.setOwner(edfa);

		codeql.getOutputs().add(codeqlProvided);
		codeql.getInputs().add(codeqlRequired);

		edfa.getInputs().add(edfaRequired);
		edfa.getOutputs().add(graphFactory.createProvidedInterface());

		Connection conn = graphFactory.createConnection();
		conn.setFrom(codeqlProvided);
		conn.setTo(edfaRequired);

		AnalysisGraph graph = graphFactory.createAnalysisGraph();
		graph.getComponents().add(codeql);
		graph.getComponents().add(edfa);
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
