package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ParameterizedAnnotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTests {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(args[0]);

        /*
            Execute test methods reflectively.
         */
        for (Method method : testClass.getDeclaredMethods()) {

            if (method.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    method.invoke(null);
                    /*
                        If we get past the method.invoke(), then we didn't encounter an exception, which is a failure
                        (we're trying to guarantee that the exception passed in as a bounded type token to the
                        annotation is the exception that gets thrown in our Sample test.)
                     */
                    System.out.printf("Test %s failed: no exception%n", method);
                } catch (InvocationTargetException wrappedException) {
                    Throwable throwable = wrappedException.getCause();

                    // Get the Exception type that was passed in as the bounded type token
                    Class<? extends Throwable> exceptionType =
                            method.getAnnotation(ExceptionTest.class).value();

                    /*
                        If the passed in exception type matches the
                        underlying exception wrapped by InvocationTargetException, then we've met the test condtions.
                     */
                    if (exceptionType.isInstance(throwable)) {
                        passed++;
                    } else {
                        System.out.printf("Test %s failed: expected %s, got %s%n",
                                method, exceptionType.getName(), throwable);
                    }

                    System.out.println(method + " failed: " + throwable);
                } catch (Exception exception) {
                    System.out.println("Invalid @Test: " + method);
                }
            }
        }
        System.out.printf("Passed: %d, Failed: %d%n", passed, tests - passed);
    }
}
