import model.Admin;
import model.Salesman;
import model.Simulation;
import model.classes.simulation.Listener;

public class Thread {

    public static void main(String[] args) {
        System.out.println("Bajo jajo 2137");
        Simulation sim = new Simulation("Simulation");
        sim.addObserver(new Listener());
        sim.start();
    }

}