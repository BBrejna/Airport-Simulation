package model.classes.logging;

import model.Simulation;
import model.tools.Tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    String textContent;
    String time;

    public Log(String textContent, boolean addTime) {
        this.textContent = textContent;
        if (addTime) this.textContent = "Time "+Tools.convertMinutesToTime(Simulation.getInstance().getTime())+" "+this.textContent;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss:SS");
        Date date = new Date();
        this.time = formatter.format(date);

        System.out.println("Logged: "+textContent+" @ "+this.time);
    }

    public String toString() {
        return time+":\t"+textContent;
    }
}
