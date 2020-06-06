package LambdasAndStreams_6.LambdasOverAnonClasses_42.LambdifiedExample;

import java.util.function.DoubleBinaryOperator;

/*
    This is a lambdified example of the Operation Enum.


 */
public enum Operation {
    PLUS    ("+", Double::sum),
    MINUS   ("-", (x, y) -> x - y),
    TIMES   ("*", (x, y) -> x * y),
    DIVIDE  ("/", (x, y) -> x / y);

    private final String symbol;
    private final DoubleBinaryOperator operator;
    Operation(String symbol, DoubleBinaryOperator operator) {
        this.symbol = symbol;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public double apply(double x, double y) {
        return operator.applyAsDouble(x, y);
    }
}
