package model.classes.people;

import model.classes.admin.Flight;
import model.classes.salesman.Ticket;

import java.util.ArrayList;

public class Passenger extends Person{
    //ATRIBUTES
    private boolean personalInfo;
    private int luggageWeight;
    private String destinationCity;
    private Ticket ticket;

    //CONSTRUCTORS
    public Passenger(String pesel, String name, String surname) {
        super(pesel, name, surname);
    }

    //GETTERS & SETTERS
    public boolean isPersonalInfo() {
        return personalInfo;
    }
    public void setPersonalInfo(boolean personalInfo) {
        this.personalInfo = personalInfo;
    }
    public int getLuggageWeight() {
        return luggageWeight;
    }
    public void setLuggageWeight(int luggageWeight) {
        this.luggageWeight = luggageWeight;
    }
    public String getDestinationCity() {
        return destinationCity;
    }
    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    //METHODS
    public Flight chooseFlight(ArrayList<Flight> list){
        //todo to be updated by kufel
        return list.get(0);
        //returns a random flight from the list
    }
    public int chooseFlightClass(double[] prices,boolean[] isAvailable){
        //todo to be updated by kufel
        return 0;
        //returns a random flight class index based on available flight classes
    }
}
