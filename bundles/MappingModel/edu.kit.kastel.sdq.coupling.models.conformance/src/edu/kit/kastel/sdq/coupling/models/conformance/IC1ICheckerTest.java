package edu.kit.kastel.sdq.coupling.models.conformance;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IC1ICheckerTest {
	    
    @Test
    public void testJPMail() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.JPMAIL);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testCoCoMe() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.COCOME);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testEclipseSecureStorage() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.ECLIPSE_SECURE_STORAGE);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
    
    @Test
    public void testTravelPlanner() throws Exception {
        SystemConfig cfg = new SystemConfig(
            "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance",
            SystemUnderEval.TRAVEL_PLANNER);

        IC1IChecker checker = new IC1IChecker(cfg);
        assertTrue(checker.runCheck());
    }
}


