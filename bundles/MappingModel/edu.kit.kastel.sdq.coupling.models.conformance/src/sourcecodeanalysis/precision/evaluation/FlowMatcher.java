package sourcecodeanalysis.precision.evaluation;

public class FlowMatcher {

    public boolean matches(ObservedFlow o, GroundTruthFlow g) {
        return o.dataType().equals(g.dataType())
            && o.fromComponent().equals(g.fromComponent())
            && o.toComponent().equals(g.toComponent())
            && o.fromLevel().equals(g.fromLevel())
            && o.toLevel().equals(g.toLevel());
    }
}
