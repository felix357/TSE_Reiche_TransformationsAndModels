package propagationevaluation;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import edu.kit.kastel.sdq.coupling.models.conformance.ReferenceMetaModelConformanceChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemUnderEval;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import propagation.AnalysisType;
import propagation.RoundRobinUncertaintyController;
import propagation.UncertaintyAnnotator;
import propagation.UncertaintyAnnotatorBuilder;
import uncertainty.UncertaintySource;

/**
 * Evaluates the accuracy of uncertainty propagation in coupled model-based
 * analyses for the CoCoMe System. Specifically, it assesses whether the computed impact set
 * accurately reflects the uncertainties present in the affected set, measuring
 * both the precision and recall of the propagation results.
 * 
 * The evaluation is based on representative example uncertainties identified
 * for the CoCoMe system, as documented in the results of the uncertainty
 * propagation evaluation.
 */
public class CoCoMePropagationEvaluation {

	@Test
	public void graphWithNoUncertaintiesTest() throws Exception {
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

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
				SystemUnderEval.COCOME);
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
				SystemUnderEval.COCOME);

		cfg.overrideModelCorrespondence("correspondences.edfacodeqlcorrespondences_invalid_structure");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
				SystemUnderEval.COCOME);

		cfg.overrideCodeQL("codeql4extendeddataflow_impre.codeql");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		cfg.overridePCMJavaCorrespondence("correspondences.pcmjavacorrespondence_invalid");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideEdfaCodeqlCorrespondence("correspondences_imprecise.edfacodeqlcorrespondences");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		cfg.overrideCodeQL("codeql4extendeddataflow_invalidSecurityLevels.codeql");
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		cfg.overrideCodeQL("codeql4extendeddataflow_impre.codeql");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
		// second case invalid -> Uncertainty Scenario: Non-conformance to input
		// interface
		// (LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsInValid)
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_incorrect");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_imprecise_ic9");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 1 for (IC5) uncertainty propagation evaluation:
	// (IC5) Uncertainty if correspondences between source code and analysis result
	// elements are missing or inconsistent.
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultValidInputDataTest() throws Exception {
		// first case valid -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 2 for (IC5) uncertainty propagation evaluation:
	// (IC5) Uncertainty if correspondences between source code and analysis result
	// elements are missing or inconsistent.
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultInValidInputDataTest() throws Exception {
		// second case invalid -> Uncertainty Scenario: Non-conformance to input
		// interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		cfg.overrideCodeqlScarModel("scar.codeqlscar_wrong_security_level");
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 3 for (IC5) uncertainty propagation evaluation:
	// (IC5) Uncertainty if correspondences between source code and analysis result
	// elements are missing or inconsistent.
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultImpreciseInputDataTest() throws Exception {
		// third case invalid -> Uncertainty Scenario: imprecise input data
		// interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		cfg.overrideCodeqlScarModel("scar.codeqlscar_imprecise_security_level");
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 1 for (IC6) uncertainty propagation evaluation:
	// (IC6) Uncertainty if result entries linking system elements and security
	// characteristics are missing or inconsistent.
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsValidInputDataTest() throws Exception {
		// first case valid -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 2 for (IC6) uncertainty propagation evaluation:
	// (IC6) Uncertainty if result entries linking system elements and security
	// characteristics are missing or inconsistent.
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsInValidInputDataTest()
			throws Exception {
		// second case invalid -> Uncertainty Scenario: Non-conformance to input
		// interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		cfg.overrideCodeqlScarModel("scar.codeqlscar_removed_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 3 for (IC6) uncertainty propagation evaluation:
	// (IC6) Uncertainty if result entries linking system elements and security
	// characteristics are missing or inconsistent.
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsImpreciseInputDataTest()
			throws Exception {
		// second case invalid -> Uncertainty Scenario: imprecise input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		cfg.overrideCodeqlScarModel("scar.codeqlscar_imprecise_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 1 for (IC7) uncertainty propagation evaluation:
	// (IC7) Uncertainty if configuration correspondences between analysis and
	// source model are missing or inconsistent.
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelValidInputDataTest() throws Exception {
		// first case -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 2 for (IC7) uncertainty propagation evaluation:
	// (IC7) Uncertainty if configuration correspondences between analysis and
	// source model are missing or inconsistent.
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelInValidInputDataTest() throws Exception {
		// second case -> Uncertainty Scenario: Non-conformance to input interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_wrong_ruleid");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 3 for (IC7) uncertainty propagation evaluation:
	// (IC7) Uncertainty if configuration correspondences between analysis and
	// source model are missing or inconsistent.
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelImpreciseInputDataTest() throws Exception {
		// third case -> Uncertainty Scenario: imprecise input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_imprecise_ic9");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 1 for (IC8) uncertainty propagation evaluation:
	// (IC8) Uncertainty if references between RIVs, system elements, and
	// configurations are missing or inconsistent.
	@Test
	public void graphWithIC8ValidInputDataTest() throws Exception {
		// first case -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 2 for (IC8) uncertainty propagation evaluation:
	// (IC8) Uncertainty if references between RIVs, system elements, and
	// configurations are missing or inconsistent.
	@Test
	public void graphWithIC8InValidInputDataTest() throws Exception {
		// second case -> Uncertainty Scenario: Non-conformance to input interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_unknown_systemelement");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 3 for (IC8) uncertainty propagation evaluation:
	// (IC8) Uncertainty if references between RIVs, system elements, and
	// configurations are missing or inconsistent.
	@Test
	public void graphWithIC8ImpreciseValidInputDataTest() throws Exception {
		// third case -> Uncertainty Scenario: Imprecise input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_imprecise_ic9");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 1 for (IC9) uncertainty propagation evaluation:
	// (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	// or inconsistent.
	@Test
	public void graphWithIC9ValidInputDataTest() throws Exception {
		// first case -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 2 for (IC9) uncertainty propagation evaluation:
	// (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	// or inconsistent.
	@Test
	public void graphWithIC9InValidInputDataTest() throws Exception {
		// second case -> Uncertainty Scenario: Non-conformance to input interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_sec_level_that_is_not_in_ic1");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

	// Tests Case 3 for (IC9) uncertainty propagation evaluation:
	// (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	// or inconsistent.
	@Test
	public void graphWithIC9ImpreciseInputDataTest() throws Exception {
		// third case -> Uncertainty Scenario: Imprecise input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		cfg.overrideRIV("resultingvalues.codeqlresultingvalues_imprecise_ic9");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
	
	// Tests Case 1 for (IC10) uncertainty propagation evaluation:
	// (IC10) Uncertainty if links between security characteristics and configurations are missing or inconsistent.
	@Test
	public void graphWithIC10ValidInputDataTest() throws Exception {
		// first case -> Uncertainty Scenario: correct input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
	
	// Tests Case 2 for (IC10) uncertainty propagation evaluation:
	// (IC10) Uncertainty if links between security characteristics and configurations are missing or inconsistent.
	@Test
	public void graphWithIC10InValidInputDataTest() throws Exception {
		// second case -> Uncertainty Scenario: Non-conformance to input interface

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		
		cfg.overrideRivCorrespondence("correspondences.codeqlresultingvaluescorrespondences_break_config_mapping");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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
	
	// Tests Case 3 for (IC10) uncertainty propagation evaluation:
	// (IC10) Uncertainty if links between security characteristics and configurations are missing or inconsistent.
	@Test
	public void graphWithIC10ImpreciseInputDataTest() throws Exception {
		// third case -> Uncertainty Scenario: Imprecise to input data

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		
		cfg.overrideRivCorrespondence("correspondences.codeqlresultingvaluescorrespondences_ambiguity");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: METHODOLOGY_APPROXIMATION",
				"CODEQL: METHODOLOGY_OVER_SIMPLIFICATION", "CODEQL: METHODOLOGY_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "CODEQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: METHODOLOGY_APPROXIMATION",
				"CODEQL: METHODOLOGY_OVER_SIMPLIFICATION", "CODEQL: METHODOLOGY_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "CODEQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.METHODOLOGY_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: METHODOLOGY_APPROXIMATION",
				"CODEQL: METHODOLOGY_OVER_SIMPLIFICATION", "CODEQL: METHODOLOGY_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "CODEQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: SCENARIO_DEFINITION_CORRECT",
				"CODEQL: SCENARIO_DEFINITION_INCORRECT", "CODEQL: OUTPUT_CORRECT", "CODEQL: OUTPUT_ERROR",
				"CODEQL: OUTPUT_IMPRECISION", "EDFA: CORRECT_INPUT_DATA", "EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: SCENARIO_DEFINITION_CORRECT",
				"CODEQL: SCENARIO_DEFINITION_INCORRECT", "CODEQL: OUTPUT_CORRECT", "CODEQL: OUTPUT_ERROR",
				"CODEQL: OUTPUT_IMPRECISION", "EDFA: CORRECT_INPUT_DATA", "EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: MODEL_CORRECT", "CODEQL: MODEL_UNDER_SPECIFICATION",
				"CODEQL: MODEL_DISCREPANCY", "CODEQL: OUTPUT_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: MODEL_CORRECT", "CODEQL: MODEL_UNDER_SPECIFICATION",
				"CODEQL: MODEL_DISCREPANCY", "CODEQL: OUTPUT_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

		annotator.annotateAnalysisComponent(codeQlAnalysis, UncertaintySource.MODELING_INDUCED);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: MODEL_CORRECT", "CODEQL: MODEL_UNDER_SPECIFICATION",
				"CODEQL: MODEL_DISCREPANCY", "CODEQL: OUTPUT_CORRECT", "CODEQL: OUTPUT_IMPRECISION",
				"CODEQL: OUTPUT_ERROR", "EDFA: CORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		AnalysisComponent eDFAAnalysis = graph.getComponents().get(1);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		RequiredInterface eDFAReq = graph.getComponents().get(1).getInputs().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);

		RequiredInterface eDFAReq = graph.getComponents().get(1).getInputs().get(0);

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);
		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);

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

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface codeQlProv = graph.getComponents().get(0).getOutputs().get(0);
		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition codeqlOutputMapping = codeQlProv.getMappingModel();
		MappingDefinition codeqlInputMapping = codeQlReq.getMappingModel();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
		annotatorCodeQLInput.annotateInterface(codeQlReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();
		List<String> expectedImpactSet = List.of("CODEQL: IMPRECISE_INPUT_DATA", "CODEQL: CORRECT_INPUT_DATA",
				"CODEQL: OUTPUT_IMPRECISION", "CODEQL: OUTPUT_CORRECT", "EDFA: IMPRECISE_INPUT_DATA",
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
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface codeQlProv = graph.getComponents().get(0).getOutputs().get(0);
		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition codeqlOutputMapping = codeQlProv.getMappingModel();

		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load mapping
		String codeqlMappingPath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/model/codeqlInputMapping_incomplete.xmi";
		MappingDefinition codeqlInputMapping = loadMapping(resSet, codeqlMappingPath);

		// Resolve proxies
		EcoreUtil.resolveAll(resSet);

		boolean codeqlInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlInputMapping, inputRefMeta);

		boolean codeqlOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(codeqlOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
		annotatorCodeQLInput.annotateInterface(codeQlReq);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("CODEQL: IMPRECISE_INPUT_DATA",
				"CODEQL: NON_CONFORMANCE_TO_INPUT_INTERFACE", "CODEQL: OUTPUT_IMPRECISION", "CODEQL: OUTPUT_ERROR",
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
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.CODEQL, AnalysisType.EDFA);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);

		MappingDefinition codeqlInputMapping = codeQlReq.getMappingModel();

		ResourceSet resSet = createResourceSet();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load changed mappings..
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

		UncertaintyAnnotator annotator = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(codeqlOutputConforms)
				.build();

		annotator.annotateInterface(edfaReq);

		UncertaintyAnnotator annotatorCodeQLInput = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(codeqlInputConforms).withOutputReferenceConformance(true).build();
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
