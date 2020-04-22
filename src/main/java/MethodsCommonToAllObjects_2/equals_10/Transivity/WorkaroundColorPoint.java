package MethodsCommonToAllObjects_2.equals_10.Transivity;

import java.util.Objects;

/*
    The workaround is one of the many instances of:

        prefer composition over inheritance.
        - we have a composition of a private Point and a public view.
 */
public class WorkaroundColorPoint {
    private final Point point;
    private final Color color;

    public WorkaroundColorPoint(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    /*
     *  Returns "Point" view of color point
     *  This is the public Point that proxies our composed class
     */
    public Point asPoint() {
        return point;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WorkaroundColorPoint))
            return false;
        WorkaroundColorPoint wcp = (WorkaroundColorPoint)o;
        return wcp.point.equals(point) && wcp.color.equals(color);

    }

}
