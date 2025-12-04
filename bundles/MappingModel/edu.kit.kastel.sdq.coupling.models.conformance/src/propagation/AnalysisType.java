package propagation;

public enum AnalysisType {
    CODEQL("codeqlInputMapping.xmi", "codeqlOutputMapping.xmi"),
    EDFA("EDFAInputMappingTest.xmi", "edfaOutputMapping.xmi");

    private static final String BASE_PATH = "C:/Users/felix/sone-ws/edu.kit.kastel.sdq.coupling.models.conformance/model/";

    private final String inputMapping;
    private final String outputMapping;

    AnalysisType(String inputMapping, String outputMapping) {
        this.inputMapping = inputMapping;
        this.outputMapping = outputMapping;
    }

    public String getInputMappingPath() {
        return BASE_PATH + inputMapping;
    }

    public String getOutputMappingPath() {
        return BASE_PATH + outputMapping;
    }
}
