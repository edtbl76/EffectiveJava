package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ArrayParamAnnotation;

import java.lang.reflect.Method;

public class RunTestsWithBadMultiElementArraySample {

    public static void main(String[] args) throws Exception {
        int tests = 0;
        int passed = 0;

        Class<?> testClass = Class.forName(args[0]);

        /*
            Execute tests reflectively
         */
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
            }

            try {
                method.invoke(null);
                System.out.printf("Test %s failed: no exception:%n", method);
            } catch (Throwable throwable) {
                Throwable wrappedException = throwable.getCause();
                int oldPassed = passed;
                Class<? extends Exception>[] exceptionTypes = method.getAnnotation(ExceptionTest.class).value();

                for (Class<? extends Exception> exceptionType : exceptionTypes) {
                    if (exceptionType.isInstance(wrappedException)) {
                        passed++;
                        break;
                    }
                }
                if (passed == oldPassed) {
                    System.out.printf("Test %s failed: %s %n", method, wrappedException);
                }
            }
        }
    }
}
