package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public class Odladzanie implements Strategia{
    @Override
    public void przygotujSamolot(Airplane airplane) {
        airplane.setIced(false);
    }
    @Override
    public void przygotujPas(Runway runway) {
        runway.setIced(false);
    }
}
