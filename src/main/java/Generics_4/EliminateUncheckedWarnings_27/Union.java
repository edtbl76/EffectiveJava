package Generics_4.EliminateUncheckedWarnings_27;

import java.util.HashSet;
import java.util.Set;

public class Union {

    public static <E> Set<E> union(Set<E> set1, Set<E> set2) {
        Set<E> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    public static void main(String[] args) {
        Set<String> metal = Set.of("AC/DC", "Aerosmith", "Guns'n'Roses");
        Set<String> boyBands = Set.of("NewKids", "BackstreetBoys", "OneDirection");
        Set<String> twoThingsThatShoundNeverBeJoined = union(metal, boyBands);
        System.out.println(twoThingsThatShoundNeverBeJoined);
    }
}
