package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC2ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		IC2IChecker checker = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testTravelPlanner() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";
		IC2IChecker checker = new IC2IChecker(basePath, "travelplanner.parameterannotation", "travelplanner");
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testEclipseSecureStorage() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";
		IC2IChecker checker = new IC2IChecker(basePath, "eclipsesecurestorage.parameterannotation", "eclipsesecurestorage");
		assertTrue(checker.runCheck());
	}
	
	@Test
	public void testCoCoMe() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";
		IC2IChecker checker = new IC2IChecker(basePath, "cocome.parameterannotation", "cocome");
		assertTrue(checker.runCheck());
	}
}
