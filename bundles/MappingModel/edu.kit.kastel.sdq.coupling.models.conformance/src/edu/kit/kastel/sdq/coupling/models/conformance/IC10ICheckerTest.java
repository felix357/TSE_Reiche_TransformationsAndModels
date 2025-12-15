package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC10ICheckerTest {

	@Test
	public void testJPMail() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();
		
		IC1IChecker checker1I = new IC1IChecker(cfg);
		checker1I.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();
		
		IC2IChecker ic2iChecker = new IC2IChecker(cfg);
		ic2iChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7IChecker iC7IChecker = new IC7IChecker(cfg, ic2iChecker);
		iC7IChecker.runCheck();

		IC8IChecker ic8ichecker = new IC8IChecker(cfg, iC5IandMChecker, iC7IChecker);
		ic8ichecker.runCheck();
		
		IC9IChecker ic9Ichecker = new IC9IChecker(cfg, checker1I);
		ic9Ichecker.runCheck();

		IC10IChecker checker = new IC10IChecker(cfg, ic8ichecker, ic9Ichecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testCoCoMe() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();
		
		IC1IChecker checker1I = new IC1IChecker(cfg);
		checker1I.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();
		
		IC2IChecker ic2iChecker = new IC2IChecker(cfg);
		ic2iChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7IChecker iC7IChecker = new IC7IChecker(cfg, ic2iChecker);
		iC7IChecker.runCheck();

		IC8IChecker ic8ichecker = new IC8IChecker(cfg, iC5IandMChecker, iC7IChecker);
		ic8ichecker.runCheck();
		
		IC9IChecker ic9Ichecker = new IC9IChecker(cfg, checker1I);
		ic9Ichecker.runCheck();

		IC10IChecker checker = new IC10IChecker(cfg, ic8ichecker, ic9Ichecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorage() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();
		
		IC1IChecker checker1I = new IC1IChecker(cfg);
		checker1I.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();
		
		IC2IChecker ic2iChecker = new IC2IChecker(cfg);
		ic2iChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7IChecker iC7IChecker = new IC7IChecker(cfg, ic2iChecker);
		iC7IChecker.runCheck();

		IC8IChecker ic8ichecker = new IC8IChecker(cfg, iC5IandMChecker, iC7IChecker);
		ic8ichecker.runCheck();
		
		IC9IChecker ic9Ichecker = new IC9IChecker(cfg, checker1I);
		ic9Ichecker.runCheck();

		IC10IChecker checker = new IC10IChecker(cfg, ic8ichecker, ic9Ichecker);
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testTravelPlanner() throws Exception {
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER);

		IC1MChecker checker1 = new IC1MChecker(cfg);
		checker1.runCheck();
		
		IC1IChecker checker1I = new IC1IChecker(cfg);
		checker1I.runCheck();

		IC2MChecker ic2mChecker = new IC2MChecker(cfg);
		ic2mChecker.runCheck();
		
		IC2IChecker ic2iChecker = new IC2IChecker(cfg);
		ic2iChecker.runCheck();

		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2mChecker.getSystemElemsC();

		IC5IandMChecker iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		iC5IandMChecker.runCheck();
		
		IC7IChecker iC7IChecker = new IC7IChecker(cfg, ic2iChecker);
		iC7IChecker.runCheck();

		IC8IChecker ic8ichecker = new IC8IChecker(cfg, iC5IandMChecker, iC7IChecker);
		ic8ichecker.runCheck();
		
		IC9IChecker ic9Ichecker = new IC9IChecker(cfg, checker1I);
		ic9Ichecker.runCheck();

		IC10IChecker checker = new IC10IChecker(cfg, ic8ichecker, ic9Ichecker);
		assertTrue(checker.runCheck());
	}
}
