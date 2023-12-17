package tests.admin;

import model.Admin;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import tools.Tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JUnitAdminTests {

    @RepeatedTest(10000)
    public void properHourReturnTest() {

        Admin admin = Admin.getInstance();
        int hour = admin.generateHour();

        assertTrue(hour >= 0);
        assertTrue(hour < 1440);

    }

    @Test
    public void convertTimeTest() {

        assertEquals(Tools.convertMinutesToTime(0), "00:00");
        assertEquals(Tools.convertMinutesToTime(61), "01:01");
        assertEquals(Tools.convertMinutesToTime(137), "03:17");
        assertEquals(Tools.convertMinutesToTime(61), "01:01");
        assertEquals(Tools.convertMinutesToTime(61), "01:01");
        assertEquals(Tools.convertMinutesToTime(61), "01:01");

    }

}
