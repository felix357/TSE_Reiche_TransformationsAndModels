package edu.kit.kastel.sdq.coupling.models.conformance;

public class SystemConfig {

	public final String basePath;
	public String pddc;
	public String modelCorrespondence;
	public String sourceCodeAnalysis;
	public String rivCorrespondence;
	public String riv;
	
    // Explicit IC2I required artifacts
    public String systemName;
    public String parameterAnnotationFile;
    public String pcmJavaCorrespondence;
    public String edfascCorrespondence;
    public String edfaConfiguration;
    
    // Needed for IC2M
    public String scConfigurationRepresentation;
    public String edfaConfigurationRepresentation;
    
    // Needed for IC5M
    public String scScarModel;
    public String javaModel;
    
    // Needed for IC7M
    public String correspondencesSCScar;
    
    public AnalysisCouplingType analysisCouplingType;
    
    // defines the analyses that are coupled
    public enum AnalysisCouplingType { CODEQLEDFA, JOANAEDFA }
	
    public SystemConfig(String root, SystemUnderEval sue, AnalysisCouplingType couplingType) {
        this.basePath = root + "/" + sue.folder;
        
        this.analysisCouplingType = couplingType;
      
        if (couplingType.equals(AnalysisCouplingType.CODEQLEDFA)) {
        	this.modelCorrespondence = sue.modelCorrespondence;
            this.sourceCodeAnalysis = sue.codeql;
            this.riv = sue.riv;
            this.rivCorrespondence = sue.rivCorrespondence;
            this.edfascCorrespondence   = sue.edfaCodeqlCorrespondence;
            this.scConfigurationRepresentation = sue.codeqlConfigurationRepresentation;
            this.scScarModel = sue.codeqlScarModel;
            this.correspondencesSCScar = sue.correspondencesCodeqlScar;
        } else {
        	this.modelCorrespondence = "correspondences.edfajoanacorrespondences";
        	this.sourceCodeAnalysis = "joana4extendeddataflowanalysis.joana";
            this.riv = "resultingvalues.joanaresultingvalues";
            this.rivCorrespondence = "correspondences.joanaresultingvaluescorrespondences";
            this.edfascCorrespondence   = "correspondences.edfajoanacorrespondences";
            this.scConfigurationRepresentation = "joana4extendeddataflowanalysis.configurationrepresentation";
            this.scScarModel = "scar.joanascar";
            this.correspondencesSCScar = "correspondences.joanascarcorrespondences";
        }
        this.pddc = sue.pddc;

        this.systemName                 = sue.systemName;
        this.parameterAnnotationFile    = sue.parameterAnnotationFile;
        this.pcmJavaCorrespondence      = sue.pcmJavaCorrespondence;
        this.edfaConfiguration          = sue.edfaConfiguration;
        
        this.edfaConfigurationRepresentation = sue.edfaConfigurationRepresentation;
        
        this.javaModel = sue.javaModelFile;
    }
    
    public SystemConfig overrideScScarModel(String codeqlScarModel) {
		this.scScarModel = codeqlScarModel;
		return this;
	}
    
    public SystemConfig overrideEdfaScCorrespondence(String edfaCodeqlCorrespondence) {
		this.edfascCorrespondence = edfaCodeqlCorrespondence;
		return this;
	}
    
    public SystemConfig overridePCMJavaCorrespondence(String pcmJavaCorrespondence) {
		this.pcmJavaCorrespondence = pcmJavaCorrespondence;
		return this;
	}

	public SystemConfig overridePDDC(String newName) {
		this.pddc = newName;
		return this;
	}

	public SystemConfig overrideRIV(String newName) {
		this.riv = newName;
		return this;
	}

	public SystemConfig overrideRivCorrespondence(String newName) {
		this.rivCorrespondence = newName;
		return this;
	}

	public SystemConfig overrideModelCorrespondence(String newName) {
		this.modelCorrespondence = newName;
		return this;
	}

	public SystemConfig overrideSc(String newName) {
		this.sourceCodeAnalysis = newName;
		return this;
	}
}
