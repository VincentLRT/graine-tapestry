package fr.pe.graine.tapestry.pages;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.pe.graine.tapestry.beans.ContexteSaisieFicheDeService;
import fr.pe.graine.tapestry.beans.Erreur;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.services.ServiceValidationFicheService;

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

    @InjectComponent
    private Form formulaireFicheServiceEnSaisie;

    @Inject
    private ServiceValidationFicheService validerFicheService;

    private FicheService ficheServiceBrouillon;

    @SessionState
    private ContexteSaisieFicheDeService contexteSaisieFicheService;

    @Persist
    private List<Erreur> listeErreurs;
    
    @InjectComponent("nomService")
    private TextField nomService;

    @InjectComponent("typeService")
    private TextField typeService;

    @InjectComponent("mailEditeur")
    private TextField mailEditeur;

    @InjectComponent("nomEditeur")
    private TextField nomEditeur;
    
    public void onActivate() {
        if (this.ficheServiceBrouillon == null) {
            this.ficheServiceBrouillon = new FicheService();
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

    // public Object onSelectedFromValiderFicheService() {
    // this.ficheServiceBrouillon.setDateDeCreation(Calendar.getInstance().getTime());
    // this.listeErreurs = this.validerFicheService.valider(this.ficheServiceBrouillon);
    //
    // if (this.listeErreurs.isEmpty()) {
    // System.out.println("fiche validée");
    // this.contexteSaisieFicheService.setFicheServiceValidee(this.ficheServiceBrouillon);
    // return RecapSaisieFicheService.class;
    // } else {
    // System.out.println("fiche non validée");
    // System.out.println("Il y a " + this.validerFicheService.valider(this.ficheServiceBrouillon).size()
    // + " erreur(s) dans la fiche de service : " + this.ficheServiceBrouillon.getNomService());
    // for (int i = 0; i < this.validerFicheService.valider(this.ficheServiceBrouillon).size(); i++) {
    // System.out.println("Erreur " + (i + 1) + " : "
    // + this.validerFicheService.valider(this.ficheServiceBrouillon).get(i).getCodeErreur() + " -> "
    // + this.validerFicheService.valider(this.ficheServiceBrouillon).get(i).getLibelleErreur());
    // }
    // return this;
    // }
    // }
    
    public void onValidateFromFormulaireFicheServiceEnSaisie() {
        this.ficheServiceBrouillon.setDateDeCreation(Calendar.getInstance().getTime());
        
        if (StringUtils.isBlank(this.ficheServiceBrouillon.getNomService())) {
            this.formulaireFicheServiceEnSaisie.recordError(this.nomService, EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT);
        }
        if (StringUtils.isBlank(this.ficheServiceBrouillon.getTypeDeService())) {
            this.formulaireFicheServiceEnSaisie.recordError(this.typeService, EntrepotReglesDeGestion.LIBELLE_ERREUR_TYPE_DE_SERVICE_ABSENT);
        }
        if (StringUtils.isBlank(this.ficheServiceBrouillon.getNomEditeur())) {
            this.formulaireFicheServiceEnSaisie.recordError(this.nomEditeur, EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT);
        }
        
        if (this.ficheServiceBrouillon.getMailEditeur() != null) {
            if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$", this.ficheServiceBrouillon.getMailEditeur()) == false) {
                this.formulaireFicheServiceEnSaisie.recordError(this.mailEditeur, EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_NON_VALIDE);
            }
        } else {
            this.formulaireFicheServiceEnSaisie.recordError(this.mailEditeur, EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_NON_VALIDE);
        }
        
    }

    public Object onSuccessFromFormulaireFicheServiceEnSaisie() {
        System.out.println("fiche validée");
        this.contexteSaisieFicheService.setFicheServiceValidee(this.ficheServiceBrouillon);
        return RecapSaisieFicheService.class;
    }

    public Object onFailureFromFormulaireFicheServiceEnSaisie() {
        System.out.println("fiche non validée");
        return this;
    }

}
