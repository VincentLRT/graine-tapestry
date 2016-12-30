package fr.pe.graine.tapestry.services.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;
import fr.pe.graine.tapestry.utilitaire.FicheServiceUtils;

public class ServiceAccesFicheServiceImplMongo implements ServiceAccesFicheService {

    private Datastore datastore;

    public ServiceAccesFicheServiceImplMongo() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        Morphia morphia = new Morphia();
        this.datastore = morphia.createDatastore(mongoClient, "graine-tapestry");
        morphia.map(FicheService.class);

    }
    
    public FicheService ecrireFicheService(FicheService ficheService) {
        
        if (ficheService == null) {
            return ficheService;
        } else {
            ficheService.setIdFicheService(FicheServiceUtils.genererUnIdFicheServiceValide(ficheService.getNomService()));
            this.datastore.save(ficheService);
            return ficheService;
        }

    }
    
    public FicheService lireFicheService(String idFicheService) {
        Query<FicheService> query = this.datastore.createQuery(FicheService.class);
        query.filter("idFicheService =", idFicheService);
        return query.get();
    }

    public Datastore getDatastore() {
        return this.datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }
    
}
