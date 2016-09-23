package fr.pe.graine.tapestry.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ServiceValidationFicheService {
    
    private Erreur e;

    public List<Erreur> validerTest(FicheService ficheService) {
        List<Erreur> listeErreurs = new ArrayList<Erreur>();
        String nomService = ficheService.getNomService();
        String nomEditeur = ficheService.getNomEditeur();
        String typeService = ficheService.getTypeService();
        
        if (StringUtils.isBlank(nomService)) {
            listeErreurs.add(this.e);
        }

        if (StringUtils.isBlank(nomEditeur)) {
            listeErreurs.add(this.e);
        }

        if (StringUtils.isBlank(typeService)) {
            listeErreurs.add(this.e);
        }

        return listeErreurs;
    }
}
