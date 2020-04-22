package MethodsCommonToAllObjects_2.equals_10.NonNullity;

public class NullCheckNotRecommended {

    /*
        This is considered unnecessary
        - to test an argument for equality, the equals() method must first cast its argument to an appropriate type
            - accessors invoked/fields accessed.
        - before the cast, the method has to use instance of to get the correct type.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        return false;
    }
}
