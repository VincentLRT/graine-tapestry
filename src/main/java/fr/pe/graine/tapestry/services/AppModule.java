package fr.pe.graine.tapestry.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

import fr.pe.graine.tapestry.services.impl.ServiceAccesFicheServiceImplMongo;

public class AppModule {

    // Module d'injection de dépendance
    public static void bind(final ServiceBinder serviceBinder) {
        serviceBinder.bind(ServiceValidationFicheService.class);
        serviceBinder.bind(ServiceAppelApiRest.class);
        // serviceBinder.bind(ServiceAccesFicheService.class, ServiceAccesFicheServiceImplBouchon.class);
        serviceBinder.bind(ServiceAccesFicheService.class, ServiceAccesFicheServiceImplMongo.class);
        // serviceBinder.bind(MongoClient.class);
    }
    
    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add("graine.path", "fr/pe/graine/tapestry");
        configuration.add("graine.statics", "classpath:${graine.path}/statics");
    }
    
}