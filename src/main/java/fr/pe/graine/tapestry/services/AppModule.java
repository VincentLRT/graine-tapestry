package fr.pe.graine.tapestry.services;

import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;

public class AppModule {
    
    // Module d'injection de dépendance
    public static void bind(final ServiceBinder serviceBinder) {
        // serviceBinder.bind(ServiceAppelRest.class);
    }

    public static void contributeApplicationDefaults(MappedConfiguration<String, Object> configuration) {
        configuration.add("graine.path", "fr/pe/graine/tapestry");
        configuration.add("graine.statics", "classpath:${graine.path}/statics");
    }

}