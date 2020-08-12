package Methods_7.DefensiveCopies_50.BrokenImmutableTimePeriodClass;

import java.util.Date;

public class Period {
    private final Date start;
    private final Date end;

    /**
     * @param start     the beginning of the period
     * @param end       the end of the period; mus tnot precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start or end is null
     */
    public Period(Date start, Date end) {
        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException(
                    start + " after " + end);
        }
        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

}
