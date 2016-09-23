package fr.pe.graine.tapestry.beans;

public class EmployeFluent extends PersonneFluent {
    
    private String numeroDeBadge;
    private String fonction;
    
    private EmployeFluent() {
    }

    public static EmployeFluent creerEmploye() {
        return new EmployeFluent();
    }

    public EmployeFluent deNumeroDeBadge(String numeroDeBadge) {
        this.numeroDeBadge = numeroDeBadge;
        return this;
    }
    
    @Override
    public EmployeFluent deNom(String nom) {
        this.nom = nom;
        return this;
    }

    @Override
    public EmployeFluent dePrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public EmployeFluent deFonction(String fonction) {
        this.fonction = fonction;
        return this;
    }
    
    public String getNumeroDeBadge() {
        return this.numeroDeBadge;
    }

    public void setNumeroDeBadge(String numeroDeBadge) {
        this.numeroDeBadge = numeroDeBadge;
    }

    public String getFonction() {
        return this.fonction;
    }

    public void setFonction(String fonction) {
        this.fonction = fonction;
    }

}
