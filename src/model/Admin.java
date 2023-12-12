package model;

import model.classes.admin.Flight;
import model.classes.people.Person;

import java.util.ArrayList;
import java.util.Random;

public class Admin extends Person {

    private ArrayList<Flight> flights;

    public Admin(String pesel, String name, String surname) {
        super(pesel, name, surname);
    }

    /** generate flight for a day, there will be  */
    public ArrayList<Flight> generateFlights(int flightsCount) {

        Random random = new Random();

        // specify the number of flights
        int flightsCountDiff = flightsCount * 3/10;
        if(random.nextInt(2) % 2 == 1) flightsCount += flightsCountDiff;
        else flightsCount -= flightsCountDiff;

        // generate calculated number of flights
        for(int i = 0; i< flightsCount; i++ ) {
            flights.add(generateRandomSingleFlight());
        }

        return getFlights();

    }

    /** generate one random flight */
    public Flight generateRandomSingleFlight() {

        return null;

    }

    /** GETTERS AND SETTERS */
    public ArrayList<Flight> getFlights() { return flights; }
    public ArrayList<Flight> getArrivals() { return flights; }
    public ArrayList<Flight> getDepartures() { return flights; }
    public void setFlights(ArrayList<Flight> flights) { this.flights = flights; }
}
