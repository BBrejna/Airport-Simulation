package model;

import controller.SimulationViewController;
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
    private int timeDelta = 10;
    private boolean isSimulationStarted = false;
    private boolean isSimulationFinished = false;
    private final int MINUTES_IN_DAY = 1440;
    private final int LAST_CALL_TIME = 15;
    private final int MAX_TIME_DELTA = 60;


    /** Singleton design pattern */
    private static final Simulation instance = new Simulation("Simulation Thread");

    public static Simulation getInstance() {
        return instance;
    }

    private SimulationViewController simulationViewController;
    public void setSimulationViewController(SimulationViewController simulationViewController) {
        this.simulationViewController = simulationViewController;
    }
    private void updateUI() {
        if (simulationViewController != null) {
            Platform.runLater(() -> {
                simulationViewController.updateCurrentTimeLabel(convertMinutesToTime((time + MINUTES_IN_DAY) % MINUTES_IN_DAY));  // Change the text accordingly
                simulationViewController.updateTimeTables();
                simulationViewController.updateLogs();
            });
        }
    }
    private void finishSimulation() {
        isTimeStopped = true;
        isSimulationFinished = true;
        if (simulationViewController != null) {
            Platform.runLater(() -> {
                simulationViewController.handleSimulationFinish();
            });
        }
    }

    private Simulation(String threadName) {
        this.threadName = threadName;
        this.weather = new Weather();
        this.t = null;
    }

    public void start(int timeDelta) {
        if (t == null || isSimulationFinished) {
            System.out.println("Starting " +  threadName );
            t = new Thread (this, threadName);

            this.timeDelta = timeDelta;
            log("PREPARING SIMULATION");

            Random random = new Random();
            int flightsCount = random.nextInt(100,500);
            int runwaysNumber = random.nextInt(3, 6);
            Admin.getInstance().generateFlights(flightsCount, runwaysNumber);
            log("Generating " + Admin.getInstance().getAllFlightsCount() +" flights");

            int peopleCount = random.nextInt(flightsCount*5, flightsCount*15);
            int peopleGenerated = generatePeople(peopleCount);
            log("Generating people: "+peopleGenerated+"/"+peopleCount+" succeeded");

            time = -1*((LAST_CALL_TIME+timeDelta-1)/timeDelta)*timeDelta;
            isSimulationStarted = true;
            isTimeStopped = false;
            isSimulationFinished = false;
            t.start ();
            log("SIMULATION HAS JUST STARTED!");
            updateUI();
        } else {
            log("CANNOT START SIMULATION, IT'S ALREADY STARTED AND HASN'T FINISHED");
        }
    }
    public void start() {
        if (timeDelta != 0) start(timeDelta);
        else start(10);
    }

    public void rerun() {
        isTimeStopped = true;
        isSimulationFinished = true;
        clearLogs();
        t.interrupt();
    }

    public void run() {
        while (t.isAlive() && !isSimulationFinished) {
            try {
                Thread.sleep(1000);
                if (isTimeStopped) {
                    continue;
                }
                int stopTime;
                int tmpTime = time+timeDelta;
                if ((tmpTime)%timeDelta != 0) {
                    stopTime = timeDelta*Math.floorDiv(tmpTime, timeDelta);
                } else {
                    stopTime = time+timeDelta;
                }

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
                        if (time < flight.getHour()-LAST_CALL_TIME && flight.getHour()-LAST_CALL_TIME <= stopTime) {
//                            System.out.println("Flight nr "+flight.getFlightNumber()+" is "+(flight.isArrival() ? "arriving" : "departuring")+" in "+LAST_CALL_TIME+" minutes @ "+convertMinutesToTime(flight.getHour())+"! (delay = "+flight.getDelayMinutes()+")");
                            Salesman.getInstance().announceLastCall(flight);
                        }
                        futureFlights.add(flight);
                    }
                });
                Admin.getInstance().setFlights(futureFlights);
                time = stopTime;
                if (time >= MINUTES_IN_DAY) {
                    log("SIMULATION HAS JUST FINISHED!");
                    time = MINUTES_IN_DAY;
                    updateUI();
                    finishSimulation();
                    return;
                }
                // realizacja timetables, announceLastCall do ekspedienta
                weather.generateWeather();
                notifyObservers(weather);
                updateUI();
            } catch (InterruptedException e) {
                log("Simulation thread has just been INTERRUPTED!");
                log("RESTARTING SIMULATION...");
                start();
                return;
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
    public boolean isSimulationFinished() {
        return isSimulationFinished;
    }
    public void setTimeDelta(int timeDelta) {
        if (!isSimulationStarted || isTimeStopped) {
            this.timeDelta = timeDelta;
        } else {
            log("TIME ISN'T STOPPED, CANNOT CHANGE TIME DELTA");
        }
    }
    public int getMAX_TIME_DELTA() {
        return MAX_TIME_DELTA;
    }
}

