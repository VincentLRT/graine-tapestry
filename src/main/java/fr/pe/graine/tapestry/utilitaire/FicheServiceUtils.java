package fr.pe.graine.tapestry.utilitaire;

public class FicheServiceUtils {
    
    public static String genererUnIdFicheServiceValide(String nomService) {
        String id = nomService.replaceAll("é|è|ê|ë", "e");
        id = id.replaceAll("à|â|ä", "a");
        id = id.replaceAll("ù|ü|û", "u");
        id = gererLesMajusculesDeLId(id);
        id = id.replaceAll("[\\W]|_", "");
        return id;
    }
    
    public static String gererLesMajusculesDeLId(String id) {
        char espace = ' ';
        char[] char_table = id.toCharArray();
        for (int i = 0; i < char_table.length; i++) {
            if (char_table[i] == espace) {
                char_table[i + 1] = Character.toUpperCase(char_table[i + 1]);
            }
        }
        char_table[0] = Character.toLowerCase(char_table[0]);
        id = new String(char_table);
        return id;
    }
}
