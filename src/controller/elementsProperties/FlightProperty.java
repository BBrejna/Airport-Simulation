package controller.elementsProperties;

import javafx.scene.image.ImageView;
import model.tools.Tools;

public class FlightProperty {

    private String flightNumber;
    private String hour;
    private String city;
    private String type;
    private String airplane;
    private String delay;
    private ImageView deleteImage;

    public FlightProperty(String flightNumber, int hour, String city, String type, String airplane, String delay, ImageView deleteImage) {
        this.flightNumber = flightNumber;
        this.hour = Tools.convertMinutesToTime(hour);
        this.city = city;
        this.type = type;
        this.airplane = airplane;
        this.delay = delay;
        this.deleteImage = deleteImage;
    }

    /** GETTERS AND SETTERS */
    public String getFlightNumber() {return flightNumber;}
    public void setFlightNumber(String flightNumber) {this.flightNumber = flightNumber;}
    public String getHour() {return hour;}
    public void setHour(String hour) {this.hour = hour;}

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAirplane() {
        return airplane;
    }

    public void setAirplane(String airplane) {
        this.airplane = airplane;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public ImageView getDeleteImage() {
        return deleteImage;
    }
}
