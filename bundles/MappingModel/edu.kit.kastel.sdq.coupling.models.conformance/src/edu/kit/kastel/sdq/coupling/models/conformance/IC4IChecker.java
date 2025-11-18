package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Checker for IC4(I): Ensures that every CodeQL security policy (pol_C) uses
 * the correct resolved security level (scs_C) according to IC1.
 */
public class IC4IChecker implements IChecker {

	private final String basePath;
	private final String configurationRepresentationPath;

	private final Map<String, String> codeqlRivMap;
	private final Map<String, String> rivValuesMap;

	public IC4IChecker(String basePath, String configurationRepresentationName, Map<String, String> codeqlRivMap,
			Map<String, String> rivValuesMap) {
		this.basePath = basePath;
		this.configurationRepresentationPath = basePath + File.separator + configurationRepresentationName;
		this.codeqlRivMap = codeqlRivMap;
		this.rivValuesMap = rivValuesMap;
	}

	@Override
	public boolean runCheck() {
	    try {
	        // Step 1: Load configuration
	        Configuration cfg = loadConfigurationRepresentation();

	        // Step 2: Extract policies from CodeQL query
	        List<Policy> policies = extractPoliciesFromQuery(
	                this.basePath + File.separator + cfg.getMainElementFile(),
	                cfg.getMainElementFragment()
	        );

	        boolean allOk = true; // assume everything is OK initially

	        // Step 3: Check each policy
	        for (Policy policy : policies) {
	            String codeqlRef = policy.securityLevelRef;
	            if (!codeqlRef.startsWith(cfg.getMainElementFile() + "#")) {
	                codeqlRef = cfg.getMainElementFile() + "#" + codeqlRef;
	            }

	            // Check whether the policy has an applied security characteristic
	            if (!codeqlRivMap.containsKey(codeqlRef)) {
	                System.out.println("Policy " + policy.id + " has no applied security characteristic. Skipping check.");
	                continue;
	            }

	            String rivId = codeqlRivMap.get(codeqlRef);

	            // Extract key after '#' for lookup in rivValuesMap
	            String resolvedLevelKey = rivId;
	            int idx = rivId.indexOf('#');
	            if (idx != -1) {
	                resolvedLevelKey = rivId.substring(idx + 1);
	            }

	            // Retrieve the resolved security characteristic value
	            String resolvedLevel = rivValuesMap.get(resolvedLevelKey);
	            if (resolvedLevel == null) {
	                System.out.println("IC4 Violation: Policy " + policy.id +
	                                   " has no resolved security level for RIV id " + rivId);
	                allOk = false; // mark violation
	            } else {
	                System.out.println("Policy " + policy.id + " resolved to security level: " + resolvedLevel);
	            }
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
	 * Extracts all policies from the specified query fragment in the CodeQL file.
	 */
	private List<Policy> extractPoliciesFromQuery(String filePath, String fragment) throws Exception {
		List<Policy> policies = new ArrayList<>();

		Document doc = ConformanceUtils.parseXmlFile(filePath);

		// Resolve query index from fragment, e.g., "//@queries.0"
		NodeList queriesList = doc.getElementsByTagName("queries");
		int queryIndex = 0;
		if (fragment.startsWith("//@queries.")) {
			queryIndex = Integer.parseInt(fragment.substring("//@queries.".length()));
		}
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

		System.out.println("Extracted " + policies.size() + " policies from " + filePath + " " + fragment);
		return policies;
	}
}