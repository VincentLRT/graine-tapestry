package fr.pe.graine.tapestry;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import fr.pe.graine.tapestry.beans.Adresse;
import fr.pe.graine.tapestry.beans.Personne;
import fr.pe.graine.tapestry.services.ServiceAdresseUtilisateur;

public class ServiceAdresseUtilisateurTest {

    private ServiceAdresseUtilisateur serviceTeste;
    
    private static String NUMERO_VOIE_DEUX = "2";
    private static String NUMERO_VOIE_TROIS = "3";
    
    private static String TYPE_VOIE_ALLEE = "Allée";
    private static String TYPE_VOIE_AVENUE = "AVENUE";
    
    private static String LIBELLE_VOIE_JEAN_BERTRAND = "Jean Bertrand";
    private static String LIBELLE_VOIE_GENERAL = "General de Gaulle";
    
    private static String COMPLEMENT_ADRESSE_CROIX_MONJOUS = "Croix de Monjous";
    private static String COMPLEMENT_ADRESSE_VIDE = "";
    
    private static String CODE_POSTALE_GRADIGNAN = "33170";
    
    private static String VILLE_GRADIGNAN = "Gradignan";
    
    private static String NOM = "Laroute";
    private static String PRENOM = "Vincent";
    private static String DATE_NAISSANCE = "26/10/1993";

    private Adresse adresseInitiale;
    private Adresse adresseCible;
    private Personne utilisateur;

    @Before
    public void avantLesTests() {
        this.serviceTeste = new ServiceAdresseUtilisateur();

        this.adresseInitiale = this.fournirUneAdresseDeTest(NUMERO_VOIE_DEUX, TYPE_VOIE_ALLEE, LIBELLE_VOIE_JEAN_BERTRAND,
                        COMPLEMENT_ADRESSE_CROIX_MONJOUS, CODE_POSTALE_GRADIGNAN, VILLE_GRADIGNAN);

        this.adresseCible = this.fournirUneAdresseDeTest(NUMERO_VOIE_TROIS, TYPE_VOIE_AVENUE, LIBELLE_VOIE_GENERAL, COMPLEMENT_ADRESSE_VIDE,
                        CODE_POSTALE_GRADIGNAN, VILLE_GRADIGNAN);

        this.utilisateur = this.fournirUnePersonneDeTest(NOM, PRENOM, DATE_NAISSANCE, this.adresseInitiale);
        
    }

    @Test
    public void recupererAdresseUtilisateur() {
        Adresse resultatRecuperationAdresseUtilisateur = this.serviceTeste.recupererAdresseUtilisateur(this.utilisateur);

        this.verifierLesAffirmations(resultatRecuperationAdresseUtilisateur, this.adresseInitiale);
    }
    
    @Test
    public void changerAdresseUtilisateur() {
        Adresse resultatChangementAdresseUtilisateur = this.serviceTeste.changerAdresseUtilisateur(this.utilisateur, this.adresseCible);

        this.verifierLesAffirmations(resultatChangementAdresseUtilisateur, this.adresseCible);
    }
    
    @Test
    public void recupererAdresseUtilisateurSurUneSeuleLigneMethode1() {
        String resultatRecuperationAdresseUtilisateur = this.serviceTeste.convertirUneAdresseSurUneSeuleLigneMethode1(this.utilisateur);
        
        assertThat(resultatRecuperationAdresseUtilisateur).isEqualTo(
                        this.utilisateur.getAdresse().getNumeroVoie() + " " + this.utilisateur.getAdresse().getTypeVoie() + " "
                                        + this.utilisateur.getAdresse().getLibelleVoie() + " "
                                        + this.utilisateur.getAdresse().getComplementAdresse() + " "
                                        + this.utilisateur.getAdresse().getCodePostale() + " " + this.utilisateur.getAdresse().getVille());
    }
    
    @Test
    public void recupererAdresseUtilisateurSurUneSeuleLigneMethode2() {
        String resultatRecuperationAdresseUtilisateur = this.serviceTeste.convertirUneAdresseSurUneSeuleLigneMethode2(this.utilisateur);
        
        assertThat(resultatRecuperationAdresseUtilisateur).isEqualTo(
                        this.utilisateur.getAdresse().getNumeroVoie() + " " + this.utilisateur.getAdresse().getTypeVoie() + " "
                                        + this.utilisateur.getAdresse().getLibelleVoie() + " "
                                        + this.utilisateur.getAdresse().getComplementAdresse() + " "
                                        + this.utilisateur.getAdresse().getCodePostale() + " " + this.utilisateur.getAdresse().getVille());
    }
    
    private void verifierLesAffirmations(Adresse adresseAVerifier, Adresse adresseAttendue) {
        assertThat(adresseAVerifier.getNumeroVoie()).isEqualTo(adresseAttendue.getNumeroVoie());
        assertThat(adresseAVerifier.getTypeVoie()).isEqualTo(adresseAttendue.getTypeVoie());
        assertThat(adresseAVerifier.getLibelleVoie()).isEqualTo(adresseAttendue.getLibelleVoie());
        assertThat(adresseAVerifier.getComplementAdresse()).isEqualTo(adresseAttendue.getComplementAdresse());
        assertThat(adresseAVerifier.getCodePostale()).isEqualTo(adresseAttendue.getCodePostale());
        assertThat(adresseAVerifier.getVille()).isEqualTo(adresseAttendue.getVille());
    }
    
    private Personne fournirUnePersonneDeTest(String nom, String prenom, String dateNaissance, Adresse adresseUtilisateur) {
        Personne personneCourante = new Personne();
        personneCourante.setNom(nom);
        personneCourante.setPrenom(prenom);
        personneCourante.setDateNaissance(dateNaissance);
        personneCourante.setAdresse(adresseUtilisateur);
        return personneCourante;
    }

    private Adresse fournirUneAdresseDeTest(String numeroVoie, String typeVoie, String libelleVoie, String complementAdresse,
        String codePostale, String ville) {
        Adresse adresseInitialeUtilisateur = new Adresse();
        adresseInitialeUtilisateur.setNumeroVoie(numeroVoie);
        adresseInitialeUtilisateur.setTypeVoie(typeVoie);
        adresseInitialeUtilisateur.setLibelleVoie(libelleVoie);
        adresseInitialeUtilisateur.setComplementAdresse(complementAdresse);
        adresseInitialeUtilisateur.setCodePostal(codePostale);
        adresseInitialeUtilisateur.setVille(ville);
        return adresseInitialeUtilisateur;
    }

}
