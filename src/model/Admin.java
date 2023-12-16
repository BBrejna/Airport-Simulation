package model;

import data.admin.AirlinesSet;
import data.admin.AirplaneModelsSet;
import model.classes.admin.*;
import model.classes.people.Person;
import model.classes.people.Pilot;

import java.util.ArrayList;
import java.util.Random;

public final class Admin extends Person {

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
    private ArrayList<Runway> runways = new ArrayList<>();
    private ArrayList<Flight> flights;
    private int allFlightsCount;
    private ArrayList<Integer> existingFlightIdentifiers = new ArrayList<>();

    /** Singleton design pattern */
    private static final Admin instance = new Admin("21376969696", "Gal", "Anonim");

    private Admin(String pesel, String name, String surname) {
        super(pesel, name, surname);
    }

    public static Admin getInstance() {
        return instance;
    }


    /** generate flight for a day, there will be  */
    public ArrayList<Flight> generateFlights(int flightsCount, int runwaysNumber) {

        Random random = new Random();

        generateRunways(runwaysNumber);

        // specify the number of flights
        int flightsCountDiff = flightsCount * 3/10;
        if(random.nextInt(2) % 2 == 1) flightsCount += flightsCountDiff;
        else flightsCount -= flightsCountDiff;

        this.allFlightsCount = flightsCount;

        // generate calculated number of flights
        for(int i = 0; i< allFlightsCount; i++ ) {
            flights.add(generateRandomSingleFlight());
        }

        // TODO segregate flights by hour

        return getFlights();

    }

    /** generate and assign runways */
    private void generateRunways(int runwaysNum) {

        for(int i = 0; i < runwaysNum; i++) {
            runways.add(new Runway(i+1));
        }

    }

    /** generate one random flight */
    public Flight generateRandomSingleFlight() {

        boolean isArrival = isArrival();
        int hour = generateHour();
        Airplane airplane = generateAirplane();
        String flightNumber = generateFlightNumber(airplane, hour);
        ArrayList<Pilot> pilots = generatePilots(airplane.getNumberOfSeats());

        return null;

    }

    /** getHour for a random flight, number of minutes after 12:00 AM */
    // public so that tests can see this
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

    /** decide whether it is arival or not */
    private boolean isArrival() {
        Random random = new Random();
        return random.nextInt(2) % 2 == 1;
    }

    /** generate a plane */
    private Airplane generateAirplane() {
        return new Airplane();
    }

    private String generateFlightNumber(Airplane airplane, int minutes) {

        String number = airplane.getAirline().getIATA_code();
        Random random = new Random();

        int flightNumberIdentifier = 9600/1440 * minutes + random.nextInt(400);
        number += Integer.toString(flightNumberIdentifier);

        if(!checkFreeNumber(number)) number = generateFlightNumber(airplane, minutes);

        return number;

    }

    // TODO check whether given flightNumber exists
    private boolean checkFreeNumber(String flightNumber) {
        return true;
    }

    // TODO do list of pilots
    private ArrayList<Pilot> generatePilots(int numberOfSeats) {

        Random random = new Random();

        int pilotsCount = 2;
        if(numberOfSeats > 303) {
            if(random.nextInt(10) < 7) {
                pilotsCount = 3 + random.nextInt(2);
            }
        } else if(numberOfSeats > 177) {
            if(random.nextInt(10) < 4) {
                pilotsCount = 3;
            }
        }

        ArrayList<Pilot> pilots = new ArrayList<>();
        for(int i = 0; i < pilotsCount; i++) {
            boolean isCaptain = (i == 0);
            int hoursFlown = 50 + random.nextInt(20000);
            pilots.add(new Pilot(isCaptain, hoursFlown));
        }

        return pilots;
    }


    /** GETTERS AND SETTERS */
    public ArrayList<Flight> getFlights() { return flights; }
    public ArrayList<Flight> getArrivals() { return flights; }
    public ArrayList<Flight> getDepartures() { return flights; }
    public void setFlights(ArrayList<Flight> flights) { this.flights = flights; }
}
