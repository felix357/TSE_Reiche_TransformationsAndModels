package edu.kit.kastel.sdq.coupling.models.conformance;

public enum SystemUnderEval {
	JPMAIL("JPMail", "jpmail.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues"),
	COCOME("CoCoMe", "cocome.pddc", "correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues"),
	ECLIPSE_SECURE_STORAGE("EclipseSecureStorage", "eclipsesecurestorage.pddc",
			"correspondences.edfacodeqlcorrespondences", "codeql4extendeddataflow.codeql",
			"correspondences.codeqlresultingvaluescorrespondences", "resultingvalues.codeqlresultingvalues"),
	TRAVEL_PLANNER("TravelPlanner", "travelplanner.pddc", "correspondences.edfacodeqlcorrespondences",
			"codeql4extendeddataflow.codeql", "correspondences.codeqlresultingvaluescorrespondences",
			"resultingvalues.codeqlresultingvalues");

	public final String folder;
	public final String pddc;
	public final String modelCorrespondence;
	public final String codeql;
	public final String rivCorrespondence;
	public final String riv;

	SystemUnderEval(String folder, String pddc, String modelCorrespondence, String codeql, String rivCorrespondence,
			String riv) {
		this.folder = folder;
		this.pddc = pddc;
		this.modelCorrespondence = modelCorrespondence;
		this.codeql = codeql;
		this.rivCorrespondence = rivCorrespondence;
		this.riv = riv;
	}
}
