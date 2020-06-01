package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MarkerAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        /*
            Passing in the name of the class we want to test
            - String -> Class, Unbounded WC Type.
         */
//        System.out.println(Sample.class.getName());
////        Class<?> testClass = Class.forName(Sample.class.getName());
        Class<?> testClass = Class.forName(args[0]);

        /*
            Execute test methods reflectively.
         */
        for (Method method : testClass.getDeclaredMethods()) {

            /*
                This determines which tests should be run, based on presence of annotation.

                First Catch Block
                - if test method throws up, reflection facility wraps it in an InvocationTargetException
                - tool captures exception and prints the failure report as we've coded it to.
                    - the InvocationTargetException includes the ORIGINAL exception thrown by the test method
                    (collected via the getCause()) so we can see what happened

                Second Catch Block
                - any exception OTHER than the InvocationTargetException is deemed an invalid use of the Test
                annotation that was missed at compile time.
                - this is how we catch invalid use... at Runtime rather than CompileTime.
             */
            if (method.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    method.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedException) {
                    Throwable throwable = wrappedException.getCause();
                    System.out.println(method + " failed: " + throwable);
                } catch (Exception exception) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
