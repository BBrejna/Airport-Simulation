package model.classes.admin;

import data.admin.AirlinesSet;
import data.admin.AirplaneModelsSet;

import java.util.Random;

public class Airplane  {

    private final AirplaneModel airplaneModel;
    private final Airline airline;
    private final int numberOfSeats;
    private final int[] numberOfSeatsClasses;
    private final int maxFreeLuggageWeight;
    private boolean isSnowy;
    private boolean isClean;
    private boolean areBinsFull;
    private boolean isBroken;


    public Airplane() {

        // get random airplane model
        Random random = new Random();
        this.airplaneModel = AirplaneModelsSet.AIRPLANES_MODELS[random.nextInt(AirplaneModelsSet.AIRPLANES_MODELS.length)];

        // assign random airline
        this.airline = AirlinesSet.AIRLINES[random.nextInt(AirlinesSet.AIRLINES.length)];

        // specify the number of seats
        int seatsRange = airplaneModel.getMaxSeats() - airplaneModel.getMinSeats();
        this.numberOfSeats = airplaneModel.getMinSeats() + random.nextInt(seatsRange + 1);

        //assign maxFreeLuggageWeight
        this.maxFreeLuggageWeight = 20 - random.nextInt(5);

        //generate number of seats in each class
        this.numberOfSeatsClasses = new int[3];
        if (numberOfSeats < 100) {
            numberOfSeatsClasses[2] = numberOfSeats;
        } else {
            int eko = (int) (0.5 * numberOfSeats);
            int standard = (int) (0.4 * numberOfSeats);
            int prem = numberOfSeats - eko - standard;
            numberOfSeatsClasses[0] = eko;
            numberOfSeatsClasses[1] = standard;
            numberOfSeatsClasses[2] = prem;
        }

        //assign values to airplane status
        isBroken = false;
        isClean = false;
        isSnowy = false;
        areBinsFull = false;
    }

    public String toString() {
        String toReturn = "\n\t\tAirplane model: " + getAirplaneModel().toString() + "\n\t\tAirline: " + getAirline().toString()
                + "\n\t\tNumber of seats: " + getNumberOfSeats() + "\n\t\tMax free luggage weight: " + getMaxFreeLuggageWeight()
                + "\n\t\tSeats classes division: "
                + "\n\t\tisClean: " + isClean + "\n\t\tisSnowy: " + isSnowy + "\n\t\tisBroken: " + isBroken + "\n\t\tareBinsFull: " + areBinsFull;
        for(int seats: getNumberOfSeatsClasses()) toReturn += seats + " ";
        return toReturn;
    }

    /** GETTERS AND SETTERS */
    public AirplaneModel getAirplaneModel() {
        return airplaneModel;
    }
    public Airline getAirline() { return airline; }
    public int getNumberOfSeats() { return numberOfSeats; }

    public int[] getNumberOfSeatsClasses() {
        return numberOfSeatsClasses;
    }
    public int getMaxFreeLuggageWeight() {
        return maxFreeLuggageWeight;
    }

    public boolean isSnowy() {
        return isSnowy;
    }

    public void setSnowy(boolean snowy) {
        isSnowy = snowy;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public boolean isAreBinsFull() {
        return areBinsFull;
    }

    public void setAreBinsFull(boolean areBinsFull) {
        this.areBinsFull = areBinsFull;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }
}
