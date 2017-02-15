package fr.pe.graine.tapestry.pages;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

public class ListerLesFichesServices {

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
    private ServiceAccesFicheService serviceAccesFicheService;
    
    @Property
    private FicheService ficheService;
    
    public List<FicheService> getListeFichesService() {
        return this.serviceAccesFicheService.listerLesFichesServices();
    }
    
}