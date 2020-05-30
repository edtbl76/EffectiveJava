package Generics_4.GenericsAndVarargs_32;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Safe {

    /*
        This is all typesafe, because we are only using generics, and not arrays.
        List.of() DOES use a varargs under the hood, but it is annotated with @SafeVarargs, because it is type safe.

     */
    private static <T> List<T> pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return List.of(a, b);
            case 1:
                return List.of(a, c);
            case 2:
                return List.of(b, c);
        }
        throw new AssertionError();
    }

    public static void main(String[] args) {
         List<String> attributes = pickTwo("This", "doesn't", "work");
    }

}
