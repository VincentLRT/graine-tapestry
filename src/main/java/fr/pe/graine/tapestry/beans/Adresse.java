package fr.pe.graine.tapestry.beans;

public class Adresse {

    private String numeroVoie;
    private String typeVoie;
    private String libelleVoie;
    private String complementAdresse;
    private String codePostal;
    private String ville;
    
    public void setNumeroVoie(String numeroVoie) {
        this.numeroVoie = numeroVoie;
    }

    public void setTypeVoie(String typeVoie) {
        this.typeVoie = typeVoie;
    }

    public void setLibelleVoie(String libelleVoie) {
        this.libelleVoie = libelleVoie;
    }

    public void setComplementAdresse(String complementAdresse) {
        this.complementAdresse = complementAdresse;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public void setVille(String ville) {
        this.ville = ville;
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
    
    public String getVille() {
        return this.ville;
    }
    
    public String getCodePostale() {
        return this.codePostal;
    }
    
}
