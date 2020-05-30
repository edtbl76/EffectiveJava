package Generics_4.GenericsAndVarargs_32;

import java.util.concurrent.ThreadLocalRandom;

public class Unsafe {

    private static <T> T[] toArray(T... args) {
        return args;
    }

    private static <T> T[] pickTwo(T a, T b, T c) {
        switch(ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return toArray(a, b);
            case 1:
                return toArray(a, c);
            case 2:
                return toArray(b, c);
        }
        throw new AssertionError();
    }

    public static void main(String[] args) {
        String[] attributes = pickTwo("This", "doesn't", "work");
    }

}
