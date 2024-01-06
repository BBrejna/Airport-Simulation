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
    private static final Workman instance = new Workman("78014305881", "Zbigniew", "Kowalski");

    private Workman(String pesel, String name, String surname) {
        super(pesel, name, surname);
    }

    public static Workman getInstance() {
        return instance;
    }

    Strategy strategy;

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
//    planeGetStan() Parameters: isClean, isSnowy, isBroken, areBinsFull, isLuggageToCollect
//    UWAGA; jesli samolot nie jest zdolny do lotu przekazuje on informacje do administratora lotow
//    washPlane( isClean )
//    clearSnow( isSnowy )
//    repairPlane( isBroken )
//    UWAGA; jesli samolotu nie da sie naprawic wyswietl informacje //przekaz ja do admina
//    emptyBins( areBinsFull )


//    public void planeGetStan(boolean isRunwayReady, boolean isClean, boolean isSnowy, boolean isBroken, boolean areBinsFull, boolean isLuggageToCollect) {
//        System.out.println("planeGetStan:\n\nisClean: " + isClean + "\nisSnowy: " + isSnowy + "\nisBroken: " + isBroken + "\nareBinsFull: " + areBinsFull + "\nisLuggageToCollect: " + isLuggageToCollect);
//
//        if (!isRunwayReady || !isClean || isSnowy || isBroken || areBinsFull || isLuggageToCollect) {
//            System.out.println("ATTENTION: the plane is not capable of flying");
//        }
//    }


    public void washPlane(Airplane airplane) {
        if (!airplane.isClean()) {
            log("The plane has been cleaned");
            airplane.setClean(true);
        }
    }
    // Metoda wykonujaca strategie
    public void prepareFlight(Airplane airplane, Runway runway, String flightNumber) {
        emptyBins(airplane, flightNumber);
        repairPlane(airplane, flightNumber);

        // nie trzeba sprawdzac osobno pasu i samolotu bo gdy jeden z nich jest zasniezony to drugi tez itd.
        if (airplane.isSnowy() && airplane.isIced()) {
            setStrategy(new CleanIceSnow());

            strategy.prepareAirplane(airplane);
            strategy.prepareRunway(runway);

            log("The plane for flight " + flightNumber  + " has been cleared of snow and ice");
            log("The runway " + runway.getRunwayNumber() + " has been cleared of snow and ice");

        }
        else if (airplane.isIced()) {
            setStrategy(new ClearIce());

            strategy.prepareAirplane(airplane);
            strategy.prepareRunway(runway);

            log("The plane for flight " + flightNumber  + " has been cleared of ice");
            log("The runway " + runway.getRunwayNumber() + " has been cleared of ice");
        }
        else if(airplane.isSnowy()){
            setStrategy(new ClearSnow());

            strategy.prepareAirplane(airplane);
            strategy.prepareRunway(runway);

            log("The plane for flight " + flightNumber  + " has been cleared of snow");
            log("The runway " + runway.getRunwayNumber() + " has been cleared of snow");
        }

    }
   /* public void clearSnow(Airplane airplane) {
        if (airplane.isSnowy()) {
            log("The plane was cleared of snow");
            airplane.setSnowy(false);
        }
    }*/

   /* public void clearRunway(Runway runway){
        if (runway.isSnowy()) {
            log("The runway has been cleared of snow");
            runway.setSnowy(false);
        }
        if (runway.isIced()){
            log("The runway was cleared of ice");
            runway.setIced(false);
        }
    }*/


    public void repairPlane(Airplane airplane, String flightNumber) {
        if (airplane.isBroken()) {
            log("The plane for flight "+flightNumber+" was repaired");
            airplane.setBroken(false);
        }
    }

    public void emptyBins(Airplane airplane, String flightNumber) {
        if (airplane.isAreBinsFull()) {
            log("The garbage cans on the plane for flight "+flightNumber+" have been emptied");
            airplane.setAreBinsFull(false);
        }
    }

}
