import model.Admin;
import model.Salesman;
import model.Simulation;
import model.classes.simulation.Listener;

public class Thread {

    public static void main(String[] args) {
        System.out.println("Bajo jajo 2137");
        Listener listener = new Listener();
        Simulation sim = new Simulation("Symulacja", Admin.getInstance(), Salesman.getInstance(), listener);
        sim.start();
    }

}