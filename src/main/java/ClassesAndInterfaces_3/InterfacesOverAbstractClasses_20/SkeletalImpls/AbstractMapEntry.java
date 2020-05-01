package ClassesAndInterfaces_3.InterfacesOverAbstractClasses_20.SkeletalImpls;

import java.util.Map;
import java.util.Objects;

public abstract class AbstractMapEntry<K,V> implements Map.Entry<K,V> {

    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    /*
        This provides additional benefit over interfaces alone, because interfaces don't support
        overriding Object methods.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof Map.Entry))
            return false;
        Map.Entry<?,?> entry = (Map.Entry)obj;
        return Objects.equals(entry.getKey(), getKey()) && Objects.equals(entry.getValue(), getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }
}
