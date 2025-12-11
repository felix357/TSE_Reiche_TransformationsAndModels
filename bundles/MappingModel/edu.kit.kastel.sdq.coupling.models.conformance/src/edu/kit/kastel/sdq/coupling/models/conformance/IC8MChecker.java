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

/**
 * IC8(T)(M) checker:
 * Checks if all ResolvedImplementationValues (RIVs) in the Resolved Implementation Values Model
 * reference system elements and configurations that correspond to system elements (IC5M) 
 * and configurations (IC7M) from the Source Code Analysis Result.
 */
public class IC8MChecker implements IChecker {

    private final String rivModelPath;
    private final IC5IandMChecker ic5Checker;
    private final IC7MChecker ic7Checker;

    private final Set<String> invalidRIVs = new HashSet<>();

    // Mapping SCAR cfg_R -> RIV configuration fragment cfg_V (e.g., "//@configurations.0")
    private final Map<String, String> cfgRtoConfigurationFragment = new HashMap<>();

    public IC8MChecker(SystemConfig cfg, IC5IandMChecker ic5Checker, IC7MChecker ic7Checker) {
        this.rivModelPath = cfg.basePath + File.separator + cfg.riv;
        this.ic5Checker = ic5Checker;
        this.ic7Checker = ic7Checker;

        // Build mapping: SCAR cfg_R -> RIV configuration fragment
        int index = 0;
        for (String cfgR : ic7Checker.getFoundCfgR()) {
            cfgRtoConfigurationFragment.put(cfgR, "//@configurations." + index);
            index++;
        }
    }

    @Override
    public boolean runCheck() {
        try {
            File file = new File(rivModelPath);
            if (!file.exists()) {
                System.out.println("IC8(T)(M): RIV model file not found: " + rivModelPath);
                return false;
            }

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(new FileInputStream(file)));

            NodeList rivNodes = doc.getElementsByTagName("resultingValues");
            boolean allValid = true;

            Set<String> validSystemElements = ic5Checker.getMappedSystemElementsR();
            Set<String> validConfigurations = new HashSet<>(cfgRtoConfigurationFragment.values());

            boolean anyValidSystemElement = false; // IC8.1
            boolean anyValidConfiguration = false; // IC8.2

            for (int i = 0; i < rivNodes.getLength(); i++) {
                Element riv = (Element) rivNodes.item(i);

                String sysElemRef = riv.getAttribute("parameter");
                String cfgRef = riv.getAttribute("ruleId");

                boolean systemElementValid = validSystemElements.stream()
                        .anyMatch(s -> s.endsWith(sysElemRef));

                boolean configurationValid = validConfigurations.contains(cfgRef);

                if (systemElementValid) {
                    anyValidSystemElement = true;
                }
                if (configurationValid) {
                    anyValidConfiguration = true;
                }

                if (!systemElementValid || !configurationValid) {
                    allValid = false;
                    invalidRIVs.add("RIV index " + i + " invalid: systemElementValid="
                            + systemElementValid + ", configurationValid=" + configurationValid
                            + ", sysElemRef=" + sysElemRef + ", cfgRef=" + cfgRef);
                }
            }

            // Explicit existential checks (IC8.1 and IC8.2)
            if (!anyValidSystemElement) {
                allValid = false;
                System.out.println("IC8.1(T)(M) violated: No RIV references a valid system element.");
            }

            if (!anyValidConfiguration) {
                allValid = false;
                System.out.println("IC8.2(T)(M) violated: No RIV references a valid configuration.");
            }

            if (allValid) {
                System.out.println("IC8(T)(M) satisfied ✅ — all RIVs reference valid system elements and configurations.");
            } else {
                System.out.println("IC8(T)(M) NOT satisfied ❌ — invalid RIVs found:");
                invalidRIVs.forEach(System.out::println);
            }

            return allValid;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public Set<String> getInvalidRIVs() {
        return new HashSet<>(invalidRIVs);
    }
}
