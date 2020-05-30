package Generics_4.BoundedWildcards_31.BoundedWC;

import java.util.List;
import java.util.Objects;

public class Max {

    /*
        This is derived from NoBoundedWCMax, which is borrowed from
        FavorGenericMethods_30#RecursiveTypeBoundAndMutualComparability.

        1.) We change the type parameter from List<T> to List<? extends T>

        Originally T was specified to extend Comparable<T>, but a comparable of T consumes ONLY T instances.
        - Comparables are always consumers, so we have to adjust T to be <? super T>


     */
    public static <T extends Comparable<? super T>> T max(List<? extends T> list) {
        if (list.isEmpty())
            throw new IllegalArgumentException("Empty Collection, buzz off");

        T result = null;
        for (T t : list) {
            if (result == null || t.compareTo(result) > 0)
                result = Objects.requireNonNull(t);
        }
        return result;
    }
}
