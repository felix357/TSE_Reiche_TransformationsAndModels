package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC9MCheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC9MChecker checker9 = new IC9MChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}
	
	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC9MChecker checker9 = new IC9MChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC9MChecker checker9 = new IC9MChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}
	
	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC9MChecker checker9 = new IC9MChecker(cfg, checker1);

		assertTrue(checker9.runCheck());
	}
}
