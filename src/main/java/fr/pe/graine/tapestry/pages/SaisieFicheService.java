package fr.pe.graine.tapestry.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Checkbox;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Select;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.SelectModelFactory;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.encoder.TypeServiceEncoder;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.enumeration.TypeServiceEnum;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;
import fr.pe.graine.tapestry.services.ServiceAppelApiRest;
import fr.pe.graine.tapestry.services.ServiceValidationFicheService;
import fr.pe.graine.tapestry.utilitaire.FicheServiceUtils;

public class SaisieFicheService {
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    
    @Inject
    @Path(ConstantesGlobales.ACCES_RESSOURCE_STATIQUE + "/images/logo-emploi-store.png")
    @Property
    private Asset logoEmploiStore;
    
    @Inject
    @Path(ConstantesGlobales.ACCES_RESSOURCE_STATIQUE + "/css/bootstrap.css")
    @Property
    private Asset bootstrap;
    
    @Inject
    @Path(ConstantesGlobales.ACCES_RESSOURCE_STATIQUE + "/css/animate.css")
    @Property
    private Asset animate;

    @Inject
    @Path(ConstantesGlobales.ACCES_RESSOURCE_STATIQUE + "/css/graine-tapestry.css")
    @Property
    private Asset graineTapestryCss;
    
    @InjectComponent
    private Form formulaireFicheServiceEnSaisie;
    
    @Inject
    private ServiceValidationFicheService validerFicheService;
    
    @Persist
    private FicheService ficheServiceBrouillon;
    
    @Inject
    private ServiceAccesFicheService serviceAccesFicheService;
    
    @InjectComponent("nomService")
    private TextField nomService;
    
    @Property
    private SelectModel typeServiceModel;
    
    @InjectComponent("typeService")
    private Select typeService;
    
    @Inject
    SelectModelFactory selectModelFactory;
    
    @InjectComponent("mailEditeur")
    private TextField mailEditeur;
    
    @InjectComponent("mailContactTechnique")
    private TextField mailContactTechnique;
    
    @InjectComponent("contactDifferent")
    private Checkbox contactDifferentCheckBox;
    
    @Persist
    private boolean contactDifferent;
    
    @InjectComponent("nomEditeur")
    private TextField nomEditeur;

    @Inject
    private PageRenderLinkSource linkSource;
    
    @Inject
    private ServiceAppelApiRest serviceAppelApiRest;
    
    public TypeServiceEncoder getTypeServiceEncoder() {
        return new TypeServiceEncoder();
    }
    
    public void onActivate() {
        if (this.ficheServiceBrouillon == null) {
            this.ficheServiceBrouillon = new FicheService();
        }
        if (this.typeServiceModel == null) {
            this.typeServiceModel = this.selectModelFactory.create(Arrays.asList(TypeServiceEnum.values()), "libelle");
        }
    }
    
    public FicheService getFicheServiceBrouillon() {
        return this.ficheServiceBrouillon;
    }
    
    public void setFicheServiceBrouillon(FicheService ficheServiceBrouillon) {
        this.ficheServiceBrouillon = ficheServiceBrouillon;
    }
    
    public String getDateDuJour() {
        return this.sdf.format(Calendar.getInstance().getTime());
    }
    
    public void onValidateFromFormulaireFicheServiceEnSaisie() {
        this.ficheServiceBrouillon.setDateDeCreation(Calendar.getInstance().getTime());
        if (StringUtils.isBlank(this.ficheServiceBrouillon.getNomService())) {
            this.formulaireFicheServiceEnSaisie.recordError(this.nomService, EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT);
        } else {
            String idFicheServiceTest = FicheServiceUtils.genererUnIdFicheServiceValide(this.ficheServiceBrouillon.getNomService());
            if (this.serviceAccesFicheService.lireFicheService(idFicheServiceTest) != null) {
                this.formulaireFicheServiceEnSaisie.recordError(this.nomService, EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_EXISTANT);
            }
        }
        if (this.ficheServiceBrouillon.getTypeDeService() == null) {
            this.formulaireFicheServiceEnSaisie.recordError(this.typeService, EntrepotReglesDeGestion.LIBELLE_ERREUR_TYPE_DE_SERVICE_ABSENT);
        }
        if (StringUtils.isBlank(this.ficheServiceBrouillon.getNomEditeur())) {
            this.formulaireFicheServiceEnSaisie.recordError(this.nomEditeur, EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT);
        }
        if (this.ficheServiceBrouillon.getMailEditeur() != null) {
            if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$", this.ficheServiceBrouillon.getMailEditeur()) == false) {
                this.formulaireFicheServiceEnSaisie.recordError(this.mailEditeur,
                                EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_EDITEUR_NON_VALIDE);
            }
        } else {
            this.formulaireFicheServiceEnSaisie.recordError(this.mailEditeur, EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_EDITEUR_NON_VALIDE);
        }
        if (this.contactDifferent) {
            if (this.ficheServiceBrouillon.getMailContactTechnique() != null) {
                if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$",
                                this.ficheServiceBrouillon.getMailContactTechnique()) == false) {
                    this.formulaireFicheServiceEnSaisie.recordError(this.mailContactTechnique,
                                    EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_CONTACT_TECHNIQUE_NON_VALIDE);
                }
            } else {
                this.formulaireFicheServiceEnSaisie.recordError(this.mailContactTechnique,
                                EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_CONTACT_TECHNIQUE_NON_VALIDE);
            }
        }
    }
    
    public Object onSuccessFromFormulaireFicheServiceEnSaisie() {
        if (!this.contactDifferent) {
            this.ficheServiceBrouillon.setMailContactTechnique(this.ficheServiceBrouillon.getMailEditeur());
        }
        // FicheService ficheServiceCreee = this.serviceAccesFicheService.ecrireFicheService(this.ficheServiceBrouillon);
        FicheService ficheServiceCreee = this.serviceAppelApiRest.appelRessourceEnregistrementFicheService(this.ficheServiceBrouillon);
        Object urlRetour = this;
        try {
            urlRetour = new URL(this.linkSource.createPageRenderLinkWithContext(RecapSaisieFicheService.class,
                            ficheServiceCreee.getIdFicheService()).toAbsoluteURI());

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return urlRetour;
    }
    
    public Object onFailureFromFormulaireFicheServiceEnSaisie() {
        return this;
    }

    public boolean isContactDifferent() {
        return this.contactDifferent;
    }

    public void setContactDifferent(boolean contactDifferent) {
        this.contactDifferent = contactDifferent;
    }

}
