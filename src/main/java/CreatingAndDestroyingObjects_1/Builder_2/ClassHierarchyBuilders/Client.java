package CreatingAndDestroyingObjects_1.Builder_2.ClassHierarchyBuilders;

import static CreatingAndDestroyingObjects_1.Builder_2.ClassHierarchyBuilders.NYStylePizza.Size.SMALL;
import static CreatingAndDestroyingObjects_1.Builder_2.ClassHierarchyBuilders.Pizza.Topping.*;

public class Client {

    public static void main(String[] args) {

        /*
            Client Code is very simple.
         */
        NYStylePizza pizza = new NYStylePizza.Builder(SMALL)
                .addTopping(HAM).addTopping(PINEAPPLE).build();
        Calzone calzone = new Calzone.Builder()
                .addTopping(PEPPERONI).withSauceInside().build();

        System.out.println(pizza + "\n" + calzone);
    }
}
