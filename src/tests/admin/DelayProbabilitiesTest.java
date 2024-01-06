package tests.admin;

import model.Admin;
import model.classes.simulation.Weather;

public class DelayProbabilitiesTest {

    public static void main(String[] args) {

        Admin admin = Admin.getInstance();
        Weather weather = new Weather(-3,1,0,1,1,1);
        admin.observerUpdateState(weather);
        System.out.println(admin.getCurrentDelayProbability());


    }

}
