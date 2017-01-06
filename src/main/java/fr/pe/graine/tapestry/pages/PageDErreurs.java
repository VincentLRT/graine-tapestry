package fr.pe.graine.tapestry.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import fr.pe.graine.tapestry.entrepot.ConstantesGlobales;

public class PageDErreurs {
    
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
    
    private String messageDErreur;
    
    @Inject
    private Messages messages;
    
    public void onActivate(String urlRetour) {
        if (this.messages.contains(urlRetour)) {
            this.setMessageDErreur(this.messages.get(urlRetour));
        } else {
            this.setMessageDErreur(this.messages.get("erreur-inexistante"));
        }
    }
    
    public String getMessageDErreur() {
        return this.messageDErreur;
    }
    
    public void setMessageDErreur(String messageDErreur) {
        this.messageDErreur = messageDErreur;
    }
}
