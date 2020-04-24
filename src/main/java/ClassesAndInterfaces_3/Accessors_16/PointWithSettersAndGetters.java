package ClassesAndInterfaces_3.Accessors_16;

public class PointWithSettersAndGetters {
    private double x;
    private double y;

    public PointWithSettersAndGetters(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
