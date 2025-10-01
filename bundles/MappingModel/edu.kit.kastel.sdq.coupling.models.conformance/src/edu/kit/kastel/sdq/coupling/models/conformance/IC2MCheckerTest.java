package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC2MCheckerTest {

    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";
		
		IC2MChecker checker = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName, edfaConfigRepFileName, pcmJavaFileName);
		assertTrue(checker.runCheck());
    }
}
