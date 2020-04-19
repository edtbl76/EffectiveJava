package CreatingAndDestroyingObjects_1.Builder_2;

public class CHConcreteCalzone extends ClassHierarchyPizza {
    private final boolean sauceInside;

    private CHConcreteCalzone(Builder builder) {
        super(builder);
        sauceInside = builder.sauceInside;
    }

    public static class Builder extends ClassHierarchyPizza.Builder<Builder> {
        private boolean sauceInside = false; // default

        public Builder withSauceInside() {
            sauceInside = true;
            return this;
        }

        @Override
        public CHConcreteCalzone build() {
            return new CHConcreteCalzone(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}
