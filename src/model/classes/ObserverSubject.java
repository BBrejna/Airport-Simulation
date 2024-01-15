package model.classes;

import java.util.ArrayList;

public abstract class ObserverSubject<T> {
    protected ArrayList<Observer<T>> observers = new ArrayList<>();

    public void notifyObservers(T t) {
        observers.forEach(observer -> observer.observerUpdateState(t));
    }

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

}
