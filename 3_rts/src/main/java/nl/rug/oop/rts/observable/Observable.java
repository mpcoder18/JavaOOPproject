package nl.rug.oop.rts.observable;

import java.util.List;

public interface Observable {
    List<Observer> observers = null;

    void addObserver(Observer observer);
}
