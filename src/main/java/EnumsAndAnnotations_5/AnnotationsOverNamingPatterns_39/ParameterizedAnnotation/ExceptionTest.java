package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.ParameterizedAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    This is an annotation with a parameter
    - The purpose is to ensure that the correct type of exception is thrown.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTest {

    /*
        This is a BOUNDED TYPE TOKEN
        - The Class object, for which some class that extends Throwable.
     */
    Class<? extends Throwable> value();
}
