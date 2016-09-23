package fr.pe.graine.tapestry.pages;

import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

public class AccordCGU {

    @Property
    @Inject
    @Path("context:images/logo_emploiStore.png")
    private Asset logoEmploiStore;

}
