package edu.kit.kastel.sdq.coupling.models.conformance;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC1ICheckerTest {
	    
    @Test
    public void testJPMailCodeQlEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.JPMAIL, AnalysisCouplingType.CODEQLEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testJPMailJoanaEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.JPMAIL, AnalysisCouplingType.JOANAEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMeCodeQlEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.COCOME, AnalysisCouplingType.CODEQLEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMeJoanaEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.COCOME, AnalysisCouplingType.JOANAEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorageCodeQlEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.CODEQLEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorageJoanaEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.ECLIPSE_SECURE_STORAGE, AnalysisCouplingType.JOANAEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlannerCodeQlEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.CODEQLEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlannerJoanaEDFA() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.TRAVEL_PLANNER, AnalysisCouplingType.JOANAEDFA);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
}


