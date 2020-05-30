package CreatingAndDestroyingObjects_1.StaticFactory_1.BasicStaticFactory;

public class User {

    private String name;
    private String email;
    private String country;

    private User(String name, String email, String country) {
        this.name = name;
        this.email = email;
        this.country = country;
    }

    public static User createInstance(String name, String email, String country) {
        return new User(name, email, country);
    }
}
