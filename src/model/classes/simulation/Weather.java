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
    snow: 0 - 10 mm/h
    fog: 1 - 5
    clouds: 1 - 5
     */

    public Weather(){
        Random rand = new Random();
        this.temperature = rand.nextDouble(-20, 35);
        this.wind = rand.nextDouble(0, 100);
        this.rain = generateRain(temperature, clouds);
        this.snow = generateSnow(temperature, clouds);
        this.fog = generateFog(wind);
        this.clouds = generateClouds(wind);
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
        if (wind < 30) {
            wind = Math.min(100, Math.max(0, wind + rand.nextDouble(-3, 5)));
        }
        else if(wind < 50){
            wind = Math.min(100, Math.max(0, wind + rand.nextDouble(-4, 5)));
        }
        else if(wind < 70){
            wind = Math.min(100, Math.max(0, wind + rand.nextDouble(-5, 5)));
        }
        else if(wind < 90){
            wind = Math.min(100, Math.max(0, wind + rand.nextDouble(-6, 5)));
        }
        else{
            wind = Math.min(100, Math.max(0, wind + rand.nextDouble(-7, 5)));
        }
        rain = Math.min(50, Math.max(0, rain + generateRainDelta(temperature, clouds)));
        snow = Math.min(10, Math.max(0, snow + generateSnowDelta(temperature, clouds)));
        clouds = Math.min(5, Math.max(1, clouds + generateCloudsDelta(wind)));
        fog = generateFog(wind);
    }
    public double generateRainDelta(double temperature, double clouds){
        Random rand = new Random();

        if(temperature > 15 || clouds < 2){
            return rand.nextDouble(-2, -1);
        }
        else if(temperature <= -3){
            return rand.nextDouble(-1, -0.5);
        }
        else{
            if(clouds > 4){
                return rand.nextDouble(-0.3, 0.7);
            }
            else if (clouds > 2){
                return rand.nextDouble(-0.5, 0.5);
            }
        }
        return 0;
    }
    public double generateSnowDelta(double temperature, double clouds){
        Random rand = new Random();

        if(temperature > 0){
            return rand.nextDouble(-1, -0.5);
        }
        else if(clouds < 2){
            return  rand.nextDouble(-1, -0.5);
        }
        else{
            if(clouds > 4){
                return rand.nextDouble(-0.1, 0.3);
            }
            else if (clouds > 2){
                return rand.nextDouble(-0.2, 0.2);
            }
        }
        return 0;
    }

    public double generateCloudsDelta(double wind){
        Random rand = new Random();
        if(wind < 30){
            return rand.nextDouble(-0.1, 0.3);
        }
        else if(wind < 60){
            return rand.nextDouble(-0.2, 0.2);
        }
        else if(wind < 80){
            return rand.nextDouble(-0.3, -0.1);
        }
        else{
            return rand.nextDouble(-0.5, -0.3);
        }
    }
    public double generateRain(double temperature, double clouds){
        Random rand = new Random();
        if(temperature < 0 && temperature > -3 && clouds > 2){
            return rand.nextDouble(0, 3);
        }
        else if(temperature < 5 && temperature > 0 && clouds > 3){
            return rand.nextDouble(8, 15);
        }
        else if(temperature < 5 && temperature > 0 && clouds > 2){
            return rand.nextDouble(2, 8);
        }
        else if(temperature < 10 && temperature > 5 && clouds > 3){
            return rand.nextDouble(8, 20);
        }
        else if(temperature < 10 && temperature > 5 && clouds > 2){
            return rand.nextDouble(0, 8);
        }
        else if(temperature < 15 && temperature > 10 && clouds > 2){
            return rand.nextDouble(0, 8);
        }
        else if(temperature < 15 && temperature > 10 && clouds > 1){
            return rand.nextDouble(0, 3);
        }
        else{
            return 0;
        }
    }
    public double generateClouds(double wind){
        Random rand = new Random();
        if(wind < 30){
            return rand.nextDouble(3, 5);
        }
        else if(wind < 60){
            return rand.nextDouble(2, 4);
        }
        else if(wind < 80){
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
        else if(wind < 80){
            return rand.nextDouble(1, 2);
        }
        else{
            return 1;
        }
    }
    public double generateSnow(double temperature, double clouds){
        Random rand = new Random();
        if(temperature < -10 && clouds > 3){
            return rand.nextDouble(5, 10);
        }
        else if(temperature < 0 && clouds > 2){
            return rand.nextDouble(1, 5);
        }
        else if(temperature < 5 && clouds > 2){
            return rand.nextDouble(0, 1);
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