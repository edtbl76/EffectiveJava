package Generics_4.BoundedWildcards_31.BoundedWC;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser<T> {
    private final List<T> choiceList;

    /*
        The constructor uses the Collection to create new instances of type T (i.e. it is a producer)
        - Therefore we can use a PE Bounded Wildcard Type (Producers -> Extend)
     */
    public Chooser(Collection<? extends T> choices) {
        choiceList = new ArrayList<>(choices);
    }

    public T choose() {
        Random random = ThreadLocalRandom.current();
        return choiceList.get(random.nextInt(choiceList.size()));
    }
}
