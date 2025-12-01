package propagation;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4MChecker;
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
	private final IC3MChecker ic3ModelChecker;
	private final IC3IChecker ic3InstanceChecker;
	private final IC4MChecker ic4ModelChecker;
	private final IC4IChecker ic4InstanceChecker;
	private boolean inputReferenceConforms = true;
	private boolean outputReferenceConforms = true;

	public UncertaintyAnnotator(IC1MChecker modelChecker, IC1IChecker instanceChecker, IC2MChecker ic2ModelChecker,
			IC2IChecker ic2InstanceChecker, IC3MChecker ic3ModelChecker, IC3IChecker ic3InstanceChecker,
			IC4MChecker ic4ModelChecker, IC4IChecker ic4InstanceChecker, boolean inputReferenceConforms,
			boolean outputReferenceConforms) {
		this.ic1ModelChecker = modelChecker;
		this.ic1InstanceChecker = instanceChecker;
		this.ic2ModelChecker = ic2ModelChecker;
		this.ic2InstanceChecker = ic2InstanceChecker;
		this.ic3ModelChecker = ic3ModelChecker;
		this.ic3InstanceChecker = ic3InstanceChecker;
		this.ic4ModelChecker = ic4ModelChecker;
		this.ic4InstanceChecker = ic4InstanceChecker;
		this.inputReferenceConforms = inputReferenceConforms;
		this.outputReferenceConforms = outputReferenceConforms;

	}

	public void annotateAnalysisComponent(AnalysisComponent analysisComponent, UncertaintySource uncertaitySource) {
		assignUncertaintyLabel(analysisComponent, uncertaitySource);
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

		// IC3
		boolean ic3Result = ic3ModelChecker == null || ic3ModelChecker.runCheck();
		if (ic3InstanceChecker != null) {
			ic2Result &= ic3InstanceChecker.runCheck();
		}

		// IC4
		boolean ic4Result = ic4ModelChecker == null || ic4ModelChecker.runCheck();
		if (ic4InstanceChecker != null) {
			ic4Result &= ic4InstanceChecker.runCheck();
		}

		assignUncertaintyLabel(req, ic1Result, ic2Result, ic3Result, ic4Result,
				outputReferenceConforms && inputReferenceConforms);
	}

	/**
	 * Assigns an uncertainty label based on IC1 check results.
	 */
	private void assignUncertaintyLabel(RequiredInterface req, boolean ic1Result, boolean ic2Result, boolean ic3Result,
			boolean ic4Result, boolean referenceMetamodelConformance) {
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);

		if (!ic1Result || !ic2Result || !ic3Result || !ic4Result || !referenceMetamodelConformance) {
			// At least one checker failed → Non-conformance to input interface
			label.setUncertaintyScenario(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE);
			label.setSeverity(SeverityOfImpact.HIGH);

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

	private void assignUncertaintyLabel(AnalysisComponent analysisComponent, UncertaintySource uncertaitySource) {
		// since we can not reduce the uncertainty scenarios we have to apply all
		// uncertainty scenarios to the coupled analysis graph.
		if (uncertaitySource == UncertaintySource.SCENARIO_ASSUMPTION_INDUCED) {
			UncertaintyLabel scenarioCorrect = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			scenarioCorrect.setSource(uncertaitySource);
			scenarioCorrect.setUncertaintyScenario(UncertaintyScenario.SCENARIO_DEFINITION_CORRECT);
			
			UncertaintyLabel scenarioInCorrect = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			scenarioInCorrect.setSource(uncertaitySource);
			scenarioInCorrect.setUncertaintyScenario(UncertaintyScenario.SCENARIO_DEFINITION_INCORRECT);

			analysisComponent.getUncertaintyLabels().add(scenarioCorrect);
			analysisComponent.getUncertaintyLabels().add(scenarioInCorrect);
		} else if (uncertaitySource == UncertaintySource.METHODOLOGY_INDUCED) {			
			UncertaintyLabel approximationLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			approximationLabel.setSource(uncertaitySource);
			approximationLabel.setUncertaintyScenario(UncertaintyScenario.METHODOLOGY_APPROXIMATION);
			
			UncertaintyLabel overSimplifiedLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			overSimplifiedLabel.setSource(uncertaitySource);
			overSimplifiedLabel.setUncertaintyScenario(UncertaintyScenario.METHODOLOGY_OVER_SIMPLIFICATION);
			
			UncertaintyLabel correctAnalysisLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			correctAnalysisLabel.setSource(uncertaitySource);
			correctAnalysisLabel.setUncertaintyScenario(UncertaintyScenario.METHODOLOGY_CORRECT);
			
			analysisComponent.getUncertaintyLabels().add(approximationLabel);
			analysisComponent.getUncertaintyLabels().add(overSimplifiedLabel);
			analysisComponent.getUncertaintyLabels().add(correctAnalysisLabel);
		} else if (uncertaitySource == UncertaintySource.MODELING_INDUCED) {
			UncertaintyLabel modelCorrectLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			modelCorrectLabel.setSource(uncertaitySource);
			modelCorrectLabel.setUncertaintyScenario(UncertaintyScenario.MODEL_CORRECT);
			
			UncertaintyLabel modelUnderSpecificationLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			modelUnderSpecificationLabel.setSource(uncertaitySource);
			modelUnderSpecificationLabel.setUncertaintyScenario(UncertaintyScenario.MODEL_UNDER_SPECIFICATION);
			
			UncertaintyLabel modelDiscrapencyLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			modelDiscrapencyLabel.setSource(uncertaitySource);
			modelDiscrapencyLabel.setUncertaintyScenario(UncertaintyScenario.MODEL_DISCREPANCY);
			
			analysisComponent.getUncertaintyLabels().add(modelCorrectLabel);
			analysisComponent.getUncertaintyLabels().add(modelUnderSpecificationLabel);
			analysisComponent.getUncertaintyLabels().add(modelDiscrapencyLabel);
		}
	}
}
