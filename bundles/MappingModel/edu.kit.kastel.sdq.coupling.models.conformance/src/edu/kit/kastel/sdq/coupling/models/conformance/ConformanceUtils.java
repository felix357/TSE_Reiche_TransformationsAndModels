package edu.kit.kastel.sdq.coupling.models.conformance;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for common XML parsing and PDDC/CodeQL reference resolution.
 * This could be extended to hold more common parsing logic.
 */
public class ConformanceUtils {

	private static final Pattern enumUsagePattern = Pattern
			.compile("enumCharacteristicType\\s+(DataClassification|Role|AssignedRoles)\\s+using\\s+(\\w+)");

	private ConformanceUtils() {
	}

	public static Document parseXmlFile(String filePath)
			throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new IOException("Datei nicht gefunden unter: " + filePath);
		}
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		docBuilderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		return docBuilder.parse(new InputSource(new FileInputStream(file)));
	}

	public static String getAttributeFromElement(Element parent, String tagName, String attributeName) {
		NodeList nodes = parent.getElementsByTagName(tagName);
		if (nodes.getLength() > 0) {
			return ((Element) nodes.item(0)).getAttribute(attributeName);
		}
		return null;
	}

	public static Set<String> getSecurityCharacteristicLiterals(String filePath) throws IOException {
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

	public static String resolvePddcReference(String href, String architecturalModelPath) throws Exception {
		String fileName = new File(architecturalModelPath).getName();
		int index = href.indexOf(fileName + "#");
		if (index == -1)
			throw new IllegalArgumentException("Href enthält nicht den erwarteten Dateinamen: " + href);

		String internalPath = href.substring(index + (fileName + "#").length());
		String[] parts = internalPath.split("-characteristicEnumerations");
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

	public static class Mapping {
		private final String edfaValue;
		private final String codeqlValue;

		public Mapping(String edfaValue, String codeqlValue) {
			this.edfaValue = edfaValue;
			this.codeqlValue = codeqlValue;
		}

		public String getEdfaValue() {
			return edfaValue;
		}

		public String getCodeqlValue() {
			return codeqlValue;
		}

		@Override
		public String toString() {
			return "EDFA='" + edfaValue + "' <-> CodeQL='" + codeqlValue + "'";
		}
	}
}
