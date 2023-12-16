package tests.admin;

import model.Admin;
import model.classes.admin.Flight;

import java.util.ArrayList;

public class OtherAdminTests {

    public static void main(String[] args) {

        Admin admin = Admin.getInstance();
        admin.generateFlights(500, 3);
        ArrayList<Flight> flights = admin.getFlights();
        for(Flight flight : flights) {
            System.out.println(flight.toString());
        }

    }

}
