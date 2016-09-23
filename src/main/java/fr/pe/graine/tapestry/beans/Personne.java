package fr.pe.graine.tapestry.beans;

public class Personne {

    protected String nom;
    protected String prenom;
    protected String dateNaissance;
    protected Adresse adresse;
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Adresse getAdresse() {
        return this.adresse;
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
