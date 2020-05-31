package EnumsAndAnnotations_5.EnumsOverIntConstants_34.EnumTypeWithDataAndBehavior;

public enum OperationBetterPlusData {
    PLUS("+")   {public double apply(double x, double y) { return x + y; }},
    MINUS("-")   {public double apply(double x, double y) { return x - y; }},
    TIMES("*")   {public double apply(double x, double y) { return x * y; }},
    DIVIDE("/")  {public double apply(double x, double y) { return x / y; }};

    private final String symbol;
    OperationBetterPlusData(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);

}
