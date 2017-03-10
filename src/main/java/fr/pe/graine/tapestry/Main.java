package fr.pe.graine.tapestry;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.restlet.resource.ClientResource;

import fr.pe.graine.tapestry.beans.AdresseFluent;
import fr.pe.graine.tapestry.beans.Employe;
import fr.pe.graine.tapestry.beans.EmployeFluent;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheServiceAutoValidee;
import fr.pe.graine.tapestry.beans.PersonneFluent;
import fr.pe.graine.tapestry.services.ServiceValidationFicheService;

public class Main {

    private static final String ESPACE = " ";
    
    // public static void main(String[] args) {
    // FicheServiceAutoValidee ficheServiceValide = new FicheServiceAutoValidee("Nom service valide", "Nom éditeur valide",
    // "Type de service valide", "laroute.vincent@gmail.com", fournirUneDate(2001, 10, 20));
    // verifierValiditeFicheDeServiceAutoValidee(ficheServiceValide);
    //
    // FicheServiceAutoValidee ficheServiceNonValide = new FicheServiceAutoValidee("", "Nom éditeur valide", "Type de service valide",
    // "vincent.laroutearobasegmail.com", fournirUneDate(2017, 10, 20));
    // verifierValiditeFicheDeServiceAutoValidee(ficheServiceNonValide);
    //
    // }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        // Create the client resource
        ClientResource resource = new ClientResource("http://localhost:8182/graine-restlet/listeDesFichesServices");

        // Write the response entity on the console
        System.out.println(resource.get());
        
    }

    private static void verifierValiditeFicheDeServiceAutoValidee(FicheServiceAutoValidee ficheServiceAutoValidee) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        
        Set<ConstraintViolation<FicheServiceAutoValidee>> constraintViolations = validator.validate(ficheServiceAutoValidee);
        
        if (constraintViolations.size() > 0) {
            System.out.println("Impossible de valider les donnees du bean pour la fiche service " + ficheServiceAutoValidee.getnomService()
                            + " : ");
            for (ConstraintViolation<FicheServiceAutoValidee> contraintes : constraintViolations) {
                System.out.println(contraintes.getRootBeanClass().getSimpleName() + "." + contraintes.getPropertyPath() + " "
                                + contraintes.getMessage());
            }
        } else {
            System.out.println("Les donnees du bean sont valides pour la fiche service " + ficheServiceAutoValidee.getnomService());
            System.out.println("");
        }
    }
    
    public static void mainBak2(String[] args) {
        FicheService ficheServiceValide = creerUneFicheDeService("Nom service valide", "Nom éditeur valide", "Type de service valide",
                        "laroute.vincent@gmail.com", 2001, 10, 20);
        verifierValiditéFicheDeService(ficheServiceValide);

        FicheService ficheServiceNonValide = creerUneFicheDeService("", "Nom éditeur valide", "Type de service valide",
                        "vincent.laroutearobasegmail.com", 2017, 10, 20);
        verifierValiditéFicheDeService(ficheServiceNonValide);
        
    }
    
    private static FicheService creerUneFicheDeService(String nomService, String nomEditeur, String typeDeService, String adresseEmail,
        int annee, int mois, int jour) {
        FicheService ficheDeService = new FicheService();
        ficheDeService.setNomService(nomService);
        ficheDeService.setNomEditeur(nomEditeur);
        // TODO affecter une enum
        // ficheDeService.setTypeDeService(typeDeService);
        ficheDeService.setMailEditeur(adresseEmail);
        ficheDeService.setDateDeCreation(fournirUneDate(annee, mois, jour));
        return ficheDeService;
    }
    
    private static Date fournirUneDate(int annee, int mois, int jour) {
        Calendar dateFournie = Calendar.getInstance();
        dateFournie.set(annee, mois - 1, jour);
        return dateFournie.getTime();
    }
    
    public static void verifierValiditéFicheDeService(FicheService ficheService) {
        ServiceValidationFicheService validerFicheService = new ServiceValidationFicheService();
        if (validerFicheService.valider(ficheService).size() == 0) {
            System.out.println("Il n'y a pas d'erreur dans la fiche de service : " + ficheService.getNomService());
        } else {
            System.out.println("Il y a " + validerFicheService.valider(ficheService).size() + " erreur(s) dans la fiche de service : "
                            + ficheService.getNomService());
            for (int i = 0; i < validerFicheService.valider(ficheService).size(); i++) {
                System.out.println("Erreur " + (i + 1) + " : " + validerFicheService.valider(ficheService).get(i).getCodeErreur() + " -> "
                                + validerFicheService.valider(ficheService).get(i).getLibelleErreur());
            }
        }
        System.out.println("");
    }

    public static void mainBak(String[] args) {
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