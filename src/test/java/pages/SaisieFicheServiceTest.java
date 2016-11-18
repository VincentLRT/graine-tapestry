package pages;

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
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.pages.SaisieFicheService;

@RunWith(MockitoJUnitRunner.class)
public class SaisieFicheServiceTest {

    private static final String NOM_SERVICE = "Service1";
    private static final String NOM_EDITEUR = "editeur1";
    private static final String TYPE_SERVICE = "Type de service 1";
    private static final String EMAIL = "laroute.vincent@gmail.com";
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
    public void verifierFicheServiceKoSiTypeDeServiceAbsent() {
        // TODO enum
        // this.ficheServiceBrouillonTeste.setTypeDeService("");
        
        this.saisieFicheServiceTeste.onValidateFromFormulaireFicheServiceEnSaisie();
        
        Mockito.verify(this.formulaireFicheServiceEnSaisieTest).recordError(this.typeService,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_TYPE_DE_SERVICE_ABSENT);
    }

    @Test
    public void verifierFicheServiceKoSiEmailAuMauvaisFormatOuVide() {
        this.ficheServiceBrouillonTeste.setMailEditeur("vincent.laroutearobasegmail.com");
        
        this.saisieFicheServiceTeste.onValidateFromFormulaireFicheServiceEnSaisie();
        
        Mockito.verify(this.formulaireFicheServiceEnSaisieTest).recordError(this.mailEditeur,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_EDITEUR_NON_VALIDE);
    }

    private FicheService fournirUnFicheDeTest() {
        FicheService ficheService = new FicheService();
        ficheService.setNomService(NOM_SERVICE);
        ficheService.setNomEditeur(NOM_EDITEUR);
        // TODO enum
        // ficheService.setTypeDeService(TYPE_SERVICE);
        ficheService.setMailEditeur(EMAIL);
        ficheService.setDateDeCreation(Calendar.getInstance().getTime());
        
        return ficheService;
    }

    public String getDateDuJour() {
        return this.sdf.format(Calendar.getInstance().getTime());
    }
}
