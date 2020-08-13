package Methods_7.OverloadingJudiciously_52.AutoBoxing;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.IntStream;

public class SetListSolution {

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
            // Provide an explicit cast to ensure that autoboxing doesnt' fuck things up.
            list.remove((Integer) i);
        });
        System.out.println(set + " " + list);




    }

}
