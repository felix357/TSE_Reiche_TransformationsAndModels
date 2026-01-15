package edu.kit.kastel.sdq.coupling.models.conformance;

import java.util.HashSet;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC2(T)(I): Ensures that the sets of security-relevant system
 * elements and configurations on the code side are not empty.
 */
public class IC2IChecker implements IChecker {
    private final String annotationPath;
    private final String systemName;
    private final String parameterAnnotationFile;
    private final String pcmJavaCorrPath;
    private final String edfaCodeqlCorrPath;
    private final String edfaConfigPath;
    private final AnalysisCouplingType analysisType;

    // Sets to store all relevant system elements and configurations
    private final Set<String> systemElementsFromIC2 = new HashSet<>();
    private final Set<String> configurationsFromIC2 = new HashSet<>();

    public IC2IChecker(SystemConfig cfg) {
        this.systemName = cfg.systemName;
        this.parameterAnnotationFile = cfg.parameterAnnotationFile;
        this.annotationPath = cfg.basePath + "/" + cfg.parameterAnnotationFile;
        this.pcmJavaCorrPath = cfg.basePath + "/" + cfg.pcmJavaCorrespondence;
        this.edfaCodeqlCorrPath = cfg.basePath + "/" + cfg.edfascCorrespondence;
        this.edfaConfigPath = cfg.basePath + "/" + cfg.edfaConfiguration;
        this.analysisType = cfg.analysisCouplingType;
    }

    @Override
    public boolean runCheck() {
        try {
            // 1. Collect annotated PCM elements
            Set<String> annotatedPcmElements = getAnnotatedPcmElements();

            // 2. Check system elements
            boolean deltaCsCNonEmpty = isSystemElementSetNonEmpty(annotatedPcmElements);

            // 3. Check configuration elements based on analysisType
            boolean cfgCsCNonEmpty;
            switch (analysisType) {
                case CODEQLEDFA:
                    cfgCsCNonEmpty = isConfigurationSetNonEmpty(edfaCodeqlCorrPath, "configuration_CodeQL");
                    break;
                case JOANAEDFA:
                    cfgCsCNonEmpty = isConfigurationSetNonEmpty(edfaCodeqlCorrPath, "configuration_JOANA");
                    break;
                default:
                    throw new IllegalStateException("Unsupported analysis type: " + analysisType);
            }

            System.out.println("IC2(T)(I) Check (" + analysisType + "):");
            System.out.println("  Delta_cs^C (Systemelemente) ≠ ∅: " + (deltaCsCNonEmpty ? "JA ✅" : "NEIN ❌"));
            System.out.println("  CFG_cs^C (Konfigurationen) ≠ ∅: " + (cfgCsCNonEmpty ? "JA ✅" : "NEIN ❌"));

            boolean result = deltaCsCNonEmpty && cfgCsCNonEmpty;

            if (result) {
                System.out.println("Die Bedingung IC2(T)(I) ist ERFÜLLT. (Robustheit des Mappings ist nachgewiesen)");
            } else {
                System.out.println("Die Bedingung IC2(T)(I) ist NICHT ERFÜLLT.");
            }
            return result;

        } catch (Exception e) {
            System.err.println("Fehler während der Prüfung:");
            e.printStackTrace();
            return false;
        }
    }

    private Set<String> getAnnotatedPcmElements() throws Exception {
        Set<String> annotatedPcmElements = new HashSet<>();
        Document doc = ConformanceUtils.parseXmlFile(annotationPath);
        NodeList annotations = doc.getElementsByTagName("annotations");

        for (int i = 0; i < annotations.getLength(); i++) {
            Element annotation = (Element) annotations.item(i);
            Element paramId = (Element) annotation.getElementsByTagName("parameterIdentification").item(0);
            if (paramId != null) {
                Element parameter = (Element) paramId.getElementsByTagName("parameter").item(0);
                String paramHref = (parameter != null) ? parameter.getAttribute("href") : null;
                if (paramHref != null) {
                    annotatedPcmElements.add(paramHref);
                }
            }
        }
        return annotatedPcmElements;
    }

    private boolean isSystemElementSetNonEmpty(Set<String> annotatedPcmElements) throws Exception {
        if (annotatedPcmElements.isEmpty()) {
            return false;
        }

        Document doc = ConformanceUtils.parseXmlFile(pcmJavaCorrPath);
        NodeList paramCorrs = doc.getElementsByTagName("pcmparameter2javaparameter");

        boolean found = false;

        for (int i = 0; i < paramCorrs.getLength(); i++) {
            Element corr = (Element) paramCorrs.item(i);
            Element pcmId = (Element) corr.getElementsByTagName("pcmParameterIdentification").item(0);

            if (pcmId != null) {
                Element pcmElement = (Element) pcmId.getElementsByTagName("parameter").item(0);
                String pcmHref = (pcmElement != null) ? pcmElement.getAttribute("href") : null;

                String startMarker = this.systemName + ".repository#//";

                if (pcmHref != null) {
                    int startIndex = pcmHref.indexOf(startMarker);
                    if (startIndex != -1) {
                        pcmHref = pcmHref.substring(startIndex);
                    }
                }

                if (pcmHref != null && annotatedPcmElements.contains(pcmHref)) {
                    systemElementsFromIC2.add(pcmHref);
                    found = true;
                }
            }
        }

        return found;
    }

    private boolean isConfigurationSetNonEmpty(String corrPath, String targetTag) throws Exception {
        String relevantConfigUriSuffix = getRelevantArchitecturalConfigUriSuffix();

        if (relevantConfigUriSuffix == null) {
            System.out.println(
                    "  [Detail] Keine Architektur-Konfiguration (cfg^A) gefunden, die die Annotation-Datei verwendet.");
            return false;
        }

        Document doc = ConformanceUtils.parseXmlFile(corrPath);
        NodeList correspondences = doc.getElementsByTagName("configurationCorrespondences");

        boolean found = false;

        for (int i = 0; i < correspondences.getLength(); i++) {
            Element corr = (Element) correspondences.item(i);
            String cfgHref = ConformanceUtils.getAttributeFromElement(corr, targetTag, "href");

            if (cfgHref != null && cfgHref.endsWith(relevantConfigUriSuffix)) {
                configurationsFromIC2.add(cfgHref);
                found = true;
            }
        }

        return found;
    }

    private String getRelevantArchitecturalConfigUriSuffix() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(edfaConfigPath);
        NodeList configs = doc.getElementsByTagName("configurations");

        for (int i = 0; i < configs.getLength(); i++) {
            Element config = (Element) configs.item(i);
            NodeList inputs = config.getElementsByTagName("inputs");

            for (int j = 0; j < inputs.getLength(); j++) {
                String inputHref = ((Element) inputs.item(j)).getAttribute("href");

                if (inputHref != null && inputHref.contains(this.parameterAnnotationFile)) {
                    return "#//@configurations." + i;
                }
            }
        }
        return null;
    }

    public Set<String> getSystemElementsFromIC2() {
        return systemElementsFromIC2;
    }

    public Set<String> getConfigurationsFromIC2() {
        return configurationsFromIC2;
    }
}
