package CreatingAndDestroyingObjects_1.FinalizersAndCleaners_8.AutoCloseableWithCleanerSafetyNet;

import java.lang.ref.Cleaner;

public class Room implements AutoCloseable {

    private static final Cleaner cleaner = Cleaner.create();

    // state of THIS room, shared w/ the cleanable
    private final State state;

    // the cleanable. Cleans the room when it is eligible for GC
    private final Cleaner.Cleanable cleanable;

    public Room(int stuffToClean) {
        state = new State(stuffToClean);
        /*
            Registers "this" (the Room) as the object to monitor, and "state" as the object containing
            the "Runnable" to perform when the monitored object becomes "phantom reachable"
         */
        cleanable = cleaner.register(this, state);
    }

    @Override
    public void close() throws Exception {

        cleanable.clean();
    }

    /*
        This represents a resource that needs to be terminated/cleaned
        - MUST NOT REFER TO ROOM
            - if State referred to ROOM, it would create a circular dependency that prevented the
            object from ever becoming "Phantom Reachable"
            - if the object never became 'phantom reachable", it would never be eligible for GC
        - MUST BE A STATIC nested class
            - non-static nested class contain REFERENCES to their enclosing instances.
            - shouldn't be a lambda either, as they often capture references to enclosing objects.

        STATIC NESTED CLASS
        - holds resources that are required by the cleaner to clean the room.
            - the "stuffToClean" field.
            - (usually a 'final long' that contains a pointer to a NATIVE PEER)

        - implements Runnable
            - run() is called at most once
                - called by the Cleanable instance that we get when we register the
                State instance w/ our cleaner in the Room constructor
     */
    private static class State implements Runnable {

        int stuffToClean;       // things that need to be freed

        State(int stuffToClean) {
            this.stuffToClean = stuffToClean;
        }

        /*
            Invoked by close() method or "cleaner"
            - This call is triggered by 1 of 2 things
                1.) a call to Room's close() method, which call's Cleanable's clean() method.
                2.) if client fails to call close() by the time the Room is eligible for GC,
                    the cleaner will (hopefully) call run()
                        - no guarantees. :(
         */
        @Override
        public void run() {
            System.out.println("Cleaning room");
            stuffToClean = 0;
        }
    }
}
