package MethodsCommonToAllObjects_2.hashCode_11;

import com.google.common.base.Objects;

// This is the PLATINUM "State of the art" standard for equals and HashCode
public class PhoneNumberGuava {
    private final short areaCode;
    private final short prefix;
    private final short lineNum;

    public PhoneNumberGuava(short areaCode, short prefix, short lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix = rangeCheck(prefix, 999, "prefix");
        this.lineNum = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    /*
        Guava produces an OK equals method (it violates Liskov Substitution Principle, as do many)
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneNumberGuava that = (PhoneNumberGuava) o;
        return areaCode == that.areaCode &&
                prefix == that.prefix &&
                lineNum == that.lineNum;
    }

    /*
        Guava's hashCode implementation is considered one of the best open source implementations for avoiding
        collisions,
            - this preserves linear time, avoiding the chance that hashCodes will result in dangling linkedlist buckets

        com.google.common.base.Objects has a static method (hashCode()) that takes an arbitrary number of objects and returns a
        hashCode for each.

        NOTE: there is a tradeoff
            - in order to reduce collisions, the hashCode() method uses arrays to manage variable number of args, and it
            performs autoboxing for any primitive type arguments.
            - this means that it is much slower.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(areaCode, prefix, lineNum);
    }
}
