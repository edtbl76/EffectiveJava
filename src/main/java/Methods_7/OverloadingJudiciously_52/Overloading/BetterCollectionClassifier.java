package Methods_7.OverloadingJudiciously_52.Overloading;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BetterCollectionClassifier {

    public static String classify(Collection<?> collection) {
        return collection instanceof Set ? "Set" :
                collection instanceof List ? "List" : "Unknown Collection";
    }

    public static void main(String[] args) {

        Collection<?>[] collections = {
                // Create and initialize a set at construction
                Stream.of("a", "b", "c").collect(Collectors.toCollection(HashSet::new)),
                // Create and initialize a list at construction
                List.of("a", "b", "c"),
                // Create a map and initialize it
                Map.of(1, "a", 2, "b", 3, "c").values()
        };


        Arrays.stream(collections).forEach(collection -> System.out.println(classify(collection)));
    }
}
