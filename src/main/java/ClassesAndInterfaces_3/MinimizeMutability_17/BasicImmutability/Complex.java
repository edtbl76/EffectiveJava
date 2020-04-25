package ClassesAndInterfaces_3.MinimizeMutability_17.BasicImmutability;

/*
    Immutable Complex Number Class
 */
public final class Complex {
    private final double realNumber;
    private final double imaginaryNumber;

    public Complex(double realNumber, double imaginaryNumber) {
        this.realNumber = realNumber;
        this.imaginaryNumber = imaginaryNumber;
    }

    public double getRealNumber() {
        return realNumber;
    }

    public double getImaginaryNumber() {
        return imaginaryNumber;
    }

    public Complex minus(Complex complex) {
        return new Complex(
                realNumber - complex.realNumber, imaginaryNumber - complex.imaginaryNumber);
    }

    public Complex times(Complex complex) {
        return new Complex(realNumber * complex.realNumber - imaginaryNumber * complex.imaginaryNumber,
                realNumber * complex.imaginaryNumber + imaginaryNumber * complex.realNumber);
    }

    public Complex dividedBy(Complex complex) {
        double tmp = complex.realNumber * complex.realNumber + complex.imaginaryNumber * complex.imaginaryNumber;
        return new Complex((realNumber * complex.realNumber + imaginaryNumber * complex.imaginaryNumber) / tmp,
                (imaginaryNumber * complex.realNumber - realNumber * complex.imaginaryNumber) / tmp);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Complex))
            return false;
        Complex c = (Complex)o;

        return Double.compare(c.realNumber, realNumber) == 0
                && Double.compare(c.imaginaryNumber, imaginaryNumber) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(realNumber) + Double.hashCode(imaginaryNumber);
    }

    @Override
    public String toString() {
        return "(" + realNumber + " + " + imaginaryNumber + "i)";
    }

}
