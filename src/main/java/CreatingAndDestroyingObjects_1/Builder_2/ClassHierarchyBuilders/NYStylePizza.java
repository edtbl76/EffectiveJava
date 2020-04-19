package CreatingAndDestroyingObjects_1.Builder_2.ClassHierarchyBuilders;

import java.util.Objects;

public class NYStylePizza extends Pizza {
    public enum Size { SMALL, MEDIUM, LARGE}
    private final Size size;

    private NYStylePizza(Builder builder) {
        super(builder);
        size = builder.size;
    }

    public static class Builder extends Pizza.Builder<Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        public NYStylePizza build() {
            return new NYStylePizza(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
