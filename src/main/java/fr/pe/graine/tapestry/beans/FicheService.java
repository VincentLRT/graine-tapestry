package fr.pe.graine.tapestry.beans;

import java.util.Date;

public class FicheService {

    private String nomService;
    private String nomEditeur;
    private String typeService;
    private String mailContact;
    private Date dateDeCreation;
    
    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public void setMailContact(String mailContact) {
        this.mailContact = mailContact;
    }

    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
        
    }

    public String getNomService() {
        return this.nomService;
    }

    public String getNomEditeur() {
        return this.nomEditeur;
    }
    
    public String getTypeService() {
        return this.typeService;
    }

}
