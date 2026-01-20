package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC4ICheckerTest {

	@Test
	public void testJpmailCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testJpmailJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testCoCoMeCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testCoCoMeJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testEclipseSecureStorageCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorageJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testTravelPlannerCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testTravelPlannerJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getSourceRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

}
