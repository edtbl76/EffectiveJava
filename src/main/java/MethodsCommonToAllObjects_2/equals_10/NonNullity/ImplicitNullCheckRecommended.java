package MethodsCommonToAllObjects_2.equals_10.NonNullity;

public class ImplicitNullCheckRecommended {

    /*
        This is better than the null check, because we are checking for the instance.
        - if the check before the class cast is missing, and 'o' is the wrong type
            - the method would throw a ClassCastException (violates equals() contract)
        - the check implicitly checks for null
            - instanceof returns false if first operand is null, regardless of what the second operand is.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ImplicitNullCheckRecommended))
            return false;

        // This is the class cast mentioned in the previous example (NullCheckNotRecommended)
        ImplicitNullCheckRecommended thing = (ImplicitNullCheckRecommended)o;
        // Don't really return true. The part we're concerned with is above this line.
        return true;
    }
}
