package controller.elementsProperties;

import model.tools.Tools;

public class SalesmanFlightProperty {

    private String flightNumber;
    private String hour;
    private String city;
    private String seats;
    private String price;

    public SalesmanFlightProperty(String flightNumber, int hour, String city, String seats, String price) {
        this.flightNumber = flightNumber;
        this.hour = Tools.convertMinutesToTime(hour);
        this.city = city;
        this.seats = seats;
        this.price = price;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
