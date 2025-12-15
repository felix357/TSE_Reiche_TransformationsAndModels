package propagation;

import java.util.Map;
import java.util.Set;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.conformance.IC10IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC10MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC5IandMChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC6IandMChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC7IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC7MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC8IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC8MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC9IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC9MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig;
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
	private final IC5IandMChecker iC5IandMChecker;
	private final IC6IandMChecker iC6IandMChecker;
	private final IC7MChecker iC7MChecker;
	private final IC7IChecker iC7IChecker;
	private final IC8MChecker ic8MChecker;
	private final IC8IChecker ic8IChecker;
	private final IC9MChecker ic9MChecker;
	private final IC9IChecker ic9IChecker;
	private final IC10MChecker ic10MChecker;
	private final IC10IChecker ic10IChecker;
	private boolean inputReferenceConforms = true;
	private boolean outputReferenceConforms = true;

	public UncertaintyAnnotator(IC1MChecker modelChecker, IC1IChecker instanceChecker, IC2MChecker ic2ModelChecker,
			IC2IChecker ic2InstanceChecker, IC3MChecker ic3ModelChecker, IC3IChecker ic3InstanceChecker,
			IC4MChecker ic4ModelChecker, IC4IChecker ic4InstanceChecker, IC5IandMChecker iC5IandMChecker,
			IC6IandMChecker ic6IandMChecker, IC7MChecker iC7MChecker, IC7IChecker iC7IChecker, IC8MChecker ic8MChecker,
			IC8IChecker ic8IChecker, IC9MChecker ic9MChecker, IC9IChecker ic9IChecker, IC10MChecker ic10MChecker,
			IC10IChecker ic10IChecker, boolean inputReferenceConforms, boolean outputReferenceConforms) {
		this.ic1ModelChecker = modelChecker;
		this.ic1InstanceChecker = instanceChecker;
		this.ic2ModelChecker = ic2ModelChecker;
		this.ic2InstanceChecker = ic2InstanceChecker;
		this.ic3ModelChecker = ic3ModelChecker;
		this.ic3InstanceChecker = ic3InstanceChecker;
		this.ic4ModelChecker = ic4ModelChecker;
		this.ic4InstanceChecker = ic4InstanceChecker;
		this.iC5IandMChecker = iC5IandMChecker;
		this.iC6IandMChecker = ic6IandMChecker;
		this.iC7MChecker = iC7MChecker;
		this.iC7IChecker = iC7IChecker;
		this.ic8MChecker = ic8MChecker;
		this.ic8IChecker = ic8IChecker;
		this.ic9MChecker = ic9MChecker;
		this.ic9IChecker = ic9IChecker;
		this.ic10MChecker = ic10MChecker;
		this.ic10IChecker = ic10IChecker;
		this.inputReferenceConforms = inputReferenceConforms;
		this.outputReferenceConforms = outputReferenceConforms;
	}

	public UncertaintyAnnotator(SystemConfig cfg) {
		this.ic1ModelChecker = new IC1MChecker(cfg);
		this.ic1InstanceChecker = new IC1IChecker(cfg);
		this.ic2ModelChecker = new IC2MChecker(cfg);
		this.ic2InstanceChecker = new IC2IChecker(cfg);

		ic1ModelChecker.runCheck();
		ic2ModelChecker.runCheck();
		Set<String> secLiterals = ic1ModelChecker.getAllSecurityLiterals();
		Set<String> systemElementsFromIC2 = ic2ModelChecker.getSystemElemsC();
		Set<String> configurationsFromIC2 = ic2ModelChecker.getConfigsRefsC();
		this.ic3ModelChecker = new IC3MChecker(cfg, secLiterals, systemElementsFromIC2, configurationsFromIC2);

		ic1InstanceChecker.runCheck();
		Map<String, String> codeqlRivMap = ic1InstanceChecker.getCodeqlRivMap();
		ic2InstanceChecker.runCheck();
		Set<String> sysElements = ic2InstanceChecker.getSystemElementsFromIC2();
		Set<String> configs = ic2InstanceChecker.getConfigurationsFromIC2();

		this.ic3InstanceChecker = new IC3IChecker(cfg, codeqlRivMap, sysElements, configs);
		Map<String, String> rivValuesMap = ic1InstanceChecker.getRivValuesMap();
		this.ic4ModelChecker = new IC4MChecker(cfg);
		this.ic4InstanceChecker = new IC4IChecker(cfg, codeqlRivMap, rivValuesMap);

		this.iC5IandMChecker = new IC5IandMChecker(cfg, secLiterals, systemElementsFromIC2);
		this.iC6IandMChecker = new IC6IandMChecker(cfg, iC5IandMChecker);

		this.iC7MChecker = new IC7MChecker(cfg, ic2ModelChecker);
		this.iC7IChecker = new IC7IChecker(cfg, ic2InstanceChecker);

		this.iC7MChecker.runCheck();

		this.ic8MChecker = new IC8MChecker(cfg, iC5IandMChecker, iC7MChecker);
		this.ic8IChecker = new IC8IChecker(cfg, iC5IandMChecker, iC7IChecker);

		this.ic9MChecker = new IC9MChecker(cfg, ic1ModelChecker);
		this.ic9IChecker = new IC9IChecker(cfg, ic1InstanceChecker);

		this.ic10MChecker = new IC10MChecker(cfg, ic8MChecker, ic9MChecker);
		this.ic10IChecker = new IC10IChecker(cfg, ic8IChecker, ic9IChecker);
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

		// IC5
		boolean ic5Result = iC5IandMChecker == null || iC5IandMChecker.runCheck();

		// IC6
		boolean ic6Result = iC6IandMChecker == null || iC6IandMChecker.runCheck();

		// IC7
		boolean ic7Result = iC7MChecker == null || iC7MChecker.runCheck();
		if (iC7IChecker != null) {
			ic7Result &= iC7IChecker.runCheck();
		}

		// IC8
		boolean ic8Result = ic8MChecker == null || ic8MChecker.runCheck();
		if (ic8IChecker != null) {
			ic8Result &= ic8IChecker.runCheck();
		}

		// IC9
		boolean ic9Result = ic9MChecker == null || ic9MChecker.runCheck();
		if (ic9IChecker != null) {
			ic9Result &= ic9IChecker.runCheck();
		}

		// IC10
		boolean ic10Result = ic10MChecker == null || ic10MChecker.runCheck();
		if (ic10IChecker != null) {
			ic9Result &= ic10IChecker.runCheck();
		}

		assignUncertaintyLabel(req, ic1Result, ic2Result, ic3Result, ic4Result, ic5Result, ic6Result, ic7Result,
				ic8Result, ic9Result, ic10Result, outputReferenceConforms && inputReferenceConforms);
	}

	public void annotateInterfaceWithUncertaintyAnnoation(RequiredInterface req, UncertaintySource uncertaitySource) {
		UncertaintyLabel labelNotFinal = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		UncertaintyLabel labelFinal = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		if (uncertaitySource.equals(UncertaintySource.ORCHESTRATION_DECISION_INDUCED)) {
			labelNotFinal.setSource(UncertaintySource.ORCHESTRATION_DECISION_INDUCED);
			labelNotFinal.setUncertaintyScenario(UncertaintyScenario.ORCHESTRATION_NOT_FINAL);

			labelFinal.setSource(UncertaintySource.ORCHESTRATION_DECISION_INDUCED);
			labelFinal.setUncertaintyScenario(UncertaintyScenario.ORCHESTRATION_FINAL);
		}
		req.getUncertaintyLabel().add(labelNotFinal);
		req.getUncertaintyLabel().add(labelFinal);
	}

	/**
	 * Assigns an uncertainty label based on IC1 check results.
	 */
	private void assignUncertaintyLabel(RequiredInterface req, boolean ic1Result, boolean ic2Result, boolean ic3Result,
			boolean ic4Result, boolean ic5Result, boolean ic6Result, boolean ic7Result, boolean ic8Result,
			boolean ic9Result, boolean ic10Result, boolean referenceMetamodelConformance) {
		UncertaintyLabel label = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
		label.setSource(UncertaintySource.INPUT_DATA_INDUCED);

		if (!ic1Result || !ic2Result || !ic3Result || !ic4Result || !ic5Result || !ic6Result || !ic7Result || !ic8Result
				|| !ic9Result || !referenceMetamodelConformance) {
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
