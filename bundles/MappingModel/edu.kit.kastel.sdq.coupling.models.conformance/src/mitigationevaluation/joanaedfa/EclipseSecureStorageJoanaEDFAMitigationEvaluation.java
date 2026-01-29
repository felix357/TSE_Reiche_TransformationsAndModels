package mitigationevaluation.joanaedfa;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemUnderEval;
import mapping.MappingDefinition;
import mapping.MappingPackage;
import propagation.AnalysisType;
import propagation.RoundRobinUncertaintyController;
import propagation.UncertaintyAnnotator;
import propagation.UncertaintyAnnotatorBuilder;
import sourcecodeanalysis.precision.evaluation.ScScarParser;
import sourcecodeanalysis.precision.evaluation.GroundTruthFlow;
import sourcecodeanalysis.precision.evaluation.GroundTruthParser;
import sourcecodeanalysis.precision.evaluation.ObservedFlow;
import sourcecodeanalysis.precision.evaluation.SourceCodePrecisionChecker;
import uncertainty.UncertaintySource;

/**
 * Evaluates the difference in uncertainty before and after mitigation in
 * coupled model-based analyses for the Eclipse Secure Storage system, specifically for
 * the Joana–EDFA coupling.
 * 
 * This evaluation assesses the extent to which the set of accuracy-impacting
 * uncertainties after mitigation is reduced compared to the corresponding set
 * before mitigation.
 * 
 * The evaluation is based on representative example uncertainties for the
 * Eclipse Secure Storage system, as documented in the results XML file of the
 * uncertainty mitigation assessment.
 */
public class EclipseSecureStorageJoanaEDFAMitigationEvaluation {

	/**
	 * Test Case 1 for (IC1) uncertainty mitigation evaluation.
	 * 
	 * (IC1) Concerns uncertainty in mapping security instances from source code
	 * analysis (Joana) to RIV and architectural EDFA analysis.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC1MappingValidTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC1 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC1) uncertainty mitigation evaluation.
	 * 
	 * (IC1) Concerns uncertainty in mapping security instances from source code
	 * analysis (Joana) to RIV and architectural EDFA analysis.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC1MappingInValidTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideModelCorrespondence("correspondences.edfajoanacorrespondences_invalid_structure");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction

		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC1) uncertainty mitigation evaluation.
	 * 
	 * (IC1) Concerns uncertainty in mapping security instances from source code
	 * analysis (Joana) to RIV and architectural EDFA analysis.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithSourceCodeAnalysisImprecisionTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC2) uncertainty mitigation evaluation.
	 * 
	 * (IC2) Missing or inconsistent code–architecture correspondences.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC2CodeArchcorrespondencesValidTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC1 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC2) uncertainty mitigation evaluation.
	 * 
	 * (IC2) Missing or inconsistent code–architecture correspondences.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC2CodeArchcorrespondencesInValidTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overridePCMJavaCorrespondence("correspondences.pcmjavacorrespondence_invalid");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC2) uncertainty mitigation evaluation.
	 * 
	 * (IC2) Missing or inconsistent code–architecture correspondences.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC2CodeArchcorrespondencesImPreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC3) uncertainty mitigation evaluation.
	 * 
	 * (IC3) Uncertainty about the existence and consistency of security annotations
	 * in the annotated source code model.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC3SecurityAnnoationsConsistetInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC3) uncertainty mitigation evaluation.
	 * 
	 * (IC3) Uncertainty about the existence and consistency of security annotations
	 * in the annotated source code model.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC3SecurityAnnoationsInConsistetInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideSc("joana4extendeddataflow_invalidSecurityLevels.joana");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC3) uncertainty mitigation evaluation.
	 * 
	 * (IC3) Uncertainty about the existence and consistency of security annotations
	 * in the annotated source code model.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC3SecurityAnnoationsimpreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC4) uncertainty mitigation evaluation.
	 * 
	 * (IC4) Uncertainty if linkages between security policies and security
	 * characteristics are incorrect or missing.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsValidInputDataTest()
			throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC4) uncertainty mitigation evaluation.
	 * 
	 * (IC4) Uncertainty if linkages between security policies and security
	 * characteristics are incorrect or missing.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsInValidInputDataTest()
			throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideRIV("resultingvalues.joanaresultingvalues_incorrect");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC4) uncertainty mitigation evaluation.
	 * 
	 * (IC4) Uncertainty if linkages between security policies and security
	 * characteristics are incorrect or missing.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC4LinkagesBetweenSecurityPoliciesAndSecurityCharacteristicsImpreciseInputDataTest()
			throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC5) uncertainty mitigation evaluation.
	 * 
	 * (IC5) Uncertainty if correspondences between source code and analysis result
	 * elements are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC5) uncertainty mitigation evaluation.
	 * 
	 * (IC5) Uncertainty if correspondences between source code and analysis result
	 * elements are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultInValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_wrong_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC5) uncertainty mitigation evaluation.
	 * 
	 * (IC5) Uncertainty if correspondences between source code and analysis result
	 * elements are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC5CorrBetweenSourceCodeAndAnalysisresultImpreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC6) uncertainty mitigation evaluation.
	 * 
	 * (IC6) Uncertainty if result entries linking system elements and security
	 * characteristics are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC6) uncertainty mitigation evaluation.
	 * 
	 * (IC6) Uncertainty if result entries linking system elements and security
	 * characteristics are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsInValidInputDataTest()
			throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_removed_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC6) uncertainty mitigation evaluation.
	 * 
	 * (IC6) Uncertainty if result entries linking system elements and security
	 * characteristics are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC6ResultEntriesLinkingSysElementsAndSecCharacteristicsImpreciseInputDataTest()
			throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_imprecise_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC7) uncertainty mitigation evaluation.
	 * 
	 * (IC7) Uncertainty if configuration correspondences between analysis and
	 * source model are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC7) uncertainty mitigation evaluation.
	 * 
	 * (IC7) Uncertainty if configuration correspondences between analysis and
	 * source model are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelInValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideRIV("resultingvalues.joanaresultingvalues_wrong_ruleid");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC7) uncertainty mitigation evaluation.
	 * 
	 * (IC7) Uncertainty if configuration correspondences between analysis and
	 * source model are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC7CorrespondencesBetweenAnalysisAndSourceModelImpreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_imprecise_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC8) uncertainty mitigation evaluation.
	 * 
	 * (IC8) Uncertainty if references between RIVs, system elements, and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC8ValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC8) uncertainty mitigation evaluation.
	 * 
	 * (IC8) Uncertainty if references between RIVs, system elements, and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC8InValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideRIV("resultingvalues.joanaresultingvalues_unknown_systemelement");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC8) uncertainty mitigation evaluation.
	 * 
	 * (IC8) Uncertainty if references between RIVs, system elements, and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC8ImpreciseValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_imprecise_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC9) uncertainty mitigation evaluation.
	 * 
	 * (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	 * or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC9ValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC9) uncertainty mitigation evaluation.
	 * 
	 * (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	 * or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC9InValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideRIV("resultingvalues.joanaresultingvalues_sec_level_that_is_not_in_ic1");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC9) uncertainty mitigation evaluation.
	 * 
	 * (IC9) Uncertainty if mappings of security characteristics in RIVs are missing
	 * or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC9ImpreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideScScarModel("scar.joanascar_imprecise_security_level");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for (IC10) uncertainty mitigation evaluation.
	 * 
	 * (IC10) Uncertainty if links between security characteristics and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = 0.</li>
	 * <li>No mitigation is required because the IC indicate full conformance and
	 * input data checks indicate precision.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC10ValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Define the expected impact set based on correct input data
		// → assumption: no IC violations, so only CORRECT_INPUT_DATA and OUTPUT_CORRECT
		// are present
		List<String> expectedImpactSet = List.of("EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();
		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → in this scenario, same as U_before, because no mitigation is required
		List<String> U_after = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_before.isEmpty(), "U_before should be empty: no initial uncertainty due to correct input data.");
		assertTrue(U_after.isEmpty(), "U_after should be empty: no mitigation required because input is correct.");
		assertEquals(U_after.size() - U_before.size(), 0, "Uncertainty difference should be zero.");
		// Because IC3 checks are fully satisfied, there is no interface
		// nonconformance hence no mitigation is applied.

		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 2 for (IC10) uncertainty mitigation evaluation.
	 * 
	 * (IC10) Uncertainty if links between security characteristics and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to input interface.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE", EDFA: OUTPUT_ERROR} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation performed by human-in-the-loop, as the IC1 checks identified
	 * non-conformance.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC10InValidInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		cfg.overrideRivCorrespondence("correspondences.joanaresultingvaluescorrespondences_break_config_mapping");

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);

		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// → assumption: IC1 violations, so only EDFA:
		// NON_CONFORMANCE_TO_INPUT_INTERFACE and EDFA: OUTPUT_ERROR are present
		List<String> expectedImpactSet = List.of("EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR");

		assertEquals(expectedImpactSet, impactSet);

		// Identify accuracy-impacting uncertainties before mitigation (U_before)
		// → in this scenario, none exist because IC1 is satisfied
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// Identify accuracy-impacting uncertainties after mitigation (U_after)
		// → interface non-conformance and its effects is not included here, because it
		// is detected by the IC checks
		// and is assumed to be resolved by a human-in-the-loop via interface correction
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		assertEquals(U_before.size(), 2);
		assertTrue(U_after.isEmpty(),
				"U_after should be empty: The detected interface non-conformance is assumed to be resolved by a human-in-the-loop by correcting the interface definition, so no accuracy-impacting uncertainty remains after mitigation.");
		assertEquals(U_after.size() - U_before.size(), -2);
		// Verify that the source code analysis precision is correctly detected,
		// confirming that no further mitigation of SC analysis precision is needed
		assertTrue(scaIsPrecise);
	}

	/**
	 * Test Case 3 for (IC10) uncertainty mitigation evaluation.
	 * 
	 * (IC10) Uncertainty if links between security characteristics and
	 * configurations are missing or inconsistent.
	 * 
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: imprecise input data.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION} set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| - |U_before| = -2.</li>
	 * <li>Mitigation was performed by a human-in-the-loop after the source code
	 * analysis evaluation detected unacceptable imprecision. The analysis
	 * interfaces were verified to be conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIC10ImpreciseInputDataTest() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + "scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, true);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		// After detection, the human-in-the-loop can mitigate the imprecision by
		// replacing the source code analysis with a correct source code analysis (e.g.,
		// standard Joana), so no accuracy-impacting uncertainty remains.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Ensure SCAR imprecision is correctly detected, validating the system flags
		// unreliable sc analysis for mitigation.
		assertFalse(scaIsPrecise);
		assertFalse(U_before.isEmpty());
		assertTrue(U_after.isEmpty(), "U_after empty after expected mitigation by human-in-the-loop");
	}

	/**
	 * Test Case 1 for ReferenceMetamodelMapping uncertainty mitigation evaluation.
	 *
	 * Uncertainty Type: Incomplete or inconsistent Reference–Class Mapping.
	 *
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: correct input data.</li>
	 * <li>All input and output reference mappings conform to their reference
	 * metamodels.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| − |U_before| = 0.</li>
	 * <li>No mitigation is required because all reference mappings are
	 * conformant.</li>
	 * </ul>
	 */
	@Test
	public void graphWithCompleteReferenceMetamodelMappingTest() throws Exception {

		ResourceSet resSet = createResourceSet();
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface joanaProv = graph.getComponents().get(0).getOutputs().get(0);

		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition joanaInputMapping = joanaReq.getMappingModel();
		MappingDefinition joanaOutputMapping = joanaProv.getMappingModel();

		// Load reference metamodels
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		EcoreUtil.resolveAll(resSet);

		// Conformance checks (analogous to scaIsPrecise)
		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		// All mappings are conformant → no uncertainty is annotated
		UncertaintyAnnotator annotatorEDFA = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(joanaOutputConforms)
				.build();
		annotatorEDFA.annotateInterface(edfaReq, false);

		UncertaintyAnnotator annotatorJoana = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(joanaInputConforms).withOutputReferenceConformance(true).build();
		annotatorJoana.annotateInterface(joanaReq, false);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("JOANA: CORRECT_INPUT_DATA", "JOANA: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Retain only uncertainties that negatively affect analysis accuracy
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// No mitigation required because no accuracy-impacting uncertainty is present
		// (U_before is empty)
		List<String> U_after = U_before;

		assertTrue(U_before.isEmpty(), "U_before should be empty because all reference mappings are conformant.");

		assertTrue(U_after.isEmpty(), "U_after should be empty because no mitigation is required.");

		assertEquals(0, U_after.size() - U_before.size(),
				"Uncertainty difference should be zero for complete reference mappings.");
	}

	/**
	 * Test Case 2 for ReferenceMetamodelMapping uncertainty mitigation evaluation.
	 *
	 * Uncertainty Type: Incomplete or inconsistent Reference–Class Mapping.
	 *
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to Joana input interface.</li>
	 * <li>Joana input mapping initially does not conform to the input reference
	 * metamodel.</li>
	 * <li>This induces input data uncertainty that propagates over the coupling
	 * graph.</li>
	 * <li>A human-in-the-loop resolves the issue by correcting the mapping.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {JOANA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE, JOANA: OUTPUT_ERROR, EDFA:
	 * NON_CONFORMANCE_TO_INPUT_INTERFACE, EDFA: OUTPUT_ERROR}</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| − |U_before| = -4.</li>
	 * </ul>
	 */
	@Test
	public void graphWithIncompleteReferenceMetamodelMappingJoanaInputTest() throws Exception {

		// Before mitigation

		ResourceSet resSet = createResourceSet();
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface joanaProv = graph.getComponents().get(0).getOutputs().get(0);

		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition joanaOutputMapping = joanaProv.getMappingModel();

		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		MappingDefinition joanaInputMapping = loadMapping(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaInputMapping_incomplete.xmi");

		EcoreUtil.resolveAll(resSet);

		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		// Annotate uncertainties caused by input non-conformance
		new UncertaintyAnnotatorBuilder().withInputReferenceConformance(edfaInputConforms)
				.withOutputReferenceConformance(joanaOutputConforms).build().annotateInterface(edfaReq, false);

		new UncertaintyAnnotatorBuilder().withInputReferenceConformance(joanaInputConforms)
				.withOutputReferenceConformance(true).build().annotateInterface(joanaReq, false);

		List<String> impactSetBefore = new RoundRobinUncertaintyController(graph).propagateWithComponentInfo().stream()
				.map(RoundRobinUncertaintyController.ScenarioWithComponent::toString).toList();

		List<String> U_before = impactSetBefore.stream()
				.filter(s -> s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR")).toList();

		assertEquals(U_before, List.of("JOANA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "JOANA: OUTPUT_ERROR",
				"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA: OUTPUT_ERROR"));

		// Human-in-the-loop mitigation of Joana input interface

		AnalysisGraph mitigatedGraph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReqMit = mitigatedGraph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReqMit = mitigatedGraph.getComponents().get(0).getInputs().get(0);

		MappingDefinition correctedJoanaInputMapping = loadMapping(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaInputMapping.xmi");

		boolean joanaInputConformsAfter = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(correctedJoanaInputMapping, inputRefMeta);

		new UncertaintyAnnotatorBuilder().withInputReferenceConformance(edfaInputConforms)
				.withOutputReferenceConformance(joanaOutputConforms).build().annotateInterface(edfaReqMit, false);

		new UncertaintyAnnotatorBuilder().withInputReferenceConformance(joanaInputConformsAfter)
				.withOutputReferenceConformance(true).build().annotateInterface(joanaReqMit, false);

		List<String> impactSetAfter = new RoundRobinUncertaintyController(mitigatedGraph).propagateWithComponentInfo()
				.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString).toList();

		List<String> U_after = impactSetAfter.stream()
				.filter(s -> s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR")).toList();

		assertTrue(U_after.isEmpty(), "U_after should be empty after human-in-the-loop correction of the mapping.");

		// Uncertainty difference must be negative after effective mitigation.
		assertEquals(U_after.size() - U_before.size(), -4);
	}

	/**
	 * Test Case 3 for ReferenceMetamodelMapping uncertainty mitigation evaluation.
	 *
	 * Uncertainty Type: Incomplete or inconsistent Reference–Class Mapping.
	 *
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: non-conformance to Joana input interface.</li>
	 * <li>All input and output reference mappings conform to their reference
	 * metamodels.</li>
	 * <li>Source code analysis results are imprecise.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): {EDFA:
	 * IMPRECISE_INPUT_DATA, EDFA: OUTPUT_IMPRECISION}.</li>
	 * <li>After human-in-the-loop mitigation, the faulty analysis is replaced by a
	 * precise one.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| − |U_before| = −2.</li>
	 * </ul>
	 */
	@Test
	public void graphWithImpreciseInputDataAndValidReferenceMappingTest() throws Exception {

		// --- System and graph setup ---
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		ResourceSet resSet = createResourceSet();
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface joanaProv = graph.getComponents().get(0).getOutputs().get(0);

		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition joanaInputMapping = joanaReq.getMappingModel();
		MappingDefinition joanaOutputMapping = joanaProv.getMappingModel();

		// Load reference metamodels
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		EcoreUtil.resolveAll(resSet);

		// Reference metamodel conformance (baseline correctness)
		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		// Sanity check: mapping is not the uncertainty source
		assertTrue(joanaInputConforms);
		assertTrue(joanaOutputConforms);
		assertTrue(edfaInputConforms);

		// --- Detect imprecision in source code analysis ---
		Path scarFile = Paths.get(cfg.basePath + "/scar.joanascar_imprecision");
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		// Imprecision is intentionally injected
		assertFalse(scaIsPrecise);

		// Annotate uncertainty caused by imprecise input data
		UncertaintyAnnotator annotatorEDFA = new UncertaintyAnnotator(cfg);
		annotatorEDFA.annotateInterface(edfaReq, true);

		UncertaintyAnnotator annotatorJoana = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(joanaInputConforms).withOutputReferenceConformance(true).build();
		annotatorJoana.annotateInterface(joanaReq, false);

		// Propagate uncertainty
		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);

		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		// Retain only accuracy-impacting uncertainties
		List<String> U_before = impactSet.stream().filter(s -> s.contains("IMPRECISE") || s.contains("IMPRECISION"))
				.toList();

		assertEquals(U_before, List.of("EDFA: IMPRECISE_INPUT_DATA", "EDFA: OUTPUT_IMPRECISION"));

		// Human-in-the-loop mitigation
		// The faulty source code analysis is replaced by a precise one
		// e.g. standard, uncorrupted Joana as shown to be precise by Reiche et al.
		List<String> U_after = getImpactSetForMitigatedCouplingGraph().stream().filter(s -> s.contains("IMPRECISE"))
				.toList();

		// Evaluation assertions
		assertTrue(U_after.isEmpty(), "All imprecision uncertainty is removed after mitigation.");

		assertEquals(U_after.size() - U_before.size(), -2);
	}

	// Tests Case 1 for Uncertainty if loss of accuracy occurs due to
	// Orchestration-decision-induced uncertainty in Coupling graph.

	/**
	 * Test Case 1 for Orchestration-decision-induced uncertainty in Coupling graph.
	 *
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: Final analysis orchestration.</li>
	 * <li>All input and output reference mappings and ics conform</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): empty
	 * set.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| − |U_before| = 0.</li>
	 * <li>No mitigation orchestration decision is required because all IC and
	 * reference metamodel checks are fulfilled, and the analysis orchestration is
	 * therefore valid.</li>
	 * </ul>
	 */
	@Test
	public void graphWithNoLossOfAccuracyDueToOrchestrationTest() throws Exception {
		ResourceSet resSet = createResourceSet();
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface joanaProv = graph.getComponents().get(0).getOutputs().get(0);

		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition joanaInputMapping = joanaReq.getMappingModel();
		MappingDefinition joanaOutputMapping = joanaProv.getMappingModel();

		// Load reference metamodels
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		EcoreUtil.resolveAll(resSet);

		// Conformance checks (analogous to scaIsPrecise)
		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		// All mappings are conformant → no uncertainty is annotated
		UncertaintyAnnotator annotatorEDFA = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(joanaOutputConforms)
				.build();
		annotatorEDFA.annotateInterface(edfaReq, false);

		UncertaintyAnnotator annotatorJoana = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(joanaInputConforms).withOutputReferenceConformance(true).build();
		annotatorJoana.annotateInterface(joanaReq, false);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		List<String> impactSet = results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString)
				.toList();

		List<String> expectedImpactSet = List.of("JOANA: CORRECT_INPUT_DATA", "JOANA: OUTPUT_CORRECT",
				"EDFA: CORRECT_INPUT_DATA", "EDFA: OUTPUT_CORRECT");

		assertEquals(expectedImpactSet, impactSet);

		// Retain only uncertainties that negatively affect analysis accuracy
		List<String> U_before = impactSet.stream()
				.filter(s -> s.contains("IMPRECISE") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		// No mitigation required because orchestration uncertainty is not present
		// (U_before is empty)
		List<String> U_after = U_before;

		assertTrue(U_before.isEmpty(), "U_before should be empty because all reference mappings are conformant.");

		assertTrue(U_after.isEmpty(), "U_after should be empty because no mitigation is required.");

		assertEquals(0, U_after.size() - U_before.size(),
				"Uncertainty difference should be zero for complete reference mappings.");
	}

	/**
	 * Test Case 2 for Orchestration-decision-induced uncertainty in Coupling graph.
	 *
	 * <p>
	 * Scenario assumptions:
	 * </p>
	 * <ul>
	 * <li>"What-if" scenario: Orchestration decision under invalid interface
	 * configuration.</li>
	 * <li>Joana input reference mapping initially violates conformance checks.</li>
	 * <li>This violation induces orchestration-decision uncertainty that impacts
	 * analysis accuracy.</li>
	 * <li>Accuracy-impacting uncertainties before mitigation (U_before): non-empty
	 * set.</li>
	 * <li>A human-in-the-loop resolves the detected interface issue.</li>
	 * <li>Accuracy-impacting uncertainties after mitigation (U_after): empty
	 * set.</li>
	 * <li>Uncertainty difference: |U_after| − |U_before| &lt; 0.</li>
	 * </ul>
	 */
	@Test
	public void graphWithOrchestrationUncertaintyResolvedByHumanInLoopTest() throws Exception {

		// Initial orchestration with failing check
		ResourceSet resSet = createResourceSet();
		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReq = graph.getComponents().get(0).getInputs().get(0);
		ProvidedInterface joanaProv = graph.getComponents().get(0).getOutputs().get(0);

		MappingDefinition edfaInputMapping = edfaReq.getMappingModel();
		MappingDefinition joanaOutputMapping = joanaProv.getMappingModel();

		// Load reference metamodels
		EPackage inputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/InputReferenceMetamodel.ecore");

		EPackage outputRefMeta = loadAndRegisterEPackage(resSet,
				"C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/OutputReferenzMetamodel.ecore");

		// Load intentionally incomplete Joana input mapping
		String incompleteMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaInputMapping_incomplete.xmi";

		MappingDefinition joanaInputMapping = loadMapping(resSet, incompleteMappingPath);

		EcoreUtil.resolveAll(resSet);

		// Conformance checks
		boolean joanaInputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMapping, inputRefMeta);

		boolean joanaOutputConforms = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaOutputMapping, outputRefMeta);

		boolean edfaInputConforms = ReferenceMetaModelConformanceChecker.conformsToReferenceMetamodel(edfaInputMapping,
				inputRefMeta);

		// Annotate uncertainties (orchestration uncertainty introduced here)
		UncertaintyAnnotator annotatorEDFA = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(joanaOutputConforms)
				.build();
		annotatorEDFA.annotateInterface(edfaReq, false);

		UncertaintyAnnotator annotatorJoana = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(joanaInputConforms).withOutputReferenceConformance(true).build();
		annotatorJoana.annotateInterface(joanaReq, false);

		if (!joanaInputConforms) {
			annotatorJoana.annotateInterfaceWithUncertaintyAnnoation(joanaReq,
					UncertaintySource.ORCHESTRATION_DECISION_INDUCED);
		}

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<String> impactSet = controller.propagateWithComponentInfo().stream()
				.map(RoundRobinUncertaintyController.ScenarioWithComponent::toString).toList();

		// Extract accuracy-impacting uncertainties before mitigation
		List<String> U_before = impactSet.stream().filter(s -> s.contains("ORCHESTRATION_NOT_FINAL")
				|| s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR")).toList();

		assertEquals(U_before,
				List.of("JOANA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "JOANA input: ORCHESTRATION_NOT_FINAL",
						"JOANA: OUTPUT_ERROR", "JOANA output: ORCHESTRATION_NOT_FINAL",
						"EDFA: NON_CONFORMANCE_TO_INPUT_INTERFACE", "EDFA input: ORCHESTRATION_NOT_FINAL",
						"EDFA output: ORCHESTRATION_NOT_FINAL", "EDFA: OUTPUT_ERROR"));

		// Human-in-the-loop mitigation

		// Load corrected (conformant) mapping
		String resolvedMappingPath = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/joanaInputMapping.xmi";

		MappingDefinition joanaInputMappingResolved = loadMapping(resSet, resolvedMappingPath);

		boolean joanaInputConformsAfterMitigation = ReferenceMetaModelConformanceChecker
				.conformsToReferenceMetamodel(joanaInputMappingResolved, inputRefMeta);

		// Rebuild graph to reflect updated orchestration decision
		AnalysisGraph mitigatedGraph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		RequiredInterface edfaReqNew = mitigatedGraph.getComponents().get(1).getInputs().get(0);
		RequiredInterface joanaReqNew = mitigatedGraph.getComponents().get(0).getInputs().get(0);

		UncertaintyAnnotator mitigatedAnnotatorEDFA = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(edfaInputConforms).withOutputReferenceConformance(joanaOutputConforms)
				.build();
		mitigatedAnnotatorEDFA.annotateInterface(edfaReqNew, false);

		UncertaintyAnnotator mitigatedAnnotatorJoana = new UncertaintyAnnotatorBuilder()
				.withInputReferenceConformance(joanaInputConformsAfterMitigation).withOutputReferenceConformance(true)
				.build();
		mitigatedAnnotatorJoana.annotateInterface(joanaReqNew, false);

		if (!joanaInputConformsAfterMitigation) {
			annotatorJoana.annotateInterfaceWithUncertaintyAnnoation(joanaReqNew,
					UncertaintySource.ORCHESTRATION_DECISION_INDUCED);
		}

		RoundRobinUncertaintyController controllerAfterMitigation = new RoundRobinUncertaintyController(mitigatedGraph);

		List<String> impactSetAfterMitigation = controllerAfterMitigation.propagateWithComponentInfo().stream()
				.map(RoundRobinUncertaintyController.ScenarioWithComponent::toString).toList();

		List<String> U_after = impactSetAfterMitigation.stream()
				.filter(s -> s.contains("ORCHESTRATION") || s.contains("NON_CONFORMANCE") || s.contains("OUTPUT_ERROR"))
				.toList();

		assertTrue(U_after.isEmpty(),
				"U_after should be empty after human-in-the-loop resolved the orchestration issue.");

		assertTrue(U_after.size() < U_before.size(),
				"Mitigation must reduce the number of accuracy-impacting uncertainties.");
	}

	public AnalysisGraph buildAnalysisGraph(AnalysisType sourceCodeAnalysis, AnalysisType architecturalAnalysis)
			throws Exception {
		AnalysiscouplinggraphFactory graphFactory = AnalysiscouplinggraphFactory.eINSTANCE;

		// ResourceSet erstellen
		ResourceSet resSet = new ResourceSetImpl();
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
		resSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("ecore", new XMIResourceFactoryImpl());

		// MappingPackage registrieren
		MappingPackage.eINSTANCE.eClass();
		resSet.getPackageRegistry().put(MappingPackage.eNS_URI, MappingPackage.eINSTANCE);

		// Relevante EPackages laden
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

		// Mapping laden basierend auf Enum
		MappingDefinition sourceCodeInputMapping = loadMapping(resSet, sourceCodeAnalysis.getInputMappingPath());
		MappingDefinition sourceCodeOutputMapping = loadMapping(resSet, sourceCodeAnalysis.getOutputMappingPath());

		MappingDefinition architecturalMapping = loadMapping(resSet, architecturalAnalysis.getInputMappingPath());

		EcoreUtil.resolveAll(resSet);

		// AnalysisGraph bauen
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

	/**
	 * Builds the analysis graph for the correct coupling scenario, annotates it
	 * with uncertainties based on source code analysis precision, propagates the
	 * uncertainties, and returns the resulting impact set.
	 *
	 * @return List of impact strings representing the uncertainty/impact for each
	 *         component
	 * @throws Exception if parsing or graph construction fails
	 */
	public List<String> getImpactSetForMitigatedCouplingGraph() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		AnalysisGraph graph = buildAnalysisGraph(AnalysisType.JOANA, AnalysisType.EDFA);

		Path scarFile = Paths.get(cfg.basePath + "/" + cfg.scScarModel);
		Path gtFile = Paths.get(cfg.basePath + "/eclipsesecurestorage.groundTruth.xml");

		ScScarParser scarParser = new ScScarParser();
		List<ObservedFlow> observedFlows = scarParser.parse(scarFile);

		GroundTruthParser gtParser = new GroundTruthParser();
		Set<GroundTruthFlow> groundTruthFlows = gtParser.parse(gtFile);

		SourceCodePrecisionChecker checker = new SourceCodePrecisionChecker();
		boolean scaIsPrecise = checker.isAcceptablePrecisionRecall(observedFlows, groundTruthFlows);

		UncertaintyAnnotator annotator = new UncertaintyAnnotator(cfg);
		RequiredInterface edfaReq = graph.getComponents().get(1).getInputs().get(0);
		annotator.annotateInterface(edfaReq, !scaIsPrecise);

		RoundRobinUncertaintyController controller = new RoundRobinUncertaintyController(graph);
		List<RoundRobinUncertaintyController.ScenarioWithComponent> results = controller.propagateWithComponentInfo();

		return results.stream().map(RoundRobinUncertaintyController.ScenarioWithComponent::toString).toList();
	}

}
