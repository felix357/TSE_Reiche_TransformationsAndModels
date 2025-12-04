package edu.kit.kastel.sdq.coupling.models.conformance;

public class SystemConfig {

	public final String basePath;
	public String pddc;
	public String modelCorrespondence;
	public String codeql;
	public String rivCorrespondence;
	public String riv;
	
    // Explicit IC2I required artifacts
    public String systemName;
    public String parameterAnnotationFile;
    public String pcmJavaCorrespondence;
    public String edfaCodeqlCorrespondence;
    public String edfaConfiguration;
    
    // Needed for IC2M
    public String codeqlConfigurationRepresentation;
    public String edfaConfigurationRepresentation;
	
    public SystemConfig(String root, SystemUnderEval sue) {
        this.basePath = root + "/" + sue.folder;
        
        this.pddc = sue.pddc;
        this.modelCorrespondence = sue.modelCorrespondence;
        this.codeql = sue.codeql;
        this.rivCorrespondence = sue.rivCorrespondence;
        this.riv = sue.riv;

        this.systemName                 = sue.systemName;
        this.parameterAnnotationFile    = sue.parameterAnnotationFile;
        this.pcmJavaCorrespondence      = sue.pcmJavaCorrespondence;
        this.edfaCodeqlCorrespondence   = sue.edfaCodeqlCorrespondence;
        this.edfaConfiguration          = sue.edfaConfiguration;
        
        this.codeqlConfigurationRepresentation = sue.codeqlConfigurationRepresentation;
        this.edfaConfigurationRepresentation = sue.edfaConfigurationRepresentation;
    }
    
    public SystemConfig overrideEdfaCodeqlCorrespondence(String edfaCodeqlCorrespondence) {
		this.edfaCodeqlCorrespondence = edfaCodeqlCorrespondence;
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

	public SystemConfig overrideCodeQL(String newName) {
		this.codeql = newName;
		return this;
	}
}
