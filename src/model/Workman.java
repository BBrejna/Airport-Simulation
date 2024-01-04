package model;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;
import model.classes.logging.Log;
import model.classes.logging.Logger;
import model.classes.people.Person;
import model.classes.workman.Odladzanie;
import model.classes.workman.Odsniezanie;
import model.classes.workman.Strategia;

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

    Strategia strategia;

    public void setStrategia(Strategia strategia) {
        this.strategia = strategia;
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
//            System.out.println("UWAGA: samolot nie jest zdolny do lotu");
//        }
//    }


    public void washPlane(Airplane airplane) {
        if (!airplane.isClean()) {
            log("Samolot zostal wysprzatany");
            airplane.setClean(true);
        }
    }
    // Metoda wykonujaca strategie
    public void wykonaj_przygotwania(Airplane airplane, Runway runway) {
        // nie trzeba sprawdzac osobno pasu i samolotu bo gdy jeden z nich jest zasniezony to drugi tez itd.
        if (airplane.isSnowy() || runway.isSnowy()) {
            setStrategia(new Odsniezanie());

            strategia.przygotujSamolot(airplane);
            strategia.przygotujPas(runway);

            log("Samolot zostal odsniezony");
            log("Pas startowy został odsniezony");

        }
        if (airplane.isIced() || runway.isIced()) {
            setStrategia(new Odladzanie());

            strategia.przygotujSamolot(airplane);
            strategia.przygotujPas(runway);

            log("Samolot zostal odlodzony");
            log("Pas startowy został odlodzony");
        }
    }
   /* public void clearSnow(Airplane airplane) {
        if (airplane.isSnowy()) {
            log("Samolot zostal odsniezony");
            airplane.setSnowy(false);
        }
    }*/

   /* public void clearRunway(Runway runway){
        if (runway.isSnowy()) {
            log("Pas startowy został odsnieżony");
            runway.setSnowy(false);
        }
        if (runway.isIced()){
            log("Pas startowy został odlodzony");
            runway.setIced(false);
        }
    }*/


    public void repairPlane(Airplane airplane) {
        if (airplane.isBroken()) {
            log("Samolot zostal naprawiony");
            airplane.setBroken(false);
        }
    }

    public void emptyBins(Airplane airplane) {
        if (airplane.isAreBinsFull()) {
            log("Smietniki zostaly oproznione");
            airplane.setAreBinsFull(false);
        }
    }

}
