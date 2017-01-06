package fr.pe.graine.tapestry.services.impl;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.mongodb.DBCollection;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;

@RunWith(MockitoJUnitRunner.class)
public class ServiceAccesFicheServiceImplMongoTest {
    private static final String NOM_SERVICE = "Sérvice numéro ùn";
    private static final String NOM_EDITEUR = "Editeur1";
    private static final String EMAIL = "laroute.vincent@gmail.com";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;

    private FicheService ficheService;

    @InjectMocks
    private ServiceAccesFicheServiceImplMongo serviceAccesFicheServiceImplMongoTeste;

    @Mock
    private Datastore datastore;
    
    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
        this.serviceAccesFicheServiceImplMongoTeste.setDatastore(this.datastore);
    }

    @Test
    public void verifierEcritureDansMongo() {

        FicheService ficheService = this.serviceAccesFicheServiceImplMongoTeste.ecrireFicheService(this.ficheService);

        verify(this.datastore).save(this.ficheService);
        assertEquals("serviceNumeroUn", ficheService.getIdFicheService());
        assertEquals(this.ficheService.getNomService(), ficheService.getNomService());
        assertEquals(this.ficheService.getNomEditeur(), ficheService.getNomEditeur());
        assertEquals(this.ficheService.getTypeDeService(), ficheService.getTypeDeService());
        assertEquals(this.ficheService.getMailEditeur(), ficheService.getMailEditeur());
        assertEquals(this.ficheService.getMailContactTechnique(), ficheService.getMailContactTechnique());
        assertEquals(this.ficheService.getDateDeCreation(), ficheService.getDateDeCreation());

    }

    @Test
    public void verifierPasDEcritureDansMongoQuandFicheServiceNulle() {

        this.ficheService = null;
        DBCollection mockDBCollection = mock(DBCollection.class);

        FicheService ficheService = this.serviceAccesFicheServiceImplMongoTeste.ecrireFicheService(this.ficheService);

        verifyZeroInteractions(mockDBCollection);

    }

    @Test
    public void verifierLectureDansLaListe() {
        Query<FicheService> mockQuery = mock(Query.class);
        when(this.datastore.createQuery(FicheService.class)).thenReturn(mockQuery);
        when(mockQuery.get()).thenReturn(this.ficheService);

        FicheService ficheServiceLue = this.serviceAccesFicheServiceImplMongoTeste.lireFicheService(this.ficheService.getIdFicheService());

        verify(mockQuery).filter("idFicheService =", ficheServiceLue.getIdFicheService());
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
