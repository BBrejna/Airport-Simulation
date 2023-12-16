package model;

import model.classes.admin.Flight;
import model.classes.people.Passenger;
import model.classes.people.Person;
import model.classes.salesman.Ticket;

import java.util.ArrayList;

public class Salesman extends Person {
    //ARIBUTES
    private double cashbox;
    private ArrayList<Passenger> passengers;
    private static final Salesman instance = new Salesman("11111111111", "Pan", "Ekspedient",0,new ArrayList<>());

    //CONSTRUCTORS
    public Salesman(String pesel, String name, String surname,double cashbox,ArrayList<Passenger> passengers) {
        super(pesel, name, surname);
        this.cashbox=cashbox;
        this.passengers=passengers;
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
    public void addPassenger(Passenger passenger){
        //finding a flight for the passenger
        Flight flight = findFlight(passenger);
        //checking passenger's passport
        if(!passenger.isPersonalInfo()){
            System.out.println("Incorrect personal info, passenger not served");
            return;
        }
        //weighing the luggage
        double multiplier=1;
        if(luggageOverweight(passenger,flight))multiplier=1.2;
        //creating an array of available flight classes
        boolean[] flightClassAvailability=new boolean[flight.getNumOfOccupiedSeats().length];
        for (int i = 0; i < flightClassAvailability.length; i++) {
            //todo update this when airplane gets updated seats
            //if(flight.getNumOfOccupiedSeats()[i]<flight.getAirplane().getNumberOfSeats()[i])flightClassAvailability[i]=true;
            if(flight.getNumOfOccupiedSeats()[i]<flight.getAirplane().getNumberOfSeats())flightClassAvailability[i]=true;
            else flightClassAvailability[i]=false;
        }
        //asking passenger to choose a flight class
        int flightClass=passenger.chooseFlightClass(flight.getTicketPrice(),flightClassAvailability);
        //adding passenger to database
        passenger.setTicket(new Ticket(flight.getFlightNumber(),flightClass));
        passengers.add(passenger);
        //changing flight info
        int[] tmp=flight.getNumOfOccupiedSeats();
        tmp[flightClass]++;
        flight.setNumOfOccupiedSeats(tmp);
        //adding the money to the cashbox
        cashbox+=flight.getTicketPrice()[flightClass]*multiplier;
    }
    public void changeData(Passenger passenger){
        cashbox+=50;
    }
    public void returnTicket(Passenger passenger){
        Ticket ticket = passenger.getTicket();
        ArrayList<Flight> flights=getFlights();
        Flight flight=flights.get(0);
        for (int i = 0; i < flights.size(); i++) {
            if(flights.get(i).getFlightNumber()==ticket.getFlightNumber()){
                flight=flights.get(i);
                break;
            }
        }
        passengers.remove(passenger);
        passenger.setTicket(null);
        cashbox-=0.5*flight.getTicketPrice()[ticket.getFlightClass()];
    }
    public Flight findFlight(Passenger passenger){
        ArrayList<Flight> list=searchFlights(passenger.getDestinationCity());
        return passenger.chooseFlight(list);
    }
    public ArrayList<Flight> searchFlights(String city){
        ArrayList<Flight> list = getFlights();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Flight flight=list.get(i);
            if(flight.isArrival()||flight.isFull()||flight.getDestinationPoint()!=city){
                size--;
                list.remove(flight);
                i--;
                continue;
            }
        }
        return list;
    }
    public ArrayList<Flight> getFlights(){
        Admin admin= Admin.getInstance();
        ArrayList<Flight> list = admin.getFlights();
        return list;
    }
    public boolean luggageOverweight(Passenger passenger, Flight flight){
        int weight= passenger.getLuggageWeight();
        //todo update this when airplane gets its atribute
        //int allowed=flight.getAirplane().getMaxFreeLuggageWeight();
        int allowed=100;
        if(weight>allowed)return true;
        return false;
    }
    public void announceLastCall(Flight flight){
        System.out.println("Attention, flight no. "+flight.getFlightNumber()+" flying to "+ flight.getDestinationPoint()+
                " is going to take off in 15 minutes");
    }
    public void announceDelay(Flight flight){
        System.out.println("Attention, flight no. "+flight.getFlightNumber()+" flying to "+ flight.getDestinationPoint()+
                " is going to take off with a delay of "+flight.getDelayMinutes()+" due to bad weather conditions");
    }
}
