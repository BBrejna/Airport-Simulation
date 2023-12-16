package model.classes;

import java.util.ArrayList;

public class Subject<T> {
    ArrayList<Observer<T>> observers = new ArrayList<>();

    public void notifyObservers(T t) {
        observers.forEach(observer -> observer.calculateDelayProbability(t));
    }

    public void addObserver(Observer<T> observer) {
        observers.add(observer);
    }

}
