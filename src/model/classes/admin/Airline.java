package model.classes.admin;

public class Airline {


    private final String airlineName;
    private final String ICAO_code;
    private final String IATA_code;

    public Airline(String airlineName, String ICAO_code, String IATA_code) {
        this.airlineName = airlineName;
        this.ICAO_code = ICAO_code;
        this.IATA_code = ICAO_code;
    }

    /** GETTERS AND SETTERS */
    public String getAirlineName() {return airlineName;}
    public String getICAO_code() { return ICAO_code; }
    public String getIATA_code() { return IATA_code; }

 }
