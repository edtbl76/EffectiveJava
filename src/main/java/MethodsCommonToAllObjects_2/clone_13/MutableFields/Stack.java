package MethodsCommonToAllObjects_2.clone_13.MutableFields;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
    }

    public void push(Object element) {
        ensureCapacity();
        elements[size++] = element;
    }

    public Object pop() {
        if (size == 0)
            throw new EmptyStackException();
        Object result = elements[--size];
        elements[size] = null; // eliminates an obsolete reference
        return result;
    }

    private void ensureCapacity() {
        if (elements.length == size)
            elements = Arrays.copyOf(elements, 2 * size + 1);
    }

    /*
        If we try to implement clone() here it is going to copy the fields one by one.
         - this means coping the elements array based on its initial state, which is bare ass naked.
            - the data will be wrong or NPE will occur

        A simple way to work around this in the short term is to recursively call clone on the elements of the
        array
     */
    @Override
    public Stack clone() {
        try {
            Stack result = (Stack) super.clone();

            /*
                NOTE: unlike the main object, we don't have to cast the results of elements.clone to Object[].

                Calling "clone" on an array returns an array whose RUNTIME and COMPILE-TIME types are identicial to
                those of the array being cloned.
                - This is preferred to duplicating
             */
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
