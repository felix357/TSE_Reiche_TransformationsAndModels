package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * Checker for IC5(T)(M) and IC5(T)(I): Checks if there exist Security
 * Characteristics and System Elements in the Source Code Analysis Result
 * corresponding to Security Characteristics and System Elements of the
 * Annotated Source Code 𝐶 that are affected by IC1 and IC2.
 */
public class IC5IandMChecker implements IChecker {

	private final AnalysisCouplingType analysisType;

	private final String resolvedValuesCorrespondencePath;
	private final Set<String> securityCharacteristicsC;
	private final Set<String> systemElementsC;
	private final String scarCodeQlScar;
	private final String codeQlFilePath;
	private final String systemName;

	private final Set<String> mappedSecurityCharacteristicsR = new HashSet<>();
	private final Set<String> mappedSystemElementsR = new HashSet<>();

	public IC5IandMChecker(SystemConfig cfg, Set<String> securityCharacteristicsC, Set<String> systemElementsC) {
		this.resolvedValuesCorrespondencePath = cfg.basePath + "/" + cfg.rivCorrespondence;
		this.securityCharacteristicsC = securityCharacteristicsC;
		this.systemElementsC = systemElementsC;
		this.scarCodeQlScar = cfg.basePath + "/" + cfg.scScarModel;
		this.analysisType = cfg.analysisCouplingType;
		if (analysisType == AnalysisCouplingType.JOANAEDFA) {
			this.codeQlFilePath = cfg.basePath + "/joana4extendeddataflowanalysis.joana";
		} else {
			this.codeQlFilePath = cfg.basePath + "/codeql4extendeddataflow.codeql";
		}
		this.systemName = cfg.systemName;
	}

	@Override
	public boolean runCheck() {
		try {
			File file = new File(resolvedValuesCorrespondencePath);
			if (!file.exists()) {
				System.out.println("IC5: Correspondence file NOT found: " + resolvedValuesCorrespondencePath);
				return false;
			}

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(new FileInputStream(file)));

			Set<String> authorizedDescriptiveIds = loadIC2AuthorizedDescriptiveIds(systemElementsC,
					systemName + "/java4extendeddataflow.java");

			loadSystemElementCorrespondences(doc, authorizedDescriptiveIds);

			Document correspondenceDoc = doc;
			String scarFilePath = scarCodeQlScar;
			loadSecurityCorrespondences(correspondenceDoc, codeQlFilePath, scarFilePath);

			boolean deltaNotEmpty = !mappedSystemElementsR.isEmpty();
			boolean scNotEmpty = !mappedSecurityCharacteristicsR.isEmpty();

			if (deltaNotEmpty && scNotEmpty) {
				System.out.println("IC5 is fulfilled. mapped system elements and security characteristics are non-empty.");
				System.out.println("Mapped security characteristics in R: " + mappedSecurityCharacteristicsR);
				System.out.println("Mapped system elements in R: " + mappedSystemElementsR);
				return true;
			}

			System.out.println("IC5 NOT fulfilled.");
			if (!deltaNotEmpty)
				System.out.println("No system element in C maps to a system element in R.");
			if (!scNotEmpty)
				System.out.println("No security characteristic in C maps to a security characteristic in R.");

			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private Set<String> loadIC2AuthorizedDescriptiveIds(Set<String> systemElementsC, String javaFilePath)
			throws Exception {
		Set<String> authorizedIds = new HashSet<>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(javaFilePath));

		for (String sysElemRef : systemElementsC) {
			String[] parts = sysElemRef.split("#");
			if (parts.length < 2)
				continue;
			String xpath = parts[1];

			Element cls = resolveClassFromXPath(doc, xpath);
			if (cls == null)
				continue;

			String fqcn = getFullyQualifiedClassName(cls);

			NodeList methods = cls.getElementsByTagName("method");
			for (int j = 0; j < methods.getLength(); j++) {
				Element method = (Element) methods.item(j);
				String methodName = method.getAttribute("name");

				NodeList params = method.getElementsByTagName("parameter");
				for (int k = 0; k < params.getLength(); k++) {
					Element param = (Element) params.item(k);
					String paramName = param.getAttribute("name");
					authorizedIds.add(fqcn + "." + methodName + "." + paramName);
				}
			}
		}
		return authorizedIds;
	}

	private void loadSystemElementCorrespondences(Document doc, Set<String> authorizedIds) throws Exception {
		NodeList nodes = doc.getElementsByTagName("parameterCorrespondences");

		for (int i = 0; i < nodes.getLength(); i++) {
			Element elem = (Element) nodes.item(i);

			String hrefC = elem.getElementsByTagName("parameter_SCAR").item(0).getAttributes().getNamedItem("href")
					.getNodeValue();
			String hrefR = elem.getElementsByTagName("parameter_ResolvedImplementationValues").item(0).getAttributes()
					.getNamedItem("href").getNodeValue();

			CodeQLSystemElement scarElem = parseSCARReference(hrefC);

			String baseId = scarElem.fullyQualifiedClassName + "." + scarElem.methodName;
			boolean matchFound = false;

			if (scarElem.parameterName != null) {
				String descriptiveIdFull = baseId + "." + scarElem.parameterName;
				if (authorizedIds.contains(descriptiveIdFull)) {
					matchFound = true;
				}
			} else {
				for (String authId : authorizedIds) {
					if (authId.startsWith(baseId + ".")) {
						matchFound = true;
						break;
					}
				}
			}

			if (matchFound) {
				mappedSystemElementsR.add(hrefR);
			}
		}
	}

	private Element resolveClassFromXPath(Document doc, String xpath) {
		try {
			String[] nodes = xpath.split("/");
			NodeList classNodes = doc.getElementsByTagName("classorinterface");
			String lastNode = nodes[nodes.length - 1];
			int index = Integer.parseInt(lastNode.split("\\.")[1]);
			if (index < classNodes.getLength())
				return (Element) classNodes.item(index);
		} catch (Exception e) {
		}
		return null;
	}

	private void loadSecurityCorrespondences(Document correspondenceDoc, String codeQlFilePath, String scarFilePath)
			throws Exception {

		String levelLabel;
		String securityLevelCorrespondence;
		String rivLabel;
		if (this.analysisType == AnalysisCouplingType.JOANAEDFA) {
			levelLabel = "levelCorrespondences";
			securityLevelCorrespondence = "level_JOANA";
			rivLabel = "level_ResolvedImplementationValues";
		} else {
			levelLabel = "securityLevelCorrespondences";
			securityLevelCorrespondence = "securityLevel_CodeQL";
			rivLabel = "securityLevel_ResolvedImplementationValues";
		}

		NodeList nodes = correspondenceDoc.getElementsByTagName(levelLabel);

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document codeQlDoc = db.parse(new File(codeQlFilePath));

		Document scarDoc = db.parse(new File(scarFilePath));

		for (int i = 0; i < nodes.getLength(); i++) {
			Element elem = (Element) nodes.item(i);

			String hrefC = elem.getElementsByTagName(securityLevelCorrespondence).item(0).getAttributes()
					.getNamedItem("href").getNodeValue();
			String hrefR = elem.getElementsByTagName(rivLabel).item(0).getAttributes().getNamedItem("href")
					.getNodeValue();

			String appliedLevelName = resolveAppliedSecurityLevelName(hrefC, codeQlDoc);
			if (appliedLevelName == null)
				continue;

			String resolvedLevelName = resolveResolvedSecurityLevelName(hrefR, scarDoc);
			if (resolvedLevelName == null)
				continue;

			Set<String> appliedParts = new HashSet<>();
			Set<String> resolvedParts = new HashSet<>();

			if (appliedLevelName.contains(";")) {
				for (String p : appliedLevelName.split("\\s*;\\s*"))
					appliedParts.add(p);
			} else {
				appliedParts.add(appliedLevelName);
			}

			if (resolvedLevelName.contains(";")) {
				for (String p : resolvedLevelName.split("\\s*;\\s*"))
					resolvedParts.add(p);
			} else {
				resolvedParts.add(resolvedLevelName);
			}

			boolean matchFound = false;
			for (String a : appliedParts) {
				if (resolvedParts.contains(a) && securityCharacteristicsC.contains(a)) {
					matchFound = true;
					break;
				}
			}

			if (matchFound) {
				mappedSecurityCharacteristicsR.add(hrefR);
			}
		}
	}

	/**
	 * Resolve CodeQL appliedSecurityLevel name from href.
	 */
	private String resolveAppliedSecurityLevelName(String href, Document codeQlDoc) {
		try {

			String labelSecLevel;
			String labelsec;
			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
				labelSecLevel = "@appliedSecurityLevel.";
				labelsec = "appliedSecurityLevel";
			} else {
				labelSecLevel = "@level.";
				labelsec = "level";
			}

			if (!href.contains(labelSecLevel))
				return null;
			int index = Integer.parseInt(href.split(labelSecLevel)[1]);
			NodeList appliedLevels = codeQlDoc.getElementsByTagName(labelsec);
			if (index < appliedLevels.getLength()) {
				Element levelElem = (Element) appliedLevels.item(index);
				return levelElem.getAttribute("name");
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Resolve SCAR resolved securityLevel name from href.
	 */
	private String resolveResolvedSecurityLevelName(String href, Document scarDoc) {
		try {
			String secLevel;
			String lvl;
			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
				secLevel = "@securityLevel.";
				lvl = "securityLevels";
			} else {
				secLevel = "@levels.";
				lvl = "levels";
			}
			if (!href.contains(secLevel))
				return null;
			int index = Integer.parseInt(href.split(secLevel)[1]);
			NodeList levels = scarDoc.getElementsByTagName(lvl);
			if (index < levels.getLength()) {
				Element levelElem = (Element) levels.item(index);
				return levelElem.getAttribute("name");
			}
		} catch (Exception e) {
		}
		return null;
	}

	private static class CodeQLSystemElement {
		String fullyQualifiedClassName;
		String methodName;
		String parameterName;
	}

	private CodeQLSystemElement parseSCARReference(String hrefC) throws Exception {
		String[] parts = hrefC.split("#");
		String filePath = parts[0];
		filePath = this.scarCodeQlScar;

		String xpathRef = parts[1];

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(filePath));

		if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
			NodeList nodes = doc.getElementsByTagName("systemElementIdentifications");
			int index = Integer.parseInt(xpathRef.replace("//@systemElementIdentifications.", ""));

			Element elem = (Element) nodes.item(index);
			CodeQLSystemElement cse = new CodeQLSystemElement();

			cse.fullyQualifiedClassName = elem.getAttribute("fullyQualifiedClassName");
			cse.methodName = elem.getAttribute("methodName");
			cse.parameterName = elem.getAttribute("parameterName");
			return cse;

		} else {
			NodeList nodes = doc.getElementsByTagName("systemElements");
			int index = Integer.parseInt(xpathRef.replace("//@systemElements.", ""));

			Element elem = (Element) nodes.item(index);
			CodeQLSystemElement cse = new CodeQLSystemElement();

			cse.fullyQualifiedClassName = elem.getAttribute("fullyQualifiedClassName");
			cse.methodName = elem.getAttribute("methodName");
			String parameterName = elem.getAttribute("parameterType");

			if (parameterName.endsWith("Header")) {
				cse.parameterName = "header";
			} else if (parameterName.endsWith("Body")) {
				cse.parameterName = "body";
			}
			return cse;
		}
	}

	private String getFullyQualifiedClassName(Element cls) {
		String name = cls.getAttribute("name");
		Element parent = (Element) cls.getParentNode();
		if (parent != null && parent.hasAttribute("name")) {
			return getFullyQualifiedClassName(parent) + "." + name;
		}
		return name;
	}

	public Set<String> getMappedSystemElementsR() {
		return mappedSystemElementsR;
	}

	public Set<String> getMappedSecurityCharacteristicsR() {
		return mappedSecurityCharacteristicsR;
	}
}
