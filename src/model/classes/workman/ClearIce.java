package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public class ClearIce implements Strategy {
    @Override
    public void prepareAirplane(Airplane airplane) {
        airplane.setIced(false);
    }
    @Override
    public void prepareRunway(Runway runway) {
        runway.setIced(false);
    }
    @Override
    public String getAction() {
        return "cleared of ice";
    }
}
