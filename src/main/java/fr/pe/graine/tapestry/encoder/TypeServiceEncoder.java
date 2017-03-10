package fr.pe.graine.tapestry.encoder;

import org.apache.tapestry5.ValueEncoder;

import fr.pe.graine.tapestry.enumeration.TypeServiceEnum;

public class TypeServiceEncoder implements ValueEncoder<TypeServiceEnum> {
    
    public String toClient(TypeServiceEnum value) {
        return value.getLibelle();
    }
    
    public TypeServiceEnum toValue(String clientValue) {
        return TypeServiceEnum.getTypeService(clientValue);
    }
    
}
