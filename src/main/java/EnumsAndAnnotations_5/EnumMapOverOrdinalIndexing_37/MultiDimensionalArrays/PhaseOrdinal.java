package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.MultiDimensionalArrays;

public enum PhaseOrdinal {
    SOLID, LIQUID, GAS;

    public enum Transition {
        MELT, FREEZE, BOIL, CONDENSE, SUBLIME, DEPOSIT;

        // Rows indexed by from-ordinal, Cols by to-ordinal
        private static final Transition[][] TRANSITIONS = {
                {   null,     MELT,       SUBLIME },
                {   FREEZE,   null,       BOIL    },
                {   DEPOSIT,  CONDENSE,   null    }
        };

        // Returns phase transition from one phase to another
        public static Transition from(PhaseOrdinal from, PhaseOrdinal to) {
            return TRANSITIONS[from.ordinal()][to.ordinal()];
        }
    }
}
