package fr.pe.graine.tapestry.beans;

public class AdresseFluent {
    
    private String numeroVoie;
    private String typeVoie;
    private String libelleVoie;
    private String complementAdresse;
    private String codePostal;
    private String ville;
    
    private AdresseFluent() {
    }

    public static AdresseFluent creerAdresse() {
        return new AdresseFluent();
    }

    public AdresseFluent deNumeroDeVoie(String numeroVoie) {
        this.numeroVoie = numeroVoie;
        return this;
    }

    public AdresseFluent deTypeDeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
        return this;
    }

    public AdresseFluent avecLibelleDeVoie(String libelleVoie) {
        this.libelleVoie = libelleVoie;
        return this;
    }

    public AdresseFluent avecCommeComplementDAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
        return this;
    }

    public AdresseFluent deCodePostal(String codePostal) {
        this.codePostal = codePostal;
        return this;
    }

    public AdresseFluent deVille(String ville) {
        this.ville = ville;
        return this;
    }
    
    public String getNumeroVoie() {
        return this.numeroVoie;
    }
    
    public String getTypeVoie() {
        return this.typeVoie;
    }
    
    public String getLibelleVoie() {
        return this.libelleVoie;
    }
    
    public String getComplementAdresse() {
        return this.complementAdresse;
    }
    
    public String getCodePostal() {
        return this.codePostal;
    }
    
    public String getVille() {
        return this.ville;
    }
    
}
