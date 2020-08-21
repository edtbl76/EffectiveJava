package Concurrency_10.PreferConcurrencyUtilties_81.TimingConcurrentExecExample;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class FrameworkForTimingConcurrentExecution {

    public static long time(Executor executor, int concurrency, Runnable runnable) throws InterruptedException {

        /*
            There are three countdown latches:

                1.) ready latch is used by worker threads to tell the "timer thread" when they're ready for work
                2.) worker threads wait on 'start' latch (2nd latch)
                3.) when the last worker thread invokes ready.countDown
                    - timer thread records start time
                    - timer invokes start.countDown
                    - worker threads can now proceed.
                4.) timer thread waits on 'done' (3rd latch)
                    - waits until last worker threads finishes executing the action, then calls done.countDown.
                5.) timer thread wakes up, records end time.

         */
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {

                // Tell the timer that we're ready to start doing work
                ready.countDown();

                try {
                    // Wait for peers to be ready
                    start.await();
                    runnable.run();
                } catch (InterruptedException e) {

                    /*
                        This causes an interrupted worker thread to return from its run() method
                        - NOTE: The executor must handle the following interrupt.
                     */
                    Thread.currentThread().interrupt();
                } finally {
                    // Tell the timer we're done (so that the VM will stop)
                    done.countDown();
                }
            });
        }

        // Wait for all workers to be ready
        ready.await();
        long startNanos = System.nanoTime();

        // Threads start working
        start.countDown();

        // Wait for workers to complete
        done.await();
        return System.nanoTime() - startNanos;
    }
}
