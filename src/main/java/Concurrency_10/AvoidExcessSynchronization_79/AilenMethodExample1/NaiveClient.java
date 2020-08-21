package Concurrency_10.AvoidExcessSynchronization_79.AilenMethodExample1;

import java.util.HashSet;

public class NaiveClient {

    public static void main(String[] args) {
        ObservableSet<Integer>  set = new ObservableSet<>(new HashSet<>());

        set.addObserver((s, e) -> System.out.println(e));

        for (int i = 0; i < 100; i ++) {
            set.add(i);
        }
    }
}
