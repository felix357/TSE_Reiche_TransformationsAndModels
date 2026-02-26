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
 * IC7(T)(M) checker checks if Configurations exist in the Source Code Analysis
 * Result corresponding to Configurations of the Annotated Source Code affected
 * by IC2.
 */
public class IC7MChecker implements IChecker {

	private final AnalysisCouplingType analysisType;
	
	private final IC2MChecker ic2;
	private final String correspondencesPath;
	private final String scarPath;
	private final Set<String> foundCfgR = new HashSet<>();

	public IC7MChecker(SystemConfig cfg, IC2MChecker ic2MChecker) {
		this.ic2 = ic2MChecker;

		this.correspondencesPath = cfg.basePath + File.separator + cfg.correspondencesSCScar;

		this.scarPath = cfg.basePath + File.separator + cfg.scScarModel;
		
		this.analysisType = cfg.analysisCouplingType;
	}

	@Override
	public boolean runCheck() {
		try {
			Set<String> cfgCsC = ic2.getConfigsRefsC();
			if (cfgCsC == null || cfgCsC.isEmpty()) {
				System.out.println(
						"IC7: IC2 hat keine Code-Konfigurationen geliefert. Prüfe IC2 zuerst.");
				return false;
			}

			Map<String, String> scToScar = loadConfigurationCorrespondences();
			if (scToScar.isEmpty()) {
				System.out.println("IC7: Keine <configurationCorrespondences> in der Correspondence-Datei gefunden.");
				return false;
			}

			boolean anyFound = false;

			for (String scHref : cfgCsC) {
				if (scHref == null || scHref.trim().isEmpty()) {
					System.out.println("IC7: Ignoriere leere CodeQL-Konfigurationsreferenz.");
					continue;
				}

				if (!scToScar.containsKey(scHref)) {
					System.out.println(
							"IC7 Warnung: Keine SCAR-Korrespondenz für CodeQL-Konfiguration gefunden: " + scHref);
					continue;
				}

				String scarHref = scToScar.get(scHref);
				if (scarHref == null || scarHref.trim().isEmpty()) {
					System.out.println("IC7 Warnung: SCAR href leer für codeql href: " + scHref);
					continue;
				}

				String scarResolvedId;
				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {					
					scarResolvedId = ic2.resolveSCReference(scarHref, "scar.codeqlscar", scarPath);
				} else {
					scarResolvedId = ic2.resolveSCReference(scarHref, "scar.joanascar", scarPath);
				}
				
				if (scarResolvedId != null) {
					anyFound = true;
					foundCfgR.add(scarResolvedId);
					System.out.println("IC7 Mapping gefunden: CodeQL href = '" + scHref + "' ↔ SCAR id = '"
							+ scarResolvedId + "'");
				} else {
					System.out.println("IC7 Fehler: SCAR-Konfiguration '" + scarHref + "' konnte in '" + scarPath
							+ "' nicht aufgelöst werden.");
				}
			}

			if (!anyFound) {
				System.out.println("IC7(T)(M) NICHT erfüllt — kein config gefunden.");
				return false;
			} else {
				System.out.println("IC7(T)(M) erfüllt — Gefundene config: " + foundCfgR);
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Reads correspondences file and builds a map: CodeQL href -> SCAR href
	 */
	private Map<String, String> loadConfigurationCorrespondences() throws Exception {
		Map<String, String> map = new HashMap<>();

		File f = new File(correspondencesPath);
		if (!f.exists()) {
			throw new IllegalStateException("Correspondence-Datei nicht gefunden: " + correspondencesPath);
		}

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(f)));
		
		String configCorrespondence = "entryPointCorrespondences";
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
			String codeqlHref = getHref(corr, scConfig);
			String scarHref = getHref(corr, scarConfig);
			if (codeqlHref != null && scarHref != null) {
				map.put(codeqlHref, scarHref);
			}
		}
		return map;
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

	public Set<String> getFoundCfgR() {
		return new HashSet<>(foundCfgR);
	}
}
