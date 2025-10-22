package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements the IC2.2(T)(M) Configuration Consistency Checker.
 *
 * This checker checks whether a correspondence exists between a security-
 * relevant configuration in the architectural model and a configuration
 * in the code model. The check starts with the correspondence file
 * and iterates over all configuration blocks to verify their relevance.
 */
public class IC2dot2MChecker implements IChecker {
	
	private final String edfaConfigPath;
    private final String edfacodeqlCorrespondencePath;
    private final String annotationFile;

    public IC2dot2MChecker(String edfaConfigPath, String edfacodeqlCorrespondencePath, String annotationFile) {
        this.edfaConfigPath = edfaConfigPath;
        this.edfacodeqlCorrespondencePath = edfacodeqlCorrespondencePath;
        this.annotationFile = annotationFile;
    }
    
    /**
     * Executes the IC2.2(T)(M) consistency check.
     * It iterates over all configuration correspondences and
     * checks whether the referenced CFG_A uses the security annotations as input.
     *
     * @return true if at least one relevant and corresponding configuration is found.
     */
	@Override
	public boolean runCheck() {
        System.out.println("  [IC2.2] Start: Iteriere über Konfigurations-Korrespondenzen und prüfe Relevanz.");

        String correspondenceContent = readFileContent(this.edfacodeqlCorrespondencePath);
        if (correspondenceContent == null || correspondenceContent.isEmpty()) {
            System.err.println("  [IC2.2] Fehler: Korrespondenzdatei-Inhalt leer oder konnte nicht gelesen werden.");
            return false;
        }

        String configBlockRegex = "(<configurationCorrespondences.*?/configurationCorrespondences>)";
        Pattern blockPattern = Pattern.compile(configBlockRegex, Pattern.DOTALL);
        Matcher blockMatcher = blockPattern.matcher(correspondenceContent);

        while (blockMatcher.find()) {
            String fullCorrespondenceBlock = blockMatcher.group(1);
            
            String edfaHref = extractConfigurationEDFAHref(fullCorrespondenceBlock);
            
            if (edfaHref != null) {
                System.out.println("\n  [SCHRITT A] Korrespondenz gefunden. EDFA Href: " + edfaHref);

                if (isConfigurationRelevant(this.edfaConfigPath, edfaHref, this.annotationFile)) {
                	
                    String codeConfigUri = extractConfigurationCodeQLHref(fullCorrespondenceBlock);
                    
                    System.out.println("  [SCHRITT B] Relevanz bestätigt. CFG_CS^C gefunden (KONSISTENT). URI: " + codeConfigUri);
                    return codeConfigUri != null;
                } else {
                    System.out.println("  [SCHRITT B] Relevanz verneint. Konfiguration nutzt die Sicherheits-Annotation NICHT.");
                }
            }
        }

        System.out.println("\n  [IC2.2] Abschluss: Keine relevante Konfigurations-Korrespondenz gefunden (INKONSISTENT).");
        return false;
	}
	
	/**
	 * Checks whether the architectural configuration referenced by 'edfaHref'
	 * in the EDFA configuration file utilizes the 'annotationFileName' as an input.
	 *
	 * @param edfaConfigPath Path to the EDFA configuration file.
	 * @param edfaHref The positional reference (e.g., extendeddataflow.configurationrepresentation#//@configurations.0).
	 * @param annotationFileName The file name of the annotation file (e.g., jpmail.parameterannotation).
	 * @return true if the referenced CFG_A block uses the annotation as an input.
	 */
    private boolean isConfigurationRelevant(String edfaConfigPath, String edfaHref, String annotationFileName) {
        String configContent = readFileContent(edfaConfigPath);
        if (configContent == null || configContent.isEmpty()) {
            return false;
        }

        Pattern posPattern = Pattern.compile("#/(.*)");
        Matcher posMatcher = posPattern.matcher(edfaHref);
        if (!posMatcher.find()) return false; 
        String positionMarker = posMatcher.group(1); 
        
        String relevanceRegex = "<configurations[^>]*id=\"([^\"]+)\"[^>]*>\\s*"
                                + ".*?"
                                + "<inputs[^>]*href=\"[^\"]*" + Pattern.quote(annotationFileName) + "[^>]*>"
                                + ".*?"
                                + "</configurations>";
        
        Pattern pattern = Pattern.compile(relevanceRegex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(configContent);
        
        return matcher.find();
    }
    
    /**
     * Extracts the positional reference (href) to the EDFA configuration from a correspondence block.
     */
    private String extractConfigurationEDFAHref(String correspondenceBlock) {
        String regex = "<configuration_EDFA\\s+href=\"([^\"]+)\"[^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correspondenceBlock);
        return matcher.find() ? matcher.group(1) : null;
    }

    /**
     * Extracts the URI to the CodeQL configuration from a correspondence block.
     */
    private String extractConfigurationCodeQLHref(String correspondenceBlock) {
        String regex = "<configuration_CodeQL\\s+href=\"([^\"]+)\"[^>]*>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(correspondenceBlock);
        return matcher.find() ? matcher.group(1) : null;
    }
    
    private String readFileContent(String path) {
        System.out.println("  [IO] Attempting to read content from path: " + path);
        try {
            return Files.readString(Path.of(path), StandardCharsets.UTF_8);

        } catch (IOException e) {
            System.err.println("ERROR: Could not read file content for path: " + path);
            System.err.println("Details: " + e.getMessage());
            return "";
        }
    }
}