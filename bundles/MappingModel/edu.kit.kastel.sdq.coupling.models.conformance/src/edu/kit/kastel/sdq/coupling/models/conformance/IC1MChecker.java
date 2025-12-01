package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.kit.kastel.sdq.coupling.models.conformance.ConformanceUtils.Mapping;

/**
 * Checker for IC1(T)(M): ensures that each security characteristic in the
 * architectural model has a corresponding annotation in the source code.
 */
public class IC1MChecker implements IChecker {

	private static final List<Mapping> mappings = new ArrayList<>();
	private static final Set<String> mappedEdfaLiterals = new HashSet<>();
	private static final Set<String> allCodeqlLevels = new HashSet<>();
	private static Set<String> allSecurityLiterals = new HashSet<>();

	private final String architecturalModelPath;
	private final String correspondencePath;
	private final String sourceCodeAnalysisPath;

	/**
	 * Constructs a checker for IC1(T)(M) using the given model and analysis paths.
	 */	
	public IC1MChecker(SystemConfig cfg) {
	    this.architecturalModelPath = cfg.basePath + "/" + cfg.pddc;
	    this.correspondencePath = cfg.basePath + "/" + cfg.modelCorrespondence;
	    this.sourceCodeAnalysisPath = cfg.basePath + "/" + cfg.codeql;
	}

	/**
	 * Runs the IC1(T)(M) check.
	 *
	 * @return true if IC1(T)(M) is fulfilled, false otherwise
	 * @throws Exception on parsing errors
	 */
	public boolean runCheck() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Step 1: Load security literals from the architectural model
			allSecurityLiterals = ConformanceUtils.getSecurityCharacteristicLiterals(architecturalModelPath);
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

				String edfaValue = ConformanceUtils.resolvePddcReference(edfaHref, architecturalModelPath);
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
}