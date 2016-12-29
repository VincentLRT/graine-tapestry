package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.tapestry5.ValidationTracker;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pe.graine.tapestry.beans.ContexteSaisieFicheDeService;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.beans.FicheService.TypeServiceEnum;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.pages.RecapSaisieFicheService;
import fr.pe.graine.tapestry.pages.SaisieFicheService;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

@RunWith(MockitoJUnitRunner.class)
public class SaisieFicheServiceTest {
    
    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    private static final TypeServiceEnum TYPE_SERVICE = TypeServiceEnum.ELEARNING;
    private static final String EMAIL = "emailEditeur@gmail.com";
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    
    @InjectMocks
    private SaisieFicheService saisieFicheServiceTeste;

    private FicheService ficheServiceBrouillonTeste;

    @Mock
    private ContexteSaisieFicheDeService contexteSaisieFicheService;
    
    @Mock
    private ValidationTracker validationTracker;
    
    @Mock
    private Form formulaireFicheServiceEnSaisieTest;
    
    @Mock
    private TextField nomService;

    @Mock
    private TextField typeService;
    
    @Mock
    private TextField mailEditeur;
    
    @Mock
    private TextField nomEditeur;
    
    @Mock
    private ServiceAccesFicheService serviceAccesFicheService;

    @Before
    public void avantLesTests() {
        this.ficheServiceBrouillonTeste = this.fournirUnFicheDeTest();
        this.saisieFicheServiceTeste.setFicheServiceBrouillon(this.ficheServiceBrouillonTeste);
        when(this.formulaireFicheServiceEnSaisieTest.getDefaultTracker()).thenReturn(this.validationTracker);
        
    }

    @Test
    public void verifierLaBonneSaisieDeLaFicheService() {
        
        this.saisieFicheServiceTeste.onSuccessFromFormulaireFicheServiceEnSaisie();
        
    }
    
    @Test
    public void verifierFicheServiceKoSiNomAbsent() {
        this.ficheServiceBrouillonTeste.setNomService("");

        this.saisieFicheServiceTeste.onValidateFromFormulaireFicheServiceEnSaisie();

        Mockito.verify(this.formulaireFicheServiceEnSaisieTest).recordError(this.nomService,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT);
    }
    
    @Test
    public void verifierFicheServiceKoSiNomEditeurAbsent() {
        this.ficheServiceBrouillonTeste.setNomEditeur("");

        this.saisieFicheServiceTeste.onValidateFromFormulaireFicheServiceEnSaisie();

        Mockito.verify(this.formulaireFicheServiceEnSaisieTest).recordError(this.nomEditeur,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT);
    }
    
    @Test
    public void verifierFicheServiceKoSiEmailAuMauvaisFormatOuVide() {
        this.ficheServiceBrouillonTeste.setMailEditeur("vincent.laroutearobasegmail.com");

        this.saisieFicheServiceTeste.onValidateFromFormulaireFicheServiceEnSaisie();

        verify(this.formulaireFicheServiceEnSaisieTest).recordError(this.mailEditeur,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_EDITEUR_NON_VALIDE);
    }
    
    @Test
    public void verifierOnSuccessFromFormulaireFicheServiceEnSaisie() {
        when(this.serviceAccesFicheService.ecrireFicheService(this.ficheServiceBrouillonTeste)).thenReturn(this.ficheServiceBrouillonTeste);
        this.saisieFicheServiceTeste.setContactDifferent(false);

        Object pageRetour = this.saisieFicheServiceTeste.onSuccessFromFormulaireFicheServiceEnSaisie();
        
        assertThat(this.ficheServiceBrouillonTeste.getMailEditeur()).isEqualTo(this.ficheServiceBrouillonTeste.getMailContactTechnique());
        assertThat(this.ficheServiceBrouillonTeste.getIdFicheService()).isNotEmpty();
        verify(this.contexteSaisieFicheService).setFicheServiceValidee(this.ficheServiceBrouillonTeste);
        verify(this.serviceAccesFicheService).ecrireFicheService(this.ficheServiceBrouillonTeste);
        assertThat(pageRetour).isEqualTo(RecapSaisieFicheService.class);
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
    
    public String getDateDuJour() {
        return this.sdf.format(Calendar.getInstance().getTime());
    }
}
