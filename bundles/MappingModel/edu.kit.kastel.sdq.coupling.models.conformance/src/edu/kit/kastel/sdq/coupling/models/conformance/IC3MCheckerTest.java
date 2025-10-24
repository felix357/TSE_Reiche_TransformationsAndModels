package edu.kit.kastel.sdq.coupling.models.conformance;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

public class IC3MCheckerTest {

    @Test
    public void testJpmail() throws Exception {
    	String basePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail";
		String architectureModelName = "jpmail.pddc";
		String correspondenceName = "correspondences.edfacodeqlcorrespondences";
		String sourceCodeAnalysisName = "codeql4extendeddataflow.codeql";
		
		IC1MChecker checker1 = new IC1MChecker(basePath, architectureModelName, correspondenceName, sourceCodeAnalysisName);
		checker1.runCheck();

		String correspondencesFileName = "correspondences.edfacodeqlcorrespondences";
		String codeqlConfigurationRepFileName = "codeql4extendeddataflow.configurationrepresentation";
		String edfaConfigRepFileName = "extendeddataflow.configurationrepresentation";
		String pcmJavaFileName = "correspondences.pcmjavacorrespondence";
		
		IC2MChecker checker2 = new IC2MChecker(basePath, correspondencesFileName, codeqlConfigurationRepFileName, edfaConfigRepFileName, pcmJavaFileName);
		checker2.runCheck();
		
		Set<String> secLiterals = checker1.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = checker2.getSystemElemsC();
		Set<String> configurationsFromIC2 = checker2.getConfigsRefsC();
		
		IC3MChecker checker3 = new IC3MChecker(
				basePath,
			    "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/JPMail/codeql4extendeddataflow.codeql",
			    secLiterals,
			    systemElementsFromIC2,
			    configurationsFromIC2
			);
		
		assertTrue(checker3.runCheck());
    }
}
