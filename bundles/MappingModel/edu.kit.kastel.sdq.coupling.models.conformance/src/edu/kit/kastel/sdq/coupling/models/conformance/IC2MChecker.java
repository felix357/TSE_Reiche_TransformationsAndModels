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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Checker for IC2(T)(M):
 * Ensures that correspondences exist between
 * - Configurations cfgA (architecture) and cfgC (code),
 * - SystemElements δA (PCM) and δC (Java).
 */
public class IC2MChecker implements IChecker {

    private final String correspondencePath;
    private final String codeqlConfigRepresentationPath;
    private final String edfaConfigRepresentationPath;
    private final String pcmJavaPath;

    private final Set<String> configsA = new HashSet<>();
    private final Set<String> configsC = new HashSet<>();
    private final Set<String> configsRefsC = new HashSet<>();
    private final Map<String, String> configCorr = new HashMap<>();

    private final Set<String> systemElemsA = new HashSet<>();
    private final Set<String> systemElemsC = new HashSet<>();
    private final Map<String, Set<String>> systemElemCorr = new HashMap<>();

    public IC2MChecker(String basePath, String correspondenceFile, String codeqlConfigRepFile, String edfaConfigRepFile, String pcmJavaFileName) {
        this.correspondencePath = basePath + File.separator + correspondenceFile;
        this.codeqlConfigRepresentationPath = basePath + File.separator + codeqlConfigRepFile;
        this.edfaConfigRepresentationPath = basePath + File.separator + edfaConfigRepFile;
        this.pcmJavaPath = basePath + File.separator + pcmJavaFileName;
    }

    /**
     * Run the IC2(T)(M) check.
     */
    public boolean runCheck() {
        try {
            loadConfigCorrespondences();
            loadSysElementCorrespondences();

            boolean configsOk = checkBidirectional("Configuration", configsA, configsC, configCorr);
            boolean systemElemsOk = checkBidirectional("Systemelement", systemElemsA, systemElemsC, systemElemCorr);

            if (configsOk && systemElemsOk) {
                System.out.println("IC2(T)(M) vollständig erfüllt ✅");
            } else {
                System.out.println("IC2(T)(M) nicht erfüllt ❌");
            }

            return configsOk && systemElemsOk;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Configuration correspondences
    private void loadConfigCorrespondences() throws Exception {
        File file = new File(correspondencePath);
        if (!file.exists()) {
            throw new IllegalStateException("Correspondence-Datei nicht gefunden: " + correspondencePath);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(file)));

        NodeList cfgNodes = doc.getElementsByTagName("configurationCorrespondences");
        for (int i = 0; i < cfgNodes.getLength(); i++) {
            Element corr = (Element) cfgNodes.item(i);
            String codeqlHref = getHref(corr, "configuration_CodeQL");
            String edfaHref = getHref(corr, "configuration_EDFA");
            String codeqlValue = resolveCodeqlReference(codeqlHref, "codeql4extendeddataflow.configurationrepresentation", codeqlConfigRepresentationPath);
            String edfaValue = resolveCodeqlReference(edfaHref, "extendeddataflow.configurationrepresentation", edfaConfigRepresentationPath);      
            
            if (edfaValue != null && codeqlValue != null) {
                configCorr.put(edfaValue, codeqlValue);
                configsA.add(edfaValue);
                configsC.add(codeqlValue);
                configsRefsC.add(codeqlHref);
            }
        }
    }

    // PCM-Java system element correspondences
    private void loadSysElementCorrespondences() throws Exception {
        File file = new File(pcmJavaPath);
        if (!file.exists()) {
            throw new IllegalStateException("PCM-Java Correspondence-Datei nicht gefunden: " + pcmJavaPath);
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(file)));

        // Alle Typen laden
        loadSysElements(doc, "basiccomponent2class", "component", "javaClass");
        loadSysElements(doc, "operationInterface2interface", "pcmInterface", "javaInterface");
        loadSysElements(doc, "compositedatatype2class", "CompositeDataType", "javaClass");
    }

    private void loadSysElements(Document doc, String tagName, String pcmTag, String javaTag) {
        NodeList nodes = doc.getElementsByTagName(tagName);
        for (int i = 0; i < nodes.getLength(); i++) {
            Element elem = (Element) nodes.item(i);
            String pcmHref = getHref(elem, pcmTag);
            String javaHref = getHref(elem, javaTag);
            if (pcmHref != null || javaHref != null) {
                systemElemCorr.computeIfAbsent(pcmHref, k -> new HashSet<>()).add(javaHref);
                systemElemsA.add(pcmHref);
                systemElemsC.add(javaHref);
            }
        }
    }

    // Utilities
    private String getHref(Element parent, String tagName) {
        NodeList nodes = parent.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return ((Element) nodes.item(0)).getAttribute("href");
        }
        return null;
    }

    String resolveCodeqlReference(String href, String fileName, String filePath) throws Exception {
        String marker = fileName + "#";
        int idx = href.indexOf(marker);
        if (idx == -1) {
            throw new IllegalArgumentException("Href enthält nicht den erwarteten Prefix: " + href);
        }

        String path = href.substring(idx + marker.length());
        String[] parts = path.split("/");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(new File(filePath))));

        Node current = doc.getDocumentElement();
        for (String part : parts) {
            if (part.startsWith("@")) {
                String[] tokens = part.substring(1).split("\\.");
                String tagName = tokens[0];
                int index = Integer.parseInt(tokens[1]);

                NodeList nodes = ((Element) current).getElementsByTagName(tagName);
                if (index < nodes.getLength()) {
                    current = nodes.item(index);
                } else {
                    return null;
                }
            }
        }

        if (current != null && current.getNodeType() == Node.ELEMENT_NODE) {
            Element elem = (Element) current;
            if (elem.hasAttribute("id")) {
                return elem.getAttribute("id");
            } else if (elem.hasAttribute("xsi:type")) {
                return elem.getAttribute("xsi:type");
            }
        }
        return null;
    }

    private boolean checkBidirectional(String kind, Set<String> elemsA, Set<String> elemsC, Map<String, ?> corr) {
        boolean ok = true;

        // A → C
        for (String a : elemsA) {
            if (a == null || a.trim().isEmpty()) {
                System.out.println("Fehler: " + kind + " im Architekturmodell enthält eine leere oder ungültige Referenz.");
                ok = false;
                continue;
            }

            if (!corr.containsKey(a)) {
                System.out.println("Fehler: " + kind + " im Architekturmodell hat kein Mapping: " + a);
                ok = false;
            }
        }

        // C → A
        for (String c : elemsC) {
            if (c == null || c.trim().isEmpty()) {
                System.out.println("Fehler: " + kind + " im Code enthält eine leere oder ungültige Referenz.");
                ok = false;
                continue;
            }

            boolean found;
            if (corr.values().iterator().next() instanceof Set) {
                found = ((Map<String, Set<String>>) corr).values().stream()
                        .anyMatch(set -> set.contains(c));
            } else {
                found = ((Map<String, String>) corr).containsValue(c);
            }

            if (!found) {
                System.out.println("Fehler: " + kind + " im Code hat kein Mapping: " + c);
                ok = false;
            }
        }

        if (elemsA.isEmpty() || elemsC.isEmpty()) {
            System.out.println("Warnung: Keine " + kind + "-Elemente gefunden.");
            ok = false;
        }

        return ok;
    }

    public Set<String> getSystemElemsC() {
        return systemElemsC;
    }

    public Set<String> getConfigsRefsC() {
        return configsRefsC;
    }

    public Map<String, Set<String>> getSystemElemCorr() {
        return systemElemCorr;
    }

    public Map<String, String> getConfigCorr() {
        return configCorr;
    }

}
