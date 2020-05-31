package EnumsAndAnnotations_5.ExtensibleEnumsViaInterfaces_38;

public class Client {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Edit your runtime configuration to have two doubles as program arguments");
            System.exit(1);
        }

        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);

        /*
            passing class literal for extended operation type from main() to test()
            - remember this is a BOUNDED TYPE TOKEN.
         */
        test(ExtendedOperation.class, x , y);

    }

    /*
        This is a complex declaration.
        - it guarantees that T (i.e. the Bounded Type Token) Class object represents both an Enum and a subtype of
        Operation.
            - this is required for us to iterate over the elements as enums and then perform the associate operation
            (from the interface) associated with each one.
     */
    private static <T extends Enum<T> & Operation> void test(Class<T> opType, double x, double y) {
        for (Operation operation : opType.getEnumConstants()) {
            System.out.printf("%f %s %f = %f%n", x, operation, y, operation.apply(x, y));
        }
    }
}
