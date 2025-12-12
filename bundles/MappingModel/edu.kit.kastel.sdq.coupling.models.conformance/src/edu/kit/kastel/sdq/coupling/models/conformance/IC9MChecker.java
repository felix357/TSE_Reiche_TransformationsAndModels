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
 * IC9(T)(M) Checker: Checks if all ResolvedImplementationValues reference valid
 * security characteristics from IC1.
 */
public class IC9MChecker implements IChecker {

	private final String rivModelPath;
	private final Set<String> validIC1SecurityLevels;

	private final Set<String> invalidRIVs = new HashSet<>();

	public IC9MChecker(SystemConfig cfg, IC1MChecker checker1) {
		this.rivModelPath = cfg.basePath + File.separator + cfg.riv;
		this.validIC1SecurityLevels = checker1.getAllSecurityLiterals();
	}

	@Override
	public boolean runCheck() {
		boolean allValid = true;
		try {
			File file = new File(rivModelPath);
			if (!file.exists()) {
				System.out.println("IC9(T)(M): RIV model file not found: " + rivModelPath);
				return false;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new FileInputStream(file)));

			// Map RIV security level IDs to their names
			Map<String, String> rivSecIdToName = new HashMap<>();
			NodeList secNodes = doc.getElementsByTagName("securityLevel");
			for (int i = 0; i < secNodes.getLength(); i++) {
				Element sec = (Element) secNodes.item(i);
				String id = "//@securityLevel." + i; // matches the resultingValues attribute
				String name = sec.getAttribute("name");
				rivSecIdToName.put(id, name);
			}

			// Iterate over RIV entries
			NodeList rivNodes = doc.getElementsByTagName("resultingValues");
			boolean anyValidSecLevel = false;

			for (int i = 0; i < rivNodes.getLength(); i++) {
				Element riv = (Element) rivNodes.item(i);
				String rivSecLevelRef = riv.getAttribute("resultingSecurityLevel"); // e.g., //@securityLevel.0

				// Map RIV ID to the actual security level name
				String secLevelName = rivSecIdToName.get(rivSecLevelRef);

				boolean valid = false;
				if (secLevelName != null) {
					// Split by semicolon if multiple security levels exist
					String[] secLevels = secLevelName.contains(";") ? secLevelName.split(";")
							: new String[] { secLevelName };

					// Check if any of the levels exist in valid IC1 security levels
					for (String level : secLevels) {
						if (validIC1SecurityLevels.contains(level.trim())) {
							valid = true;
							break;
						}
					}
				}

				if (valid) {
					anyValidSecLevel = true;
				} else {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " invalid: securityLevel=" + secLevelName);
				}
			}

			// check IC9.1(T)(M)
			if (!anyValidSecLevel) {
				allValid = false;
				System.out.println("IC9.1(T)(M) violated: No RIV references a valid security characteristic.");
			}

			if (allValid) {
				System.out.println("IC9(T)(M) satisfied ✅ — all RIVs reference valid security characteristics.");
			} else {
				System.out.println("IC9(T)(M) NOT satisfied ❌ — invalid RIVs found:");
				invalidRIVs.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return allValid;
	}

	public Set<String> getInvalidRIVs() {
		return new HashSet<>(invalidRIVs);
	}
}
