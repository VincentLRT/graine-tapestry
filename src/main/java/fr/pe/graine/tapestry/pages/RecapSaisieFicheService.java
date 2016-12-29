package fr.pe.graine.tapestry.pages;

import java.text.SimpleDateFormat;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.pe.graine.tapestry.beans.ContexteSaisieFicheDeService;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

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
    
    @SessionState
    private ContexteSaisieFicheDeService contexteSaisieFicheService;

    @Inject
    private ServiceAccesFicheService serviceAccesFicheService;
    
    public void onActivate(String idFicheService) {
        FicheService ficheServiceRercuperee = this.serviceAccesFicheService.lireFicheService(idFicheService);
        this.contexteSaisieFicheService.setFicheServiceValidee(ficheServiceRercuperee);
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