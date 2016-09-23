package fr.pe.graine.tapestry.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;

public class ServiceValidationFicheService {
    
    public List<Erreur> valider(FicheService ficheService) {
        List<Erreur> listeErreurs = new ArrayList<Erreur>();
        this.verifierAttributRenseigne(ficheService.getNomService(), EntrepotReglesDeGestion.CODE_ERREUR_NOM_SERVICE_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT, listeErreurs);
        this.verifierAttributRenseigne(ficheService.getNomEditeur(), EntrepotReglesDeGestion.CODE_ERREUR_NOM_EDITEUR_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT, listeErreurs);
        
        return listeErreurs;
    }
    
    private void verifierAttributRenseigne(String attributTeste, String codeErreur, String libelleErreur, List<Erreur> listeErreurs) {
        if (StringUtils.isBlank(attributTeste)) {
            listeErreurs.add(new Erreur(codeErreur, libelleErreur));
        }
    }
}
