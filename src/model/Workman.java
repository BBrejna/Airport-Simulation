package model;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;
import model.classes.logging.Log;
import model.classes.logging.Logger;
import model.classes.people.Person;
import model.classes.workman.CleanIceSnow;
import model.classes.workman.ClearIce;
import model.classes.workman.ClearSnow;
import model.classes.workman.Strategy;

import java.util.ArrayList;

public class Workman extends Person implements Logger {
    private ArrayList<Log> logs = new ArrayList<>();
    public ArrayList<Log> getLogs() {
        return logs;
    }
    /** Singleton design pattern **/
    private static final Workman instance = new Workman("Zbigniew", "Kowalski");

    private Workman(String name, String surname) {
        super(name, surname);
    }

    public static Workman getInstance() {
        return instance;
    }

    Strategy strategy = null;


    // Metoda wykonujaca strategie
    public void prepareFlight(Airplane airplane, Runway runway, String flightNumber, boolean isArrival) {
        // nie trzeba sprawdzac osobno pasu i samolotu bo gdy jeden z nich jest zasniezony to drugi tez itd.
        if (runway.isSnowy() && runway.isIced()) {
            strategy = new CleanIceSnow();
        }
        else if (runway.isIced()) {
            strategy = new ClearIce();
        }
        else if(runway.isSnowy()){
            strategy = new ClearSnow();
        }

        if (strategy != null) {
            if (!isArrival) {
                strategy.prepareAirplane(airplane);
                log("The plane for flight " + flightNumber + " has been "+strategy.getAction());
            }

            strategy.prepareRunway(runway);
            log("The runway " + runway.getRunwayNumber() + " for flight " + flightNumber + " has been "+strategy.getAction());
        }

        repairPlane(airplane, flightNumber, isArrival);

        if (!isArrival){
            if (strategy == null) washPlane(airplane, flightNumber);
            emptyBins(airplane, flightNumber);
        }

        strategy = null;
    }

    public void washPlane(Airplane airplane, String flightNumber) {
        if (!airplane.isClean()) {
            log("The plane for flight "+flightNumber+" has been cleaned");
            airplane.setClean(true);
        }
    }

    public void repairPlane(Airplane airplane, String flightNumber, boolean isArrival) {
        if (airplane.isBroken()) {
            log("The plane "+(isArrival ? "from" : "for")+" flight "+flightNumber+" was repaired");
            airplane.setBroken(false);
        }
    }

    public void emptyBins(Airplane airplane, String flightNumber) {
        if (airplane.isAreBinsFull()) {
            log("Garbage cans on the plane for flight "+flightNumber+" have been emptied");
            airplane.setAreBinsFull(false);
        }
    }

}
