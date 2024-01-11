package model.classes;

import model.Simulation;
import model.classes.admin.Flight;

import java.util.ArrayList;

public class StatisticsSubject {
    private StatisticsObserver observer;

    private static final StatisticsSubject instance = new StatisticsSubject();

    public static StatisticsSubject getInstance() {
        return instance;
    }

    public void addObserver(StatisticsObserver observer) {
        this.observer = observer;
    }

    public void notifyObserver(ArrayList<Flight> flights) {
        observer.observerUpdateState(flights);
    }
}
