package edu.kit.kastel.sdq.coupling.models.analysis.uncertainty.codeql;

import com.google.gson.*;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class CodeQlScenarioDefinitionChecker {

    public static void main(String[] args) throws Exception {
        String filePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe/LabeledTaintTracking4AccessAnalysis.json";
        JsonObject json = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

        String result = checkScenario(json);
        System.out.println(result);
    }

    public static String checkScenario(JsonObject json) {
        JsonObject nodes = json.getAsJsonObject("nodes");
        JsonObject edges = json.getAsJsonObject("edges");
        JsonObject select = json.getAsJsonObject("#select");

        Set<String> nodeLabels = new HashSet<>();
        for (JsonElement tupleEl : nodes.getAsJsonArray("tuples")) {
            JsonArray tuple = tupleEl.getAsJsonArray();
            String label = tuple.get(2).getAsString(); // val
            nodeLabels.add(label);
        }

        // === 2. Konsistenz der Edges ===
        for (JsonElement tupleEl : edges.getAsJsonArray("tuples")) {
            JsonArray edge = tupleEl.getAsJsonArray();
            String from = edge.get(0).getAsJsonObject().get("label").getAsString();
            String to = edge.get(1).getAsJsonObject().get("label").getAsString();
            if (!nodeLabels.contains(from) || !nodeLabels.contains(to)) {
                return "Scenario definition incorrect: edge references unknown node (" + from + " -> " + to + ")";
            }
        }

        // === 3. Konsistenz der #select-Flows ===
        for (JsonElement tupleEl : select.getAsJsonArray("tuples")) {
            JsonArray flow = tupleEl.getAsJsonArray();
            String sourceLabel = flow.get(1).getAsJsonObject().get("label").getAsString();
            String sinkLabel = flow.get(2).getAsJsonObject().get("label").getAsString();
            if (!nodeLabels.contains(sourceLabel) || !nodeLabels.contains(sinkLabel)) {
                return "Scenario definition incorrect: #select flow references unknown node (" + sourceLabel + " -> " + sinkLabel + ")";
            }
        }

        return "Scenario definition appears correct";
    }
}
