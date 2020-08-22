package Concurrency_10.DontDependOnThreadScheduler_84.BusyWaitExample;

public class CrappyCountDownLatch {

    private int count;

    public CrappyCountDownLatch(int count) {
        if (count < 0) {
            throw new IllegalArgumentException(count + " < 0");
        }
        this.count = count;
    }

    public void await() {
        // WOOF. This is slow.
        while (true) {
            synchronized (this) {
                if (count == 0) {
                    return;
                }
            }
        }
    }

    public synchronized void countDown() {
        if (count != 0) {
            count--;
        }
    }
}
