package model.classes.simulation;

import java.util.Random;

public class Weather {
    private double temperature;
    private double wind;
    private double rain;
    private double snow;
    private double fog;
    private double clouds;

    /*
    temperature: -20 - 40 celcius
    wind: 0 - 120 kph
    rain: 0 - 50 mm/h
    snow: 0 - 30 mm/h
    fog: 1 - 5
    clouds: 1 - 5
     */

    public Weather(){
        Random rand = new Random();
        this.temperature = rand.nextDouble(-20, 40);
        this.wind = rand.nextDouble(0, 120);
        this.rain = rand.nextDouble(0, 50);
        this.snow = rand.nextDouble(0, 30);
        this.fog = rand.nextDouble(1, 5);
        this.clouds = rand.nextDouble(1, 5);
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

        temperature = Math.min(40, Math.max(-20, temperature + rand.nextDouble(-1, 1)));
        wind = Math.min(120, Math.max(0, wind + rand.nextDouble(-5, 5)));
        rain = generateRain(temperature);
        snow = generateSnow(temperature);
        clouds = generateClouds(wind);
        fog = generateFog(wind);
    }

    public double generateClouds(double wind){
        Random rand = new Random();
        if(wind < 30){
            return rand.nextDouble(3, 5);
        }
        else if(wind < 60){
            return rand.nextDouble(2, 4);
        }
        else if(wind < 90){
            return rand.nextDouble(1, 3);
        }
        else{
            return rand.nextDouble(1, 2);
        }
    }
    public double generateFog(double wind){
        Random rand = new Random();
        if(wind < 30){
            return rand.nextDouble(3, 5);
        }
        else if(wind < 60){
            return rand.nextDouble(2, 3);
        }
        else if(wind < 90){
            return rand.nextDouble(1, 2);
        }
        else{
            return 1;
        }
    }
    public double generateSnow(double temperature){
        Random rand = new Random();
        if(temperature < -10){
            return rand.nextDouble(20, 30);
        }
        else if(temperature < 0){
            return rand.nextDouble(10, 20);
        }
        else if(temperature < 5){
            return rand.nextDouble(0, 10);
        }
        else{
            return 0;
        }
    }
    public double generateRain(double temperature){
        Random rand = new Random();
        if(temperature < 0){
            return rand.nextDouble(0, 10);
        }
        else if(temperature < 5){
            return rand.nextDouble(10, 30);
        }
        else if(temperature < 10){
            return rand.nextDouble(30, 50);
        }
        else if(temperature < 15){
            return rand.nextDouble(15, 35);
        }
        else{
            return 0;
        }
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