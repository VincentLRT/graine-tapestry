package fr.pe.graine.tapestry.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import fr.pe.graine.tapestry.beans.Erreur;
import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.entrepot.EntrepotReglesDeGestion;

public class ServiceValidationFicheService {

    public List<Erreur> valider(FicheService ficheService) {
        List<Erreur> listeErreurs = new ArrayList<Erreur>();
        this.verifierAttributRenseigne(ficheService.getNomService(), EntrepotReglesDeGestion.CODE_ERREUR_NOM_SERVICE_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_SERVICE_ABSENT, listeErreurs);
        this.verifierAttributRenseigne(ficheService.getNomEditeur(), EntrepotReglesDeGestion.CODE_ERREUR_NOM_EDITEUR_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_NOM_EDITEUR_ABSENT, listeErreurs);
        this.verifierAttributRenseigne(ficheService.getTypeDeService(), EntrepotReglesDeGestion.CODE_ERREUR_TYPE_DE_SERVICE_ABSENT,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_TYPE_DE_SERVICE_ABSENT, listeErreurs);
        this.verifierAdresseFormatEmail(ficheService.getEmail(), EntrepotReglesDeGestion.CODE_ERREUR_EMAIL_NON_VALIDE,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_EMAIL_NON_VALIDE, listeErreurs);
        this.verifierQueDateCreationFicheServiceAnterieurOuEgaleDateDuJour(ficheService.getDateDeCreation(), EntrepotReglesDeGestion.CODE_ERREUR_DATE_DE_CREATION_NON_VALIDE,
                        EntrepotReglesDeGestion.LIBELLE_ERREUR_DATE_DE_CREATION_NON_VALIDE, listeErreurs);

        return listeErreurs;
    }

    private void verifierAttributRenseigne(String attributTeste, String codeErreur, String libelleErreur, List<Erreur> listeErreurs) {
        if (StringUtils.isBlank(attributTeste)) {
            listeErreurs.add(new Erreur(codeErreur, libelleErreur));
        }
    }
    
    private void verifierAdresseFormatEmail(String emailTeste, String codeErreur, String libelleErreur, List<Erreur> listeErreurs) {
        if (Pattern.matches("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)+$", emailTeste) == false) {
            listeErreurs.add(new Erreur(codeErreur, libelleErreur));
        }
    }

    private void verifierQueDateCreationFicheServiceAnterieurOuEgaleDateDuJour(Date dateTeste, String codeErreur, String libelleErreur, List<Erreur> listeErreurs) {
        Date aujourdhui = new Date();
        if (dateTeste.after(aujourdhui)) {
            listeErreurs.add(new Erreur(codeErreur, libelleErreur));
        }
    }
    
}
