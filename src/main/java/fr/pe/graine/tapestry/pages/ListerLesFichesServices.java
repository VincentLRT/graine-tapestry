package fr.pe.graine.tapestry.pages;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;
import fr.pe.graine.tapestry.services.ServiceAppelApiRest;

public class ListerLesFichesServices {
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
    private Gson gson = new GsonBuilder().create();

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

    @Inject
    private ServiceAppelApiRest serviceAppelApiRest;

    @Property
    private FicheService ficheService;

    public List<FicheService> getListeFichesService() {
        List<FicheService> listeDesFiches = this.serviceAppelApiRest.appelRessourceListeDesFichesService();
        // return this.serviceAccesFicheService.listerLesFichesServices();
        return listeDesFiches;
    }
}