package EnumsAndAnnotations_5.AnnotationsOverNamingPatterns_39.MarkerAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
    These are META-ANNOTATIONS
    - annotations on annotation type declarations.

    The retention meta-annotation indicates that Test annotations are retained at runtime.
    - without this, Test annotations would be invisible to the test stool.

    The target meta-annotation indicates that Test annotations are only legal on method declarations
    - can't be used on class declarations, field declarations or other program elements.

    This should be used ONLY on parameterless static methods.
    - we can't enforce this w/o an annotation processor.
    - w/o the annotation processor, the code still compiles even if the restriction we just mentioned isn't met, leaving
    us to deal with the blast radius after the fact.

 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
