package Generics_4.TypesafeHeterogeneousContainers_33.BoundedTypeTokenExamples;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public class Example {

    static Annotation getAnnotation(AnnotatedElement element, String annotationTypeName) {

        /*
            This is an unbounded type token.
         */
        Class<?> annotationType = null;

        try {
            annotationType = Class.forName(annotationTypeName);
        } catch (Exception exception) {
            throw new IllegalArgumentException(exception);
        }

        /*
            getAnnotation() uses a bounded Type Token

                public <T extends Annotation> T getAnnotation(Class<T> annotationType);

            If we call getAnnotation directly, we get compile time warnings due to unchecked cast.

            However, we can use asSubclass()  to safely and dynamically perform what would normally be an
            unchecked cast:
                Class<? extends Annotation>

                STEP 1: above we had to get the class type using Class.forName(). (THis takes a string!)
                STEP 2: call asSubclass(Class) on that object against the class you want to test the cast against.


         */
        return element.getAnnotation(annotationType.asSubclass(Annotation.class));
    }
}
