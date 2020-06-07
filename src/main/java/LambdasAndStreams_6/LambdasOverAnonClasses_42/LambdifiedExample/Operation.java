package LambdasAndStreams_6.LambdasOverAnonClasses_42.LambdifiedExample;

import java.util.function.DoubleBinaryOperator;

/*
    This is a lambdified example of the Operation Enum.

    We've added the DoubleBinaryOperator interface for the lambdas that represent the enum constant's behavior.
    - this represents a function that takes two double arguments and returns a double result.

    - this is one of the MANY predefined functional interfaces in java.util.function.

    - remember:
        - args passed to enum constructors are evaluated in a static context.
        - therefore, lambdas in enum constructors can't access instance members of the enum.

        Lambdas have limited usefulness in enums:
            - constant-specific class bodies are still the way to go if an enum type has constant-specific behavior
            that violates the best practices recommendations for lambdas.

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
