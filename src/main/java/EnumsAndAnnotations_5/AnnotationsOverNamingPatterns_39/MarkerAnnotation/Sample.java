package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MarkerAnnotation;

public class Sample {

    // should pass
    @Test
    public static void method1() {}
    public static void method2() {}

    // should fail
    @Test
    public static void method3() {
        throw new RuntimeException("Boom!");
    }
    public static void method4() {}

    // Not valid.
    @Test
    public void method5() {}
    public static void method6() {}

    // fail
    @Test
    public static void method7() {
        throw new RuntimeException("Ooops");
    }
    public static void method8() {}

}
