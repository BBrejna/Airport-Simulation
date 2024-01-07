package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public class CleanIceSnow implements Strategy{
    @Override
    public void prepareAirplane(Airplane airplane) {
        airplane.setIced(false);
        airplane.setSnowy(false);
    }

    @Override
    public void prepareRunway(Runway runway) {
        runway.setIced(false);
        runway.setSnowy(false);
    }

    @Override
    public String getAction() {
        return "cleared of snow and ice";
    }
}
