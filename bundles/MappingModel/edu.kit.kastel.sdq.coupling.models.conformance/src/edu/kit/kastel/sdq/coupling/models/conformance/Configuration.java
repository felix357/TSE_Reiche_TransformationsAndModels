package edu.kit.kastel.sdq.coupling.models.conformance;

import java.util.ArrayList;
import java.util.List;

public class Configuration {
    private String id;
    private String mainElementFile;
    private String mainElementFragment;
    private List<String> additionalInputs = new ArrayList<>();

    // Constructor
    public Configuration(String id, String mainElementFile, String mainElementFragment) {
        this.id = id;
        this.mainElementFile = mainElementFile;
        this.mainElementFragment = mainElementFragment;
    }

    // Getters
    public String getId() { return id; }
    public String getMainElementFile() { return mainElementFile; }
    public String getMainElementFragment() { return mainElementFragment; }
    public List<String> getAdditionalInputs() { return additionalInputs; }

    // Add an additional input
    public void addAdditionalInput(String input) { this.additionalInputs.add(input); }

    @Override
    public String toString() {
        return "Configuration{" +
                "id='" + id + '\'' +
                ", mainElementFile='" + mainElementFile + '\'' +
                ", mainElementFragment='" + mainElementFragment + '\'' +
                ", additionalInputs=" + additionalInputs +
                '}';
    }
}
