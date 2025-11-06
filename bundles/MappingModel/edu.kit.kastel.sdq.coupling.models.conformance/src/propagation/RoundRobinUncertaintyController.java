package propagation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import analysiscouplinggraph.AnalysisComponent;
import analysiscouplinggraph.AnalysisGraph;
import analysiscouplinggraph.Connection;
import analysiscouplinggraph.ProvidedInterface;
import analysiscouplinggraph.RequiredInterface;
import uncertainty.UncertaintyFactory;
import uncertainty.UncertaintyLabel;
import uncertainty.UncertaintyScenario;
import uncertainty.UncertaintySource;

/**
 * Round-Robin uncertainty propagation controller with scenario-level tracking.
 */
public class RoundRobinUncertaintyController {

    private final AnalysisGraph graph;

    // Sequence of uncertainty types for round-robin propagation
    private final UncertaintySource[] roundRobinTypes = {
            UncertaintySource.INPUT_DATA_INDUCED,
            UncertaintySource.SCENARIO_ASSUMPTION_INDUCED,
            UncertaintySource.METHODOLOGY_INDUCED,
            UncertaintySource.MODELING_INDUCED,
            UncertaintySource.OUTPUT_DATA_INDUCED
    };

    /**
     * Mapping of original scenario → propagated scenario(s)
     */
    private static final Map<UncertaintyScenario, Set<UncertaintyScenario>> scenarioPropagationMap = new EnumMap<>(UncertaintyScenario.class);

    static {
        // U1 → U5
        scenarioPropagationMap.put(UncertaintyScenario.NON_CONFORMANCE_TO_INPUT_INTERFACE, EnumSet.of(UncertaintyScenario.OUTPUT_ERROR));
        scenarioPropagationMap.put(UncertaintyScenario.IMPRECISE_INPUT_DATA, EnumSet.of(UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.INCORRECT_INPUT_DATA, EnumSet.of(UncertaintyScenario.OUTPUT_ERROR));
        scenarioPropagationMap.put(UncertaintyScenario.CORRECT_INPUT_DATA, EnumSet.of(UncertaintyScenario.OUTPUT_CORRECT));

        // U2 → U5
        scenarioPropagationMap.put(UncertaintyScenario.SCENARIO_DEFINITION_INCORRECT, EnumSet.of(UncertaintyScenario.OUTPUT_ERROR, UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.SCENARIO_DEFINITION_CORRECT, EnumSet.of(UncertaintyScenario.OUTPUT_CORRECT));

        // U3 → U5
        scenarioPropagationMap.put(UncertaintyScenario.METHODOLOGY_ABSTRACTION, EnumSet.of(UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.METHODOLOGY_APPROXIMATION, EnumSet.of(UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.METHODOLOGY_OVER_SIMPLIFICATION, EnumSet.of(UncertaintyScenario.OUTPUT_ERROR));
        scenarioPropagationMap.put(UncertaintyScenario.METHODOLOGY_CORRECT, EnumSet.of(UncertaintyScenario.OUTPUT_CORRECT));

        // U4 → U5
        scenarioPropagationMap.put(UncertaintyScenario.MODEL_UNDER_SPECIFICATION, EnumSet.of(UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.MODEL_ABSTRACTION, EnumSet.of(UncertaintyScenario.OUTPUT_IMPRECISION));
        scenarioPropagationMap.put(UncertaintyScenario.MODEL_DISCREPANCY, EnumSet.of(UncertaintyScenario.OUTPUT_ERROR));
        scenarioPropagationMap.put(UncertaintyScenario.MODEL_CORRECT, EnumSet.of(UncertaintyScenario.OUTPUT_CORRECT));

        // U5 → U1
        scenarioPropagationMap.put(UncertaintyScenario.OUTPUT_ERROR, EnumSet.of(UncertaintyScenario.INCORRECT_INPUT_DATA));
        scenarioPropagationMap.put(UncertaintyScenario.OUTPUT_IMPRECISION, EnumSet.of(UncertaintyScenario.IMPRECISE_INPUT_DATA));
        scenarioPropagationMap.put(UncertaintyScenario.OUTPUT_CORRECT, EnumSet.of(UncertaintyScenario.CORRECT_INPUT_DATA));

        // U6 → no propagation
        scenarioPropagationMap.put(UncertaintyScenario.ORCHESTRATION_NOT_FINAL, EnumSet.noneOf(UncertaintyScenario.class));
        scenarioPropagationMap.put(UncertaintyScenario.ORCHESTRATION_FINAL, EnumSet.noneOf(UncertaintyScenario.class));
    }

    public RoundRobinUncertaintyController(AnalysisGraph graph) {
        this.graph = graph;
    }

    /**
     * Holds a scenario with the component it affected.
     */
    public static class ScenarioWithComponent {
        public final String componentName;
        public final UncertaintyScenario scenario;

        public ScenarioWithComponent(String componentName, UncertaintyScenario scenario) {
            this.componentName = componentName;
            this.scenario = scenario;
        }

        @Override
        public String toString() {
            return componentName + ": " + scenario.name();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ScenarioWithComponent that)) return false;
            return Objects.equals(componentName, that.componentName) &&
                   scenario == that.scenario;
        }

        @Override
        public int hashCode() {
            return Objects.hash(componentName, scenario);
        }
    }

    /**
     * Run round-robin scenario-level propagation.
     */
    public List<ScenarioWithComponent> propagateWithComponentInfo() {
        boolean changed = true;
        List<ScenarioWithComponent> afterState = new ArrayList<>();

        while (changed) {
            changed = false;
            List<ScenarioWithComponent> beforeState = copyAllScenarioWithComponent();

            // Round-robin propagation
            for (UncertaintySource type : roundRobinTypes) {
                switch (type) {
                    case INPUT_DATA_INDUCED:
                        propagateInputDataIntroducedUncertainty();
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

            afterState = copyAllScenarioWithComponent();

            if (!beforeState.equals(afterState)) {
                changed = true;
            }
        }

        return afterState;
    }

    private List<ScenarioWithComponent> copyAllScenarioWithComponent() {
        List<ScenarioWithComponent> list = new ArrayList<>();
        for (AnalysisComponent c : graph.getComponents()) {
            for (UncertaintyLabel u : c.getUncertaintyLabels()) {
                list.add(new ScenarioWithComponent(c.getName(), u.getUncertaintyScenario()));
            }
            for (RequiredInterface ri : c.getInputs()) {
                for (UncertaintyLabel u : ri.getUncertaintyLabel()) {
                    list.add(new ScenarioWithComponent(c.getName(), u.getUncertaintyScenario()));
                }
            }
            for (ProvidedInterface pi : c.getOutputs()) {
                for (UncertaintyLabel u : pi.getUncertaintyLabel()) {
                    list.add(new ScenarioWithComponent(c.getName(), u.getUncertaintyScenario()));
                }
            }
        }
        return list;
    }

    /* Input Data-induced uncertainty propagation */
    private void propagateInputDataIntroducedUncertainty() {
        for (AnalysisComponent comp : graph.getComponents()) {
            for (RequiredInterface input : comp.getInputs()) {
                for (UncertaintyLabel label : input.getUncertaintyLabel()) {
                    if (label.getSource() == UncertaintySource.INPUT_DATA_INDUCED) {
                        for (ProvidedInterface output : comp.getOutputs()) {
                            addPropagatedLabels(output, label, UncertaintySource.OUTPUT_DATA_INDUCED);
                        }
                    }
                }
            }
        }
    }

    /* Node-based uncertainty propagation (scenario/methodology/modeling) */
    private void propagateNodeIntroducedUncertainty(UncertaintySource type) {
        for (AnalysisComponent comp : graph.getComponents()) {
            for (UncertaintyLabel label : comp.getUncertaintyLabels()) {
                if (label.getSource() == type) {
                    for (ProvidedInterface output : comp.getOutputs()) {
                        addPropagatedLabels(output, label, UncertaintySource.OUTPUT_DATA_INDUCED);
                    }
                }
            }
        }
    }

    /* Output Data-induced uncertainty propagation */
    private void propagateOutputDataIntroducedUncertainty() {
        for (AnalysisComponent comp : graph.getComponents()) {
            for (ProvidedInterface output : comp.getOutputs()) {
                for (UncertaintyLabel label : output.getUncertaintyLabel()) {
                    if (label.getSource() == UncertaintySource.OUTPUT_DATA_INDUCED) {
                        for (Connection conn : graph.getConnections()) {
                            if (conn.getFrom() == output) {
                                RequiredInterface input = conn.getTo();
                                addPropagatedLabels(input, label, UncertaintySource.INPUT_DATA_INDUCED);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Adds propagated uncertainty labels to a destination interface based on the mapping.
     */
    private void addPropagatedLabels(Object destination, UncertaintyLabel originalLabel, UncertaintySource destinationSource) {
        Set<UncertaintyScenario> mapped = scenarioPropagationMap.getOrDefault(originalLabel.getUncertaintyScenario(), Set.of());
        for (UncertaintyScenario scenario : mapped) {
            UncertaintyLabel newLabel = UncertaintyFactory.eINSTANCE.createUncertaintyLabel();
            newLabel.setSource(destinationSource);
            newLabel.setSeverity(originalLabel.getSeverity());
            newLabel.setUncertaintyScenario(scenario);

            if (destination instanceof ProvidedInterface) {
                ProvidedInterface pi = (ProvidedInterface) destination;
                if (pi.getUncertaintyLabel().stream().noneMatch(l -> l.getUncertaintyScenario() == scenario)) {
                    pi.getUncertaintyLabel().add(newLabel);
                }
            } else if (destination instanceof RequiredInterface) {
                RequiredInterface ri = (RequiredInterface) destination;
                if (ri.getUncertaintyLabel().stream().noneMatch(l -> l.getUncertaintyScenario() == scenario)) {
                    ri.getUncertaintyLabel().add(newLabel);
                }
            }
        }
    }
}
