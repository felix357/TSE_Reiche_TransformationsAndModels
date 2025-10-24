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
 * Checks the IC3(C)(M) conformance rule for model coupling consistency.
 * <p>
 * IC3(C)(M) ensures that each security annotation connects a valid
 * security level, a system element, and a configuration element across
 * the involved models.
 * </p>
 */
public class IC3MChecker implements IChecker {

	private final String basePath;
    private final String codeqlFilePath;
    // from IC1M
    private final Set<String> securityLiterals;
    // from IC2M
    private final Set<String> systemElementsFromIC2;
    // from IC2M
    private final Set<String> configurationsFromIC2;

    private Map<String, String> annotationToLevel = new HashMap<>();
    private Map<String, Set<String>> levelToAnnotations = new HashMap<>();
    private Map<String, Set<String>> levelToConfigurations = new HashMap<>();

    public IC3MChecker(String basePath, String codeqlFilePath, Set<String> securityLiterals,
                       Set<String> systemElementsFromIC2, Set<String> configurationsFromIC2) {
    	this.basePath = basePath;
        this.codeqlFilePath = codeqlFilePath;
        this.securityLiterals = securityLiterals;
        this.systemElementsFromIC2 = systemElementsFromIC2;
        this.configurationsFromIC2 = configurationsFromIC2;
    }

    /**
     * Executes the IC3(C)(M) check.
     * <p>
     * Steps:
     * <ol>
     *   <li>Parse applied security levels and annotations from the CodeQL file.</li>
     *   <li>Resolve references between annotations and their applied levels.</li>
     *   <li>Resolve configurations and map them to applied security levels.</li>
     *   <li>Verify that at least one annotation connects a valid level, system element, and configuration.</li>
     * </ol>
     *
     * @return {@code true} if the IC3(C)(M) rule is satisfied, {@code false} otherwise
     */
    @Override
    public boolean runCheck() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new FileInputStream(new File(codeqlFilePath))));

            NodeList levels = doc.getElementsByTagName("appliedSecurityLevel");
            Map<String, String> levelIdToName = new HashMap<>();
            for (int i = 0; i < levels.getLength(); i++) {
                Element e = (Element) levels.item(i);
                String id = e.getAttribute("id");
                String name = e.getAttribute("name");
                levelIdToName.put(id, name);
            }

            NodeList annotations = doc.getElementsByTagName("securityLevelAnnotations");
            for (int i = 0; i < annotations.getLength(); i++) {
                Element e = (Element) annotations.item(i);
                String annotationId = e.getAttribute("id");
                String securityLevelRef = e.getAttribute("securityLevel");

                String levelId = resolveSecurityLevelId(securityLevelRef, doc);
                if (levelId != null) {
                    annotationToLevel.put(annotationId, levelId);
                    levelToAnnotations.computeIfAbsent(levelId, k -> new HashSet<>()).add(annotationId);
                }
            }

            for (String configRefC : configurationsFromIC2) {
                Set<String> levelIds = resolveRefForConfig(configRefC, dbf);
                for (String levelId : levelIds) {
                    levelToConfigurations.computeIfAbsent(levelId, k -> new HashSet<>()).add(configRefC);
                }
            }

            // IC3(C)(M) check
            for (Map.Entry<String, String> entry : annotationToLevel.entrySet()) {
                String annotationId = entry.getKey();
                String levelId = entry.getValue();

                // Check Security literal
                String levelName = levelIdToName.get(levelId);
                if (!securityLiterals.contains(levelName)) continue;

                // Check system element
                if (!affectsSystemElement(annotationId, systemElementsFromIC2, doc)) continue;

                // check configuration
                Set<String> cfgsForLevel = levelToConfigurations.get(levelId);
                if (cfgsForLevel == null || cfgsForLevel.isEmpty()) continue;

                System.out.println("IC3(C)(M) erfüllt");
                return true;
            }

            System.out.println("IC3(C)(M) NICHT erfüllt.");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Resolves a reference to a security level within the given document.
     *
     * @param ref the reference string (e.g., "codeql#//@appliedSecurityLevel.0")
     * @param doc the XML document to resolve the reference in
     * @return the ID of the referenced security level, or {@code null} if not found
     */
    private String resolveSecurityLevelId(String ref, Document doc) {
        try {
            String[] parts = ref.split("/@");
            if (parts.length < 3) return null;
            String lastPart = parts[parts.length - 1];
            String[] tokens = lastPart.split("\\.");
            String tag = tokens[0];
            int index = Integer.parseInt(tokens[1]);

            NodeList nodes = doc.getElementsByTagName(tag);
            if (index >= nodes.getLength()) return null;
            Element e = (Element) nodes.item(index);
            return e.getAttribute("id");
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Resolves a configuration reference and collects all applied security level IDs
     * linked to that configuration.
     *
     * @param configRefC reference to a configuration element
     * @param dbf document builder factory
     * @return a set of applied security level IDs found for the configuration
     */
    private Set<String> resolveRefForConfig(String configRefC, DocumentBuilderFactory dbf) {
        Set<String> levelIds = new HashSet<>();
        try {
            String[] parts = configRefC.split("#");
            String configFilePath = parts[0];       
            String configPath = parts[1];
            int configIndex = Integer.parseInt(configPath.replaceAll("\\D", ""));

            DocumentBuilder db = dbf.newDocumentBuilder();
            Document configDoc = db.parse(new File(basePath + "/" + configFilePath));
            NodeList configurations = configDoc.getElementsByTagName("configurations");
            if (configIndex >= configurations.getLength()) return levelIds;

            Element configElem = (Element) configurations.item(configIndex);

            Element mainConfigElem = (Element) configElem.getElementsByTagName("mainConfigurationElement").item(0);
            if (mainConfigElem == null) return levelIds;
            String queriesHref = mainConfigElem.getAttribute("href");

            String[] qParts = queriesHref.split("#");
            String codeqlFile = qParts[0];
            String queriesPath = qParts[1];
            int queriesIndex = Integer.parseInt(queriesPath.replaceAll("\\D", ""));

            Document codeqlDoc = db.parse(new File(basePath + "/" + codeqlFile));
            NodeList queriesList = codeqlDoc.getElementsByTagName("queries");
            if (queriesIndex >= queriesList.getLength()) return levelIds;

            Element queriesElem = (Element) queriesList.item(queriesIndex);

            NodeList levels = queriesElem.getElementsByTagName("appliedSecurityLevel");
            for (int i = 0; i < levels.getLength(); i++) {
                Element levelElem = (Element) levels.item(i);
                levelIds.add(levelElem.getAttribute("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return levelIds;
    }

    /**
     * Determines whether a given annotation affects any system element
     * identified by IC2.
     *
     * @param annotationId the ID of the security annotation
     * @param systemElementsFromIC2 set of system element references from IC2
     * @param doc the parsed CodeQL document
     * @return {@code true} if the annotation affects a known system element, {@code false} otherwise
     */
    private boolean affectsSystemElement(String annotationId, Set<String> systemElementsFromIC2, Document doc) {
        try {
            NodeList annotations = doc.getElementsByTagName("securityLevelAnnotations");
            for (int i = 0; i < annotations.getLength(); i++) {
                Element annotationElem = (Element) annotations.item(i);
                String id = annotationElem.getAttribute("id");
                if (!id.equals(annotationId)) continue;

                NodeList params = annotationElem.getElementsByTagName("parameter");
                for (int j = 0; j < params.getLength(); j++) {
                    Element param = (Element) params.item(j);
                    String href = param.getAttribute("href");

                    for (String systemElem : systemElementsFromIC2) {
                        if (href.startsWith(systemElem)) return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
