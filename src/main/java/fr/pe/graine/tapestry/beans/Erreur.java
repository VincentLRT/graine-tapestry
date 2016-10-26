package fr.pe.graine.tapestry.beans;

public class Erreur {

    private String codeErreur;
    private String libelleErreur;
    
    public Erreur(String codeErreur, String libelleErreur) {
        this.codeErreur = codeErreur;
        this.libelleErreur = libelleErreur;
    }
    
    public String getLibelleErreur() {
        return this.libelleErreur;
    }
    
    public String getCodeErreur() {
        return this.codeErreur;
    }
}
