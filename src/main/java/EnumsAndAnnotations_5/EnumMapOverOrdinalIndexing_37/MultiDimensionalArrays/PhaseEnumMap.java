package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.MultiDimensionalArrays;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public enum PhaseEnumMap {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID);

        private final PhaseEnumMap from;
        private final PhaseEnumMap to;

        Transition(PhaseEnumMap from, PhaseEnumMap to) {
            this.from = from;
            this.to = to;
        }


        public static Transition from (PhaseEnumMap from, PhaseEnumMap to) {
            return transitionMap.get(from).get(to);
        }

        /*
            Initialize Phase Transition Map
            - somewhat complicated.
            Map<Phase, Map<Phase, Transition>>
                = Map from source Phase to Map from destination Phase to Transition.

            Initialization of the Map uses a cascading sequence of 2 collections .

            1.) We use a groupedBy() collector to group transitions based on source phase.
            2.) The second collector creates an EnumMap w/ mapping from destination phase to the transition

            CONFUSED by the merge function? (x, y) -> y. ????
            - Right, this doesn't actually go anywhere or do anything, it is a limitation of java.

                We need to provide the merge function in order to get to the second parameter which is a mapFactory to
                get our EnumMap.
                    - the limitation is that Collectors provides telescoping factories, so we cheat by providing a
                    dummy factory for the first argument.
         */
        private static final Map<PhaseEnumMap, Map<PhaseEnumMap, Transition>>
            transitionMap = Stream.of(values()).collect(
                    groupingBy(
                            transition -> transition.from,
                            () -> new EnumMap<>(PhaseEnumMap.class),
                            toMap(transition -> transition.to, transition -> transition,
                                    (x, y) -> y,
                                    () -> new EnumMap<>(PhaseEnumMap.class))));
    }
}
