package fr.pe.graine.tapestry;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.pe.graine.tapestry.beans.Erreur;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.ServiceValidationFicheService;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;

public class ServiceValidationFicheServiceTest {

    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    
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

    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService(NOM_SERVICE);
        ficheService.setNomEditeur(NOM_EDITEUR);

        return ficheService;
    }

    private void verifierLesAffirmations(List<Erreur> listeErreurs, String codeErreur, String libelleErreur) {
        Erreur erreur = listeErreurs.get(0);
        assertThat(listeErreurs.size()).isEqualTo(1);
        assertThat(erreur.getCodeErreur()).isEqualTo(codeErreur);
        assertThat(erreur.getLibelleErreur()).isEqualTo(libelleErreur);
    }
    
}
