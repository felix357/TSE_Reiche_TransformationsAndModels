package propagation;

import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import uncertainty.SeverityOfImpact;
import uncertainty.UncertaintyFactory;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyScenario;
import uncertainty.UncertaintySource;

public class UncertaintyAnnotator {

	private final IC1MChecker ic1ModelChecker;
	private final IC1IChecker ic1InstanceChecker;
	private final IC2MChecker ic2ModelChecker;
	private final IC2IChecker ic2InstanceChecker;
	private boolean inputReferenceConforms;
	private boolean outputReferenceConforms;

	public UncertaintyAnnotator(IC1MChecker modelChecker, IC1IChecker instanceChecker, IC2MChecker ic2ModelChecker,
			IC2IChecker ic2InstanceChecker,  boolean inputReferenceConforms, boolean outputReferenceConforms) {
		this.ic1ModelChecker = modelChecker;
		this.ic1InstanceChecker = instanceChecker;
		this.ic2ModelChecker = ic2ModelChecker;
		this.ic2InstanceChecker = ic2InstanceChecker;
		this.inputReferenceConforms = inputReferenceConforms;
		this.outputReferenceConforms = outputReferenceConforms;
		
	}

	public void annotateInterface(RequiredInterface req) throws Exception {
		// IC1
		boolean ic1Result = ic1ModelChecker == null || ic1ModelChecker.runCheck();
		if (ic1InstanceChecker != null) {
			ic1Result &= ic1InstanceChecker.runCheck();
		}

		// IC2
		boolean ic2Result = ic2ModelChecker == null || ic2ModelChecker.runCheck();
		if (ic2InstanceChecker != null) {
			ic2Result &= ic2InstanceChecker.runCheck();
		}

		assignUncertaintyLabel(req, ic1Result, ic2Result, outputReferenceConforms && inputReferenceConforms);
	}

	/**
	 * Assigns an uncertainty label based on IC1 check results.
	 */
	private void assignUncertaintyLabel(RequiredInterface req, boolean ic1Result, boolean ic2Result, boolean referenceMetamodelConformance) {
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);

		if (!ic1Result || !ic2Result || !referenceMetamodelConformance) {
			// At least one checker failed → Non-conformance to input interface
			label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
			label.setSeverity(SeverityOfImpact.HIGH);

			UncertaintyLabel incorrectDataLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			incorrectDataLabel.setSource(UncertaintySource.INPUT_DATA_INDUCED);
			incorrectDataLabel.setUncertaintyScenario(UncertaintyScenario.INCORRECT_INPUT_DATA);
			incorrectDataLabel.setSeverity(SeverityOfImpact.HIGH);
			req.getUncertaintyLabel().add(incorrectDataLabel);

		} else {
			// All checks succeeded → correct input data
			label.setUncertaintyScenario(UncertaintyScenario.CORRECT_INPUT_DATA);
			label.setSeverity(SeverityOfImpact.NONE);
		}

		// Uncertainty scenarios that we cannot eliminate
		UncertaintyLabel impreciseLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		impreciseLabel.setSource(UncertaintySource.INPUT_DATA_INDUCED);
		impreciseLabel.setUncertaintyScenario(UncertaintyScenario.IMPRECISE_INPUT_DATA);
		impreciseLabel.setSeverity(SeverityOfImpact.LOW);
		req.getUncertaintyLabel().add(impreciseLabel);

		req.getUncertaintyLabel().add(label);
	}
}
