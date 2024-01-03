package model.classes.admin;

public class Runway {

    private final int runwayNumber;
    private boolean isSnowy;
    private boolean isIced;


    public Runway(int runwayNumber) {
        this.runwayNumber = runwayNumber;
    }

    /** GETTERS AND SETTERS **/
    public int getRunwayNumber() {
        return runwayNumber;
    }

    public boolean isSnowy() {
        return isSnowy;
    }

    public void setSnowy(boolean snowy) {
        isSnowy = snowy;
    }

    public boolean isIced() {
        return isIced;
    }

    public void setIced(boolean iced) {
        isIced = iced;
    }
}
