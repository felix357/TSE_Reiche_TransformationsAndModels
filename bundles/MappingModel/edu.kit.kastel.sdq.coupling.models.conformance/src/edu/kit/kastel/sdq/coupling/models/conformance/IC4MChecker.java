package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Checker for IC4(C)(M): ensures that all applied security policies from
 * source code are mapped to architectural security characteristics affected by IC1,
 * checking the implication for every relevant configuration (cfg_C).
 */
public class IC4MChecker implements IChecker {

    private final String architecturalModelPath;
    private final String correspondencePath;
    private final String sourceCodeAnalysisPath;
    private final String configurationRepresentationPath;

    private final Set<String> allSecurityLiterals = new HashSet<>();
    private final List<Mapping> globalMappings = new ArrayList<>();

    private static class Mapping {
        String codeqlValue;
        String scsValue;

        Mapping(String codeql, String scs) {
            this.codeqlValue = codeql;
            this.scsValue = scs;
        }

        String getCodeqlValue() { return codeqlValue; }
    }
    
    public IC4MChecker(SystemConfig cfg) {
        this.architecturalModelPath = cfg.basePath + File.separator + cfg.pddc;
        this.correspondencePath = cfg.basePath + File.separator + cfg.modelCorrespondence;
        this.sourceCodeAnalysisPath = cfg.basePath + File.separator + cfg.codeql;
        this.configurationRepresentationPath = cfg.basePath + File.separator + cfg.codeqlConfigurationRepresentation;
    }

    private Set<String> getAllCodeqlLevels(String filePath) throws Exception {
        // ... (Parsing logic from IC1MChecker remains the same) ...
        Set<String> levels = new HashSet<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(new File(filePath))));
        NodeList levelNodes = doc.getElementsByTagName("appliedSecurityLevel");
        for (int i = 0; i < levelNodes.getLength(); i++) {
            levels.add(((Element) levelNodes.item(i)).getAttribute("name"));
        }
        return levels;
    }

    private String resolveCodeqlReference(String href) throws Exception {
        // ... (Reference resolution logic from IC1MChecker remains the same) ...
        String path = href.substring(
                href.indexOf("codeql4extendeddataflow.codeql#") + "codeql4extendeddataflow.codeql#".length());
        String[] parts = path.split("/");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(new File(sourceCodeAnalysisPath))));

        org.w3c.dom.Node current = doc.getDocumentElement();
        for (String part : parts) {
            if (part.startsWith("@")) {
                String[] tokens = part.substring(1).split("\\.");
                String tagName = tokens[0];
                int index = Integer.parseInt(tokens[1]);
                NodeList nodes = ((Element) current).getElementsByTagName(tagName);
                if (index < nodes.getLength()) {
                    current = nodes.item(index);
                } else {
                    return null;
                }
            }
        }
        if (current != null && current.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
            return ((Element) current).getAttribute("name");
        }
        return null;
    }

    /**
     * Parses the configuration representation file to get the IDs of all configurations (cfg_C).
     */
    private List<String> getAllConfigurationIDs(String filePath) throws Exception {
        List<String> cfgIDs = new ArrayList<>();
        File cfgFile = new File(filePath);
        if (!cfgFile.exists()) {
             System.err.println("Configuration file not found: " + filePath);
             return Collections.emptyList();
        }

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new InputSource(new FileInputStream(cfgFile)));
        
        NodeList cfgNodes = doc.getElementsByTagName("configurations"); 
        for (int i = 0; i < cfgNodes.getLength(); i++) {
            Element cfgElement = (Element) cfgNodes.item(i);
            String id = cfgElement.getAttribute("id");
            if (!id.isEmpty()) {
                cfgIDs.add(id);
            }
        }
        return cfgIDs;
    }
    
    /**
     * STUB: Determines the set of Security Policies (pol_C) used by a specific configuration (LHS: <cfg_C, pol_C>).
     * In the provided file structure, this is often the global set, but this method allows for future refinement.
     */
    private Set<String> getPoliciesUsedByConfiguration(String cfgID, Set<String> globalPolicies) {
        // For the simple CodeQL structure, we assume all global policies are used by the single configuration.
        return globalPolicies; 
    }
    
    /**
     * STUB: Determines the set of Security Characteristics (scs_C) affected by a specific configuration (LHS: <cfg_C, scs_C>).
     * This requires cross-referencing the configuration model (cfg_C) with the architectural model (scs_C).
     */
    private Set<String> getCharacteristicsAffectedByConfiguration(String cfgID, Set<String> globalCharacteristics) {
        return globalCharacteristics; 
    }


    @Override
    public boolean runCheck() {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            boolean overallIC4Fulfilled = true;

            // Step 1: Load architectural security literals (Global Scs_C)
            this.allSecurityLiterals.addAll(ConformanceUtils.getSecurityCharacteristicLiterals(architecturalModelPath));

            // Step 2: Load all applied CodeQL levels (Global Pol_C)
            Set<String> allCodeqlLevels = getAllCodeqlLevels(sourceCodeAnalysisPath);
            
            // Step 3: Parse global <pol_C, scs_C> correspondence (The RHS of the implication)
            File correspondenceFile = new File(correspondencePath);
            if (!correspondenceFile.exists()) {
                System.out.println("Error: Correspondence file not found at path: " + correspondencePath);
                return false;
            }

            Document doc = db.parse(new InputSource(new FileInputStream(correspondenceFile)));
            NodeList literalCorrespondences = doc.getElementsByTagName("literalSecurityLevelCorrespondences");

            for (int i = 0; i < literalCorrespondences.getLength(); i++) {
                Element correspondence = (Element) literalCorrespondences.item(i);
                Element securityLevelCodeQL = (Element) correspondence.getElementsByTagName("securityLevel_CodeQL").item(0);
                Element literalsEDFA = (Element) correspondence.getElementsByTagName("literals_EDFA").item(0);

                String codeqlHref = securityLevelCodeQL.getAttribute("href");
                String edfaHref = literalsEDFA.getAttribute("href");

                String edfaValue = ConformanceUtils.resolvePddcReference(edfaHref, architecturalModelPath);
                String codeqlValue = resolveCodeqlReference(codeqlHref);

                if (edfaValue != null && codeqlValue != null && allSecurityLiterals.contains(edfaValue)) {
                    globalMappings.add(new Mapping(codeqlValue, edfaValue));
                }
            }

            Set<String> mappedCodeqlLevels = globalMappings.stream()
                .map(Mapping::getCodeqlValue)
                .collect(Collectors.toSet());

            // Step 4: Check IC4(C)(M) per configuration

            // Identify all configurations (CFG_C)
            List<String> configurationIDs = getAllConfigurationIDs(configurationRepresentationPath);
            System.out.println("\nChecking IC4(C)(M) across " + configurationIDs.size() + " configurations.");

            if (configurationIDs.isEmpty()) {
                System.out.println("No configurations (cfg_C) found. The check is vacuously FULFILLED. ✅");
                return true;
            }

            for (String cfgID : configurationIDs) {
                System.out.println("\n--- Checking Configuration: " + cfgID + " ---");
                
                Set<String> policiesForCfg = getPoliciesUsedByConfiguration(cfgID, allCodeqlLevels);
                Set<String> characteristicsForCfg = getCharacteristicsAffectedByConfiguration(cfgID, allSecurityLiterals);

                if (policiesForCfg.isEmpty() || characteristicsForCfg.isEmpty()) {
                    System.out.println("Configuration " + cfgID + " is irrelevant (LHS of implication is false). Check fulfilled for this config.");
                    continue;
                }
                
                boolean cfgCheckFulfilled = mappedCodeqlLevels.containsAll(policiesForCfg);

                if (cfgCheckFulfilled) {
                    System.out.println("IC4(C)(M) for cfg " + cfgID + " is FULFILLED. ✅");
                } else {
                    Set<String> unmapped = new HashSet<>(policiesForCfg);
                    unmapped.removeAll(mappedCodeqlLevels);
                    System.out.println("IC4(C)(M) for cfg " + cfgID + " is NOT FULFILLED. ❌");
                    System.out.println("Violation: Used policies missing mapping: " + unmapped);
                    overallIC4Fulfilled = false;
                }
            }
            
            if (overallIC4Fulfilled) {
                System.out.println("\nOVERALL IC4(C)(M) FULFILLED across all configurations. ✅");
            } else {
                System.out.println("\nOVERALL IC4(C)(M) FAILED. ❌");
            }
            
            return overallIC4Fulfilled;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}