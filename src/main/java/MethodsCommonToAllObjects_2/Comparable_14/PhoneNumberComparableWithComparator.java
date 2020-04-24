package MethodsCommonToAllObjects_2.Comparable_14;

import java.util.Comparator;

/*
    Gold equals(), hashCode() and toString(), w/ immutable clone()
    adds Comparator based compareTo() to the party.
*/
public class PhoneNumberComparableWithComparator implements Cloneable, Comparable<PhoneNumberComparableWithComparator> {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumberComparableWithComparator(short areaCode, short prefix, short lineNum) {
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
        if (!(o instanceof PhoneNumberComparableWithComparator))
            return false;
        PhoneNumberComparableWithComparator pn = (PhoneNumberComparableWithComparator)o;
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
    public PhoneNumberComparableWithComparator clone() {
        try {
            /*
                This is recommended due to Java's support of COVARIANT return types.
                - i.e. an overriding method's return type can be a subclass of the overridden method's
                return type.
                - This case is guaranteed to succeed, because all objects are subclasses of Object.
             */
            return (PhoneNumberComparableWithComparator) super.clone();
        } catch (CloneNotSupportedException e) {
            // This can't happen
            throw new AssertionError();
        }
    }

    /*
        Here is the handy dandy comparator.
        - It is a LOT more brief and easier to read, however, IMHO the perf hit probably isn't QUITE worth it.
        - While it IS brief/easier to read, there is the cast that our lambda requires us to cast the input
        parameter, because it can't quite figure it out. (That's another vote from me for the DIY method)

        stacking chainable thanComparingInt() calls creates LEXICOGRAPHIC ORDERING
            - primary key = area code
            - secondary key = prefix
            - tertiary key = lineNum.

            NOTE: These are ordered in the order of significance from our equals() method.
            - this demonstrates the "weight" of attributes within an object, and is a generic concept worth
            considering in any OOP design.
     */
    private static Comparator<PhoneNumberComparableWithComparator> COMPARATOR =
            Comparator.comparingInt((PhoneNumberComparableWithComparator pn) -> pn.areaCode)
            .thenComparingInt(pn -> pn.prefix)
            .thenComparingInt(pn -> pn.lineNum);

    @Override
    public int compareTo(PhoneNumberComparableWithComparator phoneNumber) {
        return COMPARATOR.compare(this, phoneNumber);
    }
}
