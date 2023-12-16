package model.classes.salesman;

public class Ticket {
    //ATRIBUTES
    private String flightNumber;
    private int flightClass;

    //CONSTRUCTORS
    public Ticket() {
    }
    public Ticket(String flightNumber, int flightClass) {
        this.flightNumber = flightNumber;
        this.flightClass = flightClass;
    }

    //GETTERS AND SETTERS
    public String getFlightNumber() {
        return flightNumber;
    }
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    public int getFlightClass() {
        return flightClass;
    }
    public void setFlightClass(int flightClass) {
        this.flightClass = flightClass;
    }
}
