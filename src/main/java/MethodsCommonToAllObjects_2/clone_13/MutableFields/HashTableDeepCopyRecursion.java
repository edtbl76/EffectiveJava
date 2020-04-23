package MethodsCommonToAllObjects_2.clone_13.MutableFields;

public class HashTableDeepCopyRecursion implements Cloneable {
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


        /*
            We've added a deepCopy to avoid copying references.
            - NOTE that it calls itself recursively to populate the linkedlist.
         */
        Entry deepCopy() {
            return new Entry(key, value, next == null ? null : next.deepCopy());
        }
    }

    /*
        - clone() takes advantage of our deep copy
            - we initialize the new array to the proper size.
            - we copy each non-empty bucket over to the new structure
     */
    @Override
    public HashTableDeepCopyRecursion clone() {
        try {
            HashTableDeepCopyRecursion result = (HashTableDeepCopyRecursion) super.clone();
            result.buckets = new Entry[buckets.length];
            for (int i = 0; i < buckets.length; i++) {
                result.buckets[i] = buckets[i].deepCopy();
            }
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
