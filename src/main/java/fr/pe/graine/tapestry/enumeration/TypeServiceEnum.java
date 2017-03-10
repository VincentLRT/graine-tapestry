package fr.pe.graine.tapestry.enumeration;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

public enum TypeServiceEnum {
    ELEARNING("E-learning"),
    
    SIMULATEUR("Simulateur"),

    MOOC("Mooc"),
    
    COACHING("Coaching");
    
    private String libelle;

    private TypeServiceEnum(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return this.libelle;
    }
    
    public static TypeServiceEnum getTypeService(String clientValue) {
        for (TypeServiceEnum typeServiceCourant : Arrays.asList(TypeServiceEnum.values())) {
            if (StringUtils.equals(typeServiceCourant.getLibelle(), clientValue)) {
                return typeServiceCourant;
            }
        }
        return null;
    }
}