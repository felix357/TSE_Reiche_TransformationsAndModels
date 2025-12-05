package edu.kit.kastel.sdq.coupling.models.conformance;

public enum SystemUnderEval {

	JPMAIL("JPMail", "jpmail", "jpmail.parameterannotation", "correspondences.pcmjavacorrespondence",
			"correspondences.edfacodeqlcorrespondences", "extendeddataflow.configurationrepresentation", "jpmail.pddc",
			"correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues",
			"codeql4extendeddataflow.configurationrepresentation", "extendeddataflow.configurationrepresentation", "scar.codeqlscar", "java4extendeddataflow.java"),

	COCOME("CoCoMe", "cocome", "cocome.parameterannotation", "correspondences.pcmjavacorrespondence",
			"correspondences.edfacodeqlcorrespondences", "extendeddataflow.configurationrepresentation",

			"cocome.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues",
			"codeql4extendeddataflow.configurationrepresentation", "extendeddataflow.configurationrepresentation", "scar.codeqlscar", "java4extendeddataflow.java"),

	ECLIPSE_SECURE_STORAGE("EclipseSecureStorage", "eclipsesecurestorage", "eclipsesecurestorage.parameterannotation",
			"correspondences.pcmjavacorrespondence", "correspondences.edfacodeqlcorrespondences",
			"extendeddataflow.configurationrepresentation",

			"eclipsesecurestorage.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues",
			"codeql4extendeddataflow.configurationrepresentation", "extendeddataflow.configurationrepresentation", "scar.codeqlscar", "java4extendeddataflow.java"),

	TRAVEL_PLANNER("TravelPlanner", "travelplanner", "travelplanner.parameterannotation",
			"correspondences.pcmjavacorrespondence", "correspondences.edfacodeqlcorrespondences",
			"extendeddataflow.configurationrepresentation", "travelplanner.pddc",
			"correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues",
			"codeql4extendeddataflow.configurationrepresentation", "extendeddataflow.configurationrepresentation", "scar.codeqlscar", "java4extendeddataflow.java");

	public final String folder;

	public final String systemName;
	public final String parameterAnnotationFile;
	public final String pcmJavaCorrespondence;
	public final String edfaCodeqlCorrespondence;
	public final String edfaConfiguration;

	public final String pddc;
	public final String modelCorrespondence;
	public final String codeql;
	public final String rivCorrespondence;
	public final String riv;

	public final String codeqlConfigurationRepresentation;
	public final String edfaConfigurationRepresentation;
	
	public final String codeqlScarModel;
	public final String javaModelFile;

	SystemUnderEval(String folder, String systemName, String parameterAnnotationFile, String pcmJavaCorrespondence,
			String edfaCodeqlCorrespondence, String edfaConfiguration, String pddc, String modelCorrespondence,
			String codeql, String rivCorrespondence, String riv, String codeqlConfigurationRepresentation,
			String edfaConfigurationRepresentation, String codeqlScarModel, String javaModelFile) {
		this.folder = folder;
		this.systemName = systemName;
		this.parameterAnnotationFile = parameterAnnotationFile;
		this.pcmJavaCorrespondence = pcmJavaCorrespondence;
		this.edfaCodeqlCorrespondence = edfaCodeqlCorrespondence;
		this.edfaConfiguration = edfaConfiguration;

		this.pddc = pddc;
		this.modelCorrespondence = modelCorrespondence;
		this.codeql = codeql;
		this.rivCorrespondence = rivCorrespondence;
		this.riv = riv;

		this.codeqlConfigurationRepresentation = codeqlConfigurationRepresentation;
		this.edfaConfigurationRepresentation = edfaConfigurationRepresentation;
		
		this.codeqlScarModel = codeqlScarModel;
		this.javaModelFile = javaModelFile;
	}
}
