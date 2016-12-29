package fr.pe.graine.tapestry.utilitaire;

public class FicheServiceUtils {
    
    public static String genererUnIdFicheServiceValide(String nomService) {
        String id = nomService.replaceAll("é|è|ê|ë", "e");
        id = id.replaceAll("à|â|ä", "a");
        id = id.replaceAll("ù|ü|û", "u");
        id = id.replaceAll("[\\W]|_", "");
        return id;
    }
}
