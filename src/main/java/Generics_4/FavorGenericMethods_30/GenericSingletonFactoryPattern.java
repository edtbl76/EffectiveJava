package Generics_4.FavorGenericMethods_30;

import java.util.function.UnaryOperator;

public class GenericSingletonFactoryPattern {

    // GENERIC SINGLETON FACTORY PATTERN
    private static final UnaryOperator<Object> IDENTITY_FN = (t) -> t;

    /*
        Unique case where the cast is type safe because the function doesn't modify the value
        - This is the simplest case of type-safety... we can't break what we don't change. (unless it starts
        broken...)
     */
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;
    }

    public static void main(String[] args) {
        String[] punches = {"jab", "cross", "hook", "uppercut"};
        UnaryOperator<String> samePunch = identityFunction();
        for (String punch : punches)
            System.out.println(samePunch.apply(punch));

        Number[] punchCodes = {1, 2, 3, 6};
        UnaryOperator<Number> sameCode = identityFunction();
        for (Number code : punchCodes)
            System.out.println(sameCode.apply(code));

    }
}
