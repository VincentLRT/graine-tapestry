package fr.pe.graine.tapestry.beans;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class FicheServiceAutoValidee {
    
    private String nomService;
    private String nomEditeur;
    private String typeDeService;
    private String email;
    private Date dateDeCreation;
    
    public FicheServiceAutoValidee(String nomService, String nomEditeur, String typeDeService, String email, Date dateDeCreation) {
        this.nomService = nomService;
        this.nomEditeur = nomEditeur;
        this.typeDeService = typeDeService;
        this.email = email;
        this.dateDeCreation = dateDeCreation;
    }

    @NotEmpty
    @NotNull
    public String getnomService() {
        return this.nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }
    
    @NotEmpty
    @NotNull
    public String getnomEditeur() {
        return this.nomEditeur;
    }
    
    public void setNomEditeur(String nomEditeur) {
        this.nomEditeur = nomEditeur;
    }
    
    @NotEmpty
    @NotNull
    public String getTypeDeService() {
        return this.typeDeService;
    }
    
    public void setTypeDeService(String typeDeService) {
        this.typeDeService = typeDeService;
    }

    @NotNull
    @Email
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @NotNull
    @Past
    public Date getDateDeCreation() {
        return this.dateDeCreation;
    }
    
    public void setDateDeCreation(Date dateDeCreation) {
        this.dateDeCreation = dateDeCreation;
    }
}
