package Generics_4.BoundedWildcards_31.NoBoundedWC;

import java.util.List;
import java.util.Objects;

public class Max {

    public static <T extends Comparable<T>> T max(List<T> list) {
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
