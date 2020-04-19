package CreatingAndDestroyingObjects_1.Builder_2;

import static CreatingAndDestroyingObjects_1.Builder_2.CHConcreteNYPizza.Size.SMALL;
import static CreatingAndDestroyingObjects_1.Builder_2.ClassHierarchyPizza.Topping.*;

public class CHClient {

    public static void main(String[] args) {

        /*
            Client Code is very simple.
         */
        CHConcreteNYPizza pizza = new CHConcreteNYPizza.Builder(SMALL)
                .addTopping(HAM).addTopping(PINEAPPLE).build();
        CHConcreteCalzone calzone = new CHConcreteCalzone.Builder()
                .addTopping(PEPPERONI).withSauceInside().build();

        System.out.println(pizza + "\n" + calzone);
    }
}
