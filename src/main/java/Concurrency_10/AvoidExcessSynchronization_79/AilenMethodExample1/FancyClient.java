package Concurrency_10.AvoidExcessSynchronization_79.AilenMethodExample1;

import java.util.HashSet;

public class FancyClient {

    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        /**
         *  This is a bit fancier than the NaiveClient
         *
         *  We can't use a Lambda, because Lambda's can't access themselves
         *  - we use the anonymous class so that we can call
         *
         *      observableSet.removeObserver()
         */
        set.addObserver(new SetObserver<>() {
            @Override
            public void added(ObservableSet<Integer> observableSet, Integer element) {
                System.out.println(element);
                if (element == 23) {
                    observableSet.removeObserver(this);
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
