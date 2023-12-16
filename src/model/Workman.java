package model;

import model.classes.people.Person;

public class Workman extends Person {

    /** 2137 */
    public Workman(String pesel, String name, String surname) {
        super(pesel, name, surname);
    }

//    planeGetStan() Parameters: isClean, isSnowy, isBroken, areBinsFull, isLuggageToCollect
//    UWAGA; jesli samolot nie jest zdolny do lotu przekazuje on informacje do administratora lotow
//    washPlane( isClean )
//    clearSnow( isSnowy )
//    repairPlane( isBroken )
//    UWAGA; jesli samolotu nie da sie naprawic wyswietl informacje //przekaz ja do admina
//    emptyBins( areBinsFull )


    public void planeGetStan(boolean isClean, boolean isSnowy, boolean isBroken, boolean areBinsFull, boolean isLuggageToCollect) {
        System.out.println("planeGetStan:\n\nisClean: " + isClean + "\nisSnowy: " + isSnowy + "\nisBroken: " + isBroken + "\nareBinsFull: " + areBinsFull + "\nisLuggageToCollect: " + isLuggageToCollect);

        if (!isClean || isSnowy || isBroken || areBinsFull || isLuggageToCollect) {
            System.out.println("UWAGA: samolot nie jest zdolny do lotu");
        }
    }

    public void washPlane(boolean isClean) {
        if (!isClean) {
            System.out.println("Samolot zostal wysprzatany");
            isClean = true;
        }
    }

    public void clearSnow(boolean isSnowy) {
        if (isSnowy) {
            System.out.println("Samolot zostal odsniezony");
            isSnowy = false;
        }
    }

    public void repairPlane(boolean isBroken) {
        if (isBroken) {
            System.out.println("Samolot zostal naprawiony");
            isBroken = false;
        }
    }

    public void emptyBins(boolean areBinsFull) {
        if (areBinsFull) {
            System.out.println("Smietniki zostaly oproznione");
            areBinsFull = false;
        }
    }

}
