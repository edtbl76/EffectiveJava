package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MultivaluedAnnotations;

import java.lang.reflect.Method;

public class RunTests {

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(Sample.class.getName());

        for (Method method : testClass.getDeclaredMethods()) {

            /*
                Since isAnnotationPresent() can tell the difference between an ANNOTATION TYPE and a CONTAINING
                ANNOTATION TYPE, we have to check for both...
                    otherwise the application will silently ignore non-repeated annotations.
             */
            if (method.isAnnotationPresent(ExceptionTest.class)
                    || method.isAnnotationPresent(ExceptionTestContainer.class)) {
                tests++;
                try {
                    method.invoke(null);
                    System.out.printf("Test %s failed: no exception%n", method);
                } catch (Throwable throwable) {
                    Throwable wrappedException = throwable.getCause();
                    int oldPassed = passed;
                    /*
                        getAnnotationsByType() is agnostic to repeated/non-repeated annotations, so we get everything
                        we are looking for in this array.
                     */
                    ExceptionTest[] exceptionTests = method.getAnnotationsByType(ExceptionTest.class);
                    for (ExceptionTest exceptionTest : exceptionTests) {
                        if (exceptionTest.value().isInstance(wrappedException)) {
                            passed++;
                            break;
                        }
                    }
                    if (passed == oldPassed) {
                        System.out.printf("Test %s failed: %s %n", method, throwable);
                    }
                }
            }

        }
    }
}
