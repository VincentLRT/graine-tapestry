package fr.pe.graine.tapestry.beans;

import java.util.Date;

public class FicheService {
    
    private String nomService;
    private String nomEditeur;
    private String typeDeService;
    private String email;
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

    public String getTypeDeService() {
        return this.typeDeService;
    }

    public void setTypeDeService(String typeDeService) {
        this.typeDeService = typeDeService;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Date getDateDeCreation() {
        return this.dateDeCreation;
    }
    
    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }
}
