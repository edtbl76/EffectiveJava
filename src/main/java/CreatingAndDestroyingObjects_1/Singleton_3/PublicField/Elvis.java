package CreatingAndDestroyingObjects_1.Singleton_3.PublicField;

// Singleton w/ public final field
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() {

    }

    public void leaveTheBuilding()  {};
}
