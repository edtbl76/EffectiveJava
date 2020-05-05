package Generics_4.FavorGenerics_29.GenerifyTry1;

import java.util.Arrays;
import java.util.EmptyStackException;

// Object-based Collection is a prime example for generification
public class Stack<E> {

    private E[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        /*
            This line is commented out because it won't compile!

            Stack.java:19: generic array creation
                elements = new E[DEFAULT_INITIAL_CAPACITY];
                            ^

            This fails because you can't create an array of a NON-REIFIABLE TYPE
                E = a non reifiable type, and we are backing it w/ an array.

         */
//        elements = new E[DEFAULT_INITIAL_CAPACITY];
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
