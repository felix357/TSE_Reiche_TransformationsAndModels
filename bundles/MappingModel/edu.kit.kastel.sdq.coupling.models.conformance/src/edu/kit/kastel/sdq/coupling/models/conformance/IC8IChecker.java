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
 * IC8(C)(I) Checker:
 * Checks if all instances of ResolvedImplementationValues (RIVs) in the
 * Resolved Implementation Values Model reference valid system element instances
 * (IC5) and configuration instances (IC7) from the Source Code Analysis Result.
 */
public class IC8IChecker implements IChecker {

    private final String rivModelPath;
    private final IC5IandMChecker ic5Checker;
    private final IC7IChecker ic7Checker;

    private final Set<String> invalidRIVs = new HashSet<>();

    public IC8IChecker(SystemConfig cfg, IC5IandMChecker ic5Checker, IC7IChecker ic7Checker) {
        this.rivModelPath = cfg.basePath + File.separator + cfg.riv;
        this.ic5Checker = ic5Checker;
        this.ic7Checker = ic7Checker;
    }

    @Override
    public boolean runCheck() {
        try {
            File file = new File(rivModelPath);
            if (!file.exists()) {
                System.out.println("IC8(C)(I): RIV model file not found: " + rivModelPath);
                return false;
            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new FileInputStream(file)));

            NodeList rivNodes = doc.getElementsByTagName("resultingValues");

            boolean allValid = true;

            // Retrieve valid instance sets from IC5 and IC7
            Set<String> validSystemElementInstances = ic5Checker.getMappedSystemElementsR();
            Set<String> validConfigurationInstances = ic7Checker.getFoundResultingValueInstances();

            // IC8.1(T)(I) & IC8.2(T)(I) existential checks
            if (validSystemElementInstances.isEmpty()) {
                System.out.println("IC8.1(T)(I) violated: No valid system element instances found.");
            }
            if (validConfigurationInstances.isEmpty()) {
                System.out.println("IC8.2(T)(I) violated: No valid configuration instances found.");
            }

            for (int i = 0; i < rivNodes.getLength(); i++) {
                Element riv = (Element) rivNodes.item(i);

                String sysElemInstance = riv.getAttribute("parameter");  // e.g., //@systemElementIdentifications.X
                String cfgInstance = riv.getAttribute("ruleId");         // e.g., //@configurations.0

                // IC8.3(C)(I): Match system element fragment to valid system elements
                boolean systemElementValid = validSystemElementInstances.stream()
                        .anyMatch(fullHref -> fullHref.endsWith(
                                sysElemInstance.replace("@systemElementIdentifications", "@systemElements")));

                // Configuration validity
                boolean configurationValid = validConfigurationInstances.contains(cfgInstance);

                if (!systemElementValid || !configurationValid) {
                    allValid = false;
                    invalidRIVs.add("RIV index " + i + " invalid: systemElementValid=" 
                            + systemElementValid + ", configurationValid=" + configurationValid
                            + ", sysElemInstance=" + sysElemInstance
                            + ", cfgInstance=" + cfgInstance);
                }
            }

            if (allValid) {
                System.out.println("IC8(C)(I) satisfied ✅ — all RIVs reference valid system element and configuration instances.");
                return true;
            } else {
                System.out.println("IC8(C)(I) NOT satisfied ❌ — invalid RIVs found:");
                invalidRIVs.forEach(System.out::println);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> getInvalidRIVs() {
        return new HashSet<>(invalidRIVs);
    }
}
