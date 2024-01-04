package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public class Odsniezanie implements Strategia{
    @Override
    public void przygotujSamolot(Airplane airplane) {
        airplane.setSnowy(false);
    }
    @Override
    public void przygotujPas(Runway runway) {
        runway.setSnowy(false);
    }
}
