package edu.kit.kastel.sdq.coupling.models.analysis.uncertainty.codeql;

import com.google.gson.*;
import java.io.FileReader;
import java.util.*;

public class CodeQlU3ScenarioChecker {

    // Mapping of node labels to CodeQL SecurityLevel semantics
    private static final Map<String, String> labelToSecurityLevel = Map.of(
        "barcode", "ByStanderCashierCustomerStoreManager",
        "number", "BankCustomer",
        "pin", "BankCustomer",
        "amountPayed", "ByStanderCashierCustomerStoreManager",
        "complexOrder", "StoreManager",
        "orderId", "StoreManager"
        // add all other relevant mappings from CodeQL
    );

    // Allowed flows according to CodeQL
    private static final Set<String> allowedFlows = Set.of(
        "CashierCustomer->Cashier",
        "CashierCustomer->Customer",
        "BankOtherStore->Bank",
        "BankOtherStore->OtherStore"
        // add all allowed flows from CodeQL
    );

    public static void main(String[] args) throws Exception {
        String filePath = "C:/Users/felix/Git/TSE_Reiche_TransformationsAndModels_Fork/bundles/MappingModel/edu.kit.kastel.sdq.coupling.models.conformance/CoCoMe/LabeledTaintTracking4AccessAnalysis.json";
        JsonObject json = JsonParser.parseReader(new FileReader(filePath)).getAsJsonObject();

        checkU3Scenario(json);
    }

    public static void checkU3Scenario(JsonObject json) {
        JsonObject nodes = json.getAsJsonObject("nodes");
        JsonObject edges = json.getAsJsonObject("edges");
        JsonObject select = json.getAsJsonObject("#select");

        // 1. Identify merged nodes (abstraction)
        Map<String, Integer> nodeCounts = new HashMap<>();
        for (JsonElement tupleEl : nodes.getAsJsonArray("tuples")) {
            JsonArray tuple = tupleEl.getAsJsonArray();
            String label = tuple.get(2).getAsString();
            nodeCounts.put(label, nodeCounts.getOrDefault(label, 0) + 1);
        }
        List<String> mergedNodes = new ArrayList<>();
        for (var entry : nodeCounts.entrySet()) {
            if (entry.getValue() > 1) mergedNodes.add(entry.getKey());
        }

        // 2. Check edges against allowed flows (approximation)
        List<String> approxEdges = new ArrayList<>();
        for (JsonElement tupleEl : edges.getAsJsonArray("tuples")) {
            JsonArray edge = tupleEl.getAsJsonArray();
            String from = edge.get(0).getAsJsonObject().get("label").getAsString();
            String to = edge.get(1).getAsJsonObject().get("label").getAsString();
            String sourceLevel = labelToSecurityLevel.getOrDefault(from, "UNKNOWN");
            String targetLevel = labelToSecurityLevel.getOrDefault(to, "UNKNOWN");
            String flowKey = sourceLevel + "->" + targetLevel;

            if (!allowedFlows.contains(flowKey)) {
                approxEdges.add(flowKey + " (edge: " + from + " -> " + to + ")");
            }
        }

        // 3. Check #select flows for ignored paths
        List<String> ignoredFlows = new ArrayList<>();
        for (JsonElement tupleEl : select.getAsJsonArray("tuples")) {
            JsonArray flow = tupleEl.getAsJsonArray();
            String src = flow.get(1).getAsJsonObject().get("label").getAsString();
            String dst = flow.get(2).getAsJsonObject().get("label").getAsString();
            String flowKey = labelToSecurityLevel.getOrDefault(src, "UNKNOWN") + "->" +
                             labelToSecurityLevel.getOrDefault(dst, "UNKNOWN");

            if (!allowedFlows.contains(flowKey)) {
                ignoredFlows.add(flowKey + " (select flow: " + src + " -> " + dst + ")");
            }
        }

        // 4. Generate report
        System.out.println("=== U3 Analysis Report ===");
        System.out.println("Merged nodes (potential abstraction/oversimplification): " + mergedNodes);
        System.out.println("Edges violating allowed flows (approximation): " + approxEdges);
        System.out.println("Ignored #select flows (oversimplification/approximation): " + ignoredFlows);

        if (mergedNodes.isEmpty() && approxEdges.isEmpty() && ignoredFlows.isEmpty()) {
            System.out.println("No obvious U3 issues detected; analysis seems detailed and correct.");
        }
    }
}
