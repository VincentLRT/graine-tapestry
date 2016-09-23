package fr.pe.graine.tapestry;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.pe.graine.tapestry.beans.Erreur;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.ServiceValidationFicheService;

public class ServiceValidationFicheServiceTest {
    
    private static final Object CODE_ERREUR_NOM_SERVICE = "err-nom-service";
    private static final Object CODE_ERREUR_NOM_EDITEUR = "err-nom-editeur";
    private static final Object CODE_ERREUR_TYPE_SERVICE = "err-type-service";

    private FicheService ficheService;
    private ServiceValidationFicheService serviceTeste;
    
    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
        this.serviceTeste = new ServiceValidationFicheService();
    }
    
    @Test
    public void verifierQuUneFicheServiceEstValide() {
        List<Erreur> listeErreurs = this.serviceTeste.validerTest(this.ficheService);
        
        assertThat(listeErreurs.isEmpty()).isTrue();
        assertThat(listeErreurs.size()).isEqualTo(0);
    }

    @Test
    public void verifierQueLeNomDeServiceEstValide() {
        this.ficheService.setNomService("");
        List<Erreur> listeErreurs = this.serviceTeste.validerTest(this.ficheService);

        assertThat(listeErreurs.size()).isEqualTo(1);
        this.verifierLesAffirmations(listeErreurs, CODE_ERREUR_NOM_SERVICE);
    }
    
    @Test
    public void verifierQueLeNomEditeurEstValide() {
        this.ficheService.setNomEditeur("");
        List<Erreur> listeErreurs = this.serviceTeste.validerTest(this.ficheService);

        assertThat(listeErreurs.size()).isEqualTo(2);
        this.verifierLesAffirmations(listeErreurs, CODE_ERREUR_NOM_EDITEUR);
    }
    
    @Test
    public void verifierQueLeTypeDeServiceEstValide() {
        this.ficheService.setTypeService("");
        List<Erreur> listeErreurs = this.serviceTeste.validerTest(this.ficheService);

        assertThat(listeErreurs.size()).isEqualTo(2);
        this.verifierLesAffirmations(listeErreurs, CODE_ERREUR_TYPE_SERVICE);
    }
    
    private void verifierLesAffirmations(List<Erreur> listeErreurs, Object erreur) {
        assertThat(listeErreurs.isEmpty()).isFalse();
        assertThat(listeErreurs.get(0)).isEqualTo(erreur);
    }

    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService("Service 1");
        ficheService.setNomEditeur("Pole Emploi");
        ficheService.setTypeService("Type service 1");
        ficheService.setMailContact("contact1@mail.fr");
        return ficheService;
    }
    
}
