package Generics_4.FavorGenericMethods_30;

import java.util.Collection;
import java.util.Objects;

public class RecursiveTypeBoundAndMutualComparability {

    /*
        Using a RECURSIVE TYPE BOUND to demonstrate mutual comparability
        - the 'type bound' <E extends Comparable<E>>' is read as

            any type E that can be compared to itself.
     */
    public static <E extends Comparable<E>> E max(Collection<E> collection) {
        if (collection.isEmpty())
            throw new IllegalArgumentException("Empty Collection, buzz off");

        E result = null;
        for (E e : collection) {
            if (result == null || e.compareTo(result) > 0)
                result = Objects.requireNonNull(e);
        }
        return result;
    }

}
