package MethodsCommonToAllObjects_2.toString_12;

// extends Gold equals() and Gold hashCode w/ Gold version of toString() WITHOUT Format returned.
public class PhoneNumberWithoutStringFormat {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumberWithoutStringFormat(short areaCode, short prefix, short lineNum) {
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
        if (!(o instanceof PhoneNumberWithoutStringFormat))
            return false;
        PhoneNumberWithoutStringFormat pn = (PhoneNumberWithoutStringFormat)o;
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
        Returns a brief description of this potion. The exact details of the representation are unspecified
        and subject to change
     */

    @Override
    public String toString() {
        return String.format("%03d-%03d-%4d", areaCode, prefix, lineNum);
    }
}
