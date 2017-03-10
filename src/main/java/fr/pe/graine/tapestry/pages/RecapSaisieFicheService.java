package fr.pe.graine.tapestry.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;

import fr.pe.graine.tapestry.beans.ContexteSaisieFicheDeService;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;
import fr.pe.graine.tapestry.services.ServiceAppelApiRest;

public class RecapSaisieFicheService {
    
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
    
    @SessionState
    private ContexteSaisieFicheDeService contexteSaisieFicheService;

    @Inject
    private ServiceAccesFicheService serviceAccesFicheService;

    @Inject
    private PageRenderLinkSource linkSource;
    
    @Inject
    private ServiceAppelApiRest serviceAppelApiRest;
    
    public Object onActivate(String idFicheService) {
        // FicheService ficheServiceRecuperee = this.serviceAccesFicheService.lireFicheService(idFicheService);
        FicheService ficheServiceRecuperee = this.serviceAppelApiRest.appelRessourceUneFicheService(idFicheService);
        Object urlRetour = this;
        if (ficheServiceRecuperee == null) {
            urlRetour = this.redirectionVersPageDErreurs(urlRetour);
        } else {
            this.contexteSaisieFicheService.setFicheServiceValidee(ficheServiceRecuperee);
        }
        return urlRetour;
    }

    private Object redirectionVersPageDErreurs(Object urlRetour) {
        try {
            urlRetour = new URL(this.linkSource.createPageRenderLinkWithContext(PageDErreurs.class,
                            EntrepotReglesDeGestion.CODE_ERREUR_FICHE_SERVICE_INEXISTANTE).toAbsoluteURI());
            return urlRetour;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return urlRetour;
    }
    
    public ContexteSaisieFicheDeService getContexteSaisieFicheService() {
        return this.contexteSaisieFicheService;
    }
    
    public void setContexteSaisieFicheService(ContexteSaisieFicheDeService contexteSaisieFicheService) {
        this.contexteSaisieFicheService = contexteSaisieFicheService;
    }
    
    public String getDateDeCreation() {
        return this.sdf.format(this.contexteSaisieFicheService.getFicheServiceValidee().getDateDeCreation().getTime());
    }
}