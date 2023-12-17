package tests.admin;

import model.Admin;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import model.tools.Tools;

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
        assertEquals(Tools.convertMinutesToTime(137), "02:17");
        assertEquals(Tools.convertMinutesToTime(209), "03:29");
        assertEquals(Tools.convertMinutesToTime(298), "04:58");
        assertEquals(Tools.convertMinutesToTime(360), "06:00");
        assertEquals(Tools.convertMinutesToTime(430), "07:10");
        assertEquals(Tools.convertMinutesToTime(516), "08:36");
        assertEquals(Tools.convertMinutesToTime(574), "09:34");
        assertEquals(Tools.convertMinutesToTime(600), "10:00");
        assertEquals(Tools.convertMinutesToTime(669), "11:09");
        assertEquals(Tools.convertMinutesToTime(755), "12:35");
        assertEquals(Tools.convertMinutesToTime(823), "13:43");
        assertEquals(Tools.convertMinutesToTime(899), "14:59");
        assertEquals(Tools.convertMinutesToTime(908), "15:08");
        assertEquals(Tools.convertMinutesToTime(987), "16:27");
        assertEquals(Tools.convertMinutesToTime(1049), "17:29");
        assertEquals(Tools.convertMinutesToTime(1111), "18:31");
        assertEquals(Tools.convertMinutesToTime(1196), "19:56");
        assertEquals(Tools.convertMinutesToTime(1214), "20:14");
        assertEquals(Tools.convertMinutesToTime(1288), "21:28");
        assertEquals(Tools.convertMinutesToTime(1362), "22:42");
        assertEquals(Tools.convertMinutesToTime(1420), "23:40");
        assertEquals(Tools.convertMinutesToTime(1439), "23:59");

    }

}
