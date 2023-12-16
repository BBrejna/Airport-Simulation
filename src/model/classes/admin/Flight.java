package model.classes.admin;

import model.classes.people.Pilot;

import java.util.ArrayList;

public class Flight {
    private boolean isArrival;
    private int hour;
    private Airplane airplane;
    private String flightNumber;
    private ArrayList<Pilot> pilots;
    private Runway runway;
    private int[] numOfOccupiedSeats;
    private double[] ticketPrice;


    public Flight(boolean isArrival, int hour, Airplane airplane, String flightNumber, ArrayList<Pilot> pilots, Runway runway, int[] numOfOccupiedSeats, double[] ticketPrice) {
        this.isArrival = isArrival;
        this.hour = hour;
        this.airplane = airplane;
        this.flightNumber = flightNumber;
        this.pilots = pilots;
        this.runway = runway;
        this.numOfOccupiedSeats = numOfOccupiedSeats;
        this.ticketPrice = ticketPrice;
    }


    public boolean isArrival() {
        return isArrival;
    }

    public void setArrival(boolean arrival) {
        isArrival = arrival;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public ArrayList<Pilot> getPilots() {
        return pilots;
    }

    public void setPilots(ArrayList<Pilot> pilots) {
        this.pilots = pilots;
    }

    public Runway getRunway() {
        return runway;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    public int[] getNumOfOccupiedSeats() {
        return numOfOccupiedSeats;
    }

    public void setNumOfOccupiedSeats(int[] numOfOccupiedSeats) {
        this.numOfOccupiedSeats = numOfOccupiedSeats;
    }

    public double[] getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double[] ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}
