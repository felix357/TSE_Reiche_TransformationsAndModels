package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Checker for IC2(T)(I): Ensures that the sets of security-relevant system
 * elements and configurations on the code side are not empty.
 */
public class IC2IChecker implements IChecker {

	private static final String ANNOTATION_FILE = "jpmail.parameterannotation";
	private static final String PCM_JAVA_CORR_FILE = "correspondences.pcmjavacorrespondence";
	private static final String EDFA_CODEQL_CORR_FILE = "correspondences.edfacodeqlcorrespondences";
	private static final String EDFA_CONFIG_FILE = "extendeddataflow.configurationrepresentation";

	private final String annotationPath;
	private final String pcmJavaCorrPath;
	private final String edfaCodeqlCorrPath;
	private final String edfaConfigPath;

	/**
	 * Konstruiert einen Checker für IC2(T)(I).
	 *
	 * @param basePath Basisverzeichnis-Pfad
	 */
	public IC2IChecker(String basePath) {
		this.annotationPath = new File(basePath, ANNOTATION_FILE).toString();
		this.pcmJavaCorrPath = new File(basePath, PCM_JAVA_CORR_FILE).toString();
		this.edfaCodeqlCorrPath = new File(basePath, EDFA_CODEQL_CORR_FILE).toString();
		this.edfaConfigPath = new File(basePath, EDFA_CONFIG_FILE).toString();
	}

	@Override
	public boolean runCheck() {
		try {
			Set<String> annotatedPcmElements = getAnnotatedPcmElements();

			boolean deltaCsCNonEmpty = isSystemElementSetNonEmpty(annotatedPcmElements);

			boolean cfgCsCNonEmpty = isConfigurationSetNonEmpty();

			System.out.println("IC2(T)(I) Check:");
			System.out.println("  Delta_cs^C (Systemelemente) ≠ ∅: " + (deltaCsCNonEmpty ? "JA ✅" : "NEIN ❌"));
			System.out.println("  CFG_cs^C (Konfigurationen) ≠ ∅: " + (cfgCsCNonEmpty ? "JA ✅" : "NEIN ❌"));

			boolean result = deltaCsCNonEmpty && cfgCsCNonEmpty;

			if (result) {
				System.out.println("Die Bedingung IC2(T)(I) ist ERFÜLLT. (Robustheit des Mappings ist nachgewiesen)");
			} else {
				System.out.println("Die Bedingung IC2(T)(I) ist NICHT ERFÜLLT.");
			}
			return result;

		} catch (Exception e) {
			System.err.println("Fehler während der Prüfung:");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Collects the URIs of all security-annotated PCM parameters. This set serves
	 * as the primary filter to check the IC2.1(T)(I). The method parses the
	 * annotation file to extract {@code <parameter> href} values.
	 * 
	 * @return A {@code Set<String>} containing the full URIs of annotated PCM
	 *         parameters.
	 * @throws Exception if the XML file cannot be parsed or accessed.
	 */
	private Set<String> getAnnotatedPcmElements() throws Exception {
		Set<String> annotatedPcmElements = new HashSet<>();
		Document doc = ConformanceUtils.parseXmlFile(annotationPath);
		NodeList annotations = doc.getElementsByTagName("annotations");

		for (int i = 0; i < annotations.getLength(); i++) {
			Element annotation = (Element) annotations.item(i);
			Element paramId = (Element) annotation.getElementsByTagName("parameterIdentification").item(0);
			if (paramId != null) {
				Element parameter = (Element) paramId.getElementsByTagName("parameter").item(0);
				String paramHref = (parameter != null) ? parameter.getAttribute("href") : null;

				if (paramHref != null) {
					annotatedPcmElements.add(paramHref);
				}
			}
		}
		return annotatedPcmElements;
	}

	/**
	 * This method iterates through the PCM-Java
	 * correspondence file and verifies if any $\delta_A$ URI is contained in the
	 * provided filter set.
	 * 
	 * @param annotatedPcmElements The set of URIs of all annotated PCM elements.
	 * @return true if at least one correspondence is found for an annotated
	 *         element, false otherwise.
	 * @throws Exception if the PCM-Java correspondence file cannot be parsed.
	 */
	private boolean isSystemElementSetNonEmpty(Set<String> annotatedPcmElements) throws Exception {
		if (annotatedPcmElements.isEmpty()) {
			return false;
		}

		Document doc = ConformanceUtils.parseXmlFile(pcmJavaCorrPath);

		NodeList paramCorrs = doc.getElementsByTagName("pcmparameter2javaparameter");
		for (int i = 0; i < paramCorrs.getLength(); i++) {
			Element corr = (Element) paramCorrs.item(i);
			Element pcmId = (Element) corr.getElementsByTagName("pcmParameterIdentification").item(0);

			if (pcmId != null) {
				Element pcmElement = (Element) pcmId.getElementsByTagName("parameter").item(0);
				String pcmHref = (pcmElement != null) ? pcmElement.getAttribute("href") : null;

				String startMarker = "jpmail.repository#//";

				String cutPcmHref = null;

				if (pcmHref != null) {
					int startIndex = pcmHref.indexOf(startMarker);

					if (startIndex != -1) {
						cutPcmHref = pcmHref.substring(startIndex);
					} else {

					}
				}

				if (pcmHref != null) {
					int startIndex = pcmHref.indexOf(startMarker);
					if (startIndex != -1) {
						pcmHref = pcmHref.substring(startIndex);
					}
				}

				if (pcmHref != null && annotatedPcmElements.contains(pcmHref)) {
					return true;
				}
			}
		}

		return false;
	}


	/**
	 * Checks if the relevant architectural configuration, 
	 * which references the annotation file, has a corresponding configuration in the 
	 * code model.
	 * The method first determines the URI of the relevant and then checks 
	 * for a matching {@code <configurationCorrespondences>} entry in the EDFA-CodeQL 
	 * correspondence file.
	 * @return true if a corresponding code configuration is found, false otherwise.
	 * @throws Exception if file parsing fails (e.g., the correspondence file is missing).
	 */
	private boolean isConfigurationSetNonEmpty() throws Exception {
		String relevantConfigUriSuffix = getRelevantArchitecturalConfigUriSuffix();

		if (relevantConfigUriSuffix == null) {
			System.out.println(
					"  [Detail] Keine Architektur-Konfiguration (cfg^A) gefunden, die die Annotation-Datei verwendet.");
			return false;
		}
		Document doc = ConformanceUtils.parseXmlFile(edfaCodeqlCorrPath);
		NodeList correspondences = doc.getElementsByTagName("configurationCorrespondences");

		for (int i = 0; i < correspondences.getLength(); i++) {
			Element corr = (Element) correspondences.item(i);
			String cfgEdfaHref = ConformanceUtils.getAttributeFromElement(corr, "configuration_EDFA", "href");

			if (cfgEdfaHref != null && cfgEdfaHref.endsWith(relevantConfigUriSuffix)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Finds the URI suffix of the architectural configuration
	 * that lists the security annotation file as an input. 
	 * This configuration is relevant for the IC2.2(T)(I) check.
	 * * @return The URI suffix (e.g., "#//@configurations.0") of the relevant $\text{cfg}^A$, 
	 * or {@code null} if the annotation file is not used by any configuration.
	 * @throws Exception if the EDFA configuration file cannot be parsed.
	 */
	private String getRelevantArchitecturalConfigUriSuffix() throws Exception {
		Document doc = ConformanceUtils.parseXmlFile(edfaConfigPath);
		NodeList configs = doc.getElementsByTagName("configurations");

		for (int i = 0; i < configs.getLength(); i++) {
			Element config = (Element) configs.item(i);
			NodeList inputs = config.getElementsByTagName("inputs");

			for (int j = 0; j < inputs.getLength(); j++) {
				String inputHref = ((Element) inputs.item(j)).getAttribute("href");

				if (inputHref != null && inputHref.contains(ANNOTATION_FILE)) {
					return "#//@configurations." + i;
				}
			}
		}
		return null;
	}
}