package propagation;

import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import uncertainty.SeverityOfImpact;
import uncertainty.UncertaintyFactory;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyScenario;
import uncertainty.UncertaintySource;

public class UncertaintyAnnotator {

    private final IC1MChecker modelChecker;
    private final IC1IChecker instanceChecker;

    public UncertaintyAnnotator(IC1MChecker modelChecker, IC1IChecker instanceChecker) {
        this.modelChecker = modelChecker;
        this.instanceChecker = instanceChecker;
    }

    /**
     * Annotates the specified interface with an uncertainty label based on IC1 results.
     */
    public void annotateInterface(RequiredInterface req) throws Exception {
        boolean modelResult = modelChecker.runCheck();
        boolean instanceResult = instanceChecker.runCheck();

        assignUncertaintyLabel(req, modelResult, instanceResult);
    }

    /**
     * Assigns an uncertainty label based on IC1 check results.
     */
    private void assignUncertaintyLabel(RequiredInterface req, boolean modelResult, boolean instanceResult) {
        UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
        label.setSource(UncertaintySource.INPUT_DATA_INDUCED);

        if (!modelResult || !instanceResult) {
            // Either checker failed → Non-conformance to input interface
            label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
            label.setSeverity(SeverityOfImpact.HIGH);
        } else {
            // Both checkers succeeded → correct input data
            label.setUncertaintyScenario(UncertaintyScenario.CORRECT_INPUT_DATA);
            label.setSeverity(SeverityOfImpact.NONE);
        }
        
        // Uncertainty scenarios that we cannot eliminate
        UncertaintyLabel impreciseLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
        impreciseLabel.setSource(UncertaintySource.INPUT_DATA_INDUCED);
        impreciseLabel.setUncertaintyScenario(UncertaintyScenario.IMPRECISE_INPUT_DATA);
        impreciseLabel.setSeverity(SeverityOfImpact.LOW);
        req.getUncertaintyLabel().add(impreciseLabel);

        UncertaintyLabel incorrectLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
        incorrectLabel.setSource(UncertaintySource.INPUT_DATA_INDUCED);
        incorrectLabel.setUncertaintyScenario(UncertaintyScenario.INCORRECT_INPUT_DATA);
        incorrectLabel.setSeverity(SeverityOfImpact.HIGH);
        req.getUncertaintyLabel().add(incorrectLabel);

        req.getUncertaintyLabel().add(label);
    }
}
