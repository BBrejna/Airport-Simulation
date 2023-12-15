package tests.admin;

import model.Admin;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitAdminTests {

    @RepeatedTest(10000)
    public void properHourReturnTest() {

        Admin admin = new Admin("1321", "Stefan", "Kowalski");
        int hour = admin.generateHour();

        assertTrue(hour >= 0);
        assertTrue(hour < 1440);

    }

}
