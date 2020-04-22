package MethodsCommonToAllObjects_2.equals_10.Transivity;

public class Client {

    public static void main(String[] args) {
        Point p = new Point(1, 2);
        AsymmetricalColorPoint acp = new AsymmetricalColorPoint(1, 2, Color.WHITE);
        NontransitiveColorPoint ncp1 = new NontransitiveColorPoint(1, 2, Color.WHITE);
        NontransitiveColorPoint ncp2 = new NontransitiveColorPoint(1, 2, Color.RED);

        /*
            Confirm broken SYMMETRY
         */
        System.out.println("Point -> ColorPoint: " + p.equals(acp));
        System.out.println("ColorPoint -> Point: " + acp.equals(p));
        System.out.println();

        /*
            Fixed SYMMETRY
            Broken TRANSITIVITY
            - this breaks because non"Color" Points only evaluate their own attributes. Since they ignore
            the attributes of subclasses, they'll return true to any instance of a subclass that uses the same values
            for the parent-level attributes
                - this breaks transitivity because it means the parent will confirm equality to subclasses that don't
                have comparable subclass-level attributes.
         */
        System.out.println("ColorPoint1 -> Point: " + ncp1.equals(p));
        System.out.println("Point: -> ColorPoint1: " + p.equals(ncp1));
        System.out.println("Symmetry Yay!");
        System.out.println("Point -> ColorPoint1: " + p.equals(ncp2));
        System.out.println("ColorPoint1 -> ColorPoint2: " + ncp1.equals(ncp2));
    }
}
