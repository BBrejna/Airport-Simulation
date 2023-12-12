package model.classes.admin;

import data.admin.AirplaneModelsSet;

import java.util.Random;

public class AirplaneModel {

    private final String modelName;
    private final int minSeats;
    private final int maxSeats;

    public AirplaneModel(String modelName, int minSeats, int maxSeats) {
        this.modelName = modelName;
        this.minSeats = minSeats;
        this.maxSeats = maxSeats;
    }

    /** GETTERS */
    public String getModelName() {
        return modelName;
    }

    public int getMinSeats() {
        return minSeats;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

}
