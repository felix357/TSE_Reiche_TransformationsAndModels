package sourcecodeanalysis.precision.evaluation;

public record ObservedFlow(
	    String dataType,
	    String fromComponent,
	    String fromMethod,
	    String fromLevel,
	    String toComponent,
	    String toMethod,
	    String toLevel
	) {}

