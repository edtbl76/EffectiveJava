package Serialization_12.CustomSerializedForm_87.FixingTheBadCandidate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class StringList implements Serializable {
    private transient int size = 0;
    private transient Entry head = null;

    // this isn't Serializable any more
    // This means that the Entry (an impl detail) no longer leaks into the Serialized form.
    private static class Entry {
        String data;
        Entry next;
        Entry previous;
    }

    // Appends the specified string to the list
    public final void add(String str) {

    }

    /**
     *  Serialize this (@code StringList) instance.
     *
     * @serialData The size of th elist (the number of strings
     * it contains) is emitted ({@code int}), followed by all of
     * its elements (each a {@code String}), in the proper
     * sequence.
     */
    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(size);

        // Write out all elements in the proper order
        for (Entry entry = head; entry != null; entry = entry.next) {
            objectOutputStream.writeObject(entry.data);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int numberElements = objectInputStream.readInt();

        // Read in all elements and insert them in list
        for (int i = 0; i < numberElements; i++) {
            add((String) objectInputStream.readObject());
        }

        // etc.
    }
}
