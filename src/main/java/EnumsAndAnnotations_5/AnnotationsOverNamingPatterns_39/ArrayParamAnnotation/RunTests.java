package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ArrayParamAnnotation;


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


                    /**
                     *  The only changes from the standard non-array parameterized annotation start here.
                     *  We have to take an array of the Classes we want.
                     */
                    Class<? extends Exception>[] classes =
                            method.getAnnotation(ExceptionTest.class).value();


                    /**
                     *  The adjustment here is that since we are taking in an array, in order to evaluate
                     *  the internal exceptions, we have to iterate through the data structure.
                     */
                    for (Class<? extends Exception> clazz : classes) {
                        if (clazz.isInstance(throwable)) {
                            passed++;
                        } else {
                            System.out.printf("Test %s failed: expected %s, got %s%n",
                                    method, clazz.getName(), throwable);
                        }
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
