package propagation;

import edu.kit.kastel.sdq.coupling.models.conformance.IC10IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC10MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC5IandMChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC6IandMChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC7IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC7MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC8IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC8MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC9IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC9MChecker;

public class UncertaintyAnnotatorBuilder {

	private IC1MChecker ic1ModelChecker;
	private IC1IChecker ic1InstanceChecker;
	private IC2MChecker ic2ModelChecker;
	private IC2IChecker ic2InstanceChecker;
	private IC3MChecker ic3ModelChecker;
	private IC3IChecker ic3InstanceChecker;
	private IC4MChecker ic4ModelChecker;
	private IC4IChecker ic4InstanceChecker;
	private IC5IandMChecker iC5IandMChecker;
	private IC6IandMChecker iC6IandMChecker;
	private IC7MChecker iC7MChecker;
	private IC7IChecker iC7IChecker;
	private IC8MChecker iC8MChecker;
	private IC8IChecker iC8IChecker;
	private IC9MChecker iC9MChecker;
	private IC9IChecker iC9IChecker;
	private IC10MChecker iC10MChecker;
	private IC10IChecker iC10IChecker;
	private boolean inputReferenceConforms;
	private boolean outputReferenceConforms;

	public UncertaintyAnnotatorBuilder() {
	}

	public UncertaintyAnnotatorBuilder withIC1ModelChecker(IC1MChecker checker) {
		this.ic1ModelChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC1InstanceChecker(IC1IChecker checker) {
		this.ic1InstanceChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC2ModelChecker(IC2MChecker checker) {
		this.ic2ModelChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC2InstanceChecker(IC2IChecker checker) {
		this.ic2InstanceChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC3ModelChecker(IC3MChecker checker) {
		this.ic3ModelChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC3InstanceChecker(IC3IChecker checker) {
		this.ic3InstanceChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC4ModelChecker(IC4MChecker checker) {
		this.ic4ModelChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withIC4InstanceChecker(IC4IChecker checker) {
		this.ic4InstanceChecker = checker;
		return this;
	}

	public UncertaintyAnnotatorBuilder withInputReferenceConformance(boolean inputReferenceConforms) {
		this.inputReferenceConforms = inputReferenceConforms;
		return this;
	}

	public UncertaintyAnnotatorBuilder withOutputReferenceConformance(boolean outputReferenceConforms) {
		this.outputReferenceConforms = outputReferenceConforms;
		return this;
	}

	public UncertaintyAnnotator build() {
		return new UncertaintyAnnotator(ic1ModelChecker, ic1InstanceChecker, ic2ModelChecker, ic2InstanceChecker,
				ic3ModelChecker, ic3InstanceChecker, ic4ModelChecker, ic4InstanceChecker, iC5IandMChecker,
				iC6IandMChecker, iC7MChecker, iC7IChecker, iC8MChecker, iC8IChecker, iC9MChecker, iC9IChecker,
				iC10MChecker, iC10IChecker, inputReferenceConforms, outputReferenceConforms);
	}
}