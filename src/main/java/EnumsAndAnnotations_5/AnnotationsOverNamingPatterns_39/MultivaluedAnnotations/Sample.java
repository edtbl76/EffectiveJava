package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MultivaluedAnnotations;

import java.util.ArrayList;
import java.util.List;

public class Sample {

    // Code Containing an annotation w/ an array parameter
    @ExceptionTest(IndexOutOfBoundsException.class)
    @ExceptionTest(NullPointerException.class)
    public static void doublyBad() {
        List<String> list = new ArrayList<>();

        list.addAll(5, null);
    }

}
