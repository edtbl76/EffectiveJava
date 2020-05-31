package EnumsAndAnnotations_5.EnumsOverIntConstants_34.EnumTypeWithDataAndBehavior;

public enum OperationBad {
    PLUS,
    MINUS,
    TIMES,
    DIVIDE;

    public double apply(double x, double y) {
        switch(this) {
            case PLUS:
                return x + y;
            case MINUS:
                return x - y;
            case TIMES:
                return x * y;
            case DIVIDE:
                return x / y;
        }
        /*
            This code won't compile without this throw statement., because the end of the method
            is technically reachable even though it won't ever be reached.
         */
        throw new AssertionError("Unknown op: " + this);
    }
}
