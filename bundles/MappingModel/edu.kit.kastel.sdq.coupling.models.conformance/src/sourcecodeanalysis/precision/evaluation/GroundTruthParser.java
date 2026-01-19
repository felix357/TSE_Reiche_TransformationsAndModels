package sourcecodeanalysis.precision.evaluation;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class GroundTruthParser {
	public Set<GroundTruthFlow> parse(Path gtFile) throws Exception {
		Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(gtFile.toFile());

		Set<GroundTruthFlow> flows = new HashSet<>();
		NodeList nodes = doc.getElementsByTagName("allowedFlow");

		for (int i = 0; i < nodes.getLength(); i++) {
			Element e = (Element) nodes.item(i);

			flows.add(new GroundTruthFlow(e.getAttribute("data"), e.getAttribute("fromComponent"),
					e.getAttribute("fromMethod"), e.getAttribute("fromLevel"), e.getAttribute("toComponent"),
					e.getAttribute("toMethod"), e.getAttribute("toLevel")));
		}
		return flows;
	}
}
