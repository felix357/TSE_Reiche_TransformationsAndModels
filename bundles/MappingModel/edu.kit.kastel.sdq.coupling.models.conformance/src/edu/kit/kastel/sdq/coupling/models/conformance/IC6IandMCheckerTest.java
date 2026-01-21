package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC6IandMCheckerTest {

	@Test
	public void testJpmailCodeQlEDFA() throws Exception {
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

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testJpmailJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.JOANAEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();

		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testCoCoMeCodeQlEDFA() throws Exception {
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

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testCoCoMeJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();

		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorageCodeQlEDFA() throws Exception {
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

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorageJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();

		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testTravelPlannerCodeQlEDFA() throws Exception {
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

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
	
	@Test
	public void testTravelPlannerJoanaEDFA() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.JOANAEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker checker2 = new IC2MChecker(cfg);
		checker2.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();

		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();

		IC5IandMChecker checker5 = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);

		checker5.runCheck();

		IC6IandMChecker checker6 = new IC6IandMChecker(cfg, checker5);

		assertTrue(checker6.runCheck());
	}
}
