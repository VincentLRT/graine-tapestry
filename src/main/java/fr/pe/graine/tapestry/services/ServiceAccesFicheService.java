package fr.pe.graine.tapestry.services;

import java.util.List;

import fr.pe.graine.tapestry.beans.FicheService;

public interface ServiceAccesFicheService {

    public FicheService ecrireFicheService(FicheService ficheService);

    public FicheService lireFicheService(String nomService);
    
    public List<FicheService> listerLesFichesServices();
    
}
