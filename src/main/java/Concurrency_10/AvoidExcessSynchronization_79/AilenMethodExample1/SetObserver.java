package Concurrency_10.AvoidExcessSynchronization_79.AilenMethodExample1;

/**
 *  NOTE:
 *      this is identical to
 *
 *          BiConsumer<ObservableSet<E>,E>
 *
 *      The purpose of the custom Functional Interface is because it is more readable
 *
 */
@FunctionalInterface
public interface SetObserver<E> {

    void added(ObservableSet<E> observableSet, E element);

}
