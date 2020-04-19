package CreatingAndDestroyingObjects_1.FinalizersAndCleaners_8.AutoCloseableWithCleanerSafetyNet;

public class OkClient {
    public static void main(String[] args) {
        /*
            The BadClient (should usually) fail to execute the Cleanable, but this client is a little bit more
            reliable by calling System.gc, which forces the cleanable to be executed.
         */
        new Room(101);
        System.out.println("GC it, and maybe it will clean");
        System.gc();
    }
}
