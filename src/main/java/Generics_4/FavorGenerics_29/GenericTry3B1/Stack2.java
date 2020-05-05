package Generics_4.FavorGenerics_29.GenericTry3B1;

import java.util.Arrays;
import java.util.EmptyStackException;

// Object-based Collection is a prime example for generification
public class Stack2<E> {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack2() {
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(E e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }

        /*
            This is a "kick the can" solution, because we have moved the cast to another section, resulting in
            another unchecked cast

            However, E is NONREIFIABLE, so there is no way we can check the cast at runtime.
         */
        @SuppressWarnings("unchecked")
        E result = (E)elements[--size];

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
