package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC10MCheckerTest {

	@Test
	public void testJPMail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7MChecker iC7MChecker = new IC7MChecker(cfg, ic2mChecker);
		iC7MChecker.runCheck();

		IC8MChecker ic8Mchecker = new IC8MChecker(cfg, iC5IandMChecker, iC7MChecker);
		ic8Mchecker.runCheck();
		
		IC9MChecker ic9Mchecker = new IC9MChecker(cfg, checker1);
		ic9Mchecker.runCheck();

		IC10MChecker checker = new IC10MChecker(cfg, ic8Mchecker, ic9Mchecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7MChecker iC7MChecker = new IC7MChecker(cfg, ic2mChecker);
		iC7MChecker.runCheck();

		IC8MChecker ic8Mchecker = new IC8MChecker(cfg, iC5IandMChecker, iC7MChecker);
		ic8Mchecker.runCheck();
		
		IC9MChecker ic9Mchecker = new IC9MChecker(cfg, checker1);
		ic9Mchecker.runCheck();

		IC10MChecker checker = new IC10MChecker(cfg, ic8Mchecker, ic9Mchecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7MChecker iC7MChecker = new IC7MChecker(cfg, ic2mChecker);
		iC7MChecker.runCheck();

		IC8MChecker ic8Mchecker = new IC8MChecker(cfg, iC5IandMChecker, iC7MChecker);
		ic8Mchecker.runCheck();
		
		IC9MChecker ic9Mchecker = new IC9MChecker(cfg, checker1);
		ic9Mchecker.runCheck();

		IC10MChecker checker = new IC10MChecker(cfg, ic8Mchecker, ic9Mchecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7MChecker iC7MChecker = new IC7MChecker(cfg, ic2mChecker);
		iC7MChecker.runCheck();

		IC8MChecker ic8Mchecker = new IC8MChecker(cfg, iC5IandMChecker, iC7MChecker);
		ic8Mchecker.runCheck();
		
		IC9MChecker ic9Mchecker = new IC9MChecker(cfg, checker1);
		ic9Mchecker.runCheck();

		IC10MChecker checker = new IC10MChecker(cfg, ic8Mchecker, ic9Mchecker);
		assertTrue(checker.runCheck());
	}
}
