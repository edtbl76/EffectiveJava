package Generics_4.FavorGenerics_29.GenericTry3B1;

import java.util.Arrays;
import java.util.EmptyStackException;

// Object-based Collection is a prime example for generification
public class Stack1<E> {

    /*
        Another option to eliminate the cast exception is to change the return type of Elements to Object
     */
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack1() {
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
            The lines below are commented because this fails to compile.

                Stack.java:35 incompatible types
                found: Object, required: E
                    E result = elements[--size];
                                        ^
         */
//        E result = elements[--size];

        elements[size] = null;
//        return result;
        return null;
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
