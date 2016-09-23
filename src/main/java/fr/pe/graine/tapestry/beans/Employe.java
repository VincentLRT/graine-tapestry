package fr.pe.graine.tapestry.beans;

public class Employe extends Personne {
    
    private String numeroDeBadge;
    private String fonction;

    public void setNumeroDeBadge(String numeroDeBadge) {
        this.numeroDeBadge = numeroDeBadge;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

    public String getNumeroDeBadge() {
        return this.numeroDeBadge;
    }

    public String getFonction() {
        return this.fonction;
    }

}
