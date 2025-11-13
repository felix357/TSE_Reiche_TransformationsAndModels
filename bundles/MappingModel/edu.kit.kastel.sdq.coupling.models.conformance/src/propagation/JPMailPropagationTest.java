package propagation;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

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
import edu.kit.kastel.sdq.coupling.models.conformance.ReferenceMetaModelConformanceChecker;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import uncertainty.SeverityOfImpact;
import uncertainty.UncertaintyFactory;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyScenario;
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

		// first case mapping valid -> Uncertainty Scenario:  correct input data
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String architectureModelName = "jpmail.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";

		IC1MChecker modelChecker = new IC1MChecker(basePath, architectureModelName, correspondenceName,
				sourceCodeAnalysisName);
		boolean restulModelChecker = modelChecker.runCheck();

		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";

		IC1IChecker instanceChecker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		boolean resultInstanceChecker = instanceChecker.runCheck();

		AnalysisGraph graph = buildAnalysisGraph();
		if (!(restulModelChecker && resultInstanceChecker)) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

			UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
			label.setSeverity(SeverityOfImpact.HIGH);
			label.setUncertaintyScenario(UncertaintyScenario.INCORRECT_INPUT_DATA);

			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> impactSet = controller.propagateWithComponentInfo();

		List<String> affectedSet = List.of();

		assertEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC1) uncertainty propagation evaluation:
	// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
	// EDFA.
	@Test
	public void graphWithIC1MappingInValidTest() throws Exception {
		// second case mapping invalid -> Uncertainty Scenario: Non-conformance to input interface
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String architectureModelName = "jpmail.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences_invalid_structure";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";

		IC1MChecker modelChecker = new IC1MChecker(basePath, architectureModelName, correspondenceName,
				sourceCodeAnalysisName);
		boolean restulModelChecker = modelChecker.runCheck();

		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";

		IC1IChecker instanceChecker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		boolean resultInstanceChecker = instanceChecker.runCheck();

		AnalysisGraph graph = buildAnalysisGraph();
		if (!(restulModelChecker && resultInstanceChecker)) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

			UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
			label.setSeverity(SeverityOfImpact.HIGH);
			label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertEquals(affectedSet, impactSet);
	}
	
		// Tests Case 3 for (IC1) uncertainty propagation evaluation:
		// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
		// EDFA.
		@Test
		public void graphWithIC1IncorrectInputDataTest() throws Exception {
			// third case incorrect input data represented in codeqlresults -> Uncertainty Scenario: Incorrect input data
			String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
			String architectureModelName = "jpmail.pddc";
			String correspondenceName = "correspondences.edfacodeqlcorrespondences";
			String sourceCodeAnalysisName = "codeql4extendeddataflow_incorrect_result.codeql";

			IC1MChecker modelChecker = new IC1MChecker(basePath, architectureModelName, correspondenceName,
					sourceCodeAnalysisName);
			boolean restulModelChecker = modelChecker.runCheck();

			String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
			String rivName = "resultingvalues.codeqlresultingvalues";

			IC1IChecker instanceChecker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
			boolean resultInstanceChecker = instanceChecker.runCheck();

			AnalysisGraph graph = buildAnalysisGraph();
			if (!(restulModelChecker && resultInstanceChecker)) {
				RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

				UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
				label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
				label.setSeverity(SeverityOfImpact.HIGH);
				label.setUncertaintyScenario(UncertaintyScenario.INCORRECT_INPUT_DATA);

				edfaReq.getUncertaintyLabel().add(label);
			}

			RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
			List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

			List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
					.toList();

			List<String> affectedSet = List.of("EDFA: INCORRECT_INPUT_DATA", "EDFA: OUTPUT_ERROR");
			assertNotEquals(affectedSet, impactSet);
		}
		
		// Tests Case 4 for (IC1) uncertainty propagation evaluation:
		// (IC1) Handling uncertainty in mapping CodeQL security instances to RIV and
		// EDFA.
		@Test
		public void graphWithIC1ImpreciseInputDataTest() throws Exception {
			// third case imprecise input data represented in codeqlresults -> Uncertainty Scenario: imprecise input data
			String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
			String architectureModelName = "jpmail.pddc";
			String correspondenceName = "correspondences.edfacodeqlcorrespondences";
			String sourceCodeAnalysisName = "codeql4extendeddataflow_imprecise.codeql";

			IC1MChecker modelChecker = new IC1MChecker(basePath, architectureModelName, correspondenceName,
					sourceCodeAnalysisName);
			boolean restulModelChecker = modelChecker.runCheck();

			String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
			String rivName = "resultingvalues.codeqlresultingvalues";

			IC1IChecker instanceChecker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
			boolean resultInstanceChecker = instanceChecker.runCheck();

			AnalysisGraph graph = buildAnalysisGraph();
			if (!(restulModelChecker && resultInstanceChecker)) {
				RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

				UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
				label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
				label.setSeverity(SeverityOfImpact.HIGH);
				label.setUncertaintyScenario(UncertaintyScenario.IMPRECISE_INPUT_DATA);

				edfaReq.getUncertaintyLabel().add(label);
			}

			RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
			List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

			List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
					.toList();

			List<String> affectedSet = List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
			assertNotEquals(affectedSet, impactSet);
		}

	// Tests Case 1 for (IC2) uncertainty propagation evaluation:
	// (IC2) Missing or inconsistent code–architecture correspondences.
	@Test
	public void graphWithIC2CodeArchcorrespondencesValidTest() throws Exception {

		// first case correspondences valid
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";

		IC2MChecker modelChecker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);
		boolean restultModelChecker = modelChecker.runCheck();

		IC2IChecker instanceChecker = new IC2IChecker(basePath);
		boolean restultInstanceChecker = instanceChecker.runCheck();

		AnalysisGraph graph = buildAnalysisGraph();
		if (!(restultModelChecker && restultInstanceChecker)) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

			UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
			label.setSeverity(SeverityOfImpact.HIGH);
			label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of();
		assertEquals(affectedSet, impactSet);
	}

	// Tests Case 2 for (IC2) uncertainty propagation evaluation:
	// (IC2) Missing or inconsistent code–architecture correspondences.
	@Test
	public void graphWithIC2CodeArchcorrespondencesInValidTest() throws Exception {

		// second case correspondences invalid
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence_invalid";

		IC2MChecker modelChecker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName,
				edfaConfigRepFileName, pcmJavaFileName);
		boolean restultModelChecker = modelChecker.runCheck();

		IC2IChecker instanceChecker = new IC2IChecker(basePath);
		boolean restultInstanceChecker = instanceChecker.runCheck();

		AnalysisGraph graph = buildAnalysisGraph();
		if (!(restultModelChecker && restultInstanceChecker)) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

			UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
			label.setSeverity(SeverityOfImpact.HIGH);
			label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithLossOfAccuracyDueToAbstractionInCodeQlTest() throws Exception {

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.METHODOLOGY_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.METHODOLOGY_ABSTRACTION);

		codeQlAnalysis.getUncertaintyLabels().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("CodeQL: METHODOLOGY_ABSTRACTION", "CodeQL: OUTPUT_IMPRECISION",
				"EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithInaccuracyInCodeQlOutputDataTest() throws Exception {

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		ProvidedInterface provInterface = codeQlAnalysis.getOutputs().get(0);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.OUTPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.OUTPUT_IMPRECISION);

		provInterface.getUncertaintyLabel().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("CodeQL: OUTPUT_IMPRECISION", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: OUTPUT_IMPRECISION");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithcodeQLUncertaintyDueToIncompleteScenarioCoverageTest() throws Exception {

		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent codeQlAnalysis = graph.getComponents().get(0);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.SCENARIO_DEFINITION_INCORRECT);

		codeQlAnalysis.getUncertaintyLabels().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("CodeQL: SCENARIO_DEFINITION_INCORRECT", "CodeQL: OUTPUT_ERROR",
				"CodeQL: OUTPUT_IMPRECISION", "EDFA: INCORRECT_INPUT_DATA", "EDFA: IMPRECISE_INPUT_DATA",
				"EDFA: OUTPUT_ERROR", "EDFA: OUTPUT_IMPRECISION");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithedfaRiskInaccuracyDueToSimplificationsTest() throws Exception {
		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent edfaAnalysis = graph.getComponents().get(1);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.METHODOLOGY_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.METHODOLOGY_OVER_SIMPLIFICATION);

		edfaAnalysis.getUncertaintyLabels().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: OUTPUT_ERROR");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithEdfaOutputAccuracyUncertaintyTest() throws Exception {
		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent edfaAnalysis = graph.getComponents().get(1);
		ProvidedInterface provInterface = edfaAnalysis.getOutputs().get(0);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.OUTPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.OUTPUT_IMPRECISION);

		provInterface.getUncertaintyLabel().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: OUTPUT_IMPRECISION");
		assertEquals(affectedSet, impactSet);
	}

	@Test
	public void graphWithEdfaUncertaintyDueToIncompleteAssumptionsAndScenariosTest() throws Exception {
		AnalysisGraph graph = buildAnalysisGraph();

		AnalysisComponent edfaAnalysis = graph.getComponents().get(1);

		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.SCENARIO_ASSUMPTION_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.SCENARIO_DEFINITION_INCORRECT);

		edfaAnalysis.getUncertaintyLabels().add(label);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: METHODOLOGY_OVER_SIMPLIFICATION", "EDFA: OUTPUT_IMPRECISION");
		assertNotEquals(affectedSet, impactSet);
	}

	// Tests Case 1 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithCompleteReferenceMetamodelMappingTest() throws Exception {

		// Case 1: All Reference-Class Mappings valid (edfaInputConforms,
		// codeqlInputConforms and codeqlOutputConforms are true)
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

		// Annotates the analysis graph with uncertainty reflecting model–meta-model
		// conformance.
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

		if (!codeqlInputConforms) {
			RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
			codeQlReq.getUncertaintyLabel().add(label);
		}

		if (!codeqlOutputConforms || !edfaInputConforms) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of();
		assertEquals(affectedSet, impactSet);
	}
	
	
	// Tests Case 2 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithIncompleteReferenceMetamodelMappingCodeQlInputTest() throws Exception {

		// Case 2: CodeQL input incomplete mapping to reference metamodel.
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

		// Annotates the analysis graph with uncertainty reflecting model–meta-model
		// conformance.
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
		
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

		if (!codeqlInputConforms) {
			RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
			codeQlReq.getUncertaintyLabel().add(label);
		}

		if (!codeqlOutputConforms || !edfaInputConforms) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("CodeQL: NON_CONFORMANCE_TO_INPUT_INTERFACE", "CodeQL: OUTPUT_ERROR", "EDFA: INCORRECT_INPUT_DATA", "EDFA: OUTPUT_ERROR");
		assertEquals(affectedSet, impactSet);
	}
	
	// Tests Case 3 for ReferenceMetamodelMapping uncertainty propagation
	// evaluation:
	// Incomplete Reference-Class Mapping
	@Test
	public void graphWithIncompleteReferenceMetamodelMappingCodeQlOutputandEDFAInputTest() throws Exception {

		// Case 3: CodeQL output and EDFA input have incomplete mapping to reference metamodel.
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

		// Annotates the analysis graph with uncertainty reflecting model–meta-model
		// conformance.
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
		
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		label.setSeverity(SeverityOfImpact.HIGH);
		label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);

		if (!codeqlInputConforms) {
			RequiredInterface codeQlReq = graph.getComponents().get(0).getInputs().get(0);
			codeQlReq.getUncertaintyLabel().add(label);
		}

		if (!codeqlOutputConforms || !edfaInputConforms) {
			RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
			edfaReq.getUncertaintyLabel().add(label);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> affectedSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");
		assertEquals(affectedSet, impactSet);
		assertTrue(codeqlInputConforms);
		assertFalse(codeqlOutputConforms);
		assertFalse(edfaInputConforms);
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
