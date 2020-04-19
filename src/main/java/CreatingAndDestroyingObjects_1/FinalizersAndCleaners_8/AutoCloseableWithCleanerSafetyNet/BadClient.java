package CreatingAndDestroyingObjects_1.FinalizersAndCleaners_8.AutoCloseableWithCleanerSafetyNet;

public class BadClient {
    public static void main(String[] args) {
        // Note... this never cleans the room.
        /*
            If you run this, you most likely won't see the "Cleaning Room" message which tells us that the
            safety net was executed.
            This is the unpredictability of Cleaners.
            - we didn't call close() (by using the try-with-resources), so this should have forced the run()
            method to be called, but it wasn't.
         */
        new Room(99);
        System.out.println("Peace out!");
    }
}
