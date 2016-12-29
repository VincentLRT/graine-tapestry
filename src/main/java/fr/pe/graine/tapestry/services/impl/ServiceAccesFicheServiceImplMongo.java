package fr.pe.graine.tapestry.services.impl;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;

import fr.pe.graine.tapestry.beans.FicheService;
import fr.pe.graine.tapestry.services.ServiceAccesFicheService;

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
            Key<FicheService> key = this.datastore.save(ficheService);
            ficheService.setIdFicheService(key.getId().toString());
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
