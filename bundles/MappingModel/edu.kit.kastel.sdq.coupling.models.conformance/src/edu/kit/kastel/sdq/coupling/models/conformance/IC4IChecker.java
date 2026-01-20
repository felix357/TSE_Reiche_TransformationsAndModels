package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC4(I): Checks if every security policy (pol_C) uses
 * the correct resolved security level (scs_C) according to IC1.
 * Supports both CodeQL+EDFA and JOANA+EDFA couplings.
 */
public class IC4IChecker implements IChecker {

    private final String basePath;
    private final String configurationRepresentationPath;
    private final AnalysisCouplingType analysisType;

    private final Map<String, String> rivMap;
    private final Map<String, String> rivValuesMap;

    public IC4IChecker(SystemConfig cfg, Map<String, String> rivMap, Map<String, String> rivValuesMap) {
        this.basePath = cfg.basePath;
        this.configurationRepresentationPath = cfg.basePath + File.separator + cfg.scConfigurationRepresentation;
        this.analysisType = cfg.analysisCouplingType;
        this.rivMap = rivMap;
        this.rivValuesMap = rivValuesMap;
    }

    @Override
    public boolean runCheck() {
        try {
            // Step 1: Load configuration
            Configuration cfg = loadConfigurationRepresentation();

            // Step 2: Extract policies from the correct query type
            List<Policy> policies = extractPoliciesFromQuery(this.basePath + File.separator + cfg.getMainElementFile(),
                    cfg.getMainElementFragment());

            boolean allOk = true; // assume everything is OK initially

            // Step 3: Check each policy
            for (Policy policy : policies) {
                String ref = policy.securityLevelRef;
                if (!ref.startsWith(cfg.getMainElementFile() + "#")) {
                    ref = cfg.getMainElementFile() + "#" + ref;
                }

                // Check whether the policy has an applied security characteristic
                if (!rivMap.containsKey(ref)) {
                    System.out.println(
                            "Policy " + policy.id + " has no applied security characteristic. Skipping check.");
                    continue;
                }

                String rivId = rivMap.get(ref);

                // Extract key after '#' for lookup in rivValuesMap
                String resolvedKey = rivId.contains("#") ? rivId.substring(rivId.indexOf('#') + 1) : rivId;

                String resolvedLevel = rivValuesMap.get(resolvedKey);
                if (resolvedLevel == null) {
                    System.out.println("IC4 Violation: Policy " + policy.id
                            + " has no resolved security level for RIV id " + rivId);
                    allOk = false;
                } else {
                    System.out.println("Policy " + policy.id + " resolved to security level: " + resolvedLevel);
                }
            }

            if (allOk) {
                System.out.println("IC4(I) check passed for " + analysisType + " coupling.");
            } else {
                System.out.println("IC4(I) check FAILED for " + analysisType + " coupling.");
            }

            return allOk;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads the main configuration from the XML representation.
     */
    private Configuration loadConfigurationRepresentation() throws Exception {
        Document doc = ConformanceUtils.parseXmlFile(configurationRepresentationPath);

        NodeList configs = doc.getElementsByTagName("configurations");
        if (configs.getLength() == 0) {
            throw new IllegalStateException("No <configurations> element found in configuration representation.");
        }

        Element configElem = (Element) configs.item(0);
        String id = configElem.getAttribute("id");

        Element mainElem = (Element) configElem.getElementsByTagName("mainConfigurationElement").item(0);
        String href = mainElem.getAttribute("href");

        int idx = href.indexOf('#');
        if (idx == -1) {
            throw new IllegalArgumentException("mainConfigurationElement href has no '#': " + href);
        }

        String mainFile = href.substring(0, idx);
        String mainFragment = href.substring(idx + 1);

        return new Configuration(id, mainFile, mainFragment);
    }

    /**
     * Extracts all policies from the specified fragment in the XML file.
     * Supports CodeQL and JOANA couplings.
     */
    private List<Policy> extractPoliciesFromQuery(String filePath, String fragment) throws Exception {
        List<Policy> policies = new ArrayList<>();
        Document doc = ConformanceUtils.parseXmlFile(filePath);

        if (analysisType == AnalysisCouplingType.CODEQLEDFA) {
            // CodeQL extraction
            NodeList queriesList = doc.getElementsByTagName("queries");
            int queryIndex = fragment.startsWith("//@queries.") ? Integer.parseInt(fragment.substring("//@queries.".length())) : 0;
            if (queryIndex >= queriesList.getLength()) {
                throw new IllegalArgumentException("Query index out of bounds: " + queryIndex);
            }
            Element queryElem = (Element) queriesList.item(queryIndex);

            NodeList annotations = queryElem.getElementsByTagName("securityLevelAnnotations");
            for (int i = 0; i < annotations.getLength(); i++) {
                Element elem = (Element) annotations.item(i);
                Policy p = new Policy();
                p.id = elem.getAttribute("id");
                p.securityLevelRef = elem.getAttribute("securityLevel");

                Element paramElem = (Element) elem.getElementsByTagName("parameter").item(0);
                if (paramElem != null) {
                    p.parameterHref = paramElem.getAttribute("href");
                }

                policies.add(p);
            }

        } else if (analysisType == AnalysisCouplingType.JOANAEDFA) {
            // JOANA extraction
            NodeList annotations = doc.getElementsByTagName("securityLevelAnnotation");
            for (int i = 0; i < annotations.getLength(); i++) {
                Element elem = (Element) annotations.item(i);
                Policy p = new Policy();
                p.id = elem.getAttribute("id");
                p.securityLevelRef = elem.getAttribute("level"); // JOANA uses "level" attribute

                Element paramElem = (Element) elem.getElementsByTagName("parameter").item(0);
                if (paramElem != null) {
                    p.parameterHref = paramElem.getAttribute("href");
                }

                policies.add(p);
            }
        } else {
            throw new IllegalStateException("Unsupported analysis type: " + analysisType);
        }

        System.out.println("Extracted " + policies.size() + " policies from " + filePath + " for " + analysisType);
        return policies;
    }
}
