package model.classes.people;

public class Pilot extends Person {
    private boolean isCaptain;
    private double hoursFlown;

    public Pilot(String pesel, String name, String surname, boolean isCaptain, double hoursFlown) {
        super(pesel, name, surname);
        this.hoursFlown = hoursFlown;
        this.isCaptain = isCaptain;
    }

    public Pilot(boolean isCaptain, double hoursFlown) {
        super();
        this.hoursFlown = hoursFlown;
        this.isCaptain = isCaptain;
    }

    public String toString() {
        return "\n\t\t\tName and surname: " + getName() + " " + getSurname() + "\n\t\t\tHours flown: " + getHoursFlown()
                + "\n\t\t\tCaptain: " + isCaptain();
    }


    public boolean isCaptain() {return isCaptain;}

    public void setCaptain(boolean captain) {isCaptain = captain;}

    public double getHoursFlown() {return hoursFlown;}

    public void setHoursFlown(double hoursFlown) {this.hoursFlown = hoursFlown;}
}
