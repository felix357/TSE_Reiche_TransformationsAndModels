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

/**
 * Checker for IC5(T)(M) and IC5(T)(I): Checks if there exist Security
 * Characteristics and System Elements in the Source Code Analysis Result
 * corresponding to Security Characteristics and System Elements of the
 * Annotated Source Code 𝐶 that are affected by IC1 and IC2.
 */
public class IC5IandMChecker implements IChecker {

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
		this.scarCodeQlScar = cfg.basePath + "/" + cfg.codeqlScarModel;
		this.codeQlFilePath = cfg.basePath + "/codeql4extendeddataflow.codeql";
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
				System.out.println("IC5 is fulfilled. Δ_cs_R and S_cs_R are non-empty. ✅");
				System.out.println("Mapped security characteristics in R: " + mappedSecurityCharacteristicsR);
				System.out.println("Mapped system elements in R: " + mappedSystemElementsR);
				return true;
			}

			System.out.println("IC5 NOT fulfilled. ❌");
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

			String descriptiveId = scarElem.fullyQualifiedClassName + "." + scarElem.methodName + "."
					+ scarElem.parameterName;
			if (authorizedIds.contains(descriptiveId)) {
				mappedSystemElementsR.add(hrefR);
			}
		}
	}

	private Element resolveClassFromXPath(Document doc, String xpath) {
		try {
			String[] nodes = xpath.split("/");
			NodeList classNodes = doc.getElementsByTagName("classorinterface");
			String lastNode = nodes[nodes.length - 1]; // e.g., classorinterface.0
			int index = Integer.parseInt(lastNode.split("\\.")[1]);
			if (index < classNodes.getLength())
				return (Element) classNodes.item(index);
		} catch (Exception e) {
			/* ignore invalid paths */ }
		return null;
	}

	private void loadSecurityCorrespondences(
	        Document correspondenceDoc, 
	        String codeQlFilePath, 
	        String scarFilePath) throws Exception {

	    NodeList nodes = correspondenceDoc.getElementsByTagName("securityLevelCorrespondences");

	    // Parse CodeQL XML (appliedSecurityLevel)
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(true);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document codeQlDoc = db.parse(new File(codeQlFilePath));

	    // Parse SCAR XML (resolved securityLevels)
	    Document scarDoc = db.parse(new File(scarFilePath));

	    for (int i = 0; i < nodes.getLength(); i++) {
	        Element elem = (Element) nodes.item(i);

	        String hrefC = elem.getElementsByTagName("securityLevel_CodeQL")
	                .item(0).getAttributes().getNamedItem("href").getNodeValue();
	        String hrefR = elem.getElementsByTagName("securityLevel_ResolvedImplementationValues")
	                .item(0).getAttributes().getNamedItem("href").getNodeValue();

	        // Step 1 — resolve applied level from CodeQL
	        String appliedLevelName = resolveAppliedSecurityLevelName(hrefC, codeQlDoc);
	        if (appliedLevelName == null)
	            continue;

	        // Step 2 — resolve resolved level from SCAR
	        String resolvedLevelName = resolveResolvedSecurityLevelName(hrefR, scarDoc);
	        if (resolvedLevelName == null)
	            continue;

	        // ---------------------------
	        // NEW LOGIC FOR SEMICOLONS
	        // ---------------------------
	        Set<String> appliedParts = new HashSet<>();
	        Set<String> resolvedParts = new HashSet<>();

	        // If there's a semicolon → split
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

	        // Check if ANY matching term is in BOTH sets and in securityCharacteristicsC
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
			if (!href.contains("@appliedSecurityLevel."))
				return null;
			int index = Integer.parseInt(href.split("@appliedSecurityLevel\\.")[1]);
			NodeList appliedLevels = codeQlDoc.getElementsByTagName("appliedSecurityLevel");
			if (index < appliedLevels.getLength()) {
				Element levelElem = (Element) appliedLevels.item(index);
				return levelElem.getAttribute("name");
			}
		} catch (Exception e) {
			/* ignore */ }
		return null;
	}

	/**
	 * Resolve SCAR resolved securityLevel name from href.
	 */
	private String resolveResolvedSecurityLevelName(String href, Document scarDoc) {
		try {
			if (!href.contains("@securityLevel."))
				return null;
			int index = Integer.parseInt(href.split("@securityLevel\\.")[1]);
			NodeList levels = scarDoc.getElementsByTagName("securityLevels");
			if (index < levels.getLength()) {
				Element levelElem = (Element) levels.item(index);
				return levelElem.getAttribute("name");
			}
		} catch (Exception e) {
			/* ignore */ }
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
		if (filePath.equals("scar.codeqlscar"))
			filePath = this.scarCodeQlScar;

		String xpathRef = parts[1];

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new File(filePath));

		NodeList nodes = doc.getElementsByTagName("systemElementIdentifications");
		int index = Integer.parseInt(xpathRef.replace("//@systemElementIdentifications.", ""));
		Element elem = (Element) nodes.item(index);

		CodeQLSystemElement cse = new CodeQLSystemElement();
		cse.fullyQualifiedClassName = elem.getAttribute("fullyQualifiedClassName");
		cse.methodName = elem.getAttribute("methodName");
		cse.parameterName = elem.getAttribute("parameterName");
		return cse;
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
