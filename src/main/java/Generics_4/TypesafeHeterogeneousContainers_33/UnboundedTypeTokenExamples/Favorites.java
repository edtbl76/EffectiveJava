package Generics_4.TypesafeHeterogeneousContainers_33.UnboundedTypeTokenExamples;

/*
    Our API:
    - This looks like a basic map, but we are parameterizing the KEY of the Map, rather than the map itself.
 */
public interface Favorites {
    <T> void putFavorite(Class<T> type, T instance);
    <T> T getFavorite(Class<T> type);
}
