package fr.pe.graine.tapestry;

import fr.pe.graine.tapestry.beans.AdresseFluent;
import fr.pe.graine.tapestry.beans.PersonneFluent;

public class Main {
    
    private static final String ESPACE = " ";

    public static void main(String[] args) {
        afficherLesDonnéesDUnePersonne();
        afficherLesDonneesDUneAdresse();
    }
    
    private static void afficherLesDonnéesDUnePersonne() {
        PersonneFluent personne = PersonneFluent.creerPersonne().dePrenom("Vincent").deNom("LAROUTE").dontLaDateDeNaissanceEst("26/10/1993");
        System.out.println(fournirLesDonneesDUnePersonneAAfficher(personne));
    }
    
    private static String fournirLesDonneesDUnePersonneAAfficher(PersonneFluent personne) {
        return personne.getPrenom() + ESPACE + personne.getNom() + ESPACE + personne.getDateNaissance();
    }

    private static void afficherLesDonneesDUneAdresse() {
        AdresseFluent adresse = AdresseFluent.creerAdresse().deNumeroDeVoie("2").deTypeDeVoie("Allée").avecLibelleDeVoie("Jean Bertrand");
        System.out.println(fournirLesDonneesDAdresseAAfficher(adresse));
    }
    
    private static String fournirLesDonneesDAdresseAAfficher(AdresseFluent adresse) {
        return adresse.getNumeroVoie() + ESPACE + adresse.getTypeVoie() + ESPACE + adresse.getLibelleVoie();
    }
}