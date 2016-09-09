package fr.pe.graine.tapestry.beans;

public class PersonneFluent {
    
    private String nom;
    private String prenom;
    private String dateNaissance;

    private PersonneFluent() {
    }

    public static PersonneFluent creerPersonne() {
        return new PersonneFluent();
    }

    public PersonneFluent deNom(String nom) {
        this.nom = nom;
        return this;
    }

    public PersonneFluent dePrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public PersonneFluent dontLaDateDeNaissanceEst(String dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public String getNom() {
        return this.nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public String getDateNaissance() {
        return this.dateNaissance;
    }

}
