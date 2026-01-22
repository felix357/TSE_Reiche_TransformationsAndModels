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

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC7(T)(I)
 */
public class IC7IChecker implements IChecker {

	private final AnalysisCouplingType analysisType;

	private final IC2IChecker ic2;
	private final String scScarCorrespondencesPath;
	private final String scResultingValuesCorrespondencesPath;
	private final String resultingValuesModelPath;

	private final Set<String> foundResultingValueInstances = new HashSet<>();

	public IC7IChecker(SystemConfig cfg, IC2IChecker ic2checker) {
		this.ic2 = ic2checker;
		this.scScarCorrespondencesPath = cfg.basePath + File.separator + cfg.correspondencesSCScar;
		this.scResultingValuesCorrespondencesPath = cfg.basePath + File.separator + cfg.rivCorrespondence;
		this.resultingValuesModelPath = cfg.basePath + File.separator + cfg.riv;
		this.analysisType = cfg.analysisCouplingType;
	}

	@Override
	public boolean runCheck() {
		try {
			Set<String> cfgCsC = ic2.getConfigurationsFromIC2();
			if (cfgCsC == null || cfgCsC.isEmpty()) {
				System.out.println("IC7(T)(I): IC2 provided no sc configuration hrefs.");
				return false;
			}

			Map<String, String> scToScar = loadSCToScarConfigMap();
			Map<String, String> scarToResulting = loadScarToResultingConfigMap();

			if (scToScar.isEmpty() || scarToResulting.isEmpty()) {
				System.out.println("IC7(T)(I): Correspondence maps could not be loaded.");
				return false;
			}

			Document resultingDoc = parseXmlFile(resultingValuesModelPath);
			if (resultingDoc == null) {
				System.out.println("IC7(T)(I): Could not parse resulting values model.");
				return false;
			}

			boolean anyFound = false;

			for (String scHref : cfgCsC) {
				if (scHref == null || scHref.trim().isEmpty())
					continue;

				String normalizedSCHref = scHref;
				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {

					if (!normalizedSCHref.startsWith("codeql4")) {
						normalizedSCHref = "codeql4" + normalizedSCHref;
					}
				}

				String scarHref = scToScar.get(normalizedSCHref);
				if (scarHref == null) {
					System.out.println("IC7(T)(I) warn: No SCAR correspondence for SC config: " + scHref
							+ " (normalized: " + normalizedSCHref + ")");
					continue;
				}

				String resultingHref = scarToResulting.get(scarHref);
				if (resultingHref == null) {
					System.out
							.println("IC7(T)(I) warn: No ResultingValues correspondence for SCAR config: " + scarHref);
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
				System.out.println("IC7(T)(I) NOT satisfied. No resulting-values instances found.");
				return false;
			}

			System.out.println("IC7(T)(I) satisfied. Found instances: " + foundResultingValueInstances);
			return true;

		} catch (Exception ex) {
			System.err.println("IC7(T)(I) error:");
			ex.printStackTrace();
			return false;
		}
	}

	private Map<String, String> loadSCToScarConfigMap() throws Exception {
		Map<String, String> map = new HashMap<>();
		Document doc = parseXmlFile(scScarCorrespondencesPath);
		if (doc == null)
			return map;

		String configCorrespondence;
		String scConfig;
		String scarConfig;

		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			configCorrespondence = "configurationCorrespondences";
			scConfig = "configuration_CodeQL";
			scarConfig = "configuration_SCAR";
		} else {
			configCorrespondence = "entryPointCorrespondences";
			scConfig = "entryPoint_JOANA";
			scarConfig = "entryPoint_SCAR";
		}

		NodeList cfgNodes = doc.getElementsByTagName(configCorrespondence);
		for (int i = 0; i < cfgNodes.getLength(); i++) {
			Element corr = (Element) cfgNodes.item(i);
			String scHref = getHref(corr, scConfig);
			String scarHref = getHref(corr, scarConfig);
			if (scHref != null && scarHref != null) {
				map.put(scHref, scarHref);
			}
		}
		return map;
	}

	private Map<String, String> loadScarToResultingConfigMap() throws Exception {
		Map<String, String> map = new HashMap<>();
		Document doc = parseXmlFile(scResultingValuesCorrespondencesPath);
		if (doc == null)
			return map;

		String configCorrespondence;
		String rivConfig;
		String scarConfig;

		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			configCorrespondence = "configurationCorrespondences";
			rivConfig = "configuration_ResultingValues";
			scarConfig = "configuration_SCAR";
		} else {
			configCorrespondence = "entryPointCorrespondences";
			rivConfig = "entryPoint_ResolvedImplementationValues";
			scarConfig = "entryPoint_SCAR";
		}

		NodeList cfgNodes = doc.getElementsByTagName(configCorrespondence);
		for (int i = 0; i < cfgNodes.getLength(); i++) {
			Element corr = (Element) cfgNodes.item(i);
			String scarHref = getHref(corr, scarConfig);
			String resultingHref = getHref(corr, rivConfig);
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
			String config;
			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
				config = "ruleId";
			} else {
				config = "configuration";
			}

			if (rv.hasAttribute(config)) {
				if (fragment.equals(rv.getAttribute(config))) {
					return true;
				}
			}
		}
		return false;
	}

	private String extractFragment(String href) {
		if (href == null)
			return null;
		int idx = href.indexOf('#');
		if (idx == -1 || idx + 1 >= href.length())
			return null;
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
