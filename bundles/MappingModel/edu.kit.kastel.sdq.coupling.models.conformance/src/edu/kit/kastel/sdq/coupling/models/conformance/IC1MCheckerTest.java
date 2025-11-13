package edu.kit.kastel.sdq.coupling.models.conformance;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC1MCheckerTest {
	
    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String architectureModelName = "jpmail.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";
		
		IC1MChecker checker = new IC1MChecker(basePath, architectureModelName, correspondenceName, sourceCodeAnalysisName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCocome() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe";
		String architectureModelName = "cocome.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";
		
		IC1MChecker checker = new IC1MChecker(basePath, architectureModelName, correspondenceName, sourceCodeAnalysisName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlanner() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/TravelPlanner";
		String architectureModelName = "travelplanner.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";
		
		IC1MChecker checker = new IC1MChecker(basePath, architectureModelName, correspondenceName, sourceCodeAnalysisName);
		assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorage() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/EclipseSecureStorage";
		String architectureModelName = "eclipsesecurestorage.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";
		
		IC1MChecker checker = new IC1MChecker(basePath, architectureModelName, correspondenceName, sourceCodeAnalysisName);
		assertTrue(checker.runCheck());
    }
}


