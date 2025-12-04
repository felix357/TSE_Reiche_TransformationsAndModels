package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC3MCheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker checker3 = new IC3MChecker(cfg, secLiterals, systemElementsFromIC2, configurationsFromIC2);

		assertTrue(checker3.runCheck());
	}

	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker checker3 = new IC3MChecker(cfg, secLiterals, systemElementsFromIC2, configurationsFromIC2);

		assertTrue(checker3.runCheck());
	}

	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker checker3 = new IC3MChecker(cfg, secLiterals, systemElementsFromIC2, configurationsFromIC2);

		assertTrue(checker3.runCheck());
	}

	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();

		IC3MChecker checker3 = new IC3MChecker(cfg, secLiterals, systemElementsFromIC2, configurationsFromIC2);

		assertTrue(checker3.runCheck());
	}
}
