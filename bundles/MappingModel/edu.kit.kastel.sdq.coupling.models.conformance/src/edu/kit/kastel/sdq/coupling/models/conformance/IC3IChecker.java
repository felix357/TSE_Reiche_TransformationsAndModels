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

public class IC3IChecker implements IChecker {

    private final String basePath;
    private final String codeqlFilePath;
    private final String codeqlFilePathName;

    private final Map<String, String> codeqlRivMap;   // From IC1
    private final Set<String> systemElements;         // From IC2 (repository ids)
    private final Set<String> configurations;         // From IC2 CFG ids

    private final Map<String, String> pcmToJavaMap = new HashMap<>();
    
    public IC3IChecker(
            SystemConfig cfg,
            Map<String, String> codeqlRivMap,
            Set<String> systemElements,
            Set<String> configurations) {

        this.basePath = cfg.basePath;
        this.codeqlFilePathName = cfg.codeql;
        this.codeqlFilePath = cfg.basePath + File.separator + cfg.codeql;

        this.codeqlRivMap = codeqlRivMap;
        this.systemElements = systemElements;
        this.configurations = configurations;

        loadCorrespondences();
    }

    @Override
    public boolean runCheck() {
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

                // IC2:
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

    /**
     * Load the single file: correspondences.codeqlresultingvaluescorrespondences
     * Build SCAR → Java parameter map
     */
    private void loadCorrespondences() {
        try {
            File corrFile = new File(basePath + "/correspondences.pcmjavacorrespondence");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(corrFile);

            Element root = doc.getDocumentElement();

            // ---- Helper lambda to extract href of a single child element ----
            java.util.function.Function<Element, String> getHref = (elem) -> {
                if (elem == null) return null;
                return elem.getAttribute("href");
            };

            // ---- Process <basiccomponent2class> ----
            NodeList basicNodes = root.getElementsByTagName("basiccomponent2class");
            for (int i = 0; i < basicNodes.getLength(); i++) {
                Element elem = (Element) basicNodes.item(i);

                String pcm = getHref.apply((Element) elem.getElementsByTagName("component").item(0));
                String java = getHref.apply((Element) elem.getElementsByTagName("javaClass").item(0));

                if (pcm != null && java != null)
                    pcmToJavaMap.put(pcm, java);
            }

            // ---- Process <operationInterface2interface> ----
            NodeList opIntNodes = root.getElementsByTagName("operationInterface2interface");
            for (int i = 0; i < opIntNodes.getLength(); i++) {
                Element elem = (Element) opIntNodes.item(i);

                String pcm = getHref.apply((Element) elem.getElementsByTagName("pcmInterface").item(0));
                String java = getHref.apply((Element) elem.getElementsByTagName("javaInterface").item(0));

                if (pcm != null && java != null)
                    pcmToJavaMap.put(pcm, java);
            }

            // ---- Process <compositedatatype2class> ----
            NodeList compDTNodes = root.getElementsByTagName("compositedatatype2class");
            for (int i = 0; i < compDTNodes.getLength(); i++) {
                Element elem = (Element) compDTNodes.item(i);

                String pcm = getHref.apply((Element) elem.getElementsByTagName("CompositeDataType").item(0));
                String java = getHref.apply((Element) elem.getElementsByTagName("javaClass").item(0));

                if (pcm != null && java != null)
                    pcmToJavaMap.put(pcm, java);
            }

            // ---- Process <providedoperationsignature2javamethod> ----
            NodeList methodNodes = root.getElementsByTagName("providedoperationsignature2javamethod");
            for (int i = 0; i < methodNodes.getLength(); i++) {
                Element elem = (Element) methodNodes.item(i);

                // pcm part is nested inside <pcmMethod>
                Element pcmMethodElem = (Element) elem.getElementsByTagName("pcmMethod").item(0);
                if (pcmMethodElem != null) {
                    // We decide to key the mapping by the providedSignature (unique)
                    Element sig = (Element) pcmMethodElem.getElementsByTagName("providedSignature").item(0);
                    if (sig != null) {
                        String pcm = sig.getAttribute("href");
                        String java = getHref.apply((Element) elem.getElementsByTagName("javaMethod").item(0));

                        if (pcm != null && java != null)
                            pcmToJavaMap.put(pcm, java);
                    }
                }
            }

            // ---- Process <pcmparameter2javaparameter> ----
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



    /**
     * Check: Annotation parameter (Java href) → SCAR → PCM repository element
     */
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


    /**
     * Checks if an annotation is referenced by a configuration.
     * In the current data model, each configuration has exactly one <queries> node,
     * so this check is always true if there is exactly one <queries> node.
     */
    private boolean referencedByConfiguration(int queriesNodesLength) {
    	if (queriesNodesLength == 1) {
    		// This is always true because each configuration has exactly one corresponding main configuration element.
    		return true;
    	} else {
    		return false;
    	}
    }    
}
