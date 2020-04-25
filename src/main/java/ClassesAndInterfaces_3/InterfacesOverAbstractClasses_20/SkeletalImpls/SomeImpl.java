package ClassesAndInterfaces_3.InterfacesOverAbstractClasses_20.SkeletalImpls;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

public class SomeImpl {

    static List<Integer> intArrayList(int [] array) {
        Objects.requireNonNull(array);

        return new AbstractList<>() {
            @Override
            public Integer get(int index) {
                return array[index];        // Autoboxing, ugh
            }

            public Integer set(int index, Integer value) {
                int oldVal = array[index];
                array[index] = value;           // Auto-unboxing
                return oldVal;                  // autoboxing
            }

            @Override
            public int size() {
                return array.length;
            }
        };
    }


}
