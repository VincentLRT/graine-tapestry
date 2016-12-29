package fr.pe.graine.tapestry.beans;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("ficheService")
public class FicheService {

    @Id
    private String idFicheService;
    private String nomService;
    private String nomEditeur;
    private TypeServiceEnum typeDeService;
    private String mailEditeur;
    private String mailContactTechnique;
    private Date dateDeCreation;
    
    public String getNomService() {
        return this.nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
    
    public String getNomEditeur() {
        return this.nomEditeur;
    }

    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    public TypeServiceEnum getTypeDeService() {
        return this.typeDeService;
    }

    public void setTypeDeService(TypeServiceEnum typeDeService) {
        this.typeDeService = typeDeService;
    }
    
    public String getMailEditeur() {
        return this.mailEditeur;
    }
    
    public void setMailEditeur(String mailEditeur) {
        this.mailEditeur = mailEditeur;
    }
    
    public String getMailContactTechnique() {
        return this.mailContactTechnique;
    }
    
    public void setMailContactTechnique(String mailContactTechnique) {
        this.mailContactTechnique = mailContactTechnique;
    }
    
    public Date getDateDeCreation() {
        return this.dateDeCreation;
    }
    
    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }
    
    public enum TypeServiceEnum {
        ELEARNING("E-learning"), SIMULATEUR("Simulateur"), MOOC("Mooc"), COACHING("Coaching");
        private String libelle;
        
        private TypeServiceEnum(String libelle) {
            this.libelle = libelle;
        }
        
        public String getLibelle() {
            return this.libelle;
        }

        public static FicheService.TypeServiceEnum getTypeService(String clientValue) {
            for (FicheService.TypeServiceEnum typeServiceCourant : Arrays.asList(FicheService.TypeServiceEnum.values())) {
                if (StringUtils.equals(typeServiceCourant.getLibelle(), clientValue)) {
                    return typeServiceCourant;
                }
            }
            return null;
        }
    }
    
    public String getIdFicheService() {
        return this.idFicheService;
    }
    
    public void setIdFicheService(String idFicheService) {
        this.idFicheService = idFicheService;
    }

}
