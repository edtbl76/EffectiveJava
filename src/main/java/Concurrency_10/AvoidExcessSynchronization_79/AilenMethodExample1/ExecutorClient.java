package Concurrency_10.AvoidExcessSynchronization_79.AilenMethodExample1;

import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorClient {

    public static void main(String[] args) {
        ObservableSet<Integer> set = new ObservableSet<>(new HashSet<>());

        set.addObserver(new SetObserver<Integer>() {
            @Override
            public void added(ObservableSet<Integer> observableSet, Integer element) {
                System.out.println(element);

                /**
                 *  Let's try to solve the problem of the FancyClient by pushing the work onto a background thread
                 *  (NOTE: This isn't a very good solution)
                 */
                if (element == 23) {
                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    try {
                        executorService.submit(() -> observableSet.removeObserver(this)).get();
                        // Note: Multi-Catch
                    } catch (ExecutionException | InterruptedException ex) {
                        throw new AssertionError(ex);
                    } finally {
                        executorService.shutdown();
                    }
                }
            }
        });

        for (int i = 0; i < 100; i++) {
            set.add(i);
        }
    }
}
