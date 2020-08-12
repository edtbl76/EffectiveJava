package Methods_7.DefensiveCopies_50.FixAttackInternals1;

import java.util.Date;

public class Period {

    private Date start;
    private Date end;

    public Period(Date start, Date end) {
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        if (this.start.compareTo(this.end) > 0)
            throw new IllegalArgumentException(this.start + " after " + this.end);
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }

}
