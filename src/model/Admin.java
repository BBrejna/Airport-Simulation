package model;

import data.admin.AirportSet;
import model.classes.Observer;
import model.classes.admin.*;
import model.classes.logging.Logger;
import model.classes.people.Person;
import model.classes.people.Pilot;
import model.classes.simulation.Weather;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class Admin extends Person implements Observer<Weather>{

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
    private ArrayList<Flight> flights = new ArrayList<>();
    private int allFlightsCount;
    private ArrayList<String> existingFlightNumbers = new ArrayList<>();
    private double currentDelayProbability;

    /** Singleton design pattern */
    private static final Admin instance = new Admin("78013058819", "Jan", "Nowak");

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
        if(random.nextBoolean()) flightsCount += flightsCountDiff;
        else flightsCount -= flightsCountDiff;

        this.allFlightsCount = flightsCount;

        // generate calculated number of flights
        for(int i = 0; i< allFlightsCount; i++ ) {
            flights.add(generateRandomSingleFlight());
        }

        //sort flights by hour
        flights.sort(Comparator.comparing(Flight::getHour));

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

        Random random = new Random();

        boolean isArrival = isArrival();
        int hour = generateHour();
        Airplane airplane = new Airplane();
        String flightNumber = generateFlightNumber(airplane, hour);
        existingFlightNumbers.add(flightNumber);
        ArrayList<Pilot> pilots = generatePilots(airplane.getNumberOfSeats());

        int runwayNum = getRunway(hour);
        boolean adding = random.nextBoolean();
        while( runwayNum == -1 ) {
            if(adding) {
                hour += 3;
                if(hour > 1440) {
                    hour -= 6;
                    adding = false;
                }
            } else {
                hour -= 3;
                if(hour < 0) {
                    hour += 6;
                    adding = true;
                }
            }
            runwayNum = getRunway(hour);
        }

        Runway runway = runways.get(0);
        for(Runway runwayCandidate: runways) {
            if(runwayNum == runwayCandidate.getRunwayNumber()) runway = runwayCandidate;
        }

        int[] numberOfOccupiedSeats = {0, 0, 0};

        double[] ticketPrices = {-1, -1, -1};
        ticketPrices[0] = 200 + random.nextInt(3000);
        ticketPrices[1] = ticketPrices[0] + 200 + random.nextInt(500);
        ticketPrices[2] =  ticketPrices[1] + 1100 + random.nextInt(5000);

        Airport[] airports = AirportSet.AIRPORTS;
        Airport source = airports[random.nextInt(airports.length)];
        Airport destination = airports[random.nextInt(airports.length)];
        while(source == destination) destination = airports[random.nextInt(airports.length)];

        // TODO delay

        return new Flight(isArrival, hour, airplane, flightNumber, pilots, runway, numberOfOccupiedSeats, ticketPrices,
                source, destination, 0);

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

    private String generateFlightNumber(Airplane airplane, int minutes) {

        String number = airplane.getAirline().getIATA_code();
        Random random = new Random();

        int flightNumberIdentifier = 9600/1440 * minutes + random.nextInt(400);
        number += Integer.toString(flightNumberIdentifier);

        if(!checkFreeNumber(number)) number = generateFlightNumber(airplane, minutes);

        return number;

    }

    private boolean checkFreeNumber(String flightNumber) {
        for(String existingFlightNumber: existingFlightNumbers) {
            if(existingFlightNumber.equals(flightNumber)) return false;
        }
        return true;
    }

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

    /** checks whether there are free runways at a specific time */
    private int getRunway(int hour) {

        ArrayList<Runway> freeRunways = new ArrayList<>(getRunways());

        for(Flight flight : flights) {
          if(Math.abs(flight.getHour() - hour) < 3) {
              freeRunways.remove(flight.getRunway());
          }
        }

        if(freeRunways.isEmpty()) return -1;
        Random random = new Random();
        return freeRunways.get(random.nextInt(freeRunways.size())).getRunwayNumber();

    }

    /** observe weather and calculate delay probability */
    @Override
    public void updateState(Weather weather) {

        double delayProb = 0;

        double temp = weather.getTemperature();
        double[] tempCoefficients = new double[]{0.02000000e+00, -2.27544944e-02, 1.06078294e-03, 3.61321648e-06, -1.04910230e-06, 6.27672899e-09, 4.49151696e-10, -4.71116785e-12, -5.21281385e-14, 6.42199681e-16};
        double tempProb = 0;
        for(int i = 0; i < tempCoefficients.length; i++)
            tempProb += tempCoefficients[i] * Math.pow(temp, i);

        double snow = weather.getSnow();
        double[] snowCoefficients = new double[]{0.02, -0.0227193, 0.01569328, -0.00012821};
        double snowProb = 0;
        for(int i = 0; i < snowCoefficients.length; i++)
            snowProb += snowCoefficients[i] * Math.pow(snow, i);


        double rain = weather.getRain();
        double[] rainCoefficients = new double[]{0.02, -2.83991270e-03, 2.45207523e-04, -2.50406842e-07};
        double rainProb = 0;
        for(int i = 0; i < rainCoefficients.length; i++)
            rainProb += rainCoefficients[i] * Math.pow(rain, i);

        delayProb += tempProb + snowProb + rainProb;
        currentDelayProbability = delayProb;


        //checking weather values and setting states of airplane and runway
        double snowValue = 0.6;

        for (int i = 0; i < flights.size(); i++) {
            if (!flights.get(i).isArrival() && snow > snowValue){
                flights.get(i).getAirplane().setSnowy(true);
            }
        }
        for (int i = 0; i < runways.size(); i++) {
            if (snow > snowValue){
                runways.get(i).setSnowy(true);
            }
            if (temp < -5){
                runways.get(i).setIced(true);
            }
        }
    }


    // checking state of runway and airplane
    public void checkFlight(Flight flight){
        Workman.getInstance().clearSnow(flight.getAirplane());
        Workman.getInstance().clearRunway(flight.getRunway());
    }


    //clearing all Admin compoments
    public void clearAllComponents(){
        runways.clear();
        flights.clear();
        allFlightsCount = 0;
        existingFlightNumbers.clear();
        currentDelayProbability = 0;
    }


    /** GETTERS AND SETTERS */
    public ArrayList<Flight> getFlights() {
        return new ArrayList<>(flights);
    }
    public ArrayList<Flight> getArrivals() {
        return flights.stream().filter(Flight::isArrival).collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Flight> getDepartures() {
        return flights.stream().filter(Predicate.not(Flight::isArrival)).collect(Collectors.toCollection(ArrayList::new));
    }
    public void setFlights(ArrayList<Flight> flights) { this.flights = flights; }
    public ArrayList<Runway> getRunways() {return runways;}
    public int getAllFlightsCount() {return allFlightsCount;}
    public ArrayList<String> getExistingFlightNumbers() {return existingFlightNumbers;}
    public void setAllFlightsCount(int allFlightsCount) {this.allFlightsCount = allFlightsCount;}
    public double getCurrentDelayProbability() {return currentDelayProbability;}
    public void setCurrentDelayProbability(double currentDelayProbability) {this.currentDelayProbability = currentDelayProbability;}
}
