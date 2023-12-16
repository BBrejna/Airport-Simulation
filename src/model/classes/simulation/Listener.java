package model.classes.simulation;

import model.classes.Observer;

public class Listener implements Observer {
    @Override
    public void calculateDelayProbability(Object _weather) {
        Weather weather = (Weather) _weather;
        System.out.println(weather.getTemperature());
    }
}
