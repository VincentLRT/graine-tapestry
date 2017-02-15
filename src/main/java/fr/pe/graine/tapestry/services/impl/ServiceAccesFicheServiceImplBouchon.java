package fr.pe.graine.tapestry.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

public class ServiceAccesFicheServiceImplBouchon implements ServiceAccesFicheService {
    
    private List<FicheService> listeFicheService;
    private FicheService ficheService;

    public ServiceAccesFicheServiceImplBouchon() {
        this.listeFicheService = new ArrayList<FicheService>();
    }
    
    public FicheService ecrireFicheService(FicheService ficheService) {
        
        ficheService.setIdFicheService(this.genererUnIdentifiantFicheService());
        
        this.listeFicheService.add(ficheService);
        
        return ficheService;
    }

    public FicheService lireFicheService(String nomService) {
        this.fournirDesFichesServiceAUneListe();
        for (int i = 0; i < this.listeFicheService.size(); i++) {
            if (this.listeFicheService.get(i).getNomService() == nomService) {
                this.affichageFicheServiceDansLaConsole(i);
                return this.listeFicheService.get(i);
            }
        }
        System.out.println("La fiche service demandée n'existe pas !");
        return null;
    }

    public String genererUnIdentifiantFicheService() {
        return String.valueOf(Math.random());
    }

    public List<FicheService> getListeFicheService() {
        return this.listeFicheService;
    }
    
    public void fournirDesFichesServiceAUneListe() {
        FicheService ficheService1 = new FicheService();
        ficheService1.setNomService("Service1");
        ficheService1.setNomEditeur("Editeur1");
        ficheService1.setTypeDeService(FicheService.TypeServiceEnum.ELEARNING);
        ficheService1.setMailEditeur("laroute.vincent@gmail.com");
        ficheService1.setMailContactTechnique("laroute.vincent@gmail.com");
        ficheService1.setDateDeCreation(new Date());
        this.listeFicheService.add(ficheService1);

        FicheService ficheService2 = new FicheService();
        ficheService2.setNomService("Service2");
        ficheService2.setNomEditeur("Editeur2");
        ficheService2.setTypeDeService(FicheService.TypeServiceEnum.MOOC);
        ficheService2.setMailEditeur("mailediteur2@mail.fr");
        ficheService2.setMailContactTechnique("mailcontacttechnique2@mail.fr");
        ficheService2.setDateDeCreation(new Date());
        this.listeFicheService.add(ficheService2);

        FicheService ficheService3 = new FicheService();
        ficheService3.setNomService("Service3");
        ficheService3.setNomEditeur("Editeur3");
        ficheService3.setTypeDeService(FicheService.TypeServiceEnum.ELEARNING);
        ficheService3.setMailEditeur("mailediteur3@mail.fr");
        ficheService3.setMailContactTechnique("mailcontacttechnique3@mail.fr");
        ficheService3.setDateDeCreation(new Date());
        this.listeFicheService.add(ficheService3);
    }
    
    private void affichageFicheServiceDansLaConsole(int i) {
        System.out.println(this.listeFicheService.get(i).getNomService());
        System.out.println(this.listeFicheService.get(i).getNomEditeur());
        System.out.println(this.listeFicheService.get(i).getTypeDeService());
        System.out.println(this.listeFicheService.get(i).getMailEditeur());
        System.out.println(this.listeFicheService.get(i).getMailContactTechnique());
        System.out.println(this.listeFicheService.get(i).getDateDeCreation());
    }
    
    public List<FicheService> listerLesFichesServices() {
        return null;
    }
}
