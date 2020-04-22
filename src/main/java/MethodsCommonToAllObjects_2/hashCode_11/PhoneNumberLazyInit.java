package MethodsCommonToAllObjects_2.hashCode_11;

// Includes a "Gold Standard" equals() method and a "Gold standard "hashCode"
public class PhoneNumberLazyInit {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    // cached hashCode (do this only in immutable classes!)
    // initialized to a default of 0.
    private int hashCode = 0;

    public PhoneNumberLazyInit(short areaCode, short prefix, short lineNum) {
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
        if (!(o instanceof PhoneNumberLazyInit))
            return false;
        PhoneNumberLazyInit pn = (PhoneNumberLazyInit)o;
        // note the order... it is added in the order of most -> least churn.
        return pn.lineNum == lineNum && pn.prefix == prefix && pn.areaCode == areaCode;
    }

    /*
        Lazy Loaded hashCode
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = Short.hashCode(areaCode);
            result = 31 * result + Short.hashCode(prefix);
            result = 31 * result + Short.hashCode(lineNum);
            hashCode = result;
        }
        return result;
    }
}
