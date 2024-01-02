package model.classes.logging;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    String textContent;
    String time;

    public Log(String textContent) {
        this.textContent = textContent;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SS");
        Date date = new Date();
        this.time = formatter.format(date);

        System.out.println("Logged: "+textContent+" @ "+this.time);
    }

    public String toString() {
        return time+": "+textContent;
    }
}
