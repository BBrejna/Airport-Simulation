package model;

import model.classes.Subject;
import model.classes.admin.Flight;
import model.classes.simulation.*;
import model.classes.people.Passenger;
import data.NamesAndSurnames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static model.tools.Tools.convertMinutesToTime;

public class Simulation extends Subject<Weather> implements Runnable {
    private Thread t;
    private String threadName;
    Weather weather;
    int time;
    boolean isTimeStopped;
    int timeDelta;

    public Simulation(String threadName) {
        this.threadName = threadName;
        this.weather = new Weather();
    }

    public void start(int timeDelta) {
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);

            this.timeDelta = timeDelta;

            Random random = new Random();
            int flightsCount = random.nextInt(100,500);
            int runwaysNumber = random.nextInt(3, 6);
            Admin.getInstance().generateFlights(flightsCount, runwaysNumber);
            System.out.println(Admin.getInstance().getAllFlightsCount());
            int peopleCount = random.nextInt(flightsCount*50, flightsCount*150);
            int peopleGenerated = generatePeople(peopleCount);
            System.out.println("Generating people: "+peopleGenerated+"/"+peopleCount+" succeeded");

            time = -1*((15+timeDelta-1)/timeDelta)*timeDelta;
            t.start ();
        }
    }
    public void start() {
        start(10);
    }

    public void run() {
        while (t.isAlive()) {
            try {
                Thread.sleep(1000);
                if (isTimeStopped) {
                    continue;
                }
                int stopTime = time+timeDelta;
                ArrayList<Flight> flights = Admin.getInstance().getFlights();
                ArrayList<Flight> futureFlights = new ArrayList<>();
//                System.out.println("DEBUG "+time+" "+stopTime);
                flights.forEach(flight -> {
//                    System.out.println("DEBUG "+flight.getHour());
                    if (flight.getHour() <= time) {
                        return;
                    }
                    else if (flight.getHour() <= stopTime) {
                        System.out.println("Time "+convertMinutesToTime(flight.getHour())+", Flight "+flight.getFlightNumber()+" has just "+(flight.isArrival() ? "arrived" : "departured")+"!");
                    }
                    else {
                        if (time < flight.getHour()-15 && flight.getHour()-15 <= stopTime) {
//                            System.out.println("Flight nr "+flight.getFlightNumber()+" is "+(flight.isArrival() ? "arriving" : "departuring")+" in 15 minutes @ "+convertMinutesToTime(flight.getHour())+"! (delay = "+flight.getDelayMinutes()+")");
                            Salesman.getInstance().announceLastCall(flight);
                        }
                        futureFlights.add(flight);
                    }
                });
                Admin.getInstance().setFlights(futureFlights);
                time = stopTime;
                // realizacja timetables, announceLastCall do ekspedienta
                weather.generateWeather();
                notifyObservers(weather);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    int generatePeople(int peopleCount) {
        int peopleGenerated = 0;
        for (int i = 0; i < peopleCount; i++) {
            if (generatePassenger()) {
                peopleGenerated++;
            }
        }
        return peopleGenerated;
    }

    public boolean generatePassenger(){
        Random rand = new Random();

        int[] tab_pesel = new int[11];
        for(int i = 0; i < 12; i++){
            tab_pesel[0] = rand.nextInt(0, 10);

        }
        String pesel = Arrays.toString(tab_pesel);
        String name = NamesAndSurnames.NAMES[rand.nextInt(NamesAndSurnames.NAMES.length)];
        String surname = NamesAndSurnames.SURNAMES[rand.nextInt(NamesAndSurnames.SURNAMES.length)];

        Passenger passenger = new Passenger(pesel, name, surname);
        ArrayList<String> destinations = new ArrayList<>();

        for(Flight flight : Admin.getInstance().getDepartures()) {
            destinations.add(flight.getDestinationPoint().getAirportName());
            //System.out.println(flight.getDestinationPoint().getAirportName());
        }
//        System.out.println(destinations.size());
        String destination = destinations.get(rand.nextInt(0, destinations.size()));
        passenger.setDestinationCity(destination);
        passenger.setLuggageWeight(rand.nextInt(5, 50));
        passenger.setPersonalInfo(rand.nextBoolean());

        return Salesman.getInstance().addPassenger(passenger);
    }

}

