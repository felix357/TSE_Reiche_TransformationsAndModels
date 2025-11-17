package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC4MCheckerTest {

	@Test
	public void testJpmail() throws Exception {
		IC4MChecker checker = new IC4MChecker(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail",
				"jpmail.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
				"codeql4extendeddataflow.configurationrepresentation");

		assertTrue(checker.runCheck());
	}

	@Test
	public void testCoCoMe() throws Exception {
		IC4MChecker checker = new IC4MChecker(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe",
				"cocome.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
				"codeql4extendeddataflow.configurationrepresentation");

		assertTrue(checker.runCheck());
	}

	@Test
	public void testEclipseSecureStorage() throws Exception {
		IC4MChecker checker = new IC4MChecker(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage",
				"eclipsesecurestorage.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
				"codeql4extendeddataflow.configurationrepresentation");

		assertTrue(checker.runCheck());
	}

	@Test
	public void testTravelPlanner() throws Exception {
		IC4MChecker checker = new IC4MChecker(
				"C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner",
				"travelplanner.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
				"codeql4extendeddataflow.configurationrepresentation");

		assertTrue(checker.runCheck());
	}
}
