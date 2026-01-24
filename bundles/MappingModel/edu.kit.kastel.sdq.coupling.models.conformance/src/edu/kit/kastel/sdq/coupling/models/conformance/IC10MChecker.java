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
 * IC10MChecker checks if security characteristics in Resolved Implementation
 * Values are correctly referenced in corresponding configurations in the
 * SCAR and source code models on the model level.
 */
public class IC10MChecker implements IChecker {

	private final String rivPath;
	private final String correspondencesPath;
	private final String scPath;
	private final String scarPath;

	private final IC8MChecker ic8Checker;
	private final IC9MChecker ic9Checker;

	private final Set<String> invalidRIVs = new HashSet<>();

	private Map<String, Set<String>> rivSecToScSec = new HashMap<>();
	private Map<String, String> rivParamToScParam = new HashMap<>();
	private Map<String, String> rivCfgToScarCfg = new HashMap<>();
	private Map<String, String> scarCfgToScCfg = new HashMap<>();

	private Document scarDoc;
	private Document rDoc;
	private Document scDoc;

	private final AnalysisCouplingType analysisType;

	public IC10MChecker(SystemConfig cfg, IC8MChecker ic8Checker, IC9MChecker ic9Checker) {
		this.analysisType = cfg.analysisCouplingType;

		this.rivPath = cfg.basePath + File.separator + cfg.riv;
		this.correspondencesPath = cfg.basePath + File.separator + cfg.rivCorrespondence;
		this.scPath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;

		this.ic8Checker = ic8Checker;
		this.ic9Checker = ic9Checker;

		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			this.scarPath = cfg.basePath + File.separator + "scar.codeqlscar";
		} else {
			this.scarPath = cfg.basePath + File.separator + "scar.joanascar";
		}
	}

	@Override
	public boolean runCheck() {
		boolean allValid = true;

		try {
			rDoc = parse(rivPath);
			Document corrDoc = parse(correspondencesPath);
			scDoc = parse(scPath);
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

				String resLVL;
				String cfg;
				String sysElem;
				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
					resLVL = "resultingSecurityLevel";
					cfg = "ruleId";
					sysElem = "parameter";
				} else {
					resLVL = "level";
					cfg = "configuration";
					sysElem = "systemElement";
				}

				Element riv = (Element) rivNodes.item(i);
				String secRef = riv.getAttribute(resLVL);
				String cfgRef = riv.getAttribute(cfg);
				String paramRef = riv.getAttribute(sysElem);

				Set<String> mappedScSecs = rivSecToScSec.get(secRef);
				String scarCfgFrag = rivCfgToScarCfg.get(cfgRef);
				String mappedScCfg = scarCfgToScCfg.get(scarCfgFrag);
				String mappedScParam = rivParamToScParam.get(paramRef);

				if (mappedScSecs == null || mappedScCfg == null || mappedScParam == null) {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " mapping missing: cfg=" + cfgRef + ", sec=" + secRef
							+ ", param=" + paramRef);
					continue;
				}

				boolean found = false;

				String queries;
				String annotation;
				String lvl;

				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
					queries = "queries";
					annotation = "securityLevelAnnotations";
					lvl = "securityLevel";
				} else {
					queries = "entrypoint";
					annotation = "annotation";
					lvl = "level";
				}

				NodeList queryNodes = scDoc.getElementsByTagName(queries);
				for (int q = 0; q < queryNodes.getLength(); q++) {
					Element query = (Element) queryNodes.item(q);
					String queryId = query.getAttribute("id");
					if (!mappedScCfg.equals(queryId))
						continue;

					NodeList annotations = query.getElementsByTagName(annotation);
					for (int j = 0; j < annotations.getLength(); j++) {
						Element ann = (Element) annotations.item(j);
						String annSec = ann.getAttribute(lvl);

						if (!mappedScSecs.contains(annSec))
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
						"IC10(C)(M) satisfied. All RIVs reference security characteristics inside correct configurations.");
			} else {
				System.out.println("IC10(C)(M) NOT satisfied. Violations found:");
				invalidRIVs.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return allValid;
	}

	private void loadSecurityLevelCorrespondences(Document corrDoc) {

		String levelCorr;
		String lRiv;
		String secLvl;
		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			levelCorr = "securityLevelCorrespondences";
			lRiv = "securityLevel_ResolvedImplementationValues";
			secLvl = "securityLevel_CodeQL";
		} else {
			levelCorr = "levelCorrespondences";
			lRiv = "level_ResolvedImplementationValues";
			secLvl = "level_JOANA";
		}

		NodeList nodes = corrDoc.getElementsByTagName(levelCorr);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);

			String rivHref = ((Element) corr.getElementsByTagName(lRiv).item(0))
					.getAttribute("href");
			String scHref = ((Element) corr.getElementsByTagName(secLvl).item(0))
					.getAttribute("href");

			String rivKey = rivHref.substring(rivHref.indexOf("#") + 1);
			String scVal = scHref.substring(scHref.indexOf("#") + 1);

			rivSecToScSec
				.computeIfAbsent(rivKey, k -> new HashSet<>())
				.add(scVal);
		}
	}

	private void loadParameterCorrespondences(Document corrDoc) {
		NodeList nodes = corrDoc.getElementsByTagName("parameterCorrespondences");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivParam = ((Element) corr.getElementsByTagName("parameter_ResolvedImplementationValues").item(0))
					.getAttribute("href");
			String scParam = ((Element) corr.getElementsByTagName("parameter_SCAR").item(0))
					.getAttribute("href");
			rivParamToScParam.put(rivParam.substring(rivParam.indexOf("#") + 1),
					scParam.substring(scParam.indexOf("#") + 1));
		}
	}

	private void loadConfigurationCorrespondences(Document corrDoc, Document scarDoc) {

		String cfgCorr;
		String cfgRiv;
		String cfgScar;
		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			cfgCorr = "configurationCorrespondences";
			cfgRiv = "configuration_ResultingValues";
			cfgScar = "configuration_SCAR";
		} else {
			cfgCorr = "entryPointCorrespondences";
			cfgRiv = "entryPoint_ResolvedImplementationValues";
			cfgScar = "entryPoint_SCAR";
		}

		NodeList nodes = corrDoc.getElementsByTagName(cfgCorr);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivCfgHref = ((Element) corr.getElementsByTagName(cfgRiv).item(0))
					.getAttribute("href");
			String scarCfgHref = ((Element) corr.getElementsByTagName(cfgScar).item(0))
					.getAttribute("href");

			String rivCfgFrag = rivCfgHref.substring(rivCfgHref.indexOf("#") + 1);
			String scarCfgFrag = scarCfgHref.substring(scarCfgHref.indexOf("#") + 1);

			rivCfgToScarCfg.put(rivCfgFrag, scarCfgFrag);

			String scId = findScIdInScar(scarDoc, scarCfgFrag);
			scarCfgToScCfg.put(scarCfgFrag, scId);
		}
	}

	private String findScIdInScar(Document scarDoc, String scarFrag) {

		String rules;
		String id;
		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			rules = "ruleIds";
			id = "id";
		} else {
			rules = "entryPoints";
			id = "tag";
		}

		if (!scarFrag.startsWith("//@" + rules))
			return null;

		int idx;
		try {
			String idxStr = scarFrag.substring(scarFrag.lastIndexOf('.') + 1);
			idx = Integer.parseInt(idxStr);
		} catch (NumberFormatException e) {
			return null;
		}

		NodeList ruleIds = scarDoc.getElementsByTagName(rules);
		if (idx < 0 || idx >= ruleIds.getLength())
			return null;

		Element rule = (Element) ruleIds.item(idx);
		return rule.getAttribute(id);
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
