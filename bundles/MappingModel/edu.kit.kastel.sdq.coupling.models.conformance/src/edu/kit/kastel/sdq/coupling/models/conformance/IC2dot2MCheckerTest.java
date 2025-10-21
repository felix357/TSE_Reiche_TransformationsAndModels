package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC2dot2MCheckerTest {

    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/";
		
    	String edfaConfigPath = basePath + "extendeddataflow.configurationrepresentation";
    	String edfacodeqlCorrespondencePath = basePath + "correspondences.edfacodeqlcorrespondences";
    	String annotationFile = "jpmail.parameterannotation";
		
		IC2dot2MChecker checker = new IC2dot2MChecker(edfaConfigPath, edfacodeqlCorrespondencePath, annotationFile);
		assertTrue(checker.runCheck());
    }
}
