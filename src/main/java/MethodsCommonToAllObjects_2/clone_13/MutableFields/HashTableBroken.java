package MethodsCommonToAllObjects_2.clone_13.MutableFields;

public class HashTableBroken implements Cloneable {
    private Entry[] buckets;

    private static class Entry {
        final Object key;
        Object value;
        Entry next;

        public Entry(Object key, Object value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    /*
        CLONE NOT GOING TO WORK:
        - The problem here is that the array references the same LinkedList as the original, which leads to
        nondeterministic behavior.
     */
    @Override
    public HashTableBroken clone() {
        try {
            HashTableBroken result = (HashTableBroken) super.clone();
            result.buckets = buckets.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
