package MethodsCommonToAllObjects_2.Comparable_14;

/*
    The type parameter for Comparable<> should be the class itself.
 */
public class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
    private String s;
    @Override
    public int compareTo(CaseInsensitiveString cis) {
       return String.CASE_INSENSITIVE_ORDER.compare(s, cis.s);
    }
}
