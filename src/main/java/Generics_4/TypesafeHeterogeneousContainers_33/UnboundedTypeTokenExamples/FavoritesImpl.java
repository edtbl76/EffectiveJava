package Generics_4.TypesafeHeterogeneousContainers_33.UnboundedTypeTokenExamples;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FavoritesImpl implements Favorites {
    /*
        Each Favorites Instance is backed by a Map (we knew this already!)
        - Remember the problem w/ unbounded wildcards and collections???
        - That isn't a problem here, because the unbounded wildcard is the KEY, rather than the map itself.

        By NESTING the unbounded wc we get a separate behavior
        - we create the ability to have DIFFERENT parameterized types
        - This is how heterogeneity occurs.

        The VALUE type of the Map is just an Object.
        - the guarantee that the Object is of the correct type isn't provided by the Map.

     */
    private final Map<Class<?>, Object> favorites = new HashMap<>();

    /*
        This seems pretty straight forward.
        - It's important to note that the "put" operation doesn't establish any guarantees about the Type as a key,
        and whether or not the associated data is in fact that type.

        *** Limitation 1:
        - we have actually already solved this by wrapping type.cast() around instance.

        By using type.cast() around the instance, we are preventing someone from breaking the type invariant (i.e.
        passing in a value that isn't of the same instance of the type)

            java.util.Collections supports this w/ collection wrappers such as:
                checkedSet
                checkedList
                checkedMapo

            - All of these wrappers add reification to the collections that they wrap.


        *** Limitation 2:
        - This will not support non-reifiable types (i.e. List<String>).
        - Code that tries to use non-reifiable types won't compile. There is no such thing as List<String>.class.
     */
    @Override
    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), type.cast(instance));
    }

    /*
        NOT as a straight forward
        - we get the value from the map first (based on the passed in object type)
        - The object reference is correct, bu the compile-time type is still Object, so it's going to be wrong.

        We DYNAMICALLY CAST the object reference to the type represented by the Class object, using Class's "cast()"
        method.
        - this is the "dynamic" version of Java's cast() operator (which is static)
        - it checks if the argument is an instance of the type represented by the Class object passed in as the key.
        - if it is correct, we return it, if not -> ClassCastException.

            public interface Class<T> {
                T cast(Object obj);
            }

        This is where we establish guarantees associated with the type.
        - If the Client code compiles cleanly, then we shouldn't get a ClassCast Exception.


     */
    @Override
    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
    }
}
