package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC3ICheckerTest {

    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	
    	IC2IChecker c2 = new IC2IChecker(basePath, "jpmail.parameterannotation", "jpmail");
		assertTrue(c2.runCheck());
		
		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();
		
		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements, configs);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMe() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	
    	IC2IChecker c2 = new IC2IChecker(basePath, "cocome.parameterannotation", "cocome");
		assertTrue(c2.runCheck());
		
		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();
		
		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements, configs);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlanner() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	
    	IC2IChecker c2 = new IC2IChecker(basePath, "travelplanner.parameterannotation", "travelplanner");
		assertTrue(c2.runCheck());
		
		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();
		
		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements, configs);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorage() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";    	
    	IC1IChecker c1 = new IC1IChecker(basePath, "correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues");
    	c1.runCheck();
    	Map<String, String> codeqlRivMap = c1.getCodeqlRivMap();
    	
    	IC2IChecker c2 = new IC2IChecker(basePath, "eclipsesecurestorage.parameterannotation", "eclipsesecurestorage");
		assertTrue(c2.runCheck());
		
		Set<String> sysElements = c2.getSystemElementsFromIC2();
		Set<String> configs = c2.getConfigurationsFromIC2();
		
		IC3IChecker checker = new IC3IChecker(basePath, "codeql4extendeddataflow.codeql", codeqlRivMap, sysElements, configs);
		assertTrue(checker.runCheck());
    }
    
}
