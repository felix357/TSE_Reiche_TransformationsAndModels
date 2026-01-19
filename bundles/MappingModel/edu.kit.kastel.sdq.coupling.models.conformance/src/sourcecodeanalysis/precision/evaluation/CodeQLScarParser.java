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

public class CodeQLScarParser {

    public List<ObservedFlow> parse(Path scarFile) throws Exception {
        Document doc = DocumentBuilderFactory.newInstance()
            .newDocumentBuilder().parse(scarFile.toFile());

        Map<String, SystemElement> elements = parseSystemElements(doc);
        Map<String, String> levels = parseSecurityLevels(doc);

        List<ObservedFlow> flows = new ArrayList<>();

        NodeList entries = doc.getElementsByTagName("resultEntries");
        for (int i = 0; i < entries.getLength(); i++) {
            Element entry = (Element) entries.item(i);

            Element src = (Element) entry.getElementsByTagName("source").item(0);
            Element sink = (Element) entry.getElementsByTagName("sink").item(0);

            SystemElement s = elements.get(src.getAttribute("systemElement"));
            SystemElement t = elements.get(sink.getAttribute("systemElement"));

            // strip package names
            String fromComponent = stripPackage(s.className());
            String toComponent = stripPackage(t.className());

            flows.add(new ObservedFlow(
                s.parameterType(),
                fromComponent,
                s.methodName(),
                levels.get(src.getAttribute("securityLevel")),
                toComponent,
                t.methodName(),
                levels.get(sink.getAttribute("securityLevel"))
            ));
        }
        return flows;
    }

    private Map<String, SystemElement> parseSystemElements(Document doc) {
        Map<String, SystemElement> elements = new HashMap<>();

        NodeList nodes = doc.getElementsByTagName("systemElementIdentifications");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);

            String id = "//@systemElementIdentifications." + i;

            // strip package here if desired (alternative to doing it in parse())
            String fqcn = e.getAttribute("fullyQualifiedClassName");
            String simpleClassName = stripPackage(fqcn);

            elements.put(id, new SystemElement(
                    id,
                    e.getAttribute("parameterName"),
                    e.getAttribute("parameterType"),
                    e.getAttribute("methodName"),
                    simpleClassName
            ));
        }
        return elements;
    }

    private Map<String, String> parseSecurityLevels(Document doc) {
        Map<String, String> levels = new HashMap<>();

        NodeList nodes = doc.getElementsByTagName("securityLevels");

        for (int i = 0; i < nodes.getLength(); i++) {
            Element e = (Element) nodes.item(i);

            String id = "//@securityLevels." + i;
            String name = e.getAttribute("name");

            levels.put(id, name);
        }
        return levels;
    }

    /** Helper to strip package and return only simple class name */
    private static String stripPackage(String fqcn) {
        if (fqcn == null || fqcn.isEmpty()) return fqcn;
        int lastDot = fqcn.lastIndexOf('.');
        return lastDot >= 0 ? fqcn.substring(lastDot + 1) : fqcn;
    }
}
