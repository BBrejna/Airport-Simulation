package model;

import model.classes.admin.Flight;
import model.classes.people.Person;

import java.util.ArrayList;
import java.util.Random;

public class Admin extends Person {

    /**
     * Los Angeles International Airport (LAX)
     * 0:00 - 3:00	-> 20-40	-> 20
     * 3:00 - 6:00	-> 20-40	-> 30
     * 6:00 - 9:00	-> 120-160	-> 100
     * 9:00 - 12:00	-> 60-80	-> 70
     * 12:00 - 15:00	-> 60-80	-> 75
     * 15:00 - 18:00	-> 80-100	-> 80
     * 18:00 - 21:00	-> 60-80	-> 70
     * 21:00 - 0:00	-> 40-60	-> 40
     * Sum: 475
     */
    private final int[] HOUR_DISTRIBUTION = {20, 30, 100, 70, 75, 80, 70, 40};

    private ArrayList<Flight> flights;
    private int allFlightsCount;

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

        this.allFlightsCount = flightsCount;

        // generate calculated number of flights
        for(int i = 0; i< allFlightsCount; i++ ) {
            flights.add(generateRandomSingleFlight());
        }

        return getFlights();

    }

    /** generate one random flight */
    public Flight generateRandomSingleFlight() {

        int hour = generateHour();


        return null;

    }

    /** getHour for a random flight */
    // public so that tests see this
    public int generateHour() {

        Random random = new Random();
        int number = random.nextInt(475);
        int interval = -1;

        int i = -1;
        int currentMaxNum = 0;
        while(interval == -1 && i < 7) {

            i++;
            currentMaxNum += HOUR_DISTRIBUTION[i];

            if(number < currentMaxNum) interval = i;

        }

        if(interval == -1 || interval > 7) interval = 5;

        return interval*180 + random.nextInt(180);
    }

    /** GETTERS AND SETTERS */
    public ArrayList<Flight> getFlights() { return flights; }
    public ArrayList<Flight> getArrivals() { return flights; }
    public ArrayList<Flight> getDepartures() { return flights; }
    public void setFlights(ArrayList<Flight> flights) { this.flights = flights; }
}
