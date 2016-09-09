package fr.pe.graine.tapestry.beans;

public class Personne {

    private String nom;
    private String prenom;
    private String dateNaissance;
    private Adresse adresse;
    
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

}
