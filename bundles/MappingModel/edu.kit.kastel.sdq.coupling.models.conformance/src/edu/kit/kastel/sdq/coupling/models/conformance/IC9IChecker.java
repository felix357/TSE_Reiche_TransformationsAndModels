package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Checker for IC9(T)(I): Check if all RIV instances reference security
 * characteristics that have a valid correspondence to source code security
 * characteristics (IC1).
 */
public class IC9IChecker implements IChecker {

	private final String rivModelPath;
	private final IC1IChecker ic1Checker;
	private final Set<String> invalidRIVs = new HashSet<>();

	public IC9IChecker(SystemConfig cfg, IC1IChecker ic1Checker) {
		this.rivModelPath = cfg.basePath + "/" + cfg.riv;
		this.ic1Checker = ic1Checker;
	}

	@Override
	public boolean runCheck() {
		boolean allValid = true;
		boolean anyMapped = false;

		try {
			File file = new File(rivModelPath);
			if (!file.exists()) {
				System.out.println("IC9(T)(I): RIV model file not found: " + rivModelPath);
				return false;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new FileInputStream(file)));

			// Map RIV security level IDs to names
			Map<String, String> rivSecIdToName = new HashMap<>();
			NodeList secNodes = doc.getElementsByTagName("securityLevel");
			for (int i = 0; i < secNodes.getLength(); i++) {
				Element sec = (Element) secNodes.item(i);
				String id = "//@securityLevel." + i;
				String name = sec.getAttribute("name");
				rivSecIdToName.put(id, name);
			}

			// Iterate over RIV entries
			NodeList rivNodes = doc.getElementsByTagName("resultingValues");
			Set<String> mappedSecurityLevels = new HashSet<>();

			for (int i = 0; i < rivNodes.getLength(); i++) {
				Element riv = (Element) rivNodes.item(i);
				String rivSecLevelRef = riv.getAttribute("resultingSecurityLevel"); // e.g., //@securityLevel.0
				String secLevelName = rivSecIdToName.get(rivSecLevelRef);

				if (secLevelName == null) {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " has no security level mapping.");
					continue;
				}

				String[] levels = secLevelName.split(";");
				boolean rivHasMapped = false;

				for (String level : levels) {
					level = level.trim();
					// Check correspondence to IC1 mappings
					boolean hasMapping = ic1Checker.getCodeqlRivMap().values().stream()
							.anyMatch(rivHref -> rivHref.endsWith(rivSecLevelRef));
					System.out.println(ic1Checker.getRivValuesMap().values());
					if (hasMapping && (ic1Checker.getRivValuesMap().containsValue(level)
							|| ic1Checker.getRivValuesMap().containsValue(secLevelName))) {
						rivHasMapped = true;
						mappedSecurityLevels.add(level);
					}
				}

				if (!rivHasMapped) {
					allValid = false;
					invalidRIVs.add("RIV index " + i + " contains security levels with no valid correspondence: "
							+ secLevelName);
				} else {
					anyMapped = true; // At least one RIV security level mapped => IC9.1
				}
			}

			// IC9.1(T)(I) check
			if (!anyMapped) {
				allValid = false;
				System.out.println("IC9.1(T)(I) violated: No RIV references a valid security characteristic.");
			}

			// Output results
			if (allValid) {
				System.out.println("IC9(T)(I) satisfied ✅ — all RIVs reference valid security characteristics.");
			} else {
				System.out.println("IC9(T)(I) NOT satisfied ❌ — invalid RIVs found:");
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
