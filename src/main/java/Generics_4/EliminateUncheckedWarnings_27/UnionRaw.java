package Generics_4.EliminateUncheckedWarnings_27;

import java.util.HashSet;
import java.util.Set;

public class UnionRaw {

    // Lots of unchecked cast warnings!
    public static Set union(Set set1, Set set2) {
        Set result = new HashSet(set1);
        result.addAll(set2);
        return result;
    }
}
