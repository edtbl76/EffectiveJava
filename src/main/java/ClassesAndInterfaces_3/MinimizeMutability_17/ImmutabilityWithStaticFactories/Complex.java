package ClassesAndInterfaces_3.MinimizeMutability_17.ImmutabilityWithStaticFactories;

// Immutable Class that uses Static Factories as opposed to constructors
public class Complex {
    private final double realPart;
    private final double imaginaryPart;

    /*
        Private constructor prevents it from being accessed externally
        - so we can use a static factoriy instead
     */
    private Complex(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    /*
        Here is the static factory (preferred over the constructor)
     */
    public static Complex valueOf(double realPart, double imaginaryPart) {
        return new Complex(realPart, imaginaryPart);
    }
}
