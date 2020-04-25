package ClassesAndInterfaces_3.CompositionOverInheritance_18.CompositionAndForwarding;

import java.util.Collection;
import java.util.Set;

// This InstrumentedSet implements our decoupled ForwardingSet, decoupling it from the
// effects of direct inheritance w/ Set<E>
public class InstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E element) {
        addCount++;
        return super.add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        addCount += collection.size();
        return super.addAll(collection);
    }

    public int getAddCount() {
        return addCount;
    }
}
