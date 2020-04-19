package CreatingAndDestroyingObjects_1.Singleton_3.StaticFactory;

// Singleton w/ Static Factory
public class Elvis {

    // when using a static factory, the INSTANCE field is PRIVATE
    private static final Elvis INSTANCE = new Elvis();

    private Elvis() {}

    /*
        Remember the getInstance() method?
     */
    public static Elvis getInstance() {
        return INSTANCE;
    }
}
