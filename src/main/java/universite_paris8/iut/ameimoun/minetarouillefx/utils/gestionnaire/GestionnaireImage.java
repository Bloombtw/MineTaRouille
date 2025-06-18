package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe utilitaire permettant de faire le lien entre les noms des blocs/items
 * et leurs chemins d’image correspondants.
 */
public class GestionnaireImage {

    /** Map associant les noms des blocs/items à leurs chemins d’image. */
    private static final Map<String, String> itemToImage = new HashMap<>();

    static {
        // --- BLOCS ---
        itemToImage.put("DEFAULT",             Chemin.IMAGE_DEFAULT);
        itemToImage.put("CIEL",                Chemin.IMAGE_CIEL);
        itemToImage.put("PIERRE",              Chemin.IMAGE_PIERRE);
        itemToImage.put("SABLE",               Chemin.IMAGE_SABLE);
        itemToImage.put("TRONC",               Chemin.IMAGE_TRONC);
        itemToImage.put("FEUILLAGE",           Chemin.IMAGE_FEUILLAGE);
        itemToImage.put("TERRE",               Chemin.IMAGE_TERRE);
        itemToImage.put("CIEL_CLAIR",          Chemin.IMAGE_CIEL_CLAIR);
        itemToImage.put("GAY_CIEL",            Chemin.IMAGE_GAY_CIEL);
        itemToImage.put("SABLE_ROUGE",         Chemin.IMAGE_SABLE_ROUGE);
        itemToImage.put("TRANSPARENT",         Chemin.IMAGE_TRANSPARENT);
        itemToImage.put("CIEL_SOMBRE",         Chemin.IMAGE_CIEL_SOMBRE);
        itemToImage.put("CORBEAU",             Chemin.IMAGE_DECORS_CORBEAU);
        itemToImage.put("LUNE",                Chemin.IMAGE_DECORS_LUNE);
        itemToImage.put("LUNE_ZELDA",          Chemin.IMAGE_DECORS_LUNE_ZELDA);
        itemToImage.put("ETOILE",              Chemin.IMAGE_DECORS_ETOILE);
        itemToImage.put("ARBUSTE_MORT",        Chemin.IMAGE_DECORS_ARBUSTE_MORT);
        itemToImage.put("ECHELLE",             Chemin.IMAGE_DECORS_ECHELLE);
        itemToImage.put("FLECHE_VERS_LA_DROITE", Chemin.IMAGE_DECORS_FLECHE_VERS_LA_DROITE);
        itemToImage.put("ESCALIER_DROITE",     Chemin.IMAGE_DECORS_ESCALIER_DROITE);
        itemToImage.put("GRES_CISELE",         Chemin.IMAGE_GRES_CISELE);
        itemToImage.put("GRES_COUPE",          Chemin.IMAGE_GRES_COUPE);
        itemToImage.put("POUSSE_ACACIA",       Chemin.IMAGE_DECORS_POUSSE_ACACIA);
        itemToImage.put("FEUILLAGE_ACACIA",    Chemin.IMAGE_FEUILLAGE_ACACIA);
        itemToImage.put("GRES",                Chemin.IMAGE_GRES);
        itemToImage.put("FEU",                 Chemin.IMAGE_DECORS_FEU);
        itemToImage.put("CACTUS",              Chemin.IMAGE_CACTUS);
        itemToImage.put("NUAGE",               Chemin.IMAGE_NUAGE);
        itemToImage.put("CIEL_VIOLET",         Chemin.IMAGE_CIEL_VIOLET);
        itemToImage.put("NUAGE_PARTIE1",       Chemin.IMAGE_NUAGE_PARTIE1);
        itemToImage.put("NUAGE_PARTIE2",       Chemin.IMAGE_NUAGE_PARTIE2);
        itemToImage.put("NUAGE_PARTIE3",       Chemin.IMAGE_NUAGE_PARTIE3);
        itemToImage.put("TABLE_CRAFT",         Chemin.IMAGE_TABLE_CRAFT);
        itemToImage.put("PLANCHE",             Chemin.IMAGE_PLANCHE);
        itemToImage.put("COFFRE",              Chemin.IMAGE_COFFRE);

        // --- ITEMS ---
        itemToImage.put("ARC",                 Chemin.ITEM_ARC);
        itemToImage.put("EPEE",                Chemin.ITEM_EPEE);
        itemToImage.put("MOUTON_CUIT",         Chemin.ITEM_MOUTON_CUIT);
        itemToImage.put("PELLE",               Chemin.ITEM_PELLE);
        itemToImage.put("PIOCHE",              Chemin.ITEM_PIOCHE);
        itemToImage.put("BATON",               Chemin.ITEM_BATON);
        itemToImage.put("FIL",                 Chemin.ITEM_FIL);
    }

    /**
     * Récupère le chemin d'image associé à un bloc.
     *
     * @param bloc Bloc à afficher.
     * @return Chemin vers l’image correspondante, ou une image par défaut si inconnu.
     */
    public static String getCheminImage(Bloc bloc) {
        if (bloc == null) return Chemin.IMAGE_DEFAULT;
        return itemToImage.getOrDefault(bloc.name(), Chemin.IMAGE_DEFAULT);
    }

    /**
     * Récupère le chemin d'image associé à un item.
     *
     * @param item Item (bloc ou objet).
     * @return Chemin vers l’image correspondante, ou une image par défaut si inconnu.
     */
    public static String getCheminImage(Item item) {
        String clef;
        if (item.getTypeItem() == Item.TypeItem.BLOC && item.getBloc() != null)
            clef = item.getBloc().name();
        else if (item.getTypeItem() == Item.TypeItem.OBJET && item.getObjet() != null)
            clef = item.getObjet().name();
        else
            clef = item.getNom().toUpperCase();

        return itemToImage.getOrDefault(clef, Chemin.IMAGE_DEFAULT);
    }

}
