package MethodsCommonToAllObjects_2.equals_10.Transivity;

public class BrokenLiskovColorPoint extends Point {
    private final Color color;

    public BrokenLiskovColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    /*
        This violates the Liskov substitution principle
        - This has the effect of equating objects only if they have the same implementation class.
        - The consequences violate principles of OOP
            - an instance of a subclass of Point is still a Point
            - (and it still needs to function as one)
            - this approach prevents it from doing so.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass())
            return false;
        Point p = (Point)o;
        return p.x == x && p.y == y;
    }
}
