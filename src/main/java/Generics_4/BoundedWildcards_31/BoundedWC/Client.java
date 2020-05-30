package Generics_4.BoundedWildcards_31.BoundedWC;

import java.util.Set;

public class Client {

    public static void main(String[] args) {

        // Build two sets of different types with a common parent
        Set<Integer> integers = Set.of(1, 3, 5);
        Set<Double> doubles = Set.of(2.0, 4.0, 6.0);

        // Create a set that takes the parent.
        // (Union.union() takes a bounded WC to support this funtionality)
        Set<Number> numbers = Union.union(integers, doubles);
        System.out.println(numbers);

        /*
            NOTE: prior to Java 8, the syntax above would cause an error.
            This can be alleviated by using EXPLICIT TYPE ARGUMENT

            If you look at this in Intellij, the Explicit Type Arguument is greyed out.
            - hovering over it will result in a contextual message that tells you "EXPLICIT TYPE ARGUMENTS can be
            inferred".

            We don't need these post Java 8
         */
        Set<Number> numbers2 = Union.<Number>union(integers ,doubles);
    }
}
