package ClassesAndInterfaces_3.CompositionOverInheritance_18.BadInheritance;

import java.util.Collection;
import java.util.HashSet;

/*
    This is BROKEN
    HashSet's addAll() method is built on top of add().

    This results in the "double counting" of our instrumentation field

    When we call addAll(), we are going to incr our addCount field.
    - However, this also invokes add(), which is overridden locally, so we'll redundantly incr our addCountfield.
 */
public class InstrumentedHashSet<E> extends HashSet<E> {

    private int addCount = 0;

    public InstrumentedHashSet() {}

    public InstrumentedHashSet(int initialCapacity, float loadFactor, int addCount) {
        super(initialCapacity, loadFactor);
        this.addCount = addCount;
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
