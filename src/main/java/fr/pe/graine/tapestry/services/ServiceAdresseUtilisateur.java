package fr.pe.graine.tapestry.services;

import fr.pe.graine.tapestry.beans.Adresse;
import fr.pe.graine.tapestry.beans.Personne;

public class ServiceAdresseUtilisateur {
    
    public Adresse recupererAdresseUtilisateur(Personne personneCourante) {
        return personneCourante.getAdresse();
    }
    
    public Adresse changerAdresseUtilisateur(Personne personneCourante, Adresse adresseCibleUtilisateur) {
        personneCourante.setAdresse(adresseCibleUtilisateur);
        return personneCourante.getAdresse();
    }

    public String convertirUneAdresseSurUneSeuleLigneMethode1(Personne personneCourante) {
        Adresse adresseUtilisateur = personneCourante.getAdresse();

        return adresseUtilisateur.getNumeroVoie() + " " + adresseUtilisateur.getTypeVoie() + " " + adresseUtilisateur.getLibelleVoie() + " "
        + adresseUtilisateur.getComplementAdresse() + " " + adresseUtilisateur.getCodePostale() + " "
        + adresseUtilisateur.getVille();
    }
    
    public String convertirUneAdresseSurUneSeuleLigneMethode2(Personne personneCourante) {
        Adresse adresseUtilisateur = personneCourante.getAdresse();
        
        return String.format("%s %s %s %s %s %s", adresseUtilisateur.getNumeroVoie(), adresseUtilisateur.getTypeVoie(),
                        adresseUtilisateur.getLibelleVoie(), adresseUtilisateur.getComplementAdresse(), adresseUtilisateur.getCodePostale(),
                        adresseUtilisateur.getVille());
    }
    
}
