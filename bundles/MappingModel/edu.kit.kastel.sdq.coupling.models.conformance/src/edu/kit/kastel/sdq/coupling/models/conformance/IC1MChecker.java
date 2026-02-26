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
import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC1(T)(M): ensures that each security characteristic in the
 * architectural model has a corresponding annotation in the source code.
 */
public class IC1MChecker implements IChecker {

	private static final List<Mapping> mappings = new ArrayList<>();
	private static final Set<String> mappedEdfaLiterals = new HashSet<>();
	private static final Set<String> allSourceCodeAnalysisLevels = new HashSet<>();
	private static Set<String> allSecurityLiterals = new HashSet<>();

	private final String architecturalModelPath;
	private final String correspondencePath;
	private final String sourceCodeAnalysisPath;
	private final AnalysisCouplingType analysisType;

	/**
	 * Constructs a checker for IC1(T)(M) using the given model and analysis paths.
	 */
	public IC1MChecker(SystemConfig cfg) {
		this.architecturalModelPath = cfg.basePath + "/" + cfg.pddc;
		this.correspondencePath = cfg.basePath + "/" + cfg.modelCorrespondence;
		this.sourceCodeAnalysisPath = cfg.basePath + "/" + cfg.sourceCodeAnalysis;
		this.analysisType = cfg.analysisCouplingType;
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

			// Load security literals from the architectural model
			allSecurityLiterals = ConformanceUtils.getSecurityCharacteristicLiterals(architecturalModelPath);
			System.out.println("Sicherheitsrelevante Literale im architektonischen Modell: " + allSecurityLiterals);

			// Load all security levels from source code analysis
			Document analysisDoc = db.parse(new InputSource(new FileInputStream(new File(sourceCodeAnalysisPath))));
			allSourceCodeAnalysisLevels.addAll(extractAnalysisLevels(analysisDoc));
			System.out.println("Alle SourceCodeaanalysis-Sicherheitslevels: " + allSourceCodeAnalysisLevels);

			// Load correspondence file and process mappings
			File correspondenceFile = new File(correspondencePath);
			if (!correspondenceFile.exists()) {
				System.out.println("Fehler: Korrespondenz-Datei nicht gefunden unter dem Pfad: " + correspondencePath);
				return false;
			}

			Document doc = db.parse(new InputSource(new FileInputStream(correspondenceFile)));

			processCorrespondences(doc, analysisDoc);

			// Validate IC1(M)
			return validateResults();

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

	private void processCorrespondences(Document corrDoc, Document analysisDoc) throws Exception {

		String wrapperTag = (analysisType == AnalysisCouplingType.JOANAEDFA) ? "literalLevelCorrespondences"
				: "literalSecurityLevelCorrespondences";

		String codeSideTag = (analysisType == AnalysisCouplingType.JOANAEDFA) ? "level_JOANA" : "securityLevel_CodeQL";

		NodeList correspondences = corrDoc.getElementsByTagName(wrapperTag);

		for (int i = 0; i < correspondences.getLength(); i++) {
			Element corr = (Element) correspondences.item(i);

			Element edfaElem = (Element) corr.getElementsByTagName("literals_EDFA").item(0);
			Element codeElem = (Element) corr.getElementsByTagName(codeSideTag).item(0);

			String edfaValue = ConformanceUtils.resolvePddcReference(edfaElem.getAttribute("href"),
					architecturalModelPath);

			String codeValue = (analysisType == AnalysisCouplingType.JOANAEDFA)
					? resolveJoanaReference(codeElem.getAttribute("href"), analysisDoc)
					: resolveCodeqlReference(codeElem.getAttribute("href"), analysisDoc);

			if (edfaValue != null && codeValue != null && allSecurityLiterals.contains(edfaValue)) {

				Mapping mapping = new Mapping(edfaValue, codeValue);
				mappings.add(mapping);
				mappedEdfaLiterals.add(edfaValue);

				System.out.println("Mapping gefunden: EDFA='" + edfaValue + "' <-> "
						+ (analysisType == AnalysisCouplingType.JOANAEDFA ? "JOANA" : "CodeQL") + "='" + codeValue
						+ "'");
			}
		}
	}

	private String resolveJoanaReference(String href, Document doc) {
		try {
			String fragment = href.split("#")[1];
			String[] parts = fragment.split("/");

			int entryIdx = Integer.parseInt(parts[2].replaceAll("\\D", ""));
			int levelIdx = Integer.parseInt(parts[3].replaceAll("\\D", ""));

			Element entry = (Element) doc.getElementsByTagName("entrypoint").item(entryIdx);
			Element level = (Element) entry.getElementsByTagName("level").item(levelIdx);

			return level.getAttribute("name");

		} catch (Exception e) {
			return null;
		}
	}

	private String resolveCodeqlReference(String href, Document doc) {
		try {
			String fragment = href.substring(
					href.indexOf("codeql4extendeddataflow.codeql#") + "codeql4extendeddataflow.codeql#".length());

			String[] parts = fragment.split("/");
			Node current = doc.getDocumentElement();

			for (String part : parts) {
				if (part.startsWith("@")) {
					String[] tokens = part.substring(1).split("\\.");
					String tagName = tokens[0];
					int index = Integer.parseInt(tokens[1]);

					NodeList nodes = ((Element) current).getElementsByTagName(tagName);

					if (index >= nodes.getLength()) {
						return null;
					}
					current = nodes.item(index);
				}
			}

			return (current instanceof Element) ? ((Element) current).getAttribute("name") : null;

		} catch (Exception e) {
			return null;
		}
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

	private Set<String> extractAnalysisLevels(Document doc) {
		Set<String> levels = new HashSet<>();
		String tag = (analysisType == AnalysisCouplingType.JOANAEDFA) ? "level" : "appliedSecurityLevel";

		NodeList nodes = doc.getElementsByTagName(tag);
		for (int i = 0; i < nodes.getLength(); i++) {
			levels.add(((Element) nodes.item(i)).getAttribute("name"));
		}
		return levels;
	}

	private boolean validateResults() {

		boolean allSecurityLiteralsMapped = mappedEdfaLiterals.containsAll(allSecurityLiterals);

		boolean analysisLevelsExist = !allSourceCodeAnalysisLevels.isEmpty();

		boolean success = allSecurityLiteralsMapped && analysisLevelsExist;

		if (success) {
			System.out.println("Die Bedingung IC1(T)(M) ist ERFÜLLT.");
			System.out.println("Alle sicherheitsrelevanten Literale im architektonischen Modell "
					+ "haben eine gültige Korrespondenz im Code.");
			System.out.println("Die Menge der angewandten Sicherheitslevels im Code ist nicht leer.");
		} else {
			System.out.println("Die Bedingung IC1(T)(M) ist NICHT ERFÜLLT.");

			if (!allSecurityLiteralsMapped) {
				Set<String> unmapped = new HashSet<>(allSecurityLiterals);
				unmapped.removeAll(mappedEdfaLiterals);
				System.out.println(
						"Fehler: Nicht alle sicherheitsrelevanten Literale haben ein Mapping. Fehlende: " + unmapped);
			}

			if (!analysisLevelsExist) {
				System.out.println("Fehler: Die Menge der angewandten Sicherheitslevels im Code ist leer.");
			}
		}

		return success;
	}

	public Set<String> getMappedEdfaLiterals() {
		return mappedEdfaLiterals;
	}

	public Set<String> getAllSecurityLiterals() {
		return allSecurityLiterals;
	}

	public Set<String> getAllCodeqlLevels() {
		return allSourceCodeAnalysisLevels;
	}
}