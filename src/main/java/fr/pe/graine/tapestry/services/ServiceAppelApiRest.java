package fr.pe.graine.tapestry.services;

import java.io.IOException;
import java.util.List;

import org.restlet.data.MediaType;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.pe.graine.tapestry.beans.FicheService;

public class ServiceAppelApiRest {
    
    public FicheService appelRessourceEnregistrementFicheService(FicheService ficheService) {
        ClientResource resource = new ClientResource(
                        "http://localhost:8182/graine-restlet/saisieFicheService/enregistrerUneNouvelleFicheService");
        Gson gson = new GsonBuilder().create();
        Representation responseEntity = resource.post(gson.toJson(ficheService), MediaType.APPLICATION_JSON);
        try {
            String json = responseEntity.getText();
            FicheService ficheServiceRecuperee = gson.fromJson(json, FicheService.class);
            return ficheServiceRecuperee;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public FicheService appelRessourceUneFicheService(String idFicheService) {
        ClientResource resource = new ClientResource("http://localhost:8182/graine-restlet/saisieFicheService/recapituatif?idFicheService="
                        + idFicheService);
        Representation responseEntity = resource.get();
        try {
            String json = responseEntity.getText();
            Gson gson = new GsonBuilder().create();
            FicheService ficheService = gson.fromJson(json, FicheService.class);
            return ficheService;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    
    public List<FicheService> appelRessourceListeDesFichesService() {
        ClientResource resource = new ClientResource("http://localhost:8182/graine-restlet/listeDesFichesServices");
        Representation responseEntity = resource.get();
        try {
            String json = responseEntity.getText();
            Gson gson = new GsonBuilder().create();
            List<FicheService> listeFicheService = gson.fromJson(json, List.class);
            return listeFicheService;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
    }
}