package nl.rug.oop.rts.observable;

/**
 * Interface for Observer objects.
 */
public interface Observer {
    void update();

    default void observe(Observable observable) {
        observable.addObserver(this);
    }
}
