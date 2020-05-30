package Generics_4.BoundedWildcards_31;

import java.util.List;

public class Swap {

    public static void swap(List<?> list, int i, int j) {
        swapCaptureHelper(list, i, j);
    }

    private static <E> void swapCaptureHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
