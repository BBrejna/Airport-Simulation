package model.classes.admin;

public class Airline {

    private final String IATA_Code;
    private final String airlineName;

    public Airline(String IATA_Code, String airlineName) {
        this.IATA_Code = IATA_Code;
        this.airlineName = airlineName;
    }

    /** GETTERS AND SETTERS */
    public String getIATA_Code() {return IATA_Code;}
    public String getAirlineName() {return airlineName;}
}
