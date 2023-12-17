package model.classes.admin;

public class Airport {

    private final String city;
    private final String airportName;

    public Airport(String city, String airportName) {
        this.city = city;
        this.airportName = airportName;
    }

    /** GETTERS AND SETTERS */
    public String getCity() {return city;}
    public String getAirportName() {return airportName;}

}
