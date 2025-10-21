package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Checker for IC1(T)(I):
 * Ensures that every CodeQL→RIV mapping can be resolved to a concrete RIV value.
 */
public class IC1IChecker implements IChecker {

    private final String rivCorrespondencePath;
    private final String rivPath;

    private final Map<String, String> codeqlRivMap = new HashMap<>();
    private final Map<String, String> rivValuesMap = new HashMap<>();

    public IC1IChecker(String basePath, String rivCorrespondenceName, String rivName) {
        this.rivCorrespondencePath = new File(basePath, rivCorrespondenceName).toString();
        this.rivPath = new File(basePath, rivName).toString();
    }

    public boolean runCheck() {
        try {
            loadCodeqlRivMappings();
            loadRivValues();

            if (rivValuesMap.isEmpty()) {
                System.out.println("Fehler: Die Menge der Sicherheitswerte im Code (S_csC) ist leer.");
                return false;
            }

            return checkCodeqlRivMappings();

        } catch (Exception e) {
            System.err.println("Fehler während der Prüfung:");
            e.printStackTrace();
            return false;
        }
    }

    private void loadCodeqlRivMappings() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(rivCorrespondencePath);
        NodeList correspondences = doc.getElementsByTagName("securityLevelCorrespondences");
        for (int i = 0; i < correspondences.getLength(); i++) {
            Element corr = (Element) correspondences.item(i);
            String codeqlHref = ConformanceUtils.getAttributeFromElement(corr, "securityLevel_CodeQL", "href");
            String rivHref = ConformanceUtils.getAttributeFromElement(corr, "securityLevel_ResolvedImplementationValues", "href");
            if (codeqlHref != null && rivHref != null) {
                codeqlRivMap.put(codeqlHref, rivHref);
            }
        }
    }

    private void loadRivValues() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(rivPath);
        NodeList securityLevels = doc.getElementsByTagName("securityLevel");
        for (int i = 0; i < securityLevels.getLength(); i++) {
            Element level = (Element) securityLevels.item(i);
            String id = "//@securityLevel." + i;
            String name = level.getAttribute("name");
            rivValuesMap.put(id, name);
        }
    }

    private boolean checkCodeqlRivMappings() {
        boolean allResolvable = true;
        for (Map.Entry<String, String> entry : codeqlRivMap.entrySet()) {
            String codeqlHref = entry.getKey();
            String rivHref = entry.getValue();
            String resolvedId = rivHref.substring(rivHref.indexOf("#") + 1);
            if (!rivValuesMap.containsKey(resolvedId)) {
                System.out.println("Fehler: Kein RIV-Wert gefunden für CodeQL-Mapping: " + codeqlHref
                        + " → " + rivHref);
                allResolvable = false;
            } else {
                System.out.println("Mapping auflösbar: " + codeqlHref + " → " + rivValuesMap.get(resolvedId));
            }
        }
        return allResolvable;
    }
}
