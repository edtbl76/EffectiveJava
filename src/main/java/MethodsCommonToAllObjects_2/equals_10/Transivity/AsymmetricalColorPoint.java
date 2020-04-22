package MethodsCommonToAllObjects_2.equals_10.Transivity;

public class AsymmetricalColorPoint extends Point {
    private final Color color;

    public AsymmetricalColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    /*
        1.) we can't just leave out the equals method.
            - logical equivalence is probably important here.
            - leaving out the additional attribute introduced by this

        The example below is asymmetrical,
            - we'll get different results when comparing points to colorpoints
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AsymmetricalColorPoint))
            return false;
        return super.equals(o) && ((AsymmetricalColorPoint)o).color == color;
    }
}
