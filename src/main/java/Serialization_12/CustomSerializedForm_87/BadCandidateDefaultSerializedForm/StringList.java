package Serialization_12.CustomSerializedForm_87.BadCandidateDefaultSerializedForm;

import java.io.Serializable;

public class StringList implements Serializable {
    private int size = 0;
    private Entry head = null;

    private static class Entry implements Serializable {
        String data;
        Entry next;
        Entry previous;
    }
    // remainder omitted.
}
