package Generics_4.TypesafeHeterogeneousContainers_33.UnboundedTypeTokenExamples;

public class Client {

    public static void main(String[] args) {
        Favorites favorites = new FavoritesImpl();

        /*
            Since the KEY is parameterized by the Generic, the instance of Favorites is ALWAYS typesafe.
            - we'll always get back the type we ask for, because the type itself is the key used for put()/get().


         */
        favorites.putFavorite(String.class, "Java");
        favorites.putFavorite(Integer.class, 0xcafebabe);
        favorites.putFavorite(Class.class, Favorites.class);

        String faveString = favorites.getFavorite(String.class);
        int faveInt = favorites.getFavorite(Integer.class);
        Class<?> faveClass = favorites.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", faveString, faveInt, faveClass.getName());
    }
}
