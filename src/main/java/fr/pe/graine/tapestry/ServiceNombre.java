package fr.pe.graine.tapestry;

public class ServiceNombre {
    
    public int multiplierUnNombrePar2(int nombreInitial) {
        return nombreInitial * ConstantesNombre.DEUX;
    }

    public int diviserUnNombrePar2(int nombreInitial) {
        return nombreInitial / ConstantesNombre.DEUX;
    }
    
    public int multiplierUnNombreParAutre(int nombreInitial, int nombreParLequelOnMultiplie) {
        return nombreInitial * nombreParLequelOnMultiplie;
    }
    
    public int diviserUnNombreParAutre(int nombreInitial, int nombreParLequelOnDivise) {
        if (nombreParLequelOnDivise != 0) {
            return nombreInitial / nombreParLequelOnDivise;
        } else {
            throw new RuntimeException();
        }
        
        // try {
        // return nombreInitial / nombreParLequelOnDivise;
        // } catch (RuntimeException e) {
        // throw e;
        // }
        
    }
}
