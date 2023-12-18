import model.Admin;
import model.Salesman;
import model.Simulation;
import model.classes.simulation.Listener;

public class Thread {

    public static void main(String[] args) {
        Simulation sim = Simulation.getInstance();
        sim.addObserver(new Listener());
        sim.start();
    }

}