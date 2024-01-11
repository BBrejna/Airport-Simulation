package model.classes;

import model.classes.admin.Flight;

import java.util.ArrayList;

public interface StatisticsObserver {
    void observerUpdateState(ArrayList<Flight> flights);
}
