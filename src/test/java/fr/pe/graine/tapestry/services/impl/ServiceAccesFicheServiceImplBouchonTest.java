package fr.pe.graine.tapestry.services.impl;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;

public class ServiceAccesFicheServiceImplBouchonTest {
    
    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "Editeur1";
    private static final String EMAIL = "laroute.vincent@gmail.com";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;

    private FicheService ficheService;
    private ServiceAccesFicheServiceImplBouchon serviceAccesFicheServiceImplBouchonTeste;

    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
        this.serviceAccesFicheServiceImplBouchonTeste = new ServiceAccesFicheServiceImplBouchon();
    }
    
    @Test
    public void verifierEcritureDansLaListe() {
        FicheService ficheService = this.serviceAccesFicheServiceImplBouchonTeste.ecrireFicheService(this.ficheService);
        
        assertThat(this.serviceAccesFicheServiceImplBouchonTeste.getListeFicheService().size()).isEqualTo(1);
        assertNotNull(ficheService.getIdFicheService());
        assertThat(ficheService.getNomService()).isEqualTo(this.ficheService.getNomService());
        assertThat(ficheService.getNomEditeur()).isEqualTo(this.ficheService.getNomEditeur());
        assertThat(ficheService.getMailEditeur()).isEqualTo(this.ficheService.getMailEditeur());
        assertThat(ficheService.getDateDeCreation()).isEqualTo(this.ficheService.getDateDeCreation());
    }

    @Test
    public void verifierLectureDansLaListe() {

        FicheService ficheServiceLue = this.serviceAccesFicheServiceImplBouchonTeste.lireFicheService(this.ficheService.getNomService());

        assertThat(ficheServiceLue.getNomService()).isEqualTo(this.ficheService.getNomService());
        assertThat(ficheServiceLue.getNomEditeur()).isEqualTo(this.ficheService.getNomEditeur());
        assertThat(ficheServiceLue.getMailEditeur()).isEqualTo(this.ficheService.getMailEditeur());
        assertThat(ficheServiceLue.getDateDeCreation()).isEqualTo(this.ficheService.getDateDeCreation());
    }

    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService(NOM_SERVICE);
        ficheService.setNomEditeur(NOM_EDITEUR);
        ficheService.setTypeDeService(TYPE_SERVICE);
        ficheService.setMailEditeur(EMAIL);
        ficheService.setMailContactTechnique(EMAIL);
        ficheService.setDateDeCreation(new Date());
        
        return ficheService;
    }
}
