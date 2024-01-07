package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public interface Strategy {
    public void prepareAirplane(Airplane airplane);
    public void prepareRunway(Runway runway);
    public String getAction();
}
