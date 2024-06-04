package nl.rug.oop.rts.observable;

import java.util.List;

public interface Observable {
    List<Observer> getObservers();

    void addObserver(Observer observer);

    default void notifyAllObservers() {
        for (Observer observer : this.getObservers()) {
            observer.update();
        }
    };
}
