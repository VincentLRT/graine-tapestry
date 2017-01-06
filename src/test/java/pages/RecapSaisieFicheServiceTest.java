package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

public class RecapSaisieFicheServiceTest {
    
    private static final String ID_FICHESERVICE = "service1";
    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;
    private static final String EMAIL = "emailEditeur@gmail.com";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    
    private FicheService ficheService;

    @Mock
    private ServiceAccesFicheService serviceAccesFicheService;
    
    @Before
    public void avantLesTests() {
        this.ficheService = this.fournirUnFicheDeTest();
    }
    
    @Test
    public void verifierOnActivate() {
        when(this.serviceAccesFicheService.lireFicheService(this.ficheService.getIdFicheService())).thenReturn(this.ficheService);

        FicheService ficheServiceLue = this.serviceAccesFicheService.lireFicheService(this.ficheService.getIdFicheService());

        assertThat(ficheServiceLue).equals(this.ficheService);
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
