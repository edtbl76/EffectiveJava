package Methods_7.DefensiveCopies_50.FixAttackInternals2;

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

    /*
        We resolve the second attack on our internals, by making defensive copies of
        internal fields
     */
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }

}
