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
 * Checks the IC3(C)(M) conformance rule for model coupling consistency.
 * <p>
 * IC3(C)(M) ensures that each security annotation connects a valid security
 * level, a system element, and a configuration element across the involved
 * models.
 * </p>
 */
public class IC3MChecker implements IChecker {

	private final AnalysisCouplingType analysisType;

	private final String basePath;
	private final String codeqlFilePath;
	// from IC1M
	private final Set<String> securityLiterals;
	// from IC2M
	private final Set<String> systemElementsFromIC2;
	// from IC2M
	private final Set<String> configurationsFromIC2;

	private Map<String, String> annotationToLevel = new HashMap<>();
	private Map<String, Set<String>> levelToAnnotations = new HashMap<>();
	private Map<String, Set<String>> levelToConfigurations = new HashMap<>();

	public IC3MChecker(SystemConfig cfg, Set<String> securityLiterals, Set<String> systemElementsFromIC2,
			Set<String> configurationsFromIC2) {
		this.basePath = cfg.basePath;
		this.codeqlFilePath = cfg.basePath + File.separator + cfg.sourceCodeAnalysis;
		this.securityLiterals = securityLiterals;
		this.systemElementsFromIC2 = systemElementsFromIC2;
		this.configurationsFromIC2 = configurationsFromIC2;
		this.analysisType = cfg.analysisCouplingType;
	}

	@Override
	public boolean runCheck() {
		return switch (analysisType) {
		case CODEQLEDFA -> runCodeQLCheck();
		case JOANAEDFA -> runJoanaCheck();
		};
	}

	// CodeQL–EDFA implementation
	private boolean runCodeQLCheck() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new FileInputStream(new File(codeqlFilePath))));

			NodeList levels = doc.getElementsByTagName("appliedSecurityLevel");
			Map<String, String> levelIdToName = new HashMap<>();
			for (int i = 0; i < levels.getLength(); i++) {
				Element e = (Element) levels.item(i);
				String id = e.getAttribute("id");
				String name = e.getAttribute("name");
				levelIdToName.put(id, name);
			}

			NodeList annotations = doc.getElementsByTagName("securityLevelAnnotations");
			for (int i = 0; i < annotations.getLength(); i++) {
				Element e = (Element) annotations.item(i);
				String annotationId = e.getAttribute("id");
				String securityLevelRef = e.getAttribute("securityLevel");

				String levelId = resolveSecurityLevelId(securityLevelRef, doc);
				if (levelId != null) {
					annotationToLevel.put(annotationId, levelId);
					levelToAnnotations.computeIfAbsent(levelId, k -> new HashSet<>()).add(annotationId);
				}
			}

			for (String configRefC : configurationsFromIC2) {
				Set<String> levelIds = resolveRefForConfig(configRefC, dbf);
				for (String levelId : levelIds) {
					levelToConfigurations.computeIfAbsent(levelId, k -> new HashSet<>()).add(configRefC);
				}
			}

			// IC3(C)(M) check
			for (Map.Entry<String, String> entry : annotationToLevel.entrySet()) {
				String annotationId = entry.getKey();
				String levelId = entry.getValue();

				// Check Security literal
				String levelName = levelIdToName.get(levelId);
				if (!securityLiterals.contains(levelName))
					continue;

				// Check system element
				if (!affectsSystemElement(annotationId, systemElementsFromIC2, doc))
					continue;

				// check configuration
				Set<String> cfgsForLevel = levelToConfigurations.get(levelId);
				if (cfgsForLevel == null || cfgsForLevel.isEmpty())
					continue;

				System.out.println("IC3(C)(M) erfüllt");
				return true;
			}

			System.out.println("IC3(C)(M) NICHT erfüllt");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Joana-EDFA implementation
	private boolean runJoanaCheck() {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new FileInputStream(new File(codeqlFilePath))));

			NodeList entrypoints = doc.getElementsByTagName("entrypoint");

			for (int e = 0; e < entrypoints.getLength(); e++) {
				Element entrypoint = (Element) entrypoints.item(e);
				String entrypointId = entrypoint.getAttribute("id");

				boolean configMatches = false;
				for (String cfgRef : configurationsFromIC2) {

					String[] parts = cfgRef.split("\\.");
					String cfgIndex = parts[parts.length - 1];
					if (cfgIndex.equals(entrypointId)) {
						configMatches = true;
						break;
					}
				}
				if (!configMatches)
					continue;

				Map<String, String> levelRefToName = new HashMap<>();
				NodeList levels = entrypoint.getElementsByTagName("level");
				for (int i = 0; i < levels.getLength(); i++) {
					Element level = (Element) levels.item(i);
					String ref = "//@entrypoint." + e + "/@level." + i;
					levelRefToName.put(ref, level.getAttribute("name"));
				}

				NodeList annotations = entrypoint.getElementsByTagName("annotation");

				for (int i = 0; i < annotations.getLength(); i++) {
					Element annotation = (Element) annotations.item(i);

					String levelRef = annotation.getAttribute("level");
					String levelName = levelRefToName.get(levelRef);
					if (!securityLiterals.contains(levelName))
						continue;

					NodeList params = annotation.getElementsByTagName("Parameter");
					boolean affectsSystemElement = false;

					for (int p = 0; p < params.getLength(); p++) {
						Element param = (Element) params.item(p);
						String href = param.getAttribute("href");

						for (String systemElem : systemElementsFromIC2) {
							if (href.startsWith(systemElem)) {
								affectsSystemElement = true;
								break;
							}
						}
						if (affectsSystemElement)
							break;
					}

					if (!affectsSystemElement)
						continue;

					System.out.println("IC3(C)(M) erfüllt (JOANA–EDFA)");
					return true;
				}
			}

			System.out.println("IC3(C)(M) NICHT erfüllt (JOANA–EDFA)");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private String resolveSecurityLevelId(String ref, Document doc) {
		try {
			String[] parts = ref.split("/@");
			if (parts.length < 3)
				return null;
			String lastPart = parts[parts.length - 1];
			String[] tokens = lastPart.split("\\.");
			String tag = tokens[0];
			int index = Integer.parseInt(tokens[1]);

			NodeList nodes = doc.getElementsByTagName(tag);
			if (index >= nodes.getLength())
				return null;
			Element e = (Element) nodes.item(index);
			return e.getAttribute("id");
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private Set<String> resolveRefForConfig(String configRefC, DocumentBuilderFactory dbf) {
		Set<String> levelIds = new HashSet<>();
		try {
			String[] parts = configRefC.split("#");
			String configFilePath = parts[0];
			String configPath = parts[1];
			int configIndex = Integer.parseInt(configPath.replaceAll("\\D", ""));

			DocumentBuilder db = dbf.newDocumentBuilder();
			Document configDoc = db.parse(new File(basePath + "/" + configFilePath));
			NodeList configurations = configDoc.getElementsByTagName("configurations");
			if (configIndex >= configurations.getLength())
				return levelIds;

			Element configElem = (Element) configurations.item(configIndex);

			Element mainConfigElem = (Element) configElem.getElementsByTagName("mainConfigurationElement").item(0);
			if (mainConfigElem == null)
				return levelIds;
			String queriesHref = mainConfigElem.getAttribute("href");

			String[] qParts = queriesHref.split("#");
			String codeqlFile = qParts[0];
			String queriesPath = qParts[1];
			int queriesIndex = Integer.parseInt(queriesPath.replaceAll("\\D", ""));

			Document codeqlDoc = db.parse(new File(basePath + "/" + codeqlFile));
			NodeList queriesList = codeqlDoc.getElementsByTagName("queries");
			if (queriesIndex >= queriesList.getLength())
				return levelIds;

			Element queriesElem = (Element) queriesList.item(queriesIndex);

			NodeList levels = queriesElem.getElementsByTagName("appliedSecurityLevel");
			for (int i = 0; i < levels.getLength(); i++) {
				Element levelElem = (Element) levels.item(i);
				levelIds.add(levelElem.getAttribute("id"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return levelIds;
	}

	private boolean affectsSystemElement(String annotationId, Set<String> systemElementsFromIC2, Document doc) {
		try {
			NodeList annotations = doc.getElementsByTagName("securityLevelAnnotations");
			for (int i = 0; i < annotations.getLength(); i++) {
				Element annotationElem = (Element) annotations.item(i);
				String id = annotationElem.getAttribute("id");
				if (!id.equals(annotationId))
					continue;

				NodeList params = annotationElem.getElementsByTagName("parameter");
				for (int j = 0; j < params.getLength(); j++) {
					Element param = (Element) params.item(j);
					String href = param.getAttribute("href");

					for (String systemElem : systemElementsFromIC2) {
						if (href.startsWith(systemElem))
							return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
