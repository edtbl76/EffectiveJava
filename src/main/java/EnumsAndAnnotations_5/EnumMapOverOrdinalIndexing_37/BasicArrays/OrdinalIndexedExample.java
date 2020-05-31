package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.BasicArrays;

import java.util.HashSet;
import java.util.Set;

public class OrdinalIndexedExample {

   public static void main(String[] args) {
      Set<Plant> garden = new HashSet<>();

      Set<Plant>[] plantsByLifeCycle = (Set<Plant>[]) new Set[Plant.LifeCycle.values().length];
      for (int i = 0; i < plantsByLifeCycle.length; i++) {
         plantsByLifeCycle[i] = new HashSet<>();
      }

      for (Plant plant : garden) {
         plantsByLifeCycle[plant.getLifeCycle().ordinal()].add(plant);
      }

      for (int i = 0; i < plantsByLifeCycle.length; i++) {
         System.out.printf("%s: %s%n", Plant.LifeCycle.values()[i], plantsByLifeCycle[i]);
      }
   }

}
