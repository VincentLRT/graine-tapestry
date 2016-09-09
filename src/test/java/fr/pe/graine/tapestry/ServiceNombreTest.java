package fr.pe.graine.tapestry;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ServiceNombreTest {
    
    private ServiceNombre serviceTest;
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Before
    public void avantLesTests() {
        this.serviceTest = new ServiceNombre();
    }

    @Test
    public void multiplierUnNombrePar2() {
        int nombreInitial = ConstantesNombre.DEUX;
        
        int nombreResultat = this.serviceTest.multiplierUnNombrePar2(nombreInitial);

        assertThat(nombreResultat).isEqualTo(nombreInitial * ConstantesNombre.DEUX);
    }
    
    @Test
    public void diviserUnNombrePar2() {
        int nombreInitial = ConstantesNombre.DEUX;
        
        int nombreResultat = this.serviceTest.diviserUnNombrePar2(nombreInitial);
        
        assertThat(nombreResultat).isEqualTo(nombreInitial / ConstantesNombre.DEUX);
    }

    @Test
    public void multiplierUnNombreParAutre() {
        int nombreInitial = ConstantesNombre.DEUX;
        int nombreParLequelOnMultiplie = ConstantesNombre.TROIS;
        
        int nombreResultat = this.serviceTest.multiplierUnNombreParAutre(nombreInitial, nombreParLequelOnMultiplie);

        assertThat(nombreResultat).isEqualTo(nombreInitial * nombreParLequelOnMultiplie);
    }

    @Test
    public void diviserUnNombreParUnAutreDifferentDeZero() {
        int nombreInitial = ConstantesNombre.DEUX;
        int nombreParLequelOnDivise = ConstantesNombre.QUATRE;
        
        int nombreResultat = this.serviceTest.diviserUnNombreParAutre(nombreInitial, nombreParLequelOnDivise);

        assertThat(nombreResultat).isEqualTo(nombreInitial / nombreParLequelOnDivise);
    }
    
    @Test(expected = RuntimeException.class)
    public void diviserUnNombreParZero() {
        int nombreInitial = ConstantesNombre.DEUX;
        int nombreParLequelOnDivise = ConstantesNombre.ZERO;
        
        this.serviceTest.diviserUnNombreParAutre(nombreInitial, nombreParLequelOnDivise);
    }
    
    @Test
    public void diviserUnNombreParZeroBis() {
        this.thrown.expect(RuntimeException.class);
        int nombreInitial = ConstantesNombre.DEUX;
        int nombreParLequelOnDivise = ConstantesNombre.ZERO;
        
        this.serviceTest.diviserUnNombreParAutre(nombreInitial, nombreParLequelOnDivise);
    }
}