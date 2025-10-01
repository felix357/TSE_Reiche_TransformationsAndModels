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
public class IC2MChecker {

    private final String correspondencePath;
    private final String codeqlConfigRepresentationPath;
    private final String edfaConfigRepresentationPath;
    private final String pcmJavaPath;

    private final Set<String> configsA = new HashSet<>();
    private final Set<String> configsC = new HashSet<>();
    private final Map<String, String> configCorr = new HashMap<>();

    private final Set<String> systemElemsA = new HashSet<>();
    private final Set<String> systemElemsC = new HashSet<>();
    private final Map<String, String> systemElemCorr = new HashMap<>();

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

        // basiccomponent2class
        NodeList nodes = doc.getElementsByTagName("basiccomponent2class");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element elem = (Element) nodes.item(i);
            String pcmHref = getHref(elem, "component");
            String javaHref = getHref(elem, "javaClass");
            if (pcmHref != null && javaHref != null) {
                systemElemCorr.put(pcmHref, javaHref);
                systemElemsA.add(pcmHref);
                systemElemsC.add(javaHref);
            }
        }

        // operationInterface2interface
        nodes = doc.getElementsByTagName("operationInterface2interface");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element elem = (Element) nodes.item(i);
            String pcmHref = getHref(elem, "pcmInterface");
            String javaHref = getHref(elem, "javaInterface");
            if (pcmHref != null && javaHref != null) {
                systemElemCorr.put(pcmHref, javaHref);
                systemElemsA.add(pcmHref);
                systemElemsC.add(javaHref);
            }
        }

        // compositedatatype2class
        nodes = doc.getElementsByTagName("compositedatatype2class");
        for (int i = 0; i < nodes.getLength(); i++) {
            Element elem = (Element) nodes.item(i);
            String pcmHref = getHref(elem, "CompositeDataType");
            String javaHref = getHref(elem, "javaClass");
            if (pcmHref != null && javaHref != null) {
                systemElemCorr.put(pcmHref, javaHref);
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

    private boolean checkBidirectional(String kind, Set<String> elemsA, Set<String> elemsC, Map<String, String> corr) {
        boolean ok = true;

        // A → C
        for (String a : elemsA) {
            if (!corr.containsKey(a)) {
                System.out.println("Fehler: " + kind + " im Architekturmodell hat kein Mapping: " + a);
                ok = false;
            }
        }

        // C → A
        for (String c : elemsC) {
            if (!corr.containsValue(c)) {
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
}
