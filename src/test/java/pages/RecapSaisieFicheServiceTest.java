package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pe.graine.tapestry.beans.ContexteSaisieFicheDeService;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.pages.PageDErreurs;
import fr.pe.graine.tapestry.pages.RecapSaisieFicheService;
import fr.pe.graine.tapestry.services.impl.ServiceAccesFicheServiceImplMongo;

@RunWith(MockitoJUnitRunner.class)
public class RecapSaisieFicheServiceTest {

    private static final String ID_FICHESERVICE = "service1";
    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;
    private static final String EMAIL = "emailEditeur@gmail.com";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    private Object urlFinal = this;

    private FicheService ficheService;
    
    @InjectMocks
    private RecapSaisieFicheService recapSaisieFicheServiceTeste;
    
    @Mock
    private ServiceAccesFicheServiceImplMongo serviceAccesFicheService;

    @Mock
    private ContexteSaisieFicheDeService contexteSaisieFicheService;

    @Mock
    private PageRenderLinkSource linkSource;
    
    @Mock
    private Link linkRetour;
    
    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
    }

    @Test
    public void verifierOnActivateFicheServiceNonExistante() {
        String idFicheInexistante = "ficheInexistantePourTest";
        when(this.serviceAccesFicheService.lireFicheService(idFicheInexistante)).thenReturn(null);
        when(this.linkSource.createPageRenderLinkWithContext(PageDErreurs.class, EntrepotReglesDeGestion.CODE_ERREUR_FICHE_SERVICE_INEXISTANTE))
                        .thenReturn(this.linkRetour);
        when(this.linkRetour.toAbsoluteURI()).thenReturn("http://localhost:8080/graine-tapestry/pagederreurs/erreur-nom-service-inexistant");

        this.urlFinal = this.recapSaisieFicheServiceTeste.onActivate(idFicheInexistante);

        assertThat(this.urlFinal).isNotNull();
        assertThat(this.urlFinal).isInstanceOf(URL.class);
    }
    
    @Test
    public void verifierOnActivateFicheServiceExistante() {
        when(this.serviceAccesFicheService.lireFicheService(ID_FICHESERVICE)).thenReturn(this.ficheService);

        this.urlFinal = this.recapSaisieFicheServiceTeste.onActivate(ID_FICHESERVICE);

        verify(this.contexteSaisieFicheService).setFicheServiceValidee(this.ficheService);
        assertThat(this.urlFinal).isInstanceOf(RecapSaisieFicheService.class);
    }
    
    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService(NOM_SERVICE);
        ficheService.setNomEditeur(NOM_EDITEUR);
        ficheService.setTypeDeService(TYPE_SERVICE);
        ficheService.setMailEditeur(EMAIL);
        ficheService.setDateDeCreation(Calendar.getInstance().getTime());
        
        return ficheService;
    }
}
