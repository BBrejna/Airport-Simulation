package model.classes.admin;

import data.admin.AirlinesSet;
import data.admin.AirplaneModelsSet;

import java.util.Random;

public class Airplane  {

    private final AirplaneModel airplaneModel;
    private final Airline airline;
    private final int numberOfSeats;

    public Airplane() {

        // get random airplane model
        Random random = new Random();
        this.airplaneModel = AirplaneModelsSet.AIRPLANES_MODELS[random.nextInt(AirplaneModelsSet.AIRPLANES_MODELS.length)];

        // assign random airline
        this.airline = AirlinesSet.AIRLINES[random.nextInt(AirlinesSet.AIRLINES.length)];

        // specify the number of seats
        int seatsRange = airplaneModel.getMaxSeats() - airplaneModel.getMinSeats();
        this.numberOfSeats = airplaneModel.getMinSeats() + random.nextInt(seatsRange + 1);

    }

    /** GETTERS AND SETTERS */
    public AirplaneModel getAirplaneModel() {
        return airplaneModel;
    }
    public Airline getAirline() { return airline; }
    public int getNumberOfSeats() { return numberOfSeats; }

}
