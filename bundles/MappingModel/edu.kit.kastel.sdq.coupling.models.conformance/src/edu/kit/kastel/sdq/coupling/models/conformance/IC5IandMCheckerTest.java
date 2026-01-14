package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC5IandMCheckerTest {

	@Test
	public void testJpmail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		assertTrue(checker5.runCheck());
	}
	
	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		assertTrue(checker5.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		assertTrue(checker5.runCheck());
	}
	
	@Test
	public void testEclipseTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		assertTrue(checker5.runCheck());
	}
}
