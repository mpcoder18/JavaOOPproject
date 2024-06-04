package nl.rug.oop.rts.observable;

import java.util.List;

/**
 * Interface for Observable objects.
 */
public interface Observable {
    List<Observer> getObservers();

    void addObserver(Observer observer);

    /**
     * Notify all observers.
     */
    default void notifyAllObservers() {
        for (Observer observer : this.getObservers()) {
            observer.update();
        }
    }
}
