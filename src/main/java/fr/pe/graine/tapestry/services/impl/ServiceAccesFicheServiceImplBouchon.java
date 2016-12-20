package fr.pe.graine.tapestry.services.impl;

import java.util.ArrayList;
import java.util.List;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

public class ServiceAccesFicheServiceImplBouchon implements ServiceAccesFicheService {
    
    private List<FicheService> listeFicheService;

    public ServiceAccesFicheServiceImplBouchon() {
        this.listeFicheService = new ArrayList<FicheService>();
    }
    
    public FicheService ecrireFicheService(FicheService ficheService) {
        
        ficheService.setId(this.genererUnIdentifiantFicheService());
        
        this.listeFicheService.add(ficheService);
        
        return ficheService;
    }

    public FicheService lectureListeFicheService(List<FicheService> listeFichesService) {
        FicheService ficheService = new FicheService();
        return ficheService;
    }

    public String genererUnIdentifiantFicheService() {
        return String.valueOf(Math.random());
    }

    public List<FicheService> getListeFicheService() {
        return this.listeFicheService;
    }
}
