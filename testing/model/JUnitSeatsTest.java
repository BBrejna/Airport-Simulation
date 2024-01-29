package model;

import model.classes.admin.Flight;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitSeatsTest {
    Admin admin = Admin.getInstance();
    @RepeatedTest(10000)
    void seatsTest(){
        Admin admin = Admin.getInstance();
        admin.generateFlights(10, 3);
        for(Flight flight: admin.getFlights()) {
            int[] seats = flight.getNumOfOccupiedSeats();
            int passengers = 0;
            for(int i = 0; i < seats.length; i++) {
                passengers += seats[i];
            }
            assertTrue(passengers <= flight.getAirplane().getNumberOfSeats());
        }
        admin.clearAllComponents();

    }
}
