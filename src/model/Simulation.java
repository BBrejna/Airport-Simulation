package model;

import controller.MainViewController;
import javafx.application.Platform;
import model.classes.Subject;
import model.classes.admin.Flight;
import model.classes.logging.Logger;
import model.classes.simulation.*;
import model.classes.people.Passenger;
import data.NamesAndSurnames;

import java.util.ArrayList;
import java.util.Random;

import static model.tools.Tools.convertMinutesToTime;

public class Simulation extends Subject<Weather> implements Runnable, Logger {
    private Thread t;
    private String threadName;
    private Weather weather;
    private int time;
    private boolean isTimeStopped;
    private int timeDelta;
    private boolean isSimulationStarted = false;


    /** Singleton design pattern */
    private static final Simulation instance = new Simulation("Simulation Thread");

    public static Simulation getInstance() {
        return instance;
    }

    private MainViewController mainViewController;
    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }
    private void updateUI() {
        if (mainViewController != null) {
            Platform.runLater(() -> {
                if (mainViewController != null) {
                    mainViewController.updateCurrentTimeLabel(convertMinutesToTime((time + 1440) % 1440));  // Change the text accordingly
                    mainViewController.updateTimeTables();
                    mainViewController.updateLogs();
                }
            });
        }
    }
    private Simulation(String threadName) {
        this.threadName = threadName;
        this.weather = new Weather();
        this.t = null;
    }

    public void start(int timeDelta) { // todo changing delta time on time stopped
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);

            this.timeDelta = timeDelta;

            Random random = new Random();
            int flightsCount = random.nextInt(100,500);
            int runwaysNumber = random.nextInt(3, 6);
            Admin.getInstance().generateFlights(flightsCount, runwaysNumber);
            log("Generating " + Admin.getInstance().getAllFlightsCount() +" flights");

            int peopleCount = random.nextInt(flightsCount*5, flightsCount*15);
            int peopleGenerated = generatePeople(peopleCount);
            log("Generating people: "+peopleGenerated+"/"+peopleCount+" succeeded");

            time = -1*((15+timeDelta-1)/timeDelta)*timeDelta;
            updateUI();
            t.start ();
            isSimulationStarted = true;
            log("SIMULATION HAS JUST STARTED!");
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
                        log("Time "+convertMinutesToTime(flight.getHour())+", Flight "+flight.getFlightNumber()+" has just "+(flight.isArrival() ? "arrived" : "departed")+"!");
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
                if (time >= 1440) {
                    log("SIMULATION HAS JUST FINISHED!");
                    return;
                }
                // realizacja timetables, announceLastCall do ekspedienta
                weather.generateWeather();
                notifyObservers(weather);
                updateUI();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private int generatePeople(int peopleCount) {
        int peopleGenerated = 0;
        for (int i = 0; i < peopleCount; i++) {
            if (generatePassenger()) {
                peopleGenerated++;
            }
        }
        return peopleGenerated;
    }

    private boolean generatePassenger(){
        Random rand = new Random();

        String pesel = "";
        int z;
        for(int i = 0; i < 11; i++){
            z= rand.nextInt(0, 10);
            pesel = pesel + z;
        }

        String name = NamesAndSurnames.NAMES[rand.nextInt(NamesAndSurnames.NAMES.length)];
        String surname = NamesAndSurnames.SURNAMES[rand.nextInt(NamesAndSurnames.SURNAMES.length)];

        ArrayList<String> destinations = new ArrayList<>();

        for(Flight flight : Admin.getInstance().getDepartures()) {
            destinations.add(flight.getDestinationPoint().getCity());
            //System.out.println(flight.getDestinationPoint().getAirportName());
        }
//        System.out.println(destinations.size());
        String destination = destinations.get(rand.nextInt(0, destinations.size()));
        boolean personalInfo = 0 != rand.nextInt(0,10);
        int luggageWeight = rand.nextInt(5, 30);
        Passenger passenger = new Passenger(pesel, name, surname, personalInfo, luggageWeight, destination);

        return Salesman.getInstance().addPassenger(passenger);
    }

    public String getThreadName() {
        return threadName;
    }

    public Weather getWeather() {
        return weather;
    }

    public int getTime() {
        return time;
    }

    public boolean isTimeStopped() {
        return isTimeStopped;
    }

    public int getTimeDelta() {
        return timeDelta;
    }

    public void setTimeStopped(boolean timeStopped) {
        if (isSimulationStarted) {
            if (isTimeStopped && !timeStopped) {
                log("SIMULATION HAS JUST BEEN RESUMED!");
            }
            else if (!isTimeStopped && timeStopped) {
                log("SIMULATION HAS JUST BEEN STOPPED!");
            }
            updateUI();
        }
        isTimeStopped = timeStopped;
    }

    public boolean isSimulationStarted() {
        return isSimulationStarted;
    }
//    public void setTimeDelta(int timeDelta) {
//        this.timeDelta = timeDelta;
//    }
}

