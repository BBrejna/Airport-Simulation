package model.classes.admin;

import model.classes.people.*;
import model.tools.Tools;

import java.util.ArrayList;

public class Flight {
    private boolean isArrival;
    private int hour;
    private int actualHour;
    private Airplane airplane;
    private String flightNumber;
    private ArrayList<Pilot> pilots;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    private Runway runway;
    private int[] numOfOccupiedSeats;
    private double[] ticketPrice;
    private Airport sourcePoint;
    private Airport destinationPoint;
    private int delayMinutes;


    public Flight(boolean isArrival, int hour, int actualHour, Airplane airplane, String flightNumber, ArrayList<Pilot> pilots, Runway runway,
                  int[] numOfOccupiedSeats, double[] ticketPrice, Airport sourcePoint, Airport destinationPoint, int delayMinutes) {
        this.isArrival = isArrival;
        this.hour = hour;
        this.actualHour = actualHour;
        this.airplane = airplane;
        this.flightNumber = flightNumber;
        this.pilots = pilots;
        this.runway = runway;
        this.numOfOccupiedSeats = numOfOccupiedSeats;
        this.ticketPrice = ticketPrice;
        this.sourcePoint = sourcePoint;
        this.destinationPoint = destinationPoint;
        this.delayMinutes = delayMinutes;
    }


    public String toString() {
        String additionalSpaces = "";
        for (int i = 0; i < 6-getFlightNumber().length(); i++) {
            additionalSpaces += " ";
        }
        return Tools.convertMinutesToTime(getActualHour()) + " | "  + getFlightNumber() + additionalSpaces + " | " + (isArrival() ? getSourcePoint().getCity(): getDestinationPoint().getCity());
    }


    public String getState(){
        return "\n\n=================\nFlight " + getFlightNumber() + "\n\tisArrival: " + isArrival() + "\n\thour: " + getHour() + "\n\tAirplane: " + getAirplane().toString() +
                "\n\tFlight number: " + getFlightNumber() + "\n\tPilots: " + pilotsString() + "\n\tRunway: " + getRunway().getRunwayNumber()
                + "\n\tOccupied seats: " + occupiedSeatsString() + "\n\tTicket price: " + ticketPricesString() + "\n\tSource: " + getSourcePoint().toString() + "\n\tDestination: "
                + getDestinationPoint().toString() + "\n\tDelay: " + getDelayMinutes();
    }


    private String pilotsString() {
        String toReturn = "";
        for (Pilot pilot : pilots) {
            toReturn += "\n\t\t\t--------------------";
            toReturn += pilot.toString();
        }
        return toReturn;
    }

    private String occupiedSeatsString() {
        String toReturn = "";
        for(int seats: getNumOfOccupiedSeats()) toReturn += seats + " ";
        return toReturn;
    }

    private String ticketPricesString() {
        String toReturn = "";
        for(double price: getTicketPrice()) toReturn += price + " ";
        return toReturn;
    }

    /** GETTERS AND SETTERS */
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
        this.actualHour = hour + delayMinutes;
    }
    public int getActualHour() {return actualHour;}
    public void setActualHour(int actualHour) {this.actualHour = actualHour;}
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

    public Airport getSourcePoint() {
        return sourcePoint;
    }

    public void setSourcePoint(Airport sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    public Airport getDestinationPoint() {
        return destinationPoint;
    }
    public void setDestinationPoint(Airport destinationPoint) {
        this.destinationPoint = destinationPoint;
    }
    public int getDelayMinutes() {return delayMinutes;}
    public void setDelayMinutes(int delayMinutes) {
        this.delayMinutes = delayMinutes;
        this.actualHour += delayMinutes;
    }
    public boolean isFull(){
        for (int i = 0; i < this.numOfOccupiedSeats.length; i++) {
            if(this.numOfOccupiedSeats[i] < this.airplane.getNumberOfSeatsClasses()[i]){
                return false;
            }
        }
        return true;
    }

    public void addPassenger(Passenger passenger)
    {
        this.passengers.add(passenger);
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }
    public void changeIsArrival() {
        isArrival = !isArrival;
        Airport temp = sourcePoint;
        sourcePoint = destinationPoint;
        destinationPoint = temp;
    }

}
