package model.classes.people;

public class Pilot extends Person {
    boolean isCaptain;
    double hoursFlown;


    public Pilot(String pesel, String name, String surname, boolean isCaptain, double hoursFlown) {
        super(pesel, name, surname);
        this.hoursFlown = hoursFlown;
        this.isCaptain = isCaptain;
    }


    public boolean isCaptain() {return isCaptain;}

    public void setCaptain(boolean captain) {isCaptain = captain;}

    public double getHoursFlown() {return hoursFlown;}

    public void setHoursFlown(double hoursFlown) {this.hoursFlown = hoursFlown;}
}
