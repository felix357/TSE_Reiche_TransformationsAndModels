package edu.kit.kastel.sdq.coupling.models.conformance;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;
import edu.kit.kastel.sdq.coupling.models.extension.dataflowanalysis.parameterannotation.ParameterAnnotations;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintySource;

/**
 * Round-Robin Uncertainty Propagation Controller with detailed propagation
 * logic.
 */
public class RoundRobinUncertaintyController {

	private final AnalysisGraph graph;

	// Sequence of uncertainty types for round-robin propagation
	private final UncertaintySource[] roundRobinTypes = { UncertaintySource.INPUT_DATA_INDUCED,
			UncertaintySource.SCENARIO_ASSUMPTION_INDUCED, UncertaintySource.METHODOLOGY_INDUCED,
			UncertaintySource.MODELING_INDUCED, UncertaintySource.OUTPUT_DATA_INDUCED };

	public RoundRobinUncertaintyController(AnalysisGraph graph, ParameterAnnotations parameterAnnotations) {
		this.graph = graph;
	}

	/**
	 * Execute round-robin uncertainty propagation until a fixed point is reached.
	 */
	public void propagate() {
		boolean changed = true;

		while (changed) {
			changed = false;

			List<UncertaintyLabel> beforeState = copyAllUncertaintyLabels();

			// Round-robin propagation
			for (UncertaintySource type : roundRobinTypes) {
				switch (type) {
				case INPUT_DATA_INDUCED:
					propagateInputDataIntroducedUncertainty(graph);
					break;
				case SCENARIO_ASSUMPTION_INDUCED:
				case METHODOLOGY_INDUCED:
				case MODELING_INDUCED:
					propagateNodeIntroducedUncertainty(type);
					break;
				case OUTPUT_DATA_INDUCED:
					propagateOutputDataIntroducedUncertainty();
					break;
				}
			}

			List<UncertaintyLabel> afterState = copyAllUncertaintyLabels();
			if (!Objects.equals(beforeState, afterState)) {
				changed = true;
			}
		}
	}

	/* Propagation Algorithm 1: Input Data-introduced Uncertainty */
	private void propagateInputDataIntroducedUncertainty(AnalysisGraph graph) {
		for (AnalysisComponent comp : graph.getComponents()) {
			for (RequiredInterface input : comp.getInputs()) {
				for (UncertaintyLabel label : input.getUncertaintyLabel()) {
					if (label.getSource() == UncertaintySource.INPUT_DATA_INDUCED) {
						for (ProvidedInterface output : comp.getOutputs()) {
							boolean alreadyAnnotated = output.getUncertaintyLabel().stream()
									.anyMatch(l -> l.getSource() == UncertaintySource.OUTPUT_DATA_INDUCED);
							if (!alreadyAnnotated) {
								UncertaintyLabel newLabel = uncertainty.UncertaintyFactory.eINSTANCE
										.createUncertaintyLabel();
								newLabel.setSource(UncertaintySource.OUTPUT_DATA_INDUCED);
								newLabel.setSeverity(label.getSeverity()); // optional: copy severity
								output.getUncertaintyLabel().add(newLabel);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Propagation Algorithm 2: Node-based uncertainty
	 * (Scenario/Methodology/Modeling)
	 */
	private void propagateNodeIntroducedUncertainty(UncertaintySource type) {
		for (AnalysisComponent component : graph.getComponents()) {
			for (UncertaintyLabel u : component.getUncertaintyLabels()) {
				if (u.getSource() == type) {
					List<RequiredInterface> inputs = component.getInputs();
					List<ProvidedInterface> outputs = component.getOutputs();
					
					for (RequiredInterface input : inputs) {
						for (ProvidedInterface output : outputs) {
							if (shouldPropagate(u, input, output)) {
								annotateOutput(output, UncertaintySource.OUTPUT_DATA_INDUCED);
							}
						}
					}
				}
			}
		}
	}

	/** Propagation Algorithm 3: Output Data-introduced Uncertainty */
	private void propagateOutputDataIntroducedUncertainty() {
		for (AnalysisComponent component : graph.getComponents()) {
			for (ProvidedInterface output : component.getOutputs()) {
				for (UncertaintyLabel u : output.getUncertaintyLabel()) {
					if (u.getSource() == UncertaintySource.OUTPUT_DATA_INDUCED) {
						for (Connection conn : graph.getConnections()) {
							if (conn.getFrom() == output) {
								RequiredInterface input = conn.getTo();
								if (shouldPropagate(u, output, input)) {
									annotateInput(input, UncertaintySource.INPUT_DATA_INDUCED);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Helper to annotate an OutputInterface if it doesn't already have the label
	 */
	private void annotateOutput(ProvidedInterface output, UncertaintySource type) {
		boolean exists = output.getUncertaintyLabel().stream().anyMatch(l -> l.getSource() == type);
		if (!exists) {
			UncertaintyLabel label = uncertainty.UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(type);
			label.setSeverity(uncertainty.SeverityOfImpact.LOW);
			output.getUncertaintyLabel().add(label);
		}
	}

	/** Helper to annotate an InputInterface if it doesn't already have the label */
	private void annotateInput(RequiredInterface input, UncertaintySource type) {
		boolean exists = input.getUncertaintyLabel().stream().anyMatch(l -> l.getSource() == type);
		if (!exists) {
			UncertaintyLabel label = uncertainty.UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
			label.setSource(type);
			label.setSeverity(uncertainty.SeverityOfImpact.LOW);
			input.getUncertaintyLabel().add(label);
		}
	}

	private boolean shouldPropagate(UncertaintyLabel u, RequiredInterface input, ProvidedInterface output) {
		return true;
	}

	private boolean shouldPropagate(UncertaintyLabel u, ProvidedInterface output, RequiredInterface input) {
		return true;
	}

	/** Helper to copy all uncertainty labels from the graph */
	private List<UncertaintyLabel> copyAllUncertaintyLabels() {
		Set<UncertaintyLabel> allLabels = new HashSet<>();
		for (AnalysisComponent c : graph.getComponents()) {
			allLabels.addAll(c.getUncertaintyLabels());
			c.getInputs().forEach(i -> allLabels.addAll(i.getUncertaintyLabel()));
			c.getOutputs().forEach(o -> allLabels.addAll(o.getUncertaintyLabel()));
		}
		return List.copyOf(allLabels);
	}
}
