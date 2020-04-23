package MethodsCommonToAllObjects_2.clone_13.ImmutableFields;

/*
    Gold equals(), hashCode() and toString(), w/ immutable clone()
    - Provides a degenerate clone method to prevent subclasses from implementating it.
*/
public class PhoneNumberDegenerateClone {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumberDegenerateClone(short areaCode, short prefix, short lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumberDegenerateClone))
            return false;
        PhoneNumberDegenerateClone pn = (PhoneNumberDegenerateClone)o;
        // note the order... it is added in the order of most -> least churn.
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    @Override
    public int hashCode() {
        int result = Short.hashCode(areaCode);
        result = 31 * result + Short.hashCode(prefix);
        result = 31 * result + Short.hashCode(lineNum);
        return result;
    }

    /*
        Returns the string representation of this phone number.
        The string consists of twelve characters whose format is
        "XXX-YYY-ZZZZ", where XXX is the area code, YYY is the
        prefix, and ZZZZ is the line number. Each of the capital
        letters represents a single decimal digit

        If any of the three parts of this phone number is too small
        to fill up its field, the field is padded with leading zeros.
        For example, if the value of the line number is 123, the last
        four characters of the string representation will be "0123"
     */

    @Override
    public String toString() {
        return String.format("%03d-%03d-%4d", areaCode, prefix, lineNum);
    }

    /*
        Return type is Object, and the method is final to prevent implementation within subclasses.
     */
    @Override
    public final Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();

    }
}
