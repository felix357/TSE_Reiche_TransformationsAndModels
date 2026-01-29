package sourcecodeanalysis.precision.evaluation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ScScarParser {

	public List<ObservedFlow> parse(Path scarFile) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(scarFile.toFile());

		ScarFormat format = detectFormat(doc);

		Map<String, SystemElement> elements = parseSystemElements(doc, format);
		Map<String, String> levels = parseSecurityLevels(doc, format);

		List<ObservedFlow> flows = new ArrayList<>();

		NodeList flowNodes = doc.getElementsByTagName(format.flowsTag());
		for (int i = 0; i < flowNodes.getLength(); i++) {
			Element flow = (Element) flowNodes.item(i);

			Element src = (Element) flow.getElementsByTagName("source").item(0);
			Element sink = (Element) flow.getElementsByTagName("sink").item(0);

			SystemElement s = elements.get(src.getAttribute("systemElement"));
			SystemElement t = elements.get(sink.getAttribute("systemElement"));

			flows.add(new ObservedFlow(s.parameterType(), stripPackage(s.className()), s.methodName(),
					levels.get(src.getAttribute(format.sourceLevelAttr())), stripPackage(t.className()), t.methodName(),
					levels.get(sink.getAttribute(format.sinkLevelAttr()))));
		}

		return flows;
	}

	private Map<String, SystemElement> parseSystemElements(Document doc, ScarFormat format) {
		Map<String, SystemElement> elements = new HashMap<>();

		NodeList nodes = doc.getElementsByTagName(format.systemElementsTag());

		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);
			String id = "//@" + format.systemElementsTag() + "." + i;

			String fqcn = e.getAttribute("fullyQualifiedClassName");
			String simpleClassName = stripPackage(fqcn);

			elements.put(id, new SystemElement(id, e.getAttribute("parameterName"),
					stripPackage(e.getAttribute("parameterType")), e.getAttribute("methodName"), simpleClassName));
		}

		return elements;
	}

	private Map<String, String> parseSecurityLevels(Document doc, ScarFormat format) {
		Map<String, String> levels = new HashMap<>();

		NodeList nodes = doc.getElementsByTagName(format.levelsTag());

		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);
			String id = "//@" + format.levelsTag() + "." + i;
			levels.put(id, e.getAttribute("name"));
		}

		return levels;
	}

	private ScarFormat detectFormat(Document doc) {
		if (doc.getElementsByTagName("resultEntries").getLength() > 0) {
			return new CodeQLScarFormat();
		}
		if (doc.getElementsByTagName("flows").getLength() > 0) {
			return new JoanaScarFormat();
		}
		throw new IllegalArgumentException("Unknown SCAR format");
	}

	private interface ScarFormat {
		String systemElementsTag();

		String levelsTag();

		String flowsTag();

		String sourceLevelAttr();

		String sinkLevelAttr();
	}

	private static class CodeQLScarFormat implements ScarFormat {
		public String systemElementsTag() {
			return "systemElementIdentifications";
		}

		public String levelsTag() {
			return "securityLevels";
		}

		public String flowsTag() {
			return "resultEntries";
		}

		public String sourceLevelAttr() {
			return "securityLevel";
		}

		public String sinkLevelAttr() {
			return "securityLevel";
		}
	}

	private static class JoanaScarFormat implements ScarFormat {
		public String systemElementsTag() {
			return "systemElements";
		}

		public String levelsTag() {
			return "levels";
		}

		public String flowsTag() {
			return "flows";
		}

		public String sourceLevelAttr() {
			return "sourceLevel";
		}

		public String sinkLevelAttr() {
			return "sinkLevel";
		}
	}

	private static String stripPackage(String fqcn) {
		if (fqcn == null || fqcn.isEmpty())
			return fqcn;
		int lastDot = fqcn.lastIndexOf('.');
		return lastDot >= 0 ? fqcn.substring(lastDot + 1) : fqcn;
	}
}
