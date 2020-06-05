package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ArrayParamAnnotation;


import java.util.ArrayList;
import java.util.List;

public class BadMultiElementArraySample {

    // Code Containing an annotation w/ an array parameter
    @ExceptionTest({ IndexOutOfBoundsException.class, NullPointerException.class })
    public static void doublyBad() {
        List<String> list = new ArrayList<>();

        /*
            The specification permits this method to throw either
                - IndexOutOfBoundsException or
                - NPE

            based on the parameterization of the annotation.
         */
        list.addAll(5, null);
    }
}
