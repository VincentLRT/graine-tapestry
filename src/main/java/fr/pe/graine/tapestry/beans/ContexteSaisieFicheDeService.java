package fr.pe.graine.tapestry.beans;

public class ContexteSaisieFicheDeService {

    private FicheService ficheServiceValidee;

    public FicheService getFicheServiceValidee() {
        return this.ficheServiceValidee;
    }

    public void setFicheServiceValidee(FicheService ficheServiceValidee) {
        this.ficheServiceValidee = ficheServiceValidee;
    }
}
