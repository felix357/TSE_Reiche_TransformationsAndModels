package sourcecodeanalysis.precision.evaluation;

public record GroundTruthFlow(
	    String dataType,
	    String fromComponent,
	    String fromMethod,
	    String fromLevel,
	    String toComponent,
	    String toMethod,
	    String toLevel
	) {}

