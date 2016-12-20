package fr.pe.graine.tapestry;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.pe.graine.tapestry.beans.Erreur;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.services.ServiceValidationFicheService;

public class ServiceValidationFicheServiceTest {
    
    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;
    private static final String EMAIL = "laroute.vincent@gmail.com";
    
    private FicheService ficheService;
    private ServiceValidationFicheService serviceTeste;

    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
        this.serviceTeste = new ServiceValidationFicheService();
    }

    @Test
    public void verifierFicheServiceOk() {
        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);

        assertThat(listeErreurs.isEmpty()).isTrue();
        assertThat(listeErreurs.size()).isEqualTo(0);
    }
    
    @Test
    public void verifierFicheServiceKoSiNomAbsent() {
        this.ficheService.setNomService("");
        
        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);
        
        this.verifierLesAffirmations(listeErreurs, EntrepotReglesDeGestion.CODE_ERREUR_NOM_SERVICE_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT);
    }

    @Test
    public void verifierFicheServiceKoSiNomEditeurAbsent() {
        this.ficheService.setNomEditeur("");
        
        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);

        this.verifierLesAffirmations(listeErreurs, EntrepotReglesDeGestion.CODE_ERREUR_NOM_EDITEUR_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT);
    }

    @Test
    public void verifierFicheServiceKoSiTypeDeServiceAbsent() {

        this.ficheService.setTypeDeService(TypeServiceEnum.ELEARNING);

        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);
        
        this.verifierLesAffirmations(listeErreurs, EntrepotReglesDeGestion.CODE_ERREUR_TYPE_DE_SERVICE_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_TYPE_DE_SERVICE_ABSENT);
    }

    @Test
    public void verifierFicheServiceKoSiEmailAuMauvaisFormatOuVide() {
        this.ficheService.setMailEditeur("vincent.laroutearobasegmail.com");
        
        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);
        
        this.verifierLesAffirmations(listeErreurs, EntrepotReglesDeGestion.CODE_ERREUR_EMAIL_NON_VALIDE,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_EDITEUR_NON_VALIDE);
    }

    @Test
    public void verifierFicheServiceKoSiDateDeCreationPlusUlterieureALaDateDuJour() {
        this.ficheService.setDateDeCreation(this.fournirUneDateDeErronneeTest());
        
        List<Erreur> listeErreurs = this.serviceTeste.valider(this.ficheService);
        
        this.verifierLesAffirmations(listeErreurs, EntrepotReglesDeGestion.CODE_ERREUR_DATE_DE_CREATION_NON_VALIDE,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_DATE_DE_CREATION_NON_VALIDE);
    }
    
    private Date fournirUneDateDeErronneeTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 2);
        
        return calendar.getTime();
    }

    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService(NOM_SERVICE);
        ficheService.setNomEditeur(NOM_EDITEUR);
        ficheService.setTypeDeService(TYPE_SERVICE);
        ficheService.setMailEditeur(EMAIL);
        ficheService.setDateDeCreation(new Date());
        
        return ficheService;
    }
    
    private void verifierLesAffirmations(List<Erreur> listeErreurs, String codeErreur, String libelleErreur) {
        Erreur erreur = listeErreurs.get(0);
        assertThat(listeErreurs.size()).isGreaterThanOrEqualTo(1);
        assertThat(erreur.getCodeErreur()).isEqualTo(codeErreur);
        assertThat(erreur.getLibelleErreur()).isEqualTo(libelleErreur);
    }

}
