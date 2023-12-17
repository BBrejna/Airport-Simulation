package model;

import model.classes.Subject;
import model.classes.admin.Flight;
import model.classes.simulation.*;
import model.classes.people.Person;
import data.NamesAndSurnames;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
//                ArrayList<Flight> flights = Admin.getInstance().getFlights();
//                ArrayList<Flight> futureFlights = new ArrayList<>();
//                System.out.println("DEBUG "+time+" "+stopTime);
//                flights.forEach(flight -> {
//                    System.out.println("DEBUG "+flight.getHour());
//                    if (flight.getHour() <= time) {
//                        return;
//                    }
//                    else if (flight.getHour() <= stopTime) {
//                        System.out.println("Time "+makePrettyTime(flight.getHour())+", Flight "+flight.getFlightNumber()+" has just "+(flight.isArrival() ? "arrived" : "departured")+"!");
//                    }
//                    else {
//                        if (time < flight.getHour()-15 && flight.getHour()-15 <= stopTime) {
//                            System.out.println("Flight nr "+flight.getFlightNumber()+" is "+(flight.isArrival() ? "arriving" : "departuring")+" in 15 minutes @ "+makePrettyTime(flight.getHour())+"! (delay = "+flight.getDelayMinutes()+")");
//                        }
//                        futureFlights.add(flight);
//                    }
//                });
//                Admin.getInstance().setFlights(futureFlights);
                time = stopTime;
                // realizacja timetables, announceLastCall do ekspedienta
                weather.generateWeather();
                notifyObservers(weather);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    String makePrettyTime(int time) {
        String hh = Integer.toString(time/60);
        if (hh.length() == 1) hh = "0"+hh;
        String mm = Integer.toString(time%60);
        if (mm.length() == 1) mm = "0"+mm;
        return hh+":"+mm;
    }

    int generatePeople(int peopleCount) {
        int peopleGenerated = 0;
        for (int i = 0; i < peopleCount; i++) {
            if (generatePerson()) {
                peopleGenerated++;
            }
        }
        return peopleGenerated;
    }

    public boolean generatePerson(){
        Random rand = new Random();
        Person person = new Person();
        int[] tab_pesel = new int[11];
        for(int i = 0; i < 12; i++){
            tab_pesel[0] = rand.nextInt(0, 10);
        }
        String pesel = Arrays.toString(tab_pesel);
        person.setPesel(pesel);
        person.setName(NamesAndSurnames.NAMES[rand.nextInt(NamesAndSurnames.NAMES.length)]);
        person.setSurname(NamesAndSurnames.SURNAMES[rand.nextInt(NamesAndSurnames.SURNAMES.length)]);

        /*
            String[] destinations = new String[admin.getDepartures.size()];
            for(int i = 0; i < admin.getDepartures.size(); i++){
                destinations[i] = admin.getDepartures.destination


            }

         */

        return true;
    }
}
