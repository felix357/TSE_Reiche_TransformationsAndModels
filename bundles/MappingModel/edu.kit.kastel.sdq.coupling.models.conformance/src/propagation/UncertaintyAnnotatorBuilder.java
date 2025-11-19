package propagation;

import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC3MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC4MChecker;

public class UncertaintyAnnotatorBuilder {

	private IC1MChecker ic1ModelChecker;
	private IC1IChecker ic1InstanceChecker;
	private IC2MChecker ic2ModelChecker;
	private IC2IChecker ic2InstanceChecker;
	private IC3MChecker ic3ModelChecker;
	private IC3IChecker ic3InstanceChecker;
	private IC4MChecker ic4ModelChecker;
	private IC4IChecker ic4InstanceChecker;
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
				ic3ModelChecker, ic3InstanceChecker, ic4ModelChecker, ic4InstanceChecker, inputReferenceConforms,
				outputReferenceConforms);
	}
}