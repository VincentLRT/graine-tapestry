package pages;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.apache.tapestry5.ioc.Messages;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fr.pe.graine.tapestry.pages.PageDErreurs;

@RunWith(MockitoJUnitRunner.class)
public class PageDErreursTest {

    @InjectMocks
    private PageDErreurs pageDErreurTeste;

    private String url;
    
    @Mock
    private Messages messages;
    
    @Test
    public void verifierOnActivateConditionTrue() {
        this.url = "erreur-nom-service-existant";
        when(this.messages.contains(this.url)).thenReturn(true);
        when(this.messages.get(this.url)).thenReturn("La fiche service demandée n'existe pas !");

        this.pageDErreurTeste.onActivate(this.url);
        
        assertThat(this.pageDErreurTeste.getMessageDErreur()).isEqualTo("La fiche service demandée n'existe pas !");
    }

    @Test
    public void verifierOnActivateConditionFalse() {
        this.url = "erreur-nom-service-existant-faux";
        when(this.messages.contains(this.url)).thenReturn(false);
        when(this.messages.get("erreur-inexistante")).thenReturn("Erreur non trouvée !");
        
        this.pageDErreurTeste.onActivate(this.url);
        
        assertThat(this.pageDErreurTeste.getMessageDErreur()).isEqualTo("Erreur non trouvée !");
    }
}
