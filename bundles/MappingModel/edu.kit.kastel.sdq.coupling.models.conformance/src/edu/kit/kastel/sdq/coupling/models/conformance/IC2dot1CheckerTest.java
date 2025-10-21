package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC2dot1CheckerTest {

	@Test
	public void testJpmail() throws Exception {
		String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/";
		IC2dot1MChecker checker = new IC2dot1MChecker(basePath + "jpmail.parameterannotation",
				basePath + "correspondences.pcmjavacorrespondence",
				"jpmail.pddc#_SIw9sNGFEe6e8_rNGeIvbg-characteristicEnumerations@0.literals@");
		assertTrue(checker.runCheck());
	}
}
