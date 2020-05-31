package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.MultiDimensionalArrays;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public enum PhaseEnumMapV2 {
    SOLID, LIQUID, GAS, PLASMA;

    public enum Transition {
        MELT(SOLID, LIQUID),
        FREEZE(LIQUID, SOLID),
        BOIL(LIQUID, GAS),
        CONDENSE(GAS, LIQUID),
        SUBLIME(SOLID, GAS),
        DEPOSIT(GAS, SOLID),
        IONIZE(GAS, PLASMA),
        DEIONIZE(PLASMA, GAS);

        private final PhaseEnumMapV2 from;
        private final PhaseEnumMapV2 to;

        Transition(PhaseEnumMapV2 from, PhaseEnumMapV2 to) {
            this.from = from;
            this.to = to;
        }


        public static Transition from (PhaseEnumMapV2 from, PhaseEnumMapV2 to) {
            return transitionMap.get(from).get(to);
        }

        private static final Map<PhaseEnumMapV2, Map<PhaseEnumMapV2, Transition>>
            transitionMap = Stream.of(values()).collect(
                    groupingBy(
                            transition -> transition.from,
                            () -> new EnumMap<>(PhaseEnumMapV2.class),
                            toMap(transition -> transition.to, transition -> transition,
                                    (x, y) -> y,
                                    () -> new EnumMap<>(PhaseEnumMapV2.class))));
    }
}
