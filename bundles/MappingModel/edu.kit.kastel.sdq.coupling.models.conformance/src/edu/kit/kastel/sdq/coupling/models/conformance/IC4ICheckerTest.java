package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class IC4ICheckerTest {

    @Test
    public void testJpmail() throws Exception {
    	
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	Map<String, String> rivValuesMap = c1.getRivValuesMap();
    	
    	
		IC4IChecker checker = new IC4IChecker(
				basePath,
				"codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap,
				rivValuesMap
			);
		
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMe() throws Exception {
    	
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";
    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	Map<String, String> rivValuesMap = c1.getRivValuesMap();
    	
    	
		IC4IChecker checker = new IC4IChecker(
				basePath,
				"codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap,
				rivValuesMap
			);
		
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorage() throws Exception {
    	
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";
    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	Map<String, String> rivValuesMap = c1.getRivValuesMap();
    	
    	
		IC4IChecker checker = new IC4IChecker(
				basePath,
				"codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap,
				rivValuesMap
			);
		
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlanner() throws Exception {
    	
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";
    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	Map<String, String> rivValuesMap = c1.getRivValuesMap();
    	
    	
		IC4IChecker checker = new IC4IChecker(
				basePath,
				"codeql4extendeddataflow.configurationrepresentation",
				codeqlRivMap,
				rivValuesMap
			);
		
		assertTrue(checker.runCheck());
    }
    
}
