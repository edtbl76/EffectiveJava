package CreatingAndDestroyingObjects_1.Builder_2.JavaBeansPattern;

public class NutritionFacts {

    // Nutrition Facts
    private int servingSize     = -1;   // required; no default value
    private int servings        = -1;   // required; no default value
    private int calories        = 0;
    private int fat             = 0;
    private int sodium          = 0;
    private int carbohydrate    = 0;

    public NutritionFacts() {
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(int carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
}
