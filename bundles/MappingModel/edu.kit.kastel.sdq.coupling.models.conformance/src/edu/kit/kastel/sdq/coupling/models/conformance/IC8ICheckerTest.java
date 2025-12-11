package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC8ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();
		
		IC2IChecker checker2i = new IC2IChecker(cfg);
		checker2i.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		checker5.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2i);
		checker7.runCheck();

		IC8IChecker checker8 = new IC8IChecker(cfg, checker5, checker7);

		assertTrue(checker8.runCheck());
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
		
		IC2IChecker checker2i = new IC2IChecker(cfg);
		checker2i.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		checker5.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2i);
		checker7.runCheck();

		IC8IChecker checker8 = new IC8IChecker(cfg, checker5, checker7);

		assertTrue(checker8.runCheck());
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
		
		IC2IChecker checker2i = new IC2IChecker(cfg);
		checker2i.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		checker5.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2i);
		checker7.runCheck();

		IC8IChecker checker8 = new IC8IChecker(cfg, checker5, checker7);

		assertTrue(checker8.runCheck());
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
		
		IC2IChecker checker2i = new IC2IChecker(cfg);
		checker2i.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		checker5.runCheck();

		IC7IChecker checker7 = new IC7IChecker(cfg, checker2i);
		checker7.runCheck();

		IC8IChecker checker8 = new IC8IChecker(cfg, checker5, checker7);

		assertTrue(checker8.runCheck());
	}
}
