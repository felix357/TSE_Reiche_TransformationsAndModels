package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC9ICheckerTest {

	@Test
	public void testJpmailCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testJpmailJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testCoCoMeCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testCoCoMeJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testEclipseSecureStorageCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testEclipseSecureStorageJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testTravelPlannerCodeQlEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}

	@Test
	public void testTravelPlannerJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.JOANAEDFA);

		IC1IChecker checker1 = new IC1IChecker(cfg);
		checker1.runCheck();

		IC9IChecker checker9 = new IC9IChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}
}
