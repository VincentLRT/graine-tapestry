package fr.pe.graine.tapestry;

import fr.pe.graine.tapestry.beans.AdresseFluent;
import fr.pe.graine.tapestry.beans.Employe;
import fr.pe.graine.tapestry.beans.EmployeFluent;
import fr.pe.graine.tapestry.beans.PersonneFluent;

public class Main {
    
    private static final String ESPACE = " ";

    public static void main(String[] args) {
        afficherLesDonneesDUnePersonne();
        afficherLesDonneesDUneAdresse();
        afficherLesDonnesDUnEmploye();
        
        Employe employe = new Employe();
        employe.setNom("LAROUTE");
        employe.setPrenom("Vincent");
        employe.setNumeroDeBadge("12689");
        employe.setFonction("Apprenti developpeur");
        
        System.out.println(employe.getNom() + " " + employe.getPrenom() + " " + employe.getNumeroDeBadge() + " " + employe.getFonction());
    }
    
    private static void afficherLesDonneesDUnePersonne() {
        PersonneFluent personne = PersonneFluent.creerPersonne().dePrenom("Vincent").deNom("LAROUTE").dontLaDateDeNaissanceEst("26/10/1993");
        System.out.println(fournirLesDonneesDUnePersonneAAfficher(personne));
    }
    
    private static void afficherLesDonnesDUnEmploye() {
        EmployeFluent employeFluent = EmployeFluent.creerEmploye().deNom("LEFEBVRE").dePrenom("Tony").deNumeroDeBadge("10253")
                        .deFonction("Developpeur");
        System.out.println(fournirLesDonneesDUnEmployeAAfficher(employeFluent));
    }
    
    private static String fournirLesDonneesDUnEmployeAAfficher(EmployeFluent employeFluent) {
        return employeFluent.getPrenom() + ESPACE + employeFluent.getNom() + ESPACE + employeFluent.getNumeroDeBadge() + ESPACE
                        + employeFluent.getFonction();
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