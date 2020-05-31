package EnumsAndAnnotations_5.EnumMapOverOrdinalIndexing_37.BasicArrays;

import EnumsAndAnnotations_5.EnumsOverIntConstants_34.EnumTypeWithDataAndBehavior.Planet;

public class Plant {

    enum LifeCycle { ANNUAL, PERENNIAL, BIENNIAL }

    private String name;
    private LifeCycle lifeCycle;

    public Plant(String name, LifeCycle lifeCycle) {
        this.name = name;
        this.lifeCycle = lifeCycle;
    }

    @Override
    public String toString() {
        return name;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }
}
