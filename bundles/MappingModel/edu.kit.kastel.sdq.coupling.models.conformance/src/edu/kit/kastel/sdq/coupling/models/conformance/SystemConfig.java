package edu.kit.kastel.sdq.coupling.models.conformance;

public class SystemConfig {

	public final String basePath;
	public String pddc;
	public String modelCorrespondence;
	public String codeql;
	public String rivCorrespondence;
	public String riv;

	public SystemConfig(String root, SystemUnderEval sue) {
		this.basePath = root + "/" + sue.folder;
		this.pddc = sue.pddc;
		this.modelCorrespondence = sue.modelCorrespondence;
		this.codeql = sue.codeql;
		this.rivCorrespondence = sue.rivCorrespondence;
		this.riv = sue.riv;
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
