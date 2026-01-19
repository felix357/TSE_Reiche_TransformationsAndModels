package sourcecodeanalysis.precision.evaluation;

public class ComparisonResult {

    private final int truePositives;
    private final int falsePositives;
    private final int falseNegatives;

    public ComparisonResult(int tp, int fp, int fn) {
        this.truePositives = tp;
        this.falsePositives = fp;
        this.falseNegatives = fn;
    }

    public int getTruePositives() {
        return truePositives;
    }

    public int getFalsePositives() {
        return falsePositives;
    }

    public int getFalseNegatives() {
        return falseNegatives;
    }

    public double getPrecision() {
        int denom = truePositives + falsePositives;
        return denom == 0 ? 1.0 : (double) truePositives / denom;
    }

    public double getRecall() {
        int denom = truePositives + falseNegatives;
        return denom == 0 ? 1.0 : (double) truePositives / denom;
    }

    @Override
    public String toString() {
        return String.format("TP=%d, FP=%d, FN=%d, Precision=%.2f, Recall=%.2f",
                truePositives, falsePositives, falseNegatives, getPrecision(), getRecall());
    }
}
