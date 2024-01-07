package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public class ClearSnow implements Strategy {
    @Override
    public void prepareAirplane(Airplane airplane) {
        airplane.setSnowy(false);
    }
    @Override
    public void prepareRunway(Runway runway) {
        runway.setSnowy(false);
    }
    @Override
    public String getAction() {
        return "cleared of snow";
    }
}
