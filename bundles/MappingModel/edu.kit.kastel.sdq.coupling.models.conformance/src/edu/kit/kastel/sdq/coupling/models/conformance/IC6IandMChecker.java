package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import edu.kit.kastel.sdq.coupling.models.conformance.SystemConfig.AnalysisCouplingType;

/**
 * This class checks IC6(C)(M) and IC6(C)(I).
 * 
 * It checks the existence of a Result Entry Element in the Source Code Analysis
 * Result that contains both a System Element and a Security Characteristic,
 * where both elements are affected by IC5.
 */
public class IC6IandMChecker implements IChecker {

	private final AnalysisCouplingType analysisType;

	private final String scarFilePath;
	private final String rFilePath;

	private final Set<String> mappedSystemElementsR;
	private final Set<String> mappedSecurityCharacteristicsR;

	private Document scarDoc;
	private Document rDoc;

	public IC6IandMChecker(SystemConfig cfg, IC5IandMChecker ic5) {
		this.scarFilePath = cfg.basePath + "/" + cfg.scScarModel;
		this.rFilePath = cfg.basePath + "/" + cfg.riv;

		this.mappedSystemElementsR = ic5.getMappedSystemElementsR();
		this.mappedSecurityCharacteristicsR = ic5.getMappedSecurityCharacteristicsR();

		this.analysisType = cfg.analysisCouplingType;
	}

	@Override
	public boolean runCheck() {

		System.out.println("Running IC6(C)(M) semantic check...");

		if (mappedSystemElementsR.isEmpty() || mappedSecurityCharacteristicsR.isEmpty()) {
			System.out.println("IC6 NOT fulfilled: IC5 produced no mapped elements.");
			return false;
		}

		try {
			loadScar();
			loadRModel();

			String dataFlows;

			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
				dataFlows = "resultEntries";
			} else {
				dataFlows = "flows";
			}

			NodeList resultEntries = scarDoc.getElementsByTagName(dataFlows);

			for (int i = 0; i < resultEntries.getLength(); i++) {

				Element re = (Element) resultEntries.item(i);

				Set<Sem> sysInEntry = new HashSet<>();
				Set<String> secInEntry = new HashSet<>();

				extractFromNodeList(re.getElementsByTagName("source"), sysInEntry, secInEntry);
				extractFromNodeList(re.getElementsByTagName("sink"), sysInEntry, secInEntry);

				boolean hasSystem = intersectsSemantic(sysInEntry);
				boolean hasSecurity = intersectsSecurity(secInEntry);

				if (hasSystem && hasSecurity) {
					System.out.println("IC6 fulfilled (REE index: " + i + ")");
					return true;
				}
			}

			System.out.println("IC6 NOT fulfilled. No matching ResultEntry found.");
			return false;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void loadScar() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		scarDoc = db.parse(new File(scarFilePath));
	}

	private void loadRModel() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db = dbf.newDocumentBuilder();
		rDoc = db.parse(new File(rFilePath));
	}

	private void extractFromNodeList(NodeList list, Set<Sem> sysElems, Set<String> secElems) {
		for (int i = 0; i < list.getLength(); i++) {
			Element e = (Element) list.item(i);

			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {

				if (e.hasAttribute("securityLevel")) {
					secElems.add(e.getAttribute("securityLevel"));
				}
			} else {
				if (e.hasAttribute("sourceLevel")) {
					secElems.add(e.getAttribute("sourceLevel"));
				}
				if (e.hasAttribute("sinkLevel")) {
					secElems.add(e.getAttribute("sinkLevel"));
				}
			}

			if (e.hasAttribute("systemElement")) {
				String href = e.getAttribute("systemElement");
				Sem s = resolveSystemElement(href);
				if (s != null)
					sysElems.add(s);
			}
		}
	}

	private Sem resolveSystemElement(String href) {

		try {
			
			String sysElementId;
			String param;
			if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
				sysElementId = "systemElementIdentifications";
				param = "parameterName";
			} else {
				sysElementId = "systemElements";
				param = "parameterType";
			}
			
			if (href.startsWith("//@" + sysElementId)) {
				int idx = Integer.parseInt(href.replaceAll("\\D+", ""));
				NodeList list = scarDoc.getElementsByTagName(sysElementId);
				if (idx >= list.getLength())
					return null;
				Element e = (Element) list.item(idx);
				return new Sem(e.getAttribute("fullyQualifiedClassName"), e.getAttribute("methodName"),
						e.getAttribute(param));
			}

			if (href.contains("#//@systemElements")) {
				int idx = Integer.parseInt(href.replaceAll("\\D+", ""));
				NodeList list = rDoc.getElementsByTagName("systemElements");
				if (idx >= list.getLength())
					return null;
				Element e = (Element) list.item(idx);
				String p = e.getAttribute("ParameterName");
				if (this.analysisType == AnalysisCouplingType.JOANAEDFA) {
					p = e.getAttribute(param);
				}
				return new Sem(e.getAttribute("fullyQualifiedClassName"), e.getAttribute("methodName"),
						p);
			}
			
			

		} catch (Exception ignored) {
		}

		return null;
	}

	private boolean intersectsSemantic(Set<Sem> entrySet) {

		for (String href : mappedSystemElementsR) {
			Sem mapped = resolveSystemElement(href);
			if (mapped == null)
				continue;

			for (Sem inEntry : entrySet) {
				if (mapped.equals(inEntry))
					return true;
			}
		}

		return false;
	}

	/**
	 * Resolves securityLevel hrefs to names and compares for intersection.
	 */
	private boolean intersectsSecurity(Set<String> secInEntry) {
		try {
			Set<String> entryNames = new HashSet<>();
			for (String href : secInEntry) {
				String name;
				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
					name = resolveSecurityLevelNameCodeQl(href, scarDoc);
				} else {
					name = resolveSecurityLevelNameJoana(href, scarDoc);
				}
				if (name != null)
					entryNames.add(name);
			}

			Set<String> mappedNames = new HashSet<>();
			for (String href : mappedSecurityCharacteristicsR) {
				String name;
				if (this.analysisType == AnalysisCouplingType.CODEQLEDFA) {
					name = resolveSecurityLevelNameCodeQl(href, scarDoc);
				} else {
					name = resolveSecurityLevelNameJoana(href, scarDoc);
				}
				if (name != null)
					mappedNames.add(name);
			}

			for (String n : entryNames) {
				if (mappedNames.contains(n))
					return true;
			}
		} catch (Exception ignored) {
		}

		return false;
	}

	private String resolveSecurityLevelNameCodeQl(String href, Document doc) {
		try {
			if (href.contains("@securityLevels")) {
				int idx = Integer.parseInt(href.split("@securityLevels\\.")[1]);
				NodeList list = doc.getElementsByTagName("securityLevels");
				if (idx < list.getLength()) {
					return ((Element) list.item(idx)).getAttribute("name");
				}
			} else if (href.contains("@securityLevel")) {
				int idx = Integer.parseInt(href.split("@securityLevel\\.")[1]);
				NodeList list = doc.getElementsByTagName("securityLevels");
				if (idx < list.getLength()) {
					return ((Element) list.item(idx)).getAttribute("name");
				}
			}
		} catch (Exception ignored) {
		}
		return null;
	}
	
	private String resolveSecurityLevelNameJoana(String href, Document doc) {
	    try {
	        if (href.contains("@levels")) {
	            int idx = Integer.parseInt(href.split("@levels\\.")[1]);
	            NodeList list = doc.getElementsByTagName("levels");
	            if (idx < list.getLength()) {
	                return ((Element) list.item(idx)).getAttribute("name");
	            }
	        }
	    } catch (Exception ignored) {
	    }
	    return null;
	}


	/**
	 * Represents a semantic triple consisting of a fully qualified class name
	 * (FQCN), a method name, and a parameter name.
	 * 
	 * This class is used to model System Elements in the Source Code Analysis
	 * Result for IC6(C)(M) and IC6(C)(I) checks.
	 * 
	 * Equality and hash code are based on the combination of fqcn, method, and
	 * param.
	 */
	private static class Sem {
		final String fqcn, method, param;

		Sem(String fqcn, String method, String param) {
			this.fqcn = fqcn;
			this.method = method;
			this.param = param;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Sem))
				return false;
			Sem s = (Sem) o;
			return fqcn.equals(s.fqcn) && method.equals(s.method) && param.equals(s.param);
		}

		@Override
		public String toString() {
			return "(" + fqcn + ", " + method + ", " + param + ")";
		}
	}
}
