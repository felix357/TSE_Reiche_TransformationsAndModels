package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Checker for IC2(T)(I): Ensures that the sets of security-relevant system
 * elements and configurations on the code side are not empty.
 */
public class IC2IChecker implements IChecker {

    private static final String PCM_JAVA_CORR_FILE = "correspondences.pcmjavacorrespondence";
    private static final String EDFA_CODEQL_CORR_FILE = "correspondences.edfacodeqlcorrespondences";
    private static final String EDFA_CONFIG_FILE = "extendeddataflow.configurationrepresentation";

    private final String annotationPath;
    private String systemName;
    private final String parameterAnnotationFile;
    private final String pcmJavaCorrPath;
    private final String edfaCodeqlCorrPath;
    private final String edfaConfigPath;

    // Sets to store all relevant system elements and configurations for IC3
    private final Set<String> systemElementsFromIC2 = new HashSet<>();
    private final Set<String> configurationsFromIC2 = new HashSet<>();

    public IC2IChecker(String basePath, String parameterAnnotationFile, String systemName) {
        this.systemName = systemName;
        this.parameterAnnotationFile = parameterAnnotationFile;
        this.annotationPath = new File(basePath, parameterAnnotationFile).toString();
        this.pcmJavaCorrPath = new File(basePath, PCM_JAVA_CORR_FILE).toString();
        this.edfaCodeqlCorrPath = new File(basePath, EDFA_CODEQL_CORR_FILE).toString();
        this.edfaConfigPath = new File(basePath, EDFA_CONFIG_FILE).toString();
    }

    @Override
    public boolean runCheck() {
        try {
            Set<String> annotatedPcmElements = getAnnotatedPcmElements();

            boolean deltaCsCNonEmpty = isSystemElementSetNonEmpty(annotatedPcmElements);
            boolean cfgCsCNonEmpty = isConfigurationSetNonEmpty();

            System.out.println("IC2(T)(I) Check:");
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
                    systemElementsFromIC2.add(pcmHref); // collect all matches
                    found = true;
                }
            }
        }

        return found;
    }

    private boolean isConfigurationSetNonEmpty() throws Exception {
        String relevantConfigUriSuffix = getRelevantArchitecturalConfigUriSuffix();

        if (relevantConfigUriSuffix == null) {
            System.out.println(
                    "  [Detail] Keine Architektur-Konfiguration (cfg^A) gefunden, die die Annotation-Datei verwendet.");
            return false;
        }

        Document doc = ConformanceUtils.parseXmlFile(edfaCodeqlCorrPath);
        NodeList correspondences = doc.getElementsByTagName("configurationCorrespondences");

        boolean found = false;

        for (int i = 0; i < correspondences.getLength(); i++) {
            Element corr = (Element) correspondences.item(i);
            String cfgEdfaHref = ConformanceUtils.getAttributeFromElement(corr, "configuration_EDFA", "href");

            if (cfgEdfaHref != null && cfgEdfaHref.endsWith(relevantConfigUriSuffix)) {
                configurationsFromIC2.add(cfgEdfaHref); // collect all matches
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
