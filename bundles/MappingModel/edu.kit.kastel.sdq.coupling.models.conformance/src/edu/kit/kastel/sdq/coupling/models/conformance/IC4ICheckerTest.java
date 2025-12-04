package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class IC4ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
		Map<String, String> rivValuesMap = c1.getRivValuesMap();

		IC4IChecker checker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		assertTrue(checker.runCheck());
	}

}
