package EnumsAndAnnotations_5.EnumsOverIntConstants_34.EnumTypeWithDataAndBehavior;

public class EnumOperationClient {

    public static void main(String[] args) {
        // We're pretending that someone added these as args.
        double x = Double.parseDouble("10.0");
        double y = Double.parseDouble("2.0");

        for (OperationBetterPlusData operation : OperationBetterPlusData.values()) {
            System.out.printf("%f %s %f = %f%n", x, operation, y, operation.apply(x, y));
        }
    }
}
