package edu.kit.kastel.sdq.coupling.models.conformance;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC1(T)(I):
 * Ensures that every CodeQL→RIV mapping can be resolved to a concrete RIV value.
 */
public class IC1IChecker implements IChecker {

	private final String rivCorrespondencePath;
    private final String rivPath;

    private final Map<String, String> sourceRivMap = new HashMap<>();
    private final Map<String, String> rivValuesMap = new HashMap<>();
    
    private final AnalysisCouplingType analysisType;

    public IC1IChecker(SystemConfig cfg) {
        this.rivCorrespondencePath = cfg.basePath + "/" + cfg.rivCorrespondence;
        this.rivPath = cfg.basePath + "/" + cfg.riv;
        this.analysisType = cfg.analysisCouplingType;
    }

    @Override
    public boolean runCheck() {
        try {
            loadSourceRivMappings();
            loadRivValues();

            if (rivValuesMap.isEmpty()) {
                System.out.println("Fehler: Die Menge der Sicherheitswerte im Code (S_csC) ist leer.");
                return false;
            }

            return checkSourceRivMappings();

        } catch (Exception e) {
            System.err.println("Fehler während der Prüfung:");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads mappings from source analysis (CodeQL or Joana) to RIV values.
     */
    private void loadSourceRivMappings() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(rivCorrespondencePath);

        NodeList correspondences;
        if (analysisType == AnalysisCouplingType.CODEQLEDFA) {
            correspondences = doc.getElementsByTagName("securityLevelCorrespondences");
        } else { // JOANAEDFA
            correspondences = doc.getElementsByTagName("levelCorrespondences");
        }

        for (int i = 0; i < correspondences.getLength(); i++) {
            Element corr = (Element) correspondences.item(i);

            String sourceHref;
            String rivHref;

            if (analysisType == AnalysisCouplingType.CODEQLEDFA) {
                sourceHref = ConformanceUtils.getAttributeFromElement(corr, "securityLevel_CodeQL", "href");
                rivHref = ConformanceUtils.getAttributeFromElement(corr, "securityLevel_ResolvedImplementationValues", "href");
            } else { // JOANAEDFA
                sourceHref = ConformanceUtils.getAttributeFromElement(corr, "level_JOANA", "href");
                rivHref = ConformanceUtils.getAttributeFromElement(corr, "level_ResolvedImplementationValues", "href");
            }

            if (sourceHref != null && rivHref != null) {
                sourceRivMap.put(sourceHref, rivHref);
            }
        }
    }

    /**
     * Loads RIV values from the RIV XML file.
     */
    private void loadRivValues() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(rivPath);
        NodeList levels;

        if (analysisType == AnalysisCouplingType.CODEQLEDFA) {
            levels = doc.getElementsByTagName("securityLevel");
        } else { // JOANAEDFA
            levels = doc.getElementsByTagName("levels");
        }

        for (int i = 0; i < levels.getLength(); i++) {
            Element level = (Element) levels.item(i);
            String id = analysisType == AnalysisCouplingType.CODEQLEDFA
                        ? "//@securityLevel." + i
                        : "//@levels." + i;
            String name = level.getAttribute("name");
            rivValuesMap.put(id, name);
        }
    }

    /**
     * Checks that all source → RIV mappings can be resolved.
     */
    private boolean checkSourceRivMappings() {
        boolean allResolvable = true;

        for (Map.Entry<String, String> entry : sourceRivMap.entrySet()) {
            String sourceHref = entry.getKey();
            String rivHref = entry.getValue();
            String resolvedId = rivHref.substring(rivHref.indexOf("#") + 1);

            if (!rivValuesMap.containsKey(resolvedId)) {
                System.out.println("Fehler: Kein RIV-Wert gefunden für Mapping: " + sourceHref + " → " + rivHref);
                allResolvable = false;
            } else {
                System.out.println("Mapping auflösbar: " + sourceHref + " → " + rivValuesMap.get(resolvedId));
            }
        }

        return allResolvable;
    }

    public Map<String, String> getSourceRivMap() {
        return sourceRivMap;
    }

    public Map<String, String> getRivValuesMap() {
        return rivValuesMap;
    }

}
