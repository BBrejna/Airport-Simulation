package model.classes.admin;

public class Airport {

    private final String city;
    private final String airportName;

    public Airport(String city, String airportName) {
        this.city = city;
        this.airportName = airportName;
    }

    public String toString() {
        return "\n\t\tCity: " + getCity() + "\n\t\tAirport name: " + getAirportName();
    }

    /** GETTERS AND SETTERS */
    public String getCity() {return city;}
    public String getAirportName() {return airportName;}

}
