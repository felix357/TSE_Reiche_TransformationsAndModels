package edu.kit.kastel.sdq.coupling.models.conformance;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC1ICheckerTest {
	
    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";
		
		IC1IChecker checker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMe() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";
		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";
		
		IC1IChecker checker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorage() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";
		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";
		
		IC1IChecker checker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlanner() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";
		String rivCorrespondenceName = "correspondences.codeqlresultingvaluescorrespondences";
		String rivName = "resultingvalues.codeqlresultingvalues";
		
		IC1IChecker checker = new IC1IChecker(basePath, rivCorrespondenceName, rivName);
		assertTrue(checker.runCheck());
    }
}


