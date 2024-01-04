package model.classes.workman;

import model.classes.admin.Airplane;
import model.classes.admin.Runway;

public interface Strategia {
    public void przygotujSamolot(Airplane airplane);
    public void przygotujPas(Runway runway);
}
