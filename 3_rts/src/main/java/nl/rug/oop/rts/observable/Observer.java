package nl.rug.oop.rts.observable;

public interface Observer {
    void update();

    default void observe(Observable observable) {
        observable.addObserver(this);
    }
}
