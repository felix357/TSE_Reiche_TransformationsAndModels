package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC4(C)(M): checks if all applied security policies from source
 * code analysis are mapped to architectural security characteristics affected
 * by IC1, for every relevant configuration (cfg_C).
 */
public class IC4MChecker implements IChecker {

	private final String architecturalModelPath;
	private final String correspondencePath;
	private final String sourceCodeAnalysisPath;
	private final String configurationRepresentationPath;
	private final AnalysisCouplingType analysisType;

	private final Set<String> allSecurityLiterals = new HashSet<>();
	private final List<Mapping> globalMappings = new ArrayList<>();

	private static class Mapping {
		String policyValue;
		String scsValue;

		Mapping(String policyValue, String scsValue) {
			this.policyValue = policyValue;
			this.scsValue = scsValue;
		}

		String getPolicyValue() {
			return policyValue;
		}
	}

	public IC4MChecker(SystemConfig cfg) {
		this.architecturalModelPath = cfg.basePath + File.separator + cfg.pddc;
		this.correspondencePath = cfg.basePath + File.separator + cfg.modelCorrespondence;
		this.sourceCodeAnalysisPath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;
		this.configurationRepresentationPath = cfg.basePath + File.separator + cfg.scConfigurationRepresentation;
		this.analysisType = cfg.analysisCouplingType;
	}

	private Set<String> getAllPolicies(String filePath) throws Exception {
		switch (analysisType) {
		case CODEQLEDFA:
			return getAllCodeqlLevels(filePath);
		case JOANAEDFA:
			return getAllJoanaLevels(filePath);
		default:
			throw new IllegalStateException("Unsupported analysis type: " + analysisType);
		}
	}

	private Set<String> getAllCodeqlLevels(String filePath) throws Exception {
		Set<String> levels = new HashSet<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(new File(filePath))));

		NodeList levelNodes = doc.getElementsByTagName("appliedSecurityLevel");
		for (int i = 0; i < levelNodes.getLength(); i++) {
			levels.add(((Element) levelNodes.item(i)).getAttribute("name"));
		}
		return levels;
	}

	private Set<String> getAllJoanaLevels(String filePath) throws Exception {
		Set<String> levels = new HashSet<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(new File(filePath))));

		NodeList levelNodes = doc.getElementsByTagName("level");
		for (int i = 0; i < levelNodes.getLength(); i++) {
			String name = ((Element) levelNodes.item(i)).getAttribute("name");
			if (!name.isEmpty()) {
				levels.add(name);
			}
		}
		return levels;
	}

	private String resolvePolicyReference(String href) throws Exception {
		switch (analysisType) {
		case CODEQLEDFA:
			return resolveCodeqlReference(href);
		case JOANAEDFA:
			return resolveJoanaReference(href);
		default:
			throw new IllegalStateException("Unsupported analysis type: " + analysisType);
		}
	}

	private String resolveCodeqlReference(String href) throws Exception {
		String path = href.substring(
				href.indexOf("codeql4extendeddataflow.codeql#") + "codeql4extendeddataflow.codeql#".length());
		String[] parts = path.split("/");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(new File(sourceCodeAnalysisPath))));

		org.w3c.dom.Node current = doc.getDocumentElement();
		for (String part : parts) {
			if (part.startsWith("@")) {
				String[] tokens = part.substring(1).split("\\.");
				String tagName = tokens[0];
				int index = Integer.parseInt(tokens[1]);
				NodeList nodes = ((Element) current).getElementsByTagName(tagName);
				if (index >= nodes.getLength()) {
					return null;
				}
				current = nodes.item(index);
			}
		}
		return (current instanceof Element) ? ((Element) current).getAttribute("name") : null;
	}

	private String resolveJoanaReference(String href) throws Exception {
		String path = href.substring(href.indexOf("#//") + 3);
		String[] parts = path.split("/");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(new File(sourceCodeAnalysisPath))));

		org.w3c.dom.Node current = doc.getDocumentElement();
		for (String part : parts) {
			if (part.startsWith("@")) {
				String[] tokens = part.substring(1).split("\\.");
				String tagName = tokens[0];
				int index = Integer.parseInt(tokens[1]);
				NodeList nodes = ((Element) current).getElementsByTagName(tagName);
				if (index >= nodes.getLength()) {
					return null;
				}
				current = nodes.item(index);
			}
		}
		return (current instanceof Element) ? ((Element) current).getAttribute("name") : null;
	}

	private List<String> getAllConfigurationIDs(String filePath) throws Exception {
		List<String> cfgIDs = new ArrayList<>();
		File cfgFile = new File(filePath);
		if (!cfgFile.exists()) {
			return Collections.emptyList();
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(cfgFile)));

		NodeList cfgNodes = doc.getElementsByTagName("configurations");
		for (int i = 0; i < cfgNodes.getLength(); i++) {
			String id = ((Element) cfgNodes.item(i)).getAttribute("id");
			if (!id.isEmpty()) {
				cfgIDs.add(id);
			}
		}
		return cfgIDs;
	}

	private Set<String> getPoliciesUsedByConfiguration(String cfgID, Set<String> globalPolicies) {
		return globalPolicies; // Stub
	}

	private Set<String> getCharacteristicsAffectedByConfiguration(String cfgID, Set<String> globalCharacteristics) {
		return globalCharacteristics; // Stub
	}

	@Override
	public boolean runCheck() {
	    try {
	        boolean overallFulfilled = true;

	        System.out.println("=== Running IC4(C)(M) Check ===");
	        System.out.println("Analysis type: " + analysisType);

	        // Load architectural security characteristics (Scs_C)
	        allSecurityLiterals.addAll(
	                ConformanceUtils.getSecurityCharacteristicLiterals(architecturalModelPath));
	        System.out.println("Loaded architectural security characteristics: " + allSecurityLiterals);

	        // Load policies from source code analysis (Pol_C)
	        Set<String> allPolicies = getAllPolicies(sourceCodeAnalysisPath);
	        System.out.println("Loaded security policies from source code analysis: " + allPolicies);

	        File correspondenceFile = new File(correspondencePath);
	        if (!correspondenceFile.exists()) {
	            System.err.println("❌ Correspondence file not found: " + correspondencePath);
	            return false;
	        }

	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(true);
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        Document doc = db.parse(new InputSource(new FileInputStream(correspondenceFile)));

	        // Load <policy, scs> mappings
	        loadGlobalMappings(doc);

	        Set<String> mappedPolicies = globalMappings.stream()
	                .map(Mapping::getPolicyValue)
	                .collect(Collectors.toSet());

	        System.out.println("Loaded policy-to-SCS mappings:");
	        for (Mapping m : globalMappings) {
	            System.out.println("  " + m.policyValue + " -> " + m.scsValue);
	        }

	        // Load configurations (CFG_C)
	        List<String> cfgIDs = getAllConfigurationIDs(configurationRepresentationPath);
	        System.out.println("\nChecking IC4(C)(M) for " + cfgIDs.size() + " configuration(s).");

	        if (cfgIDs.isEmpty()) {
	            System.out.println("No configurations found. Check is vacuously fulfilled. ✅");
	            return true;
	        }

	        // Per-configuration check
	        for (String cfgID : cfgIDs) {
	            System.out.println("\n--- Configuration: " + cfgID + " ---");

	            Set<String> polCfg = getPoliciesUsedByConfiguration(cfgID, allPolicies);
	            Set<String> scsCfg = getCharacteristicsAffectedByConfiguration(cfgID, allSecurityLiterals);

	            System.out.println("Policies used in cfg: " + polCfg);
	            System.out.println("Security characteristics affected in cfg: " + scsCfg);

	            if (polCfg.isEmpty() || scsCfg.isEmpty()) {
	                System.out.println("Configuration is irrelevant (LHS of implication is false). ✅");
	                continue;
	            }

	            if (!mappedPolicies.containsAll(polCfg)) {
	                Set<String> missing = new HashSet<>(polCfg);
	                missing.removeAll(mappedPolicies);

	                System.out.println("❌ IC4(C)(M) VIOLATION");
	                System.out.println("Missing mappings for policies: " + missing);

	                overallFulfilled = false;
	            } else {
	                System.out.println("IC4(C)(M) fulfilled for this configuration. ✅");
	            }
	        }

	        System.out.println("\n=== IC4(C)(M) RESULT ===");
	        if (overallFulfilled) {
	            System.out.println("OVERALL RESULT: FULFILLED ✅");
	        } else {
	            System.out.println("OVERALL RESULT: FAILED ❌");
	        }

	        return overallFulfilled;

	    } catch (Exception e) {
	        System.err.println("❌ Exception during IC4(C)(M) check:");
	        e.printStackTrace();
	        return false;
	    }
	}

	private void loadGlobalMappings(Document doc) throws Exception {
		if (analysisType == AnalysisCouplingType.CODEQLEDFA) {
			loadCodeqlMappings(doc);
		} else {
			loadJoanaMappings(doc);
		}
	}

	private void loadCodeqlMappings(Document doc) throws Exception {
		NodeList correspondences = doc.getElementsByTagName("literalSecurityLevelCorrespondences");

		for (int i = 0; i < correspondences.getLength(); i++) {
			Element corr = (Element) correspondences.item(i);

			Element pol = (Element) corr.getElementsByTagName("securityLevel_CodeQL").item(0);
			Element edfa = (Element) corr.getElementsByTagName("literals_EDFA").item(0);

			String polValue = resolvePolicyReference(pol.getAttribute("href"));
			String scsValue = ConformanceUtils.resolvePddcReference(edfa.getAttribute("href"), architecturalModelPath);

			if (polValue != null && scsValue != null && allSecurityLiterals.contains(scsValue)) {
				globalMappings.add(new Mapping(polValue, scsValue));
			}
		}
	}

	private void loadJoanaMappings(Document doc) throws Exception {
		NodeList correspondences = doc.getElementsByTagName("literalLevelCorrespondences");

		for (int i = 0; i < correspondences.getLength(); i++) {
			Element corr = (Element) correspondences.item(i);

			Element pol = (Element) corr.getElementsByTagName("level_JOANA").item(0);
			Element edfa = (Element) corr.getElementsByTagName("literals_EDFA").item(0);

			String polValue = resolvePolicyReference(pol.getAttribute("href"));
			String scsValue = ConformanceUtils.resolvePddcReference(edfa.getAttribute("href"), architecturalModelPath);

			if (polValue != null && scsValue != null && allSecurityLiterals.contains(scsValue)) {
				globalMappings.add(new Mapping(polValue, scsValue));
			}
		}
	}
}
