package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Checker for IC7(T)(I)
 */
public class IC7IChecker implements IChecker {

    private final IC2IChecker ic2;
    private final String codeqlScarCorrespondencesPath;
    private final String codeqlResultingValuesCorrespondencesPath;
    private final String resultingValuesModelPath;

    private final Set<String> foundResultingValueInstances = new HashSet<>();

    public IC7IChecker(SystemConfig cfg, IC2IChecker ic2checker) {
        this.ic2 = ic2checker;
        this.codeqlScarCorrespondencesPath = cfg.basePath + File.separator + cfg.correspondencesCodeqlScar;
        this.codeqlResultingValuesCorrespondencesPath = cfg.basePath + File.separator + cfg.rivCorrespondence;
        this.resultingValuesModelPath = cfg.basePath + File.separator + cfg.riv;
    }

    @Override
    public boolean runCheck() {
        try {
            Set<String> cfgCsC = ic2.getConfigurationsFromIC2();
            if (cfgCsC == null || cfgCsC.isEmpty()) {
                System.out.println("IC7(T)(I): IC2 provided no CodeQL configuration hrefs.");
                return false;
            }

            Map<String, String> codeqlToScar = loadCodeqlToScarConfigMap();
            Map<String, String> scarToResulting = loadScarToResultingConfigMap();

            if (codeqlToScar.isEmpty() || scarToResulting.isEmpty()) {
                System.out.println("IC7(T)(I): Correspondence maps could not be loaded.");
                return false;
            }

            Document resultingDoc = parseXmlFile(resultingValuesModelPath);
            if (resultingDoc == null) {
                System.out.println("IC7(T)(I): Could not parse resulting values model.");
                return false;
            }

            boolean anyFound = false;

            for (String codeqlHref : cfgCsC) {
                if (codeqlHref == null || codeqlHref.trim().isEmpty()) continue;

                String normalizedCodeqlHref = codeqlHref;
                if (!normalizedCodeqlHref.startsWith("codeql4")) {
                    normalizedCodeqlHref = "codeql4" + normalizedCodeqlHref;
                }
                // -----------------------------------------------------------

                String scarHref = codeqlToScar.get(normalizedCodeqlHref);
                if (scarHref == null) {
                    System.out.println("IC7(T)(I) warn: No SCAR correspondence for CodeQL config: "
                            + codeqlHref + " (normalized: " + normalizedCodeqlHref + ")");
                    continue;
                }

                String resultingHref = scarToResulting.get(scarHref);
                if (resultingHref == null) {
                    System.out.println("IC7(T)(I) warn: No ResultingValues correspondence for SCAR config: " + scarHref);
                    continue;
                }

                String fragment = extractFragment(resultingHref);
                if (fragment == null) {
                    System.out.println("IC7(T)(I) warn: Could not extract fragment from: " + resultingHref);
                    continue;
                }

                boolean foundForThis = hasResultingValuesWithRuleId(resultingDoc, fragment);
                if (foundForThis) {
                    anyFound = true;
                    foundResultingValueInstances.add(fragment);
                    System.out.println("IC7(T)(I): Found resultingValues for fragment " + fragment);
                } else {
                    System.out.println("IC7(T)(I) warn: No resultingValues reference " + fragment);
                }
            }

            if (!anyFound) {
                System.out.println("IC7(T)(I) NOT satisfied ❌ — no resulting-values instances found.");
                return false;
            }

            System.out.println("IC7(T)(I) satisfied ✅ — Found instances: " + foundResultingValueInstances);
            return true;

        } catch (Exception ex) {
            System.err.println("IC7(T)(I) error:");
            ex.printStackTrace();
            return false;
        }
    }

    private Map<String, String> loadCodeqlToScarConfigMap() throws Exception {
        Map<String, String> map = new HashMap<>();
        Document doc = parseXmlFile(codeqlScarCorrespondencesPath);
        if (doc == null) return map;

        NodeList cfgNodes = doc.getElementsByTagName("configurationCorrespondences");
        for (int i = 0; i < cfgNodes.getLength(); i++) {
            Element corr = (Element) cfgNodes.item(i);
            String codeqlHref = getHref(corr, "configuration_CodeQL");
            String scarHref = getHref(corr, "configuration_SCAR");
            if (codeqlHref != null && scarHref != null) {
                map.put(codeqlHref, scarHref);
            }
        }
        return map;
    }

    private Map<String, String> loadScarToResultingConfigMap() throws Exception {
        Map<String, String> map = new HashMap<>();
        Document doc = parseXmlFile(codeqlResultingValuesCorrespondencesPath);
        if (doc == null) return map;

        NodeList cfgNodes = doc.getElementsByTagName("configurationCorrespondences");
        for (int i = 0; i < cfgNodes.getLength(); i++) {
            Element corr = (Element) cfgNodes.item(i);
            String scarHref = getHref(corr, "configuration_SCAR");
            String resultingHref = getHref(corr, "configuration_ResultingValues");
            if (scarHref != null && resultingHref != null) {
                map.put(scarHref, resultingHref);
            }
        }
        return map;
    }

    private boolean hasResultingValuesWithRuleId(Document resultingDoc, String fragment) {
        NodeList nodes = resultingDoc.getElementsByTagName("resultingValues");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element rv = (Element) nodes.item(i);
            if (rv.hasAttribute("ruleId")) {
                if (fragment.equals(rv.getAttribute("ruleId"))) {
                    return true;
                }
            }
        }
        return false;
    }

    private String extractFragment(String href) {
        if (href == null) return null;
        int idx = href.indexOf('#');
        if (idx == -1 || idx + 1 >= href.length()) return null;
        return href.substring(idx + 1);
    }

    private String getHref(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            Element e = (Element) nodes.item(0);
            if (e.hasAttribute("href")) {
                return e.getAttribute("href");
            }
        }
        return null;
    }

    private Document parseXmlFile(String path) throws Exception {
        File f = new File(path);
        if (!f.exists()) {
            throw new IllegalStateException("File not found: " + path);
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(new InputSource(new FileInputStream(f)));
    }

    public Set<String> getFoundResultingValueInstances() {
        return new HashSet<>(foundResultingValueInstances);
    }
}
