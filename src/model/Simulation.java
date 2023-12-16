package model;

import model.classes.simulation.*;
import model.classes.people.Person;
import data.NamesAndSurnames;

import java.util.Arrays;
import java.util.Random;

public class Simulation extends Thread{
    private Thread t;
    private String threadName;
    Weather weather;
    int time;
    boolean isTimeStopped;
    int timeDelta;
    Admin admin;
    SalesMan salesMan;

    public Simulation(String threadName, Admin admin, SalesMan salesMan) {
        this.threadName = threadName;
        this.admin = admin;
        this.salesMan = salesMan;
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
//            admin.generateFlights(flightsCount, runwaysNumber);

            int peopleCount = random.nextInt(flightsCount*50, flightsCount*150);
            int peopleGenerated = generatePeople(peopleCount);
            System.out.println("Generating people: "+peopleGenerated+"/"+peopleCount+" succeeded");

            t.start ();
        }
    }
    public void start() {
        start(10);
    }

    public void run() {
        while (t.isAlive()) {
            try {
                t.sleep(1000);
                if (isTimeStopped) {
                    continue;
                }

                weather.generateWeather();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
