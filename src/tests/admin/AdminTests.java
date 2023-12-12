package tests.admin;

import model.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminTests {

    @Test
    public void properHour() {

        Admin admin = new Admin("1321", "Stefan", "Kowalski");
        int hour = admin.generateHour();

        assertTrue(hour >= 0);
        assertTrue(hour < 1440);

    }


}
