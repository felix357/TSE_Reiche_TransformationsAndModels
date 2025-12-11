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
 * IC7(T)(M) checker checks if Configurations exist in the Source Code Analysis
 * Result corresponding to Configurations of the Annotated Source Code affected
 * by IC2.
 */
public class IC7MChecker implements IChecker {

	private final IC2MChecker ic2;
	private final String correspondencesPath;
	private final String scarPath;
	private final Set<String> foundCfgR = new HashSet<>();

	public IC7MChecker(SystemConfig cfg, IC2MChecker ic2MChecker) {
		this.ic2 = ic2MChecker;

		this.correspondencesPath = cfg.basePath + File.separator + cfg.correspondencesCodeqlScar;

		this.scarPath = cfg.basePath + File.separator + cfg.codeqlScarModel;
	}

	@Override
	public boolean runCheck() {
		try {
			Set<String> cfgCsC = ic2.getConfigsRefsC();
			if (cfgCsC == null || cfgCsC.isEmpty()) {
				System.out.println(
						"IC7: IC2 hat keine Code-Konfigurationen geliefert (CFG_cs_C ist leer). Prüfe IC2 zuerst.");
				return false;
			}

			Map<String, String> codeqlToScar = loadConfigurationCorrespondences();
			if (codeqlToScar.isEmpty()) {
				System.out.println("IC7: Keine <configurationCorrespondences> in der Correspondence-Datei gefunden.");
				return false;
			}

			boolean anyFound = false;

			for (String codeqlHref : cfgCsC) {
				if (codeqlHref == null || codeqlHref.trim().isEmpty()) {
					System.out.println("IC7: Ignoriere leere CodeQL-Konfigurationsreferenz.");
					continue;
				}

				// Find SCAR href via correspondences
				if (!codeqlToScar.containsKey(codeqlHref)) {
					System.out.println(
							"IC7 Warnung: Keine SCAR-Korrespondenz für CodeQL-Konfiguration gefunden: " + codeqlHref);
					continue;
				}

				String scarHref = codeqlToScar.get(codeqlHref);
				if (scarHref == null || scarHref.trim().isEmpty()) {
					System.out.println("IC7 Warnung: SCAR href leer für codeql href: " + codeqlHref);
					continue;
				}

				// Resolve SCAR href in scar.file
				String scarResolvedId = ic2.resolveCodeqlReference(scarHref, "scar.codeqlscar", scarPath);
				if (scarResolvedId != null) {
					anyFound = true;
					foundCfgR.add(scarResolvedId);
					System.out.println("IC7 Mapping gefunden: CodeQL href = '" + codeqlHref + "' ↔ SCAR id = '"
							+ scarResolvedId + "'");
				} else {
					System.out.println("IC7 Fehler: SCAR-Konfiguration '" + scarHref + "' konnte in '" + scarPath
							+ "' nicht aufgelöst werden.");
				}
			}

			// Final condition: CFG_cs_R != ∅
			if (!anyFound) {
				System.out.println("IC7(T)(M) NICHT erfüllt ❌ — kein cfg_R gefunden (CFG_cs_R ist leer).");
				return false;
			} else {
				System.out.println("IC7(T)(M) erfüllt ✅ — Gefundene cfg_R: " + foundCfgR);
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

		NodeList cfgNodes = doc.getElementsByTagName("configurationCorrespondences");
		for (int i = 0; i < cfgNodes.getLength(); i++) {
			Element corr = (Element) cfgNodes.item(i);
			String codeqlHref = getHref(corr, "configuration_CodeQL");
			String scarHref = getHref(corr, "configuration_SCAR");
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
