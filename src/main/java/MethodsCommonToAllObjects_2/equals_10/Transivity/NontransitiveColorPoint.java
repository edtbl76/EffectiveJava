package MethodsCommonToAllObjects_2.equals_10.Transivity;

public class NontransitiveColorPoint extends Point {
    private final Color color;

    public NontransitiveColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    /*
        - By treating each level of the hierarchy differently we can handle symmetry
        ... but transitivity is broken

        NOTE:
            this implementation also can cause infinite recursion:
                if we have an additional subclass, and we try to evaluate equality with this method
                (i.e. OtherPoint.equals(ColorPoint))
                it will eventually throw a StackOverflowError
     */
    @Override
    public boolean equals(Object o) {
        // if not a point, bail out. This covers childeren
        if (!(o instanceof Point))
            return false;

        // if o is the parent only, do a comparison specific to the parent's attributes only
        if (!(o instanceof NontransitiveColorPoint))
            return o.equals(this);

        // handle the remaining case of a full comparison against children
        return super.equals(o) && ((NontransitiveColorPoint)o).color == color;
    }
}
