package CreatingAndDestroyingObjects_1.Builder_2;

import java.util.Objects;

public class CHConcreteNYPizza extends ClassHierarchyPizza {
    public enum Size { SMALL, MEDIUM, LARGE}
    private final Size size;

    private CHConcreteNYPizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public static class Builder extends ClassHierarchyPizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public CHConcreteNYPizza build() {
            return new CHConcreteNYPizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
