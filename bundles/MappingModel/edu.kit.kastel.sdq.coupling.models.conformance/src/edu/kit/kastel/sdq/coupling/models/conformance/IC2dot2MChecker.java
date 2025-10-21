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
 * This checker checks if necessary existence and correspondence of a 
 * security-relevant configuration defined in the architectural model 
 * (EDFA) with a corresponding configuration in the code model (CodeQL).
 * It ensures that the required analysis setup is mapped from architecture to code.
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
     *
     * @return true if at least one corresponding code element exists for the
     *         annotated architectural elements, false otherwise.
     */
	@Override
	public boolean runCheck() {
        String architecturalConfigId = identifyArchitecturalConfiguration(this.edfaConfigPath, this.annotationFile);

        if (architecturalConfigId == null || architecturalConfigId.isEmpty()) {
            System.err.println("  [IC2.2] Fehler: Keine relevante Architektur-Konfiguration (CFG_A) gefunden.");
            return false;
        }

        String codeConfigUri = findCorrespondingCodeConfiguration(this.edfacodeqlCorrespondencePath, architecturalConfigId);

        boolean isConsistent = codeConfigUri != null && !codeConfigUri.isEmpty();
        
        System.out.println("  [IC2.2] Abschluss: CFG_CS^C ist " + (isConsistent ? "nicht leer (KONSISTENT)." : "leer (INKONSISTENT)."));
        
        return isConsistent;
	}
	
	/**
	 * Verifies the existence of a corresponding CodeQL configuration
	 * for the architectural configuration by searching the correspondence model.
	 *
	 * @param correspondencePath The file path to the EDFA-CodeQL correspondence model.
	 * @param architecturalConfigId The ID of the architectural configuration.
	 * @return The URI (href) of the CodeQL configuration, or {@code null} if the existence check fails.
	 */
	public String findCorrespondingCodeConfiguration(String correspondencePath, String architecturalConfigId) {
	    String content = readFileContent(correspondencePath);
	    System.out.println("  [SCHRITT 2] Finde CFG_CS^C: Suche korrespondierende Code-Konfiguration...");

	    if (content == null || content.isEmpty()) {
	        System.err.println("  [SCHRITT 2] Fehler: Korrespondenzdatei-Inhalt leer oder konnte nicht gelesen werden.");
	        return null;
	    }

	    String regex = "<configurationCorrespondences[^>]*>\\s*" 
	                   + ".*?<configuration_CodeQL\\s+href=\"([^\"]+)\"[^>]*>";
	                   
	    Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
	    
	    Matcher matcher = pattern.matcher(content);
	    
	    if (matcher.find()) {
	        String codeConfigUri = matcher.group(1); 
	        System.out.println("    -> CFG_CS^C gefunden: URI = " + codeConfigUri);

	        return codeConfigUri;
	    }
	    
	    System.out.println("    -> CFG_CS^C nicht gefunden: Keine Konfigurations-Korrespondenz vorhanden.");
	    return null;
	}
	
	/**
	 * Identifies the unique ID of the relevant Architectural Configuration
	 * from the EDFA configuration file.
	 *
	 * It filters configurations by matching the provided annotation file name in the input references.
	 *
	 * @param edfaConfigPath Path to the EDFA configuration file.
	 * @param annotationFileName The file name of the annotation model used as input (e.g., '*.parameterannotation').
	 * @return The ID of the matching, or {@code null} if not found.
	 */
	public String identifyArchitecturalConfiguration(String edfaConfigPath, String annotationFileName) {
	    String content = readFileContent(edfaConfigPath);
	    System.out.println("  [SCHRITT 1] Suche CFG_A: Identifiziere Architektur-Konfiguration...");
	    
	    if (content == null || content.isEmpty()) {
	        System.err.println("  [SCHRITT 1] Fehler: Konfigurationsdatei-Inhalt leer oder konnte nicht gelesen werden.");
	        return null;
	    }
	   
	    
	    String regex = "<configurations[^>]*id=\"([^\"]+)\"[^>]*>\\s*"
	                   + "(?:[^<]*<inputs[^>]*>)?\\s*"
	                   + "<inputs[^>]*href=\"[^\"]*" + Pattern.quote(annotationFileName) + "[^>]*>";
	    
	    Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);

	    Matcher matcher = pattern.matcher(content);
	    
	    if (matcher.find()) {
	        String configId = matcher.group(1);
	        System.out.println("    -> CFG_A gefunden: ID = " + configId);
	        return configId;
	    }
	    
	    System.out.println("    -> CFG_A nicht gefunden: Keine Konfiguration referenziert '" + annotationFileName + "'.");
	    return null;
	}

	private String readFileContent(String path) {
	    System.out.println("  [IO] Attempting to read content from path: " + path);
	    try {
	        return Files.readString(Path.of(path), StandardCharsets.UTF_8);

	    } catch (IOException e) {
	        System.err.println("ERROR: Could not read file content for path: " + path);
	        System.err.println("Details: " + e.getMessage());
	        return "";
	    } catch (Exception e) {
	        System.err.println("ERROR: An unexpected error occurred while processing path: " + path);
	        return "";
	    }
	}
	

}
