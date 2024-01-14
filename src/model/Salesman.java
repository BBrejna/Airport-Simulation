package model;

import model.classes.admin.Flight;
import model.classes.logging.Log;
import model.classes.logging.Logger;
import model.classes.people.Passenger;
import model.classes.people.Person;
import model.classes.salesman.Ticket;
import model.tools.Tools;

import java.util.ArrayList;

public class Salesman extends Person implements Logger {
    private ArrayList<Log> logs = new ArrayList<>();
    public ArrayList<Log> getLogs() {
        return logs;
    }
    //ARIBUTES
    private double cashbox;
    private ArrayList<Passenger> passengers;
    private static final Salesman instance = new Salesman("Pan", "Ekspedient", 0, new ArrayList<>());

    //CONSTRUCTORS
    private Salesman(String name, String surname, double cashbox, ArrayList<Passenger> passengers) {
        super(name, surname);
        this.cashbox = cashbox;
        this.passengers = passengers;
    }

    public static Salesman getInstance() {
        return instance;
    }

    //GETTERS & SETTERS
    public double getCashbox() {
        return cashbox;
    }

    public void setCashbox(double cashbox) {
        this.cashbox = cashbox;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    //METHODS
    public boolean addPassenger(Passenger passenger) {
        //finding a flight for the passenger
        Flight flight = findFlight(passenger);
        if (flight == null) {
            return false;
        }
        //checking passenger's passport
        if (!passenger.isPersonalInfo()) {
            return false;
        }
        //weighing the luggage
        double multiplier = 1;
        if (luggageOverweight(passenger, flight)) multiplier = 1.2;
        //creating an array of available flight classes
        boolean[] flightClassAvailability = new boolean[flight.getNumOfOccupiedSeats().length];
        for (int i = 0; i < flightClassAvailability.length; i++) {
            if (flight.getNumOfOccupiedSeats()[i] < flight.getAirplane().getNumberOfSeatsClasses()[i]) {
                flightClassAvailability[i] = true;
            } else flightClassAvailability[i] = false;
        }
        //asking passenger to choose a flight class
        int flightClass = passenger.chooseFlightClass(flight.getTicketPrice(), flightClassAvailability);
        //adding passenger to database
        passenger.setTicket(new Ticket(flight.getFlightNumber(), flightClass));
        flight.addPassenger(passenger);
        passengers.add(passenger);
        //changing flight info
        int[] tmp = flight.getNumOfOccupiedSeats();
        tmp[flightClass]++;
        flight.setNumOfOccupiedSeats(tmp);
        //adding the money to the cashbox
        cashbox += flight.getTicketPrice()[flightClass] * multiplier;
        return true;
    }

    public boolean changeData(Passenger passenger) {
        cashbox += 50;
        return true;
    }

    public boolean returnTicket(Passenger passenger) {
        Ticket ticket = passenger.getTicket();
        ArrayList<Flight> flights = new ArrayList<>(Admin.getInstance().getDepartures());
        Flight flight = null;
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getFlightNumber() == ticket.getFlightNumber()) {
                flight = flights.get(i);
                break;
            }
        }
        if(flight==null){
            return false;
        }
        passengers.remove(passenger);
        passenger.setTicket(null);
        cashbox -= 0.5 * flight.getTicketPrice()[ticket.getFlightClass()];
        int[] tmp = flight.getNumOfOccupiedSeats();
        tmp[ticket.getFlightClass()]--;
        flight.setNumOfOccupiedSeats(tmp);
        return true;
    }

    public Flight findFlight(Passenger passenger) {
        ArrayList<Flight> list = searchFlights(passenger.getDestinationCity());
        if (list.size() == 0)
            return null;
        return passenger.chooseFlight(list);
    }

    public ArrayList<Flight> searchFlights(String city) {
        ArrayList<Flight> list=new ArrayList<>();
        ArrayList<Flight> allDepartures=new ArrayList<>(Admin.getInstance().getDepartures());
        for (int i = 0; i < allDepartures.size(); i++) {
            if(!allDepartures.get(i).isFull() && allDepartures.get(i).getDestinationPoint().getCity() == city){
                list.add(allDepartures.get(i));
            }
        }
        return list;
    }

    public boolean luggageOverweight(Passenger passenger, Flight flight) {
        int weight = passenger.getLuggageWeight();
        int allowed = flight.getAirplane().getMaxFreeLuggageWeight();
        if (weight > allowed) return true;
        return false;
    }

    public void announceLastCall(Flight flight) {
        String output = "Attention, flight no. " + flight.getFlightNumber() + " flying "+
                (flight.isArrival() ? "from "+flight.getSourcePoint().getAirportName()+" in "+flight.getSourcePoint().getCity() : "to "+flight.getDestinationPoint().getAirportName()+ " in "+flight.getDestinationPoint().getCity()) +
                " is going to "+(flight.isArrival() ? "LAND" : "TAKE OFF")+" in 15 minutes (at "+ Tools.convertMinutesToTime(flight.getActualHour()) +").";
        if(flight.getDelayMinutes()>0)
            output += " Current delay is "+flight.getDelayMinutes()+" minutes. We are sorry for the delay.";
        log(output);
    }

    public void announceDelay(Flight flight) {
        log("Attention, flight no. " + flight.getFlightNumber() + " flying "+
                (flight.isArrival() ? "from "+flight.getSourcePoint().getAirportName()+" in "+flight.getSourcePoint().getCity() : "to "+flight.getDestinationPoint().getAirportName()+ " in "+flight.getDestinationPoint().getCity()) +
                " is going to "+(flight.isArrival() ? "LAND" : "TAKE OFF")+" with a delay of " + flight.getDelayMinutes() +
                " minutes due to bad weather conditions. The new "+(flight.isArrival() ? "arrive" : "departure")+" time is "+ Tools.convertMinutesToTime(flight.getActualHour()));
    }

    public void clearAllComponents() {
        cashbox = 0;

        passengers = new ArrayList<>();

        log("All Salesman components have just been cleared", false);
    }

}
