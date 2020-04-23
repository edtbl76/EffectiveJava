package MethodsCommonToAllObjects_2.clone_13.MutableFields;

public class HashTableDeepCopyIteration implements Cloneable {
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
            Iteration provides a more memory efficient method of deepCopy() than Recursion
         */
        Entry deepCopy() {
            Entry result = new Entry(key, value, next);
            for (Entry entry = result; entry.next != null; entry = entry.next)
                entry.next = new Entry(entry.next.key, entry.next.value, entry.next.next);
            return result;
        }
    }
    /*
        - clone() takes advantage of our deep copy
            - we initialize the new array to the proper size.
            - we copy each non-empty bucket over to the new structure
     */
    @Override
    public HashTableDeepCopyIteration clone() {
        try {
            HashTableDeepCopyIteration result = (HashTableDeepCopyIteration) super.clone();
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
