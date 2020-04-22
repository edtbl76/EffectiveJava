package MethodsCommonToAllObjects_2.equals_10.Symmetry;

import java.util.Objects;

public final class CaseInsensitiveStringWorks {
    private final String s;

    public CaseInsensitiveStringWorks(String s) {
        this.s = Objects.requireNonNull(s);
    }


    /*
        This preserves SYMMETRY by ensuring that the two objects are equal if and only if
        both criteria are met.
            - CaseInsensitive -> String (true)
            - String -> CaseInsensitive -> (true)
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveStringWorks &&
                ((CaseInsensitiveStringWorks) o).s.equalsIgnoreCase(s);
    }

}
