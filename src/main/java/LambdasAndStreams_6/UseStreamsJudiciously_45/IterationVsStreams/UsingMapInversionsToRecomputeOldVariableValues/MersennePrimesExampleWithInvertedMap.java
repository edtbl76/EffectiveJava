package LambdasAndStreams_6.UseStreamsJudiciously_45.IterationVsStreams.UsingMapInversionsToRecomputeOldVariableValues;

import java.math.BigInteger;
import java.util.stream.Stream;

import static java.math.BigInteger.ONE;
import static java.math.BigInteger.TWO;

public class MersennePrimesExampleWithInvertedMap {

    public static void main(String[] args) {
        primes().map(prime -> TWO.pow(prime.intValueExact()).subtract(ONE))
                .filter(mersenne -> mersenne.isProbablePrime(50))
                .limit(20)
                /*
                    We've edited the terminal operation to get the exponent provided in the
                    source stream.
                    - map.bitLength() "inverts the map"
                 */
                .forEach(map -> System.out.println(map.bitLength() + ": " + map));
    }

    public static Stream<BigInteger> primes () {
        /*
            Iterate takes 2 parameters
            - the first element in the stream
            - a function to generate the next element in the stream based on the previous element.
         */
        return Stream.iterate(TWO, BigInteger::nextProbablePrime);
    }
}
