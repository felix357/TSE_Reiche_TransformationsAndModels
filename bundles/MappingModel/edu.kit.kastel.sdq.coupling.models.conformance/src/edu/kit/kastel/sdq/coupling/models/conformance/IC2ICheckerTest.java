package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC2ICheckerTest {

	@Test
	public void testJpmail() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		IC2IChecker checker = new IC2IChecker(basePath);
		assertTrue(checker.runCheck());
	}
}
