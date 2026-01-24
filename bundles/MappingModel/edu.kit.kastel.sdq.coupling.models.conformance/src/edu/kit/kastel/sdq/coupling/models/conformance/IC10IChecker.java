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
 * IC10IChecker checks if security characteristics in Resolved Implementation
 * Values (RIVs) are correctly referenced in corresponding configurations in the
 * SCAR and CodeQL models on the instance level.
 */
public class IC10IChecker implements IChecker {

	private final String rivPath;
	private final String correspondencesPath;
	private final String codeqlPath;
	private final String scarPath;

	private final IC8IChecker ic8Checker;
	private final IC9IChecker ic9Checker;

	private final Set<String> invalidRIVs = new HashSet<>();

	private Map<String, Set<String>> rivSecToCodeqlSec = new HashMap<>();
	private Map<String, String> rivParamToCodeqlParam = new HashMap<>();
	private Map<String, String> rivCfgToScarCfg = new HashMap<>();
	private Map<String, String> scarCfgToCodeqlCfg = new HashMap<>();

	private Document scarDoc;
	private Document rDoc;
	private Document codeqlDoc;

	private final AnalysisCouplingType analysisType;

	public IC10IChecker(SystemConfig cfg, IC8IChecker ic8Checker, IC9IChecker ic9Checker) {
		this.rivPath = cfg.basePath + File.separator + cfg.riv;
		this.correspondencesPath = cfg.basePath + File.separator + cfg.rivCorrespondence;
		this.codeqlPath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;

		this.ic8Checker = ic8Checker;
		this.ic9Checker = ic9Checker;

		this.analysisType = cfg.analysisCouplingType;
		
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

				Set<String> mappedCodeqlSecs = rivSecToCodeqlSec.get(secRef);
				String scarCfgFrag = rivCfgToScarCfg.get(cfgRef);
				String mappedCodeqlCfg = scarCfgToCodeqlCfg.get(scarCfgFrag);
				String mappedCodeqlParam = rivParamToCodeqlParam.get(paramRef);

				if (mappedCodeqlSecs == null || mappedCodeqlCfg == null || mappedCodeqlParam == null) {
					allValid = false;
					invalidRIVs.add(
						"RIV index " + i + " mapping missing: cfg=" + cfgRef +
						", sec=" + secRef + ", param=" + paramRef
					);
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

				NodeList queryNodes = codeqlDoc.getElementsByTagName(queries);
				for (int q = 0; q < queryNodes.getLength(); q++) {
					Element query = (Element) queryNodes.item(q);
					String queryId = query.getAttribute("id");

					if (!mappedCodeqlCfg.equals(queryId))
						continue;

					NodeList annotations = query.getElementsByTagName(annotation);
					for (int j = 0; j < annotations.getLength(); j++) {
						Element ann = (Element) annotations.item(j);
						String annSec = ann.getAttribute(lvl);

						if (!mappedCodeqlSecs.contains(annSec))
							continue;

						found = true;
						break;
					}
					if (found)
						break;
				}

				if (!found) {
					allValid = false;
					invalidRIVs.add(
						"RIV index " + i + " violates IC10(C)(I): security characteristic " +
						secRef + " not referenced in configuration " + cfgRef
					);
				}
			}

			if (allValid) {
				System.out.println(
					"IC10(C)(I) satisfied. All RIVs reference security characteristics inside correct configurations."
				);
			} else {
				System.out.println("IC10(C)(I) NOT satisfied. Violations found:");
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

			String rivHref =
				((Element) corr.getElementsByTagName(lRiv).item(0))
					.getAttribute("href");
			String codeqlHref =
				((Element) corr.getElementsByTagName(secLvl).item(0))
					.getAttribute("href");

			String rivKey = rivHref.substring(rivHref.indexOf("#") + 1);
			String codeqlVal = codeqlHref.substring(codeqlHref.indexOf("#") + 1);

			rivSecToCodeqlSec
				.computeIfAbsent(rivKey, k -> new HashSet<>())
				.add(codeqlVal);
		}
	}

	private void loadParameterCorrespondences(Document corrDoc) {
		NodeList nodes = corrDoc.getElementsByTagName("parameterCorrespondences");
		for (int i = 0; i < nodes.getLength(); i++) {
			Element corr = (Element) nodes.item(i);
			String rivParam =
				((Element) corr.getElementsByTagName("parameter_ResolvedImplementationValues").item(0))
					.getAttribute("href");
			String codeqlParam =
				((Element) corr.getElementsByTagName("parameter_SCAR").item(0))
					.getAttribute("href");

			rivParamToCodeqlParam.put(
				rivParam.substring(rivParam.indexOf("#") + 1),
				codeqlParam.substring(codeqlParam.indexOf("#") + 1)
			);
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
			String rivCfgHref =
				((Element) corr.getElementsByTagName(cfgRiv).item(0))
					.getAttribute("href");
			String scarCfgHref =
				((Element) corr.getElementsByTagName(cfgScar).item(0))
					.getAttribute("href");

			String rivCfgFrag = rivCfgHref.substring(rivCfgHref.indexOf("#") + 1);
			String scarCfgFrag = scarCfgHref.substring(scarCfgHref.indexOf("#") + 1);

			rivCfgToScarCfg.put(rivCfgFrag, scarCfgFrag);

			String codeqlId = findCodeqlIdInScar(scarDoc, scarCfgFrag);
			scarCfgToCodeqlCfg.put(scarCfgFrag, codeqlId);
		}
	}

	private String findCodeqlIdInScar(Document scarDoc, String scarFrag) {
		
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
