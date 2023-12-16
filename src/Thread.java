import model.Admin;
import model.SalesMan;
import model.Simulation;

public class Thread {

    public static void main(String[] args) {
        System.out.println("Bajo jajo 2137");
        Admin admin = new Admin("213769696", "Gal", "Anonim");
        SalesMan ss = new SalesMan("213769696", "Gal", "Anonim");
        Simulation sim = new Simulation("Symulacja", admin, ss);
        sim.start();
    }

}