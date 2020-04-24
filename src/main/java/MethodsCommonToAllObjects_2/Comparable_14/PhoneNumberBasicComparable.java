package MethodsCommonToAllObjects_2.Comparable_14;

/*
    Gold equals(), hashCode() and toString(), w/ immutable clone()
    adds compareTo() to the party.
*/
public class PhoneNumberBasicComparable implements Cloneable, Comparable<PhoneNumberBasicComparable> {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumberBasicComparable(short areaCode, short prefix, short lineNum) {
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
        if (!(o instanceof PhoneNumberBasicComparable))
            return false;
        PhoneNumberBasicComparable pn = (PhoneNumberBasicComparable)o;
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

    @Override
    public PhoneNumberBasicComparable clone() {
        try {
            /*
                This is recommended due to Java's support of COVARIANT return types.
                - i.e. an overriding method's return type can be a subclass of the overridden method's
                return type.
                - This case is guaranteed to succeed, because all objects are subclasses of Object.
             */
            return (PhoneNumberBasicComparable) super.clone();
        } catch (CloneNotSupportedException e) {
            // This can't happen
            throw new AssertionError();
        }
    }

    @Override
    public int compareTo(PhoneNumberBasicComparable phoneNumber) {
        int result = Short.compare(areaCode, phoneNumber.areaCode);
        if (result == 0) {
            result = Short.compare(prefix, phoneNumber.prefix);
            if (result == 0) {
                result = Short.compare(lineNum, phoneNumber.lineNum);
            }
        }
        return result;
    }
}
