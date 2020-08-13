package Methods_7.OverloadingJudiciously_52.AutoBoxing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class SetList {
    public static void main(String[] args) {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();

        IntStream.range(-3, 3).forEach(i -> {
            set.add(i);
            list.add(i);
        });
        System.out.println(set + " " + list);

        IntStream.range(0, 3).forEach(i -> {
            set.remove(i);
            list.remove(i);
        });
        System.out.println(set + " " + list);

    }
}

/*
    Explanation of Logic and result
    First:
        - we insert -3 -> 2 (inclusive) to a set and a list.
        - we print it out to confirm that they both contain the same elements

    Second:
        - we iterate through the same structure w/ remove() calls.

        WE EXPECT: That both the list and the set will result w/ the positive numbers removed. Right?

        WRONG!

        The SET DOES end up having the positive values removed.
        However, the LIST ends up having the ODD numbers removed????

    WHAT HAPPENED?

        = set.remove(i) selects the overloading remove(E).
            - (E is the element of the type of the set (Integer).)
            - it autoboxes 'i' from int to Integer.
            - this is ok, because in a set, this will remove by value.

        = BUT, list.remove(i) selects the overloading remove(int i).
            - this is a problem, because this removes the element at the specified POSITION in the list.
            - this results in removing the 0th, 1st and 2nd elements:
                - remove the 0 (chops off -3) and then everything shifts down.
                - remove the 1 (skips -2, which is now [0]) and removes -1, and everything shifts down
                - remove the 2 (skips -2 and 0, which are [0] and [1] after the shift)
                - -2, 0 and 2 are left.
 */