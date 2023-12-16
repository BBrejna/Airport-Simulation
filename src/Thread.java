import model.Admin;
import model.SalesMan;
import model.Simulation;
import model.classes.simulation.Listener;

public class Thread {

    public static void main(String[] args) {
        System.out.println("Bajo jajo 2137");
        Admin admin = new Admin("213769696", "Gal", "Anonim");
        SalesMan ss = new SalesMan("213769696", "Gal", "Anonim");
        Listener listener = new Listener();
        Simulation sim = new Simulation("Symulacja", admin, ss, listener);
        sim.start();
    }

}