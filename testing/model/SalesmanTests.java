package model;

import model.classes.admin.Flight;
import model.classes.people.Passenger;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SalesmanTests {
    Admin admin = Admin.getInstance();
    @RepeatedTest(10000)
    void findPassengerTest(){
        Admin admin = Admin.getInstance();
        Salesman salesman  = Salesman.getInstance();
        admin.generateFlights(10, 3);
        for(Flight flight: admin.getFlights()) {
            for(Passenger passenger : flight.getPassengers())
            {
                assertTrue(salesman.findPassenger(passenger.getPesel(),flight)==passenger);
            }
        }
        admin.clearAllComponents();
    }
}
