package MethodsCommonToAllObjects_2.equals_10.Symmetry;

import java.util.Objects;

// Violates Symmetry
public final class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    /*
        This implementation violates SYMMETRY.
        - the comparisons only work one way
     */
    @Override
    public boolean equals(Object o) {

        if (o instanceof CaseInsensitiveString)
            return s.equalsIgnoreCase(((CaseInsensitiveString)o).s);
        // This only works one way.
        if (o instanceof String)
            return s.equalsIgnoreCase((String) o);
        return false;
    }

}
