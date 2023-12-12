package model.classes.admin;

import data.admin.AirplaneModelsSet;

import java.util.Random;

public class Airplane  {

    private final AirplaneModel airplaneModel;

    public Airplane() {

        // get random airplane model
        Random random = new Random();
        this.airplaneModel = AirplaneModelsSet.AIRPLANES_MODELS[random.nextInt(AirplaneModelsSet.AIRPLANES_MODELS.length)];

    }

    /** GETTERS AND SETTERS */
    public AirplaneModel getAirplaneModel() {
        return airplaneModel;
    }

}
