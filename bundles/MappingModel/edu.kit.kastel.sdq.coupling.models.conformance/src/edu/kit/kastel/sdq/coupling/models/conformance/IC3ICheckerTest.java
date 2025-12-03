package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC3ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.JPMAIL);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(cfg);
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements,
				configs);
		assertTrue(checker.runCheck());
	}

	@Test
	public void testCoCoMe() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";

		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.COCOME);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(cfg);
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements,
				configs);
		assertTrue(checker.runCheck());
	}

	@Test
	public void testTravelPlanner() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.TRAVEL_PLANNER);

		IC1IChecker c1 = new IC1IChecker(cfg);

		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(cfg);
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements,
				configs);
		assertTrue(checker.runCheck());
	}

	@Test
	public void testEclipseSecureStorage() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";
		SystemConfig cfg = new SystemConfig(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
				SystemUnderEval.ECLIPSE_SECURE_STORAGE);

		IC1IChecker c1 = new IC1IChecker(cfg);
		c1.runCheck();
		Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();

		IC2IChecker c2 = new IC2IChecker(cfg);
		assertTrue(c2.runCheck());

		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();

		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements,
				configs);
		assertTrue(checker.runCheck());
	}

}
