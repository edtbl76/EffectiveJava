package CreatingAndDestroyingObjects_1.Builder_2;

public class NutritionFactsWithBuilder {

    // Nutrition Facts
    private final int servingSize;          // (mL)             required
    private final int servings;             // (per container)  required
    private final int calories;             // (per serving)    optional
    private final int fat;                  // (g/serving)      optional
    private final int sodium;               // (mg/serving)     optional
    private final int carbohydrate;         // (g/serving)      optional

    /*
        Constructor takes an instance of the static nested class as its only parameter.
        The attributes are set based on how the values are initialized in the builder.

        - required parameters are placed in the Builder's constructor, ensuring that the minimum requirements for
        creating the object are met, as well as allowing us to validate the object.

        - optional parameters are initialized in chainable withXXXX() methods.
            In this implementation, Optional parameters are initialized to default values to avoid NPE
            (NullObject might be a good pattern here if defaults aren't plausible)

        - The parent class (BuilderPattern in this case) has ALL final (immutable) attributes.
           Thread Safe!
     */
    private NutritionFactsWithBuilder(Builder builder) {
        servingSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }

    public static class Builder {
        // Required Paramters
        private final int servingSize;
        private final int servings;

        // Optional parameters (initialized to defaults)
        private int calories        = 0;
        private int fat             = 0;
        private int sodium          = 0;
        private int carbohydrate    = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder withCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder withFat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder withSodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder withCarbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        // build() method.
        public NutritionFactsWithBuilder build() {
            return new NutritionFactsWithBuilder(this);
        }
    }

}
