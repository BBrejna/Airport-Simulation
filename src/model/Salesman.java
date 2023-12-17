package model;

import model.classes.admin.Flight;
import model.classes.people.Passenger;
import model.classes.people.Person;
import model.classes.salesman.Ticket;
import model.tools.Tools;

import java.util.ArrayList;

public class Salesman extends Person {
    //ARIBUTES
    private double cashbox;
    private ArrayList<Passenger> passengers;
    private static final Salesman instance = new Salesman("11111111111", "Pan", "Ekspedient", 0, new ArrayList<>());

    //CONSTRUCTORS
    public Salesman(String pesel, String name, String surname, double cashbox, ArrayList<Passenger> passengers) {
        super(pesel, name, surname);
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
        ArrayList<Flight> list = new ArrayList<>(Admin.getInstance().getDepartures());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Flight flight = list.get(i);
            if (flight.isFull() || flight.getDestinationPoint().getCity()!= city) {
                size--;
                list.remove(flight);
                i--;
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
        System.out.println("Attention, flight no. " + flight.getFlightNumber() + " flying to "
                + flight.getDestinationPoint().getAirportName()+" in "+ flight.getDestinationPoint().getCity() +
                " is going to take off in 15 minutes (at "+ Tools.convertMinutesToTime(flight.getHour()) +").");
        if(flight.getDelayMinutes()>0)
            System.out.print("The current delay is "+flight.getDelayMinutes()+" minutes. We are sorry for the delay.");
    }

    public void announceDelay(Flight flight) {
        System.out.println("Attention, flight no. " + flight.getFlightNumber() + " flying to "
                +flight.getDestinationPoint().getAirportName()+" in "+ flight.getDestinationPoint().getCity() +
                " is going to take off with a delay of " + flight.getDelayMinutes() +
                " due to bad weather conditions. The new departure time is "+ Tools.convertMinutesToTime(flight.getHour()));
    }
}
