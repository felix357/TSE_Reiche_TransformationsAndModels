package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

public class IC3IChecker implements IChecker {

    private final AnalysisCouplingType analysisType;

    private final String basePath;
    private final String codeqlFilePath;
    private final String codeqlFilePathName;

    private final Map<String, String> codeqlRivMap;   // From IC1
    private final Set<String> systemElements;         // From IC2 (PCM elements)
    private final Set<String> configurations;         // From IC2

    private final Map<String, String> pcmToJavaMap = new HashMap<>();

    public IC3IChecker(
            SystemConfig cfg,
            Map<String, String> codeqlRivMap,
            Set<String> systemElements,
            Set<String> configurations) {

        this.basePath = cfg.basePath;
        this.codeqlFilePathName = cfg.sourceCodeAnalysis;
        this.codeqlFilePath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;

        this.codeqlRivMap = codeqlRivMap;
        this.systemElements = systemElements;
        this.configurations = configurations;

        this.analysisType = cfg.analysisCouplingType;

        loadCorrespondences();
    }

    @Override
    public boolean runCheck() {
        return switch (analysisType) {
            case CODEQLEDFA -> runCodeQLCheck();
            case JOANAEDFA -> runJoanaCheck();
        };
    }

    // CodeQL–EDFA
    private boolean runCodeQLCheck() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new FileInputStream(new File(codeqlFilePath))));

            NodeList queriesNodes = doc.getElementsByTagNameNS("*", "queries");
            NodeList annotations = doc.getElementsByTagName("securityLevelAnnotations");

            for (int i = 0; i < annotations.getLength(); i++) {

                Element annotationElem = (Element) annotations.item(i);
                String annotationId = annotationElem.getAttribute("id");
                String securityLevelRef = annotationElem.getAttribute("securityLevel");

                String fullKey = "codeql4extendeddataflow.codeql" + "#" + securityLevelRef;

                // IC1 check
                String resolvedValue = codeqlRivMap.get(fullKey);
                if (resolvedValue == null)
                    continue;

                // IC2: system element check
                if (!affectsSystemElement(annotationElem))
                    continue;

                // IC2: configuration reference
                if (!referencedByConfiguration(queriesNodes.getLength()))
                    continue;

                System.out.println("IC3(C)(I) erfüllt: " + annotationId);
                return true;
            }

            System.out.println("IC3(C)(I) NICHT erfüllt.");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //JOANA–EDFA
    private boolean runJoanaCheck() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new FileInputStream(new File(codeqlFilePath))));

            NodeList entrypoints = doc.getElementsByTagName("entrypoint");

            for (int e = 0; e < entrypoints.getLength(); e++) {
                Element entrypoint = (Element) entrypoints.item(e);
                String entrypointId = entrypoint.getAttribute("id");

                // Only consider entrypoints referenced by configurations (IC2)
                if (!configurationMatches(entrypointId)) {
                    continue;
                }

                // Map level references to names
                Map<String, String> levelRefToName = new HashMap<>();
                NodeList levels = entrypoint.getElementsByTagName("level");
                for (int i = 0; i < levels.getLength(); i++) {
                    Element level = (Element) levels.item(i);
                    String ref = "//@entrypoint." + e + "/@level." + i;
                    levelRefToName.put(ref, level.getAttribute("name"));
                }

                NodeList annotations = entrypoint.getElementsByTagName("annotation");
                for (int i = 0; i < annotations.getLength(); i++) {
                    Element annotation = (Element) annotations.item(i);

                    // Security characteristic check (IC1)
                    String levelRef = annotation.getAttribute("level");
                    String levelName = levelRefToName.get(levelRef);
                    if (!levelNameIsValid(levelName))
                        continue;

                    // System element check (IC2) using PCM→Java mapping
                    if (!affectsSystemElementJOANA(annotation))
                        continue;

                    System.out.println("IC3(C)(I) erfüllt (JOANA): " + annotation.getAttribute("id"));
                    return true;
                }
            }

            System.out.println("IC3(C)(I) NICHT erfüllt (JOANA).");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean levelNameIsValid(String levelName) {
        return levelName != null && !levelName.isEmpty();
    }

    /** Map PCM/Repository system elements to Java and check if annotation affects them */
    private boolean affectsSystemElementJOANA(Element annotationElem) {
        NodeList params = annotationElem.getElementsByTagName("Parameter");

        for (int j = 0; j < params.getLength(); j++) {
            Element param = (Element) params.item(j);
            String href = param.getAttribute("href"); // JOANA Java href

            for (String sysElem : systemElements) {
                // Find a PCM key that ends with the sysElem
                for (Map.Entry<String, String> entry : pcmToJavaMap.entrySet()) {
                    if (entry.getKey().endsWith(sysElem)) {
                        String mappedJava = entry.getValue();
                        if (href.startsWith(mappedJava) || href.contains(mappedJava)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /** IC1 helper for CodeQL */
    private boolean affectsSystemElement(Element annotationElem) {
        NodeList params = annotationElem.getElementsByTagName("parameter");

        for (int j = 0; j < params.getLength(); j++) {
            Element param = (Element) params.item(j);

            String annotationHref = param.getAttribute("href");

            String pcmId = null;
            for (Map.Entry<String,String> e : pcmToJavaMap.entrySet()) {
                if (annotationHref.equals(e.getValue())) {
                    pcmId = e.getKey();
                    break;
                }
            }

            if (pcmId == null)
                continue;

            for (String sys : systemElements) {
                if (pcmId.endsWith(sys)) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Check if entrypoint matches one of the configurations */
    private boolean configurationMatches(String entrypointId) {
        for (String cfgRef : configurations) {
            String[] parts = cfgRef.split("\\.");
            String cfgIndex = parts[parts.length - 1];
            if (cfgIndex.equals(entrypointId)) {
                return true;
            }
        }
        return false;
    }

    private boolean referencedByConfiguration(int queriesNodesLength) {
        return queriesNodesLength == 1;
    }

    /** Load PCM–Java correspondences*/
    private void loadCorrespondences() {
        try {
            File corrFile = new File(basePath + "/correspondences.pcmjavacorrespondence");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(corrFile);

            Element root = doc.getDocumentElement();
            java.util.function.Function<Element, String> getHref = (elem) -> elem == null ? null : elem.getAttribute("href");

            NodeList basicNodes = root.getElementsByTagName("basiccomponent2class");
            for (int i = 0; i < basicNodes.getLength(); i++) {
                Element elem = (Element) basicNodes.item(i);
                String pcm = getHref.apply((Element) elem.getElementsByTagName("component").item(0));
                String java = getHref.apply((Element) elem.getElementsByTagName("javaClass").item(0));
                if (pcm != null && java != null)
                    pcmToJavaMap.put(pcm, java);
            }

            NodeList opIntNodes = root.getElementsByTagName("operationInterface2interface");
            for (int i = 0; i < opIntNodes.getLength(); i++) {
                Element elem = (Element) opIntNodes.item(i);
                String pcm = getHref.apply((Element) elem.getElementsByTagName("pcmInterface").item(0));
                String java = getHref.apply((Element) elem.getElementsByTagName("javaInterface").item(0));
                if (pcm != null && java != null)
                    pcmToJavaMap.put(pcm, java);
            }

            NodeList methodNodes = root.getElementsByTagName("providedoperationsignature2javamethod");
            for (int i = 0; i < methodNodes.getLength(); i++) {
                Element elem = (Element) methodNodes.item(i);
                Element pcmMethodElem = (Element) elem.getElementsByTagName("pcmMethod").item(0);
                if (pcmMethodElem != null) {
                    Element sig = (Element) pcmMethodElem.getElementsByTagName("providedSignature").item(0);
                    if (sig != null) {
                        String pcm = sig.getAttribute("href");
                        String java = getHref.apply((Element) elem.getElementsByTagName("javaMethod").item(0));
                        if (pcm != null && java != null)
                            pcmToJavaMap.put(pcm, java);
                    }
                }
            }

            NodeList paramNodes = root.getElementsByTagName("pcmparameter2javaparameter");
            for (int i = 0; i < paramNodes.getLength(); i++) {
                Element elem = (Element) paramNodes.item(i);
                Element pcmParamId = (Element) elem.getElementsByTagName("pcmParameterIdentification").item(0);
                if (pcmParamId != null) {
                    Element param = (Element) pcmParamId.getElementsByTagName("parameter").item(0);
                    if (param != null) {
                        String pcm = param.getAttribute("href");
                        String java = getHref.apply((Element) elem.getElementsByTagName("javaParameter").item(0));
                        if (pcm != null && java != null)
                            pcmToJavaMap.put(pcm, java);
                    }
                }
            }

            System.out.println("[IC3] Loaded PCM-Java correspondences: " + pcmToJavaMap.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
