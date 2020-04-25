package ClassesAndInterfaces_3.InheritanceBestPractices_19.ConstructorsAndOverridableMethods;

import java.time.Instant;

public final class Sub extends Super {
    // Blank final set by constructor
    private final Instant instant;

    Sub() {
        instant = Instant.now();
    }

    @Override
    public void overrideMe() {
        System.out.println(instant);
    }

    /*
        This prints "null" on the first line, because the overrideMe() is invoked by the Super()
        constructor before the Sub constructor has had a change to initialize the "instant" field.

        NOTE:
            - This demonstrates leakage! We have a final field in 2 different states.
     */
    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
    }

}
