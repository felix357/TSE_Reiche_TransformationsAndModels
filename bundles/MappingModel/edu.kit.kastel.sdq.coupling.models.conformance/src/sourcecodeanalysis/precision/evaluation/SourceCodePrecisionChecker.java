package sourcecodeanalysis.precision.evaluation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SourceCodePrecisionChecker {

	/**
	 * Evaluates precision and recall by comparing observed flows against the ground
	 * truth.
	 */
	public ComparisonResult evaluate(List<ObservedFlow> observed, Set<GroundTruthFlow> groundTruth) {

		int tp = 0, fp = 0;
		Set<GroundTruthFlow> unmatchedGT = new HashSet<>(groundTruth);

		for (ObservedFlow o : observed) {

			// Try to find a matching ground truth flow
			Optional<GroundTruthFlow> match = unmatchedGT.stream().filter(gt -> matches(o, gt)).findFirst();

			if (match.isPresent()) {
				tp++;
				unmatchedGT.remove(match.get());
			} else {
				fp++;
			}
		}

		int fn = unmatchedGT.size();
		return new ComparisonResult(tp, fp, fn);
	}

	/**
	 * Evaluates precision and recall by comparing observed flows against the ground
	 * truth, and returns true if precision > 0.5 and recall >= 1.
	 */
	public boolean isAcceptablePrecisionRecall(List<ObservedFlow> observed, Set<GroundTruthFlow> groundTruth) {
		ComparisonResult result = evaluate(observed, groundTruth);

		double recall = result.getRecall();

		return recall >= 1.0;
	}

	/**
	 * Checks whether an observed flow matches a ground truth flow.
	 *
	 * Matching rules: Data type must match Source component must match Target
	 * component must match security levels must match
	 */
	private boolean matches(ObservedFlow o, GroundTruthFlow gt) {
		if (!normalizeType(o.dataType()).equals(normalizeType(gt.dataType()))) {
			return false;
		}

		if (!o.fromComponent().equals(gt.fromComponent()))
			return false;

		if (!o.toComponent().equals(gt.toComponent()))
			return false;

		if (!o.fromLevel().equals(gt.fromLevel()))
			return false;
		if (!o.toLevel().equals(gt.toLevel())) {
			System.out.println(gt);
			return false;
		}

		return true;
	}

	private String normalizeType(String type) {
		return type.replace("\\[", "[").replace("\\]", "]").replaceAll("\\s+", "");
	}

}
