import model.Admin;
import model.Salesman;
import model.Simulation;
import model.classes.simulation.Listener;

public class Thread {

    public static void main(String[] args) {
        Simulation sim = new Simulation("Simulation");
        sim.addObserver(new Listener());
        sim.start();
    }

}