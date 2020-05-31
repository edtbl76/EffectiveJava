package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.BasicArrays;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnumMapExample {

    public static void main(String[] args) {
        Set<Plant> garden = new HashSet<>();

        Map<Plant.LifeCycle, Set<Plant>> plantsByLifeCycle = new EnumMap<>(Plant.LifeCycle.class);

        for (Plant.LifeCycle lifeCycle : Plant.LifeCycle.values()) {
            plantsByLifeCycle.put(lifeCycle, new HashSet<>());
        }

        for (Plant plant : garden) {
            plantsByLifeCycle.get(plant.getLifeCycle()).add(plant);
            System.out.println(plantsByLifeCycle);
        }
    }
}
