package CreatingAndDestroyingObjects_1.Builder_2;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public abstract class ClassHierarchyPizza {

    public enum Topping { PEPPERONI, HAM, MUSHROOM, ONION, PEPPER, SAUSAGE, PINEAPPLE }
    final Set<Topping> toppings;

    /*
        This is a GENERIC TYPE w/ a RECURSIVE TYPE PARAMETER
            - this + self() allows method chaining to work properly in subclasses w/o the need for casting.

            This is known as the SIMULATED SELF-TYPE idiom. It is used in languages that don't have a "self" type.
            (like Java)
     */
    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract ClassHierarchyPizza build();

        // subclasses override this method to return "this"
        protected abstract T self();
    }

    ClassHierarchyPizza(Builder<?> builder) {
        toppings = builder.toppings.clone();
    }
}
