package CreatingAndDestroyingObjects_1.FinalizersAndCleaners_8.AutoCloseableWithCleanerSafetyNet;

public class GoodClient {
    public static void main(String[] args) throws Exception {
        try (Room room = new Room(7)) {
            System.out.println("See ya!");
        }
    }
}
