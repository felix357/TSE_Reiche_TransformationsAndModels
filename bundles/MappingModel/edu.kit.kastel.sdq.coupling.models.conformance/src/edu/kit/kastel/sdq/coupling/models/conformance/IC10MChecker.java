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
 * IC10MChecker checks if security characteristics in Resolved Implementation
 * Values (RIVs) are correctly referenced in corresponding configurations in the
 * SCAR and CodeQL models on the model level.
 */
public class IC10MChecker implements IChecker {

	private final String rivPath;
	private final String correspondencesPath;
	private final String codeqlPath;
	private final String scarPath;

	private final IC8MChecker ic8Checker;
	private final IC9MChecker ic9Checker;

	private final Set<String> invalidRIVs = new HashSet<>();

	private Map<String, String> rivSecToCodeqlSec = new HashMap<>();
	private Map<String, String> rivParamToCodeqlParam = new HashMap<>(); // Beibehalten für Initial Mapping Check
	private Map<String, String> rivCfgToScarCfg = new HashMap<>();
	private Map<String, String> scarCfgToCodeqlCfg = new HashMap<>();

	private Document scarDoc;
	private Document rDoc;
	private Document codeqlDoc;

	public IC10MChecker(SystemConfig cfg, IC8MChecker ic8Checker, IC9MChecker ic9Checker) {
		this.rivPath = cfg.basePath + File.separator + cfg.riv;
		this.correspondencesPath = cfg.basePath + File.separator + cfg.rivCorrespondence;
		this.codeqlPath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;
		this.scarPath = cfg.basePath + File.separator + "scar.codeqlscar";

		this.ic8Checker = ic8Checker;
		this.ic9Checker = ic9Checker;
	}

	@Override
	public boolean runCheck() {
		boolean allValid = true;

		try {
			rDoc = parse(rivPath);
			Document corrDoc = parse(correspondencesPath);
			codeqlDoc = parse(codeqlPath);
			scarDoc = parse(scarPath);

			loadSecurityLevelCorrespondences(corrDoc);
			loadParameterCorrespondences(corrDoc);
			loadConfigurationCorrespondences(corrDoc, scarDoc);

			NodeList rivNodes = rDoc.getElementsByTagName("resultingValues");

			for (int i = 0; i < rivNodes.getLength(); i++) {
				if (ic8Checker.getInvalidRIVs().contains("RIV index " + i)
						|| ic9Checker.getInvalidRIVs().contains("RIV index " + i)) {
					continue;
				}

				Element riv = (Element) rivNodes.item(i);
				String secRef = riv.getAttribute("resultingSecurityLevel");
				String cfgRef = riv.getAttribute("ruleId");
				String paramRef = riv.getAttribute("parameter");

				String mappedCodeqlSec = rivSecToCodeqlSec.get(secRef);
				String scarCfgFrag = rivCfgToScarCfg.get(cfgRef);
				String mappedCodeqlCfg = scarCfgToCodeqlCfg.get(scarCfgFrag);
				String mappedCodeqlParam = rivParamToCodeqlParam.get(paramRef);

				if (mappedCodeqlSec == null || mappedCodeqlCfg == null || mappedCodeqlParam == null) {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " mapping missing: cfg=" + cfgRef + ", sec=" + secRef
							+ ", param=" + paramRef);
					continue;
				}

				boolean found = false;

				NodeList queryNodes = codeqlDoc.getElementsByTagName("queries");
				for (int q = 0; q < queryNodes.getLength(); q++) {
					Element query = (Element) queryNodes.item(q);
					String queryId = query.getAttribute("id");
					if (!mappedCodeqlCfg.equals(queryId))
						continue;

					NodeList annotations = query.getElementsByTagName("securityLevelAnnotations");
					for (int j = 0; j < annotations.getLength(); j++) {
						Element ann = (Element) annotations.item(j);
						String annSec = ann.getAttribute("securityLevel");

						if (!mappedCodeqlSec.equals(annSec))
							continue;

						found = true;
						break;
					}
					if (found)
						break;
				}

				if (!found) {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " violates IC10(C)(M): security characteristic " + secRef
							+ " not referenced in configuration " + cfgRef);
				}
			}

			if (allValid) {
				System.out.println(
						"IC10(C)(M) satisfied ✅ — all RIVs reference security characteristics inside correct configurations.");
			} else {
				System.out.println("IC10(C)(M) NOT satisfied ❌ — violations found:");
				invalidRIVs.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return allValid;
	}

	private void loadSecurityLevelCorrespondences(Document corrDoc) {
		NodeList nodes = corrDoc.getElementsByTagName("securityLevelCorrespondences");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivHref = ((Element) corr.getElementsByTagName("securityLevel_ResolvedImplementationValues").item(0))
					.getAttribute("href");
			String codeqlHref = ((Element) corr.getElementsByTagName("securityLevel_CodeQL").item(0))
					.getAttribute("href");
			rivSecToCodeqlSec.put(rivHref.substring(rivHref.indexOf("#") + 1),
					codeqlHref.substring(codeqlHref.indexOf("#") + 1));
		}
	}

	private void loadParameterCorrespondences(Document corrDoc) {
		NodeList nodes = corrDoc.getElementsByTagName("parameterCorrespondences");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivParam = ((Element) corr.getElementsByTagName("parameter_ResolvedImplementationValues").item(0))
					.getAttribute("href");
			String codeqlParam = ((Element) corr.getElementsByTagName("parameter_SCAR").item(0)).getAttribute("href");
			rivParamToCodeqlParam.put(rivParam.substring(rivParam.indexOf("#") + 1),
					codeqlParam.substring(codeqlParam.indexOf("#") + 1));
		}
	}

	private void loadConfigurationCorrespondences(Document corrDoc, Document scarDoc) {
		NodeList nodes = corrDoc.getElementsByTagName("configurationCorrespondences");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivCfgHref = ((Element) corr.getElementsByTagName("configuration_ResultingValues").item(0))
					.getAttribute("href");
			String scarCfgHref = ((Element) corr.getElementsByTagName("configuration_SCAR").item(0))
					.getAttribute("href");

			String rivCfgFrag = rivCfgHref.substring(rivCfgHref.indexOf("#") + 1);
			String scarCfgFrag = scarCfgHref.substring(scarCfgHref.indexOf("#") + 1);

			rivCfgToScarCfg.put(rivCfgFrag, scarCfgFrag);

			String codeqlId = findCodeqlIdInScar(scarDoc, scarCfgFrag);
			scarCfgToCodeqlCfg.put(scarCfgFrag, codeqlId);
		}
	}

	private String findCodeqlIdInScar(Document scarDoc, String scarFrag) {
		if (!scarFrag.startsWith("//@ruleIds"))
			return null;

		int idx = 0;
		try {
			String idxStr = scarFrag.substring(scarFrag.lastIndexOf('.') + 1);
			idx = Integer.parseInt(idxStr);
		} catch (NumberFormatException e) {
			return null;
		}

		NodeList ruleIds = scarDoc.getElementsByTagName("ruleIds");
		if (idx < 0 || idx >= ruleIds.getLength())
			return null;

		Element rule = (Element) ruleIds.item(idx);
		return rule.getAttribute("id");
	}

	private Document parse(String path) throws Exception {
		File f = new File(path);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		return db.parse(new InputSource(new FileInputStream(f)));
	}

	public Set<String> getInvalidRIVs() {
		return new HashSet<>(invalidRIVs);
	}
}