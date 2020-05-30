package CreatingAndDestroyingObjects_1.StaticFactory_1.SingletonStaticFactory;

public class User {

    private static volatile User instance = null;

    private final String name;
    private final String email;
    private final String country;

    private User(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public static User getInstance(String name, String email, String country) {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User(name, email, country);
                }
            }
        }
        return instance;
    }


}
