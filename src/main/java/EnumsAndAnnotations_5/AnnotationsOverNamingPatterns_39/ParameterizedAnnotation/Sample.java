package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ParameterizedAnnotation;

public class Sample {

    // Passing test
    @ExceptionTest(ArithmeticException.class)
    public static void method1() {
        int i = 0;

        i = i / i;
    }

    // Should fail, because it is the wrong type of exception
    @ExceptionTest(ArithmeticException.class)
    public static void method2() {
        int [] a = new int[0];
        int i = a[1];
    }

    // Should fail, No exception!
    @ExceptionTest(ArithmeticException.class)
    public static void method3() {

    }


}
