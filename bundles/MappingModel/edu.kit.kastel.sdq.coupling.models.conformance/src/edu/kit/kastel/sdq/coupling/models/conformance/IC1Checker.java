package edu.kit.kastel.sdq.coupling.models.conformance;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Checker for IC1(T)(M): ensures that each security characteristic in the
 * architectural model has a corresponding annotation in the source code.
 */
public class IC1Checker {

	private static final List<Mapping> mappings = new ArrayList<>();
	private static final Set<String> mappedEdfaLiterals = new HashSet<>();
	private static final Set<String> allCodeqlLevels = new HashSet<>();
	private static Set<String> allSecurityLiterals = new HashSet<>();
	private static final Pattern enumUsagePattern = Pattern
			.compile("enumCharacteristicType\\s+(DataClassification|Role|AssignedRoles)\\s+using\\s+(\\w+)");

	private final String architecturalModelPath;
	private final String correspondencePath;
	private final String sourceCodeAnalysisPath;

	/**
	 * Constructs a checker for IC1(T)(M) using the given model and analysis paths.
	 *
	 * @param basePath               Base directory path
	 * @param architectureModelName  PDDC file name
	 * @param correspondenceName     Correspondence file name
	 * @param sourceCodeAnalysisName CodeQL source analysis file name
	 */
	public IC1Checker(String basePath, String architectureModelName, String correspondenceName,
			String sourceCodeAnalysisName) {
		this.architecturalModelPath = basePath + File.separator + architectureModelName;
		this.correspondencePath = basePath + File.separator + correspondenceName;
		this.sourceCodeAnalysisPath = basePath + File.separator + sourceCodeAnalysisName;
	}

	/**
	 * Runs the IC1(T)(M) check.
	 *
	 * @return true if IC1(T)(M) is fulfilled, false otherwise
	 * @throws Exception on parsing errors
	 */
	public boolean runCheck() throws Exception {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Step 1: Load security literals from the architectural model
			allSecurityLiterals = getSecurityCharacteristicLiterals(architecturalModelPath);
			System.out.println("Sicherheitsrelevante Literale im architektonischen Modell: " + allSecurityLiterals);

			// Step 2: Load all applied security levels from source code
			allCodeqlLevels.addAll(getAllCodeqlLevels(sourceCodeAnalysisPath));
			System.out.println("Alle CodeQL-Sicherheitslevels: " + allCodeqlLevels);

			// Step 3: Load correspondence file and process mappings
			File correspondenceFile = new File(correspondencePath);
			if (!correspondenceFile.exists()) {
				System.out.println("Fehler: Korrespondenz-Datei nicht gefunden unter dem Pfad: " + correspondencePath);
				return false;
			}

			Document doc = db.parse(new InputSource(new FileInputStream(correspondenceFile)));
			NodeList literalCorrespondences = doc.getElementsByTagName("literalSecurityLevelCorrespondences");

			System.out.println("\nAnzahl der gefundenen Korrespondenz-Einträge: " + literalCorrespondences.getLength());

			for (int i = 0; i < literalCorrespondences.getLength(); i++) {
				Element correspondence = (Element) literalCorrespondences.item(i);
				Element securityLevelCodeQL = (Element) correspondence.getElementsByTagName("securityLevel_CodeQL")
						.item(0);
				Element literalsEDFA = (Element) correspondence.getElementsByTagName("literals_EDFA").item(0);

				String codeqlHref = securityLevelCodeQL.getAttribute("href");
				String edfaHref = literalsEDFA.getAttribute("href");

				String edfaValue = resolvePddcReference(edfaHref, architecturalModelPath);
				String codeqlValue = resolveCodeqlReference(codeqlHref);

				// Only store mappings that relate to a security characteristic
				if (edfaValue != null && codeqlValue != null && allSecurityLiterals.contains(edfaValue)) {
					Mapping mapping = new Mapping(edfaValue, codeqlValue);
					mappings.add(mapping);
					mappedEdfaLiterals.add(edfaValue);
					System.out.println("Mapping gefunden: " + mapping);
				}
			}

			// Step 4: Check IC1 conditions
			boolean allSecurityLiteralsMapped = mappedEdfaLiterals.containsAll(allSecurityLiterals);
			boolean codeqlLevelsExist = !allCodeqlLevels.isEmpty();

			boolean ic1Fulfilled = allSecurityLiteralsMapped && codeqlLevelsExist;

			if (ic1Fulfilled) {
				System.out.println("Die Bedingung IC1(T)(M) ist ERFÜLLT. ✅");
				System.out.println(
						"Alle sicherheitsrelevanten Literale im architektonischen Modell haben eine gültige Korrespondenz im Code.");
				System.out.println("Die Menge der angewandten Sicherheitslevels im Code ist nicht leer.");
			} else {
				System.out.println("Die Bedingung IC1(T)(M) ist NICHT ERFÜLLT. ❌");
				if (!allSecurityLiteralsMapped) {
					Set<String> unmappedLiterals = new HashSet<>(allSecurityLiterals);
					unmappedLiterals.removeAll(mappedEdfaLiterals);
					System.out.println("Fehler: Nicht alle sicherheitsrelevanten Literale haben ein Mapping. Fehlende: "
							+ unmappedLiterals);
				}
				if (!codeqlLevelsExist) {
					System.out.println("Fehler: Die Menge der angewandten Sicherheitslevels im Code ist leer.");
				}
			}

			return ic1Fulfilled;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Extracts all security literals from the architectural PDDC model.
	 *
	 * @param filePath Path to PDDC file
	 * @return Set of literal names
	 * @throws Exception on file read errors
	 */
	private Set<String> getSecurityCharacteristicLiterals(String filePath) throws Exception {
		Set<String> literals = new HashSet<>();
		List<String> lines = Files.readAllLines(Paths.get(filePath));

		String relevantEnumName = null;
		for (String line : lines) {
			Matcher matcher = enumUsagePattern.matcher(line);
			if (matcher.find()) {
				relevantEnumName = matcher.group(2);
			}
		}

		if (relevantEnumName != null) {
			boolean inRelevantEnum = false;
			for (String line : lines) {
				String trimmedLine = line.trim();
				if (trimmedLine.startsWith("enum " + relevantEnumName)) {
					inRelevantEnum = true;
				} else if (inRelevantEnum && trimmedLine.equals("}")) {
					inRelevantEnum = false;
				} else if (inRelevantEnum && !trimmedLine.isEmpty() && !trimmedLine.equals("{")) {
					literals.add(trimmedLine.replaceAll("\\s|\\{", ""));
				}
			}
		}
		return literals;
	}

	/**
	 * Extracts all applied security levels from the CodeQL analysis report.
	 *
	 * @param filePath Path to CodeQL XML file
	 * @return Set of level names
	 * @throws Exception on XML parse errors
	 */
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

	/**
	 * Resolves a PDDC reference href to the actual literal name.
	 *
	 * @param href                   Reference string
	 * @param architecturalModelPath Path to PDDC file
	 * @return Literal name or null
	 * @throws Exception on parsing errors
	 */
	public String resolvePddcReference(String href, String architecturalModelPath) throws Exception {
		// Step 1: Extract the file name from the file path
		String fileName = new File(architecturalModelPath).getName();

		// Step 2: Extract the internal path after the file name
		int index = href.indexOf(fileName + "#");
		if (index == -1) {
			throw new IllegalArgumentException("Href enthält nicht den erwarteten Dateinamen: " + href);
		}

		String internalPath = href.substring(index + (fileName + "#").length());

		String[] parts = internalPath.split("-characteristicEnumerations");

		// Step 3: Process the internal path
		String[] tokens = parts[1].split("\\.");

		int enumIndex = Integer.parseInt(tokens[0].split("@")[1]);
		int literalIndex = Integer.parseInt(tokens[1].split("@")[1]);

		List<String> lines = Files.readAllLines(Paths.get(architecturalModelPath));
		boolean inEnum = false;
		int currentEnumIndex = -1;
		int currentLiteralIndex = -1;

		for (String line : lines) {
			String trimmedLine = line.trim();
			if (trimmedLine.startsWith("enum ")) {
				currentEnumIndex++;
				inEnum = true;
				currentLiteralIndex = -1;
			} else if (trimmedLine.equals("}")) {
				inEnum = false;
			} else if (inEnum && currentEnumIndex == enumIndex) {
				if (!trimmedLine.isEmpty() && !trimmedLine.equals("{")) {
					currentLiteralIndex++;
					if (currentLiteralIndex == literalIndex) {
						return trimmedLine.replaceAll("\\s|\\{", "");
					}
				}
			}
		}
		return null;
	}

	/**
	 * Resolves a CodeQL reference href to the literal name.
	 *
	 * @param href Reference string
	 * @return Literal name or null
	 * @throws Exception on XML parse errors
	 */
	String resolveCodeqlReference(String href) throws Exception {
		String path = href.substring(
				href.indexOf("codeql4extendeddataflow.codeql#") + "codeql4extendeddataflow.codeql#".length());
		String[] parts = path.split("/");

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new InputSource(new FileInputStream(new File(sourceCodeAnalysisPath))));

		Node current = doc.getDocumentElement();
		for (String part : parts) {
			if (part.startsWith("@")) {
				String[] tokens = part.substring(1).split("\\.");
				String tagName = tokens[0];
				int index = Integer.parseInt(tokens[1]);
				NodeList nodes = ((Element) current).getElementsByTagName(tagName);
				if (index < nodes.getLength()) {
					current = nodes.item(index);
				} else {
					return null;
				}
			}
		}
		if (current != null && current.getNodeType() == Node.ELEMENT_NODE) {
			return ((Element) current).getAttribute("name");
		}
		return null;
	}

	public Set<String> getMappedEdfaLiterals() {
		return mappedEdfaLiterals;
	}

	public Set<String> getAllSecurityLiterals() {
		return allSecurityLiterals;
	}

	public Set<String> getAllCodeqlLevels() {
		return allCodeqlLevels;
	}

	/**
	 * Represents a correspondence between a security characteristic literal in the
	 * architectural model and its counterpart in the source code.
	 */
	private class Mapping {
		private final String edfaValue;
		private final String codeqlValue;

		public Mapping(String edfaValue, String codeqlValue) {
			this.edfaValue = edfaValue;
			this.codeqlValue = codeqlValue;
		}

		@Override
		public String toString() {
			return "EDFA='" + edfaValue + "' <-> CodeQL='" + codeqlValue + "'";
		}
	}
}