package Serialization_12.DefensiveReadObjectMethods_88.ImmutableClassWithDefensiveCopy;

import java.util.Date;

public class Period {
    private final Date start;
    private final Date end;

    /**
     *
     * @param start the beginning of the period
     * @param end nthe end of the period; must not precede start
     * @throws IllegalArgumentException if start is after end
     * @throws NullPointerException if start ot end is null
     */
    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
        if (this.start.compareTo(this.end) > 0) {
            throw new IllegalArgumentException(start + " after " + end);
        }
    }

    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Period{");
        sb.append("start=").append(start);
        sb.append(", end=").append(end);
        sb.append('}');
        return sb.toString();
    }
}
