package edu.kit.kastel.sdq.coupling.models.conformance;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements the IC2.1(T)(M) Consistency Checker to verify the required traceability 
 * and consistency of security annotations between the architectural model
 * and the corresponding source code.
 */
public class IC2dot1MChecker implements IChecker {
	
	private final String annotationPath;
	private final String correspondencePath;
	private final String expectedLiteralPrefix;
	
	public IC2dot1MChecker(String annotationPath, String correspondencePath, String expectedLiteralPrefix) {
		this.annotationPath = annotationPath;
		this.correspondencePath = correspondencePath;
		this.expectedLiteralPrefix = expectedLiteralPrefix;
	}
	
	/**
	 * Executes the IC2.1(T)(M) check.
	 *
	 * This method finds and maps architectural elements to 
	 * code elements using the configuration files provided.
	 *
	 * @param annotationPath Path to the architectural parameter annotation file.
	 * @param correspondencePath Path to the correspondence file.
	 * @param expectedLiteralPrefix The URI prefix identifying relevant security literals.
	 * @return {@code true} if the IC2.1 holds, {@code false} otherwise.
	 */
	public boolean runCheck() {
		
		Map<String, Set<String>> m = identifyAnnotatedArchitecturalElements(annotationPath, expectedLiteralPrefix);
		
		Map<String, Set<String>> ma = getCorrespondingCodeElements(m, correspondencePath);
		
		return !ma.isEmpty();
	}
	
	/**
	 * Identifies all system elements within the architectural model
	 * that are annotated with a security characteristic.
	 *
	 * @param annotationPath The file path to the architectural parameter annotation file (e.g., *.parameterannotation).
     * @param expectedLiteralPrefix The expected URI prefix for the security literal (e.g., 'jpmail.pddc#_SIw9sNGFEe6e8_rNGeIvbg-characteristicEnumerations@0.literals@').
	 * @return A map where the key is the URI of the security literal (String) and the 
	 * value is a set of URIs (Set<String>) representing the annotated architectural 
	 * elements.
	 */
	public Map<String, Set<String>> identifyAnnotatedArchitecturalElements(String annotationPath, String expectedLiteralPrefix) {
	    String annotationContent = readFileContent(annotationPath);
	    Map<String, Set<String>> deltaA_by_literal = new HashMap<>();
	    
	    if (annotationContent.isEmpty()) {
            System.out.println("  [SCHRITT 1] Dateinhalt leer oder konnte nicht gelesen werden. Delta_A ist leer.");
            return deltaA_by_literal;
        }
	    
	    System.out.println("  [SCHRITT 1] Bestimme alle Delta_A: Suche und strukturiere Elemente nach Sicherheitsliteral...");
	    
	    Pattern pattern = Pattern.compile(
	        "<values\\s+href=\"([^\"]+)\"[^>]*>\\s*([\\s\\S]*?)</characteristics>\\s*<parameterIdentification>\\s*<operationSignature[^>]*>\\s*<parameter\\s+href=\"([^\"]+)\"", 
	        Pattern.CASE_INSENSITIVE
	    );

	    Matcher matcher = pattern.matcher(annotationContent);
	    
	    int totalAnnotationsFound = 0;
	    while (matcher.find()) {
            String literalUri = matcher.group(1);
            String parameterId = matcher.group(3);
            
            if (literalUri.contains(expectedLiteralPrefix)) {
                
                deltaA_by_literal
                    .computeIfAbsent(literalUri, k -> new HashSet<>())
                    .add(parameterId);
                
                totalAnnotationsFound++;
                System.out.println("    -> Element gefunden: " + parameterId + " -> Literal: " + literalUri);
            }
	    }
	    
        int uniqueElements = deltaA_by_literal.values().stream().mapToInt(Set::size).sum();
	    System.out.println("  [SCHRITT 1] Abschluss: " + totalAnnotationsFound + " Annotationen (insgesamt) gefunden. " 
                           + deltaA_by_literal.size() + " Literale mit " + uniqueElements + " eindeutigen Elementen.");
	    
	    return deltaA_by_literal;
	}
	
	/**
	 * Maps the annotated architectural elements to their corresponding 
	 * code elements using the provided correspondence file.
	 *
	 * @param deltaA_by_literal Annotated architectural elements grouped by security literal.
	 * @param correspondencePath Path to the architectural-to-code correspondence file (*.pcmjavacorrespondence).
	 * @return A map where keys are security literal URIs and values are the corresponding 
	 * Java parameter URIs.
	 */
	public Map<String, Set<String>> getCorrespondingCodeElements(Map<String, Set<String>> deltaA_by_literal, String correspondencePath) {
	    String correspondenceContent = readFileContent(correspondencePath);
	    Map<String, Set<String>> deltaCsC_by_literal = new HashMap<>();
	    
	    if (correspondenceContent.isEmpty() || deltaA_by_literal.isEmpty()) {
	        System.out.println("  [SCHRITT 2] Korrespondenzdatei oder Delta_A sind leer. Delta_cs^C ist leer.");
	        return deltaCsC_by_literal;
	    }

	    System.out.println("  [SCHRITT 2] Bestimme Delta_cs^C: Suche korrespondierende Java-Elemente...");

	    for (Map.Entry<String, Set<String>> entry : deltaA_by_literal.entrySet()) {
	        String literalUri = entry.getKey();
	        Set<String> deltaA_ids = entry.getValue();
	        
	        Set<String> deltaCsC_uris = new HashSet<>();
	        
	        for (String deltaA_id : deltaA_ids) {
	            String uniqueIdSuffix = deltaA_id.substring(deltaA_id.indexOf('#'));
	            String escapedIdSuffix = Pattern.quote(uniqueIdSuffix);
	            
	            String regex = escapedIdSuffix 
	                           + "[\\s\\S]*?"
	                           + "<javaParameter\\s+href=\"([^\"]+)\"";
	            
	            Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	            Matcher matcher = pattern.matcher(correspondenceContent);
	            
	            if (matcher.find()) {
	                String javaUri = matcher.group(1); 
	                deltaCsC_uris.add(javaUri);
	                System.out.println("    -> Korrespondenz gefunden für " + literalUri.substring(literalUri.lastIndexOf('@')) + ": " + uniqueIdSuffix + " -> " + javaUri);
	            } else {
	                System.err.println("    -> WARNUNG: Keine Code-Korrespondenz gefunden für Architekturelement: " + deltaA_id);
	            }
	        }
	        
	        deltaCsC_by_literal.put(literalUri, deltaCsC_uris);
	    }
	    
	    int totalElements = deltaCsC_by_literal.values().stream().mapToInt(Set::size).sum();
	    System.out.println("  [SCHRITT 2] Abschluss: " + totalElements + " Delta_cs^C Elemente identifiziert.");
	    return deltaCsC_by_literal;
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