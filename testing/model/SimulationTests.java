package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.Random;

public class SimulationTests {
    Simulation simulation = Simulation.getInstance();
    Random random = new Random();

    @BeforeEach
    public void generateComponents() {
        int flightsCount = random.nextInt(100,500);
        int runwaysNumber = random.nextInt(3, 6);
        Admin.getInstance().generateFlights(flightsCount, runwaysNumber);
    }

    @AfterEach
    public void clearComponents() {
        Admin.getInstance().clearAllComponents();
        Salesman.getInstance().clearAllComponents();
    }

    @RepeatedTest(1000)
    public void departingPassengersTest() {
        int desiredPeopleCount = random.nextInt(200, 500);
        int generatedPeople = Simulation.getInstance().generatePeople(desiredPeopleCount);

        assert(desiredPeopleCount*0.8 <= generatedPeople);
        assert(generatedPeople <= desiredPeopleCount*1.2);
    }

    @RepeatedTest(1000)
    public void arrivingPassengersTest() {
        int generatedPeople = Simulation.getInstance().generateArrivingPassengers();

        assert(generatedPeople >= 0);
    }
}
