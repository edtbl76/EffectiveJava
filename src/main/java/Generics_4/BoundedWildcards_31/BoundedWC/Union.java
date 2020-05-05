package Generics_4.BoundedWildcards_31.BoundedWC;

import java.util.HashSet;
import java.util.Set;

public class Union {

    /*
        The parameters are both producers (i.e. they are being used to CREATE a new value)
            PE Bounded WildCard Type

            NOTE: NEVER make return types Bounded WildCard Types
     */
    public static <E> Set<E> union(Set<? extends E> set1, Set<? extends E> set2) {
        Set<E> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }
}
