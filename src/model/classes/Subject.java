package model.classes;

import java.util.ArrayList;

public class Subject<T> {
    protected ArrayList<Observer<T>> observers = new ArrayList<>();

    public void notifyObservers(T t) {
        observers.forEach(observer -> observer.updateState(t));
    }

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

}
