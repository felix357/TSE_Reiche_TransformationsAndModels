package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC7ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

		IC2IChecker checker2 = new IC2IChecker(cfg);
		checker2.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2);

		assertTrue(checker7.runCheck());
	}

	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC2IChecker checker2 = new IC2IChecker(cfg);
		checker2.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2);

		assertTrue(checker7.runCheck());
	}

	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

		IC2IChecker checker2 = new IC2IChecker(cfg);
		checker2.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2);

		assertTrue(checker7.runCheck());
	}

	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

		IC2IChecker checker2 = new IC2IChecker(cfg);
		checker2.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2);

		assertTrue(checker7.runCheck());
	}

}
