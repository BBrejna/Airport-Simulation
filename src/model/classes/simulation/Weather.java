package model.classes.simulation;

import java.util.Random;

public class Weather {
    private double temperature;
    private double wind;
    private double rain;
    private double snow;
    private double fog;
    private double clouds;

    public Weather() {
        this.temperature = 25;
        this.wind = 0.5;
        this.rain = 0.5;
        this.snow = 0.5;
        this.fog = 0.5;
        this.clouds = 0.5;
    }

    public Weather(double temperature, double wind, double rain, double snow, double fog, double clouds) {
        this.temperature = temperature;
        this.wind = wind;
        this.rain = rain;
        this.snow = snow;
        this.fog = fog;
        this.clouds = clouds;
    }

    public void generateWeather(){
        Random rand = new Random();
        //Do sprawdzania czy dziala odladzanie
        /*boolean b = 0 != rand.nextInt(0,10);
        if(!b) {
            temperature = -6;
        }
        else{
            temperature = Math.min(40, Math.max(-20, temperature + rand.nextDouble(-1, 1)));
        }*/
        temperature = Math.min(40, Math.max(-20, temperature + rand.nextDouble(-1, 1)));
        wind = rand.nextDouble(0, 1);
        rain = rand.nextDouble(0, 1);
        if(temperature < -5){
            snow = rand.nextDouble(0.7, 1);
        }
        else if(temperature < 0){
            snow = rand.nextDouble(0.4, 0.7);
        }
        else if(temperature < 5){
            snow = rand.nextDouble(0, 0.3);
        }
        else{
            snow = 0;
        }

        fog = rand.nextDouble(0, 1);
        clouds = rand.nextDouble(0, 1);
    }
    // GETTERS AND SETTERS

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }

    public double getFog() {
        return fog;
    }

    public void setFog(double fog) {
        this.fog = fog;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }
}