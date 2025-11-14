package propagation;

import edu.kit.kastel.sdq.coupling.models.conformance.IC1IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC1MChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2IChecker;
import edu.kit.kastel.sdq.coupling.models.conformance.IC2MChecker;

public class UncertaintyAnnotatorBuilder {

	private IC1MChecker ic1ModelChecker;
	private IC1IChecker ic1InstanceChecker;
	private IC2MChecker ic2ModelChecker;
	private IC2IChecker ic2InstanceChecker;

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

	public UncertaintyAnnotator build() {
		return new UncertaintyAnnotator(ic1ModelChecker, ic1InstanceChecker, ic2ModelChecker, ic2InstanceChecker);
	}
}