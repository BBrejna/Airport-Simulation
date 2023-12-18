package model.classes.simulation;

import model.classes.Observer;

public class Listener implements Observer<Weather> {
    public void calculateDelayProbability(Weather weather) {
        System.out.println("Listener: current temperature = "+weather.getTemperature()+"!");
    }
}
