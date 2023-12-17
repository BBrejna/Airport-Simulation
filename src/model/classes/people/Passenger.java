package model.classes.people;

import model.classes.admin.Flight;
import model.classes.salesman.Ticket;

import java.util.*;

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
    public Passenger() {
        super();
        this.personalInfo = true;
        this.luggageWeight = 0;
        this.destinationCity = "";
        this.ticket = null;
    }

    public Passenger(String pesel, String name, String surname, boolean personalInfo, int luggageWeight, String destinationCity, Ticket ticket) {
        super(pesel, name, surname);
        this.personalInfo = personalInfo;
        this.luggageWeight = luggageWeight;
        this.destinationCity = destinationCity;
        this.ticket = ticket;
    }
    public Passenger(String pesel, String name, String surname, boolean personalInfo, int luggageWeight, String destinationCity) {
        super(pesel, name, surname);
        this.personalInfo = personalInfo;
        this.luggageWeight = luggageWeight;
        this.destinationCity = destinationCity;
        this.ticket = null;
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
    public Flight chooseFlight(ArrayList<Flight> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }
    public int chooseFlightClass(double[] prices, boolean[] isAvailable) {
        if (prices == null || isAvailable == null || prices.length != isAvailable.length) {
            return -1;
        }
        ArrayList<Integer> availableClasses = new ArrayList<>();
        for (int i = 0; i < isAvailable.length; i++) {
            if (isAvailable[i]) {
                availableClasses.add(i);
            }
        }
        if (availableClasses.isEmpty()) {
            return -1;
        }
        Random rand = new Random();
        return availableClasses.get(rand.nextInt(availableClasses.size()));
    }
}
