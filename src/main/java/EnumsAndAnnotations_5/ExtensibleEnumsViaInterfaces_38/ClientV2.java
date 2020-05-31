package EnumsAndAnnotations_5.ExtensibleEnumsViaInterfaces_38;

import java.util.Arrays;
import java.util.Collection;

public class ClientV2 {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Edit your runtime configuration to have two doubles as program arguments");
            System.exit(1);
        }

        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);

        /*
            Instead of passing in the BOUNDED TYPE TOKEN, we can pass in a BOUNDED WC TYPE,

                Collection<? extends Operation>

            As far as "types" we are passing in a Collection instead of a Class object.
         */
        test(Arrays.asList(ExtendedOperation.values()), x, y);

    }

    /*
        As with most cases, the second alternative is slightly better.
         - Passing in the collection (BOUNDED WC TYPE) vs. the BOUNDED TYPE TOKEN creates a less complicated method
         signature/declaration as well as more flexible code.

         The one downside:
         - since we are no longer using an in the declaration, we can't use EnumSet or EnumMap on the operations.

     */
    private static void test(Collection<? extends Operation> operationSet, double x, double y) {
        for (Operation operation : operationSet) {
            System.out.printf("%f %s %f = %f%n", x, operation, y, operation.apply(x, y));
        }
    }
}
