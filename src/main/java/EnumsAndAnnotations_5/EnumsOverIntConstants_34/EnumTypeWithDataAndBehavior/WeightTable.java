package EnumsAndAnnotations_5.EnumsOverIntConstants_34.EnumTypeWithDataAndBehavior;

public class WeightTable {

    public static void main(String[] args) {
        double earthWeight = Double.parseDouble(args[0]);
        double mass = earthWeight / Planet.EARTH.surfaceGravity();
        for (Planet planet : Planet.values()) {
            System.out.printf("Weight on %s is %f%n", planet, planet.surfaceWeight(mass));
        }
    }
}
