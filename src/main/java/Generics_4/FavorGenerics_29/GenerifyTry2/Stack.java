package Generics_4.FavorGenerics_29.GenerifyTry2;

import java.util.Arrays;
import java.util.EmptyStackException;

// Object-based Collection is a prime example for generification
public class Stack<E> {

    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        /*
            This resolves the compilation error we encountered in Try 1

            However, this is going to result in an unchecked cast warning, because we've cast an Object array
            to our ACTUAL TYPE PARAM.

                Stack.java.22: warning: [unchecked] unchecked cast
                found: Object[], required: E[]
                    elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];



         */
        elements = (E[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        E result = elements[--size];
        /*
            ELIMINATE OBSOLETE REFERENCE to avoid UORs
         */
        elements[size] = null;
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }
}
