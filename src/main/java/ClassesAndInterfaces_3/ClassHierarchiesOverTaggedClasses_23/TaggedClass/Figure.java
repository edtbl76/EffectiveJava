package ClassesAndInterfaces_3.ClassHierarchiesOverTaggedClasses_23.TaggedClass;

public class Figure {

    enum Shape{
        RECTANGLE, CIRCLE
    };

    // TAG FIELD - the shape of this figure
    final Shape shape;

    // These fields are only used if the shape is rectangular
    double length;
    double width;

    // ... and this if its a circle
    double radius;


    // Rectangle Constructor
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    // ... and Circle
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                // Remember! This Constant is strongly tied to Math....
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }


}
