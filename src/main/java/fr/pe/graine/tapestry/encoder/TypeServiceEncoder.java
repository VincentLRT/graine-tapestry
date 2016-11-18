package fr.pe.graine.tapestry.encoder;

import org.apache.tapestry5.ValueEncoder;

import fr.pe.graine.tapestry.beans.FicheService;

public class TypeServiceEncoder implements ValueEncoder<FicheService.TypeServiceEnum> {

    public String toClient(FicheService.TypeServiceEnum value) {
        return value.getLibelle();
    }

    public FicheService.TypeServiceEnum toValue(String clientValue) {
        return FicheService.TypeServiceEnum.getTypeService(clientValue);
    }

}
