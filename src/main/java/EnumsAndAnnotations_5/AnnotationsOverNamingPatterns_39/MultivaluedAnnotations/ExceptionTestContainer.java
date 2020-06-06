package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MultivaluedAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    This is a
    CONTAINING ANNOTATION TYPE.
    - This is the parameter of @Repeatable meta-annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExceptionTestContainer {
    ExceptionTest[] value();
}
