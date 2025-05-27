package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import javafx.scene.image.Image;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import java.util.HashMap;
import java.util.Map;

public class GestionnaireImage {

    private static final Map<String, String> itemToImage = new HashMap<>();

    static {
        // --- BLOCS ---
        itemToImage.put("DEFAULT",         "/img/default.png");
        itemToImage.put("CIEL",            "/img/blocs/traversable/ciel.png");
        itemToImage.put("PIERRE",          "/img/blocs/solide/pierre.png");
        itemToImage.put("SABLE",           "/img/blocs/solide/sable.png");
        itemToImage.put("TRONC",           "/img/blocs/solide/tronc.png");
        itemToImage.put("FEUILLAGE",       "/img/blocs/solide/feuillage.png");
        itemToImage.put("TERRE",           "/img/blocs/solide/terre.png");
        itemToImage.put("CIEL_CLAIR",      "/img/blocs/traversable/ciel_clair.png");
        itemToImage.put("GAY_CIEL",        "/img/blocs/traversable/gayciel.png");
        itemToImage.put("SABLE_ROUGE",     "/img/blocs/solide/sable_rouge.png");
        itemToImage.put("TRANSPARENT",     "/img/blocs/traversable/transparent.png");
        itemToImage.put("CIEL_SOMBRE",     "/img/blocs/traversable/ciel_sombre.png");
        itemToImage.put("CORBEAU",         "/img/decors/corbeau.png");
        itemToImage.put("LUNE",            "/img/decors/lune.png");
        itemToImage.put("LUNE_ZELDA",      "/img/decors/lune_zelda.jpg");
        itemToImage.put("ETOILE",          "/img/decors/etoile.png");
        itemToImage.put("ARBUSTE_MORT",    "/img/decors/arbuste_mort.png");
        itemToImage.put("ECHELLE",         "/img/decors/echelle.png");
        itemToImage.put("FLECHE_VERS_LA_DROITE", "/img/decors/flecheVersDroite.png");
        itemToImage.put("ESCALIER_DROITE", "/img/decors/escalier_Droite.png");
        itemToImage.put("GRES_CISELE",     "/img/blocs/solide/gres_cisele.png");
        itemToImage.put("GRES_COUPE",      "/img/blocs/solide/gres_coupe.png");
        itemToImage.put("POUSSE_ACACIA",   "/img/decors/pousse_acacia.png");
        itemToImage.put("FEUILLAGE_ACACIA","/img/blocs/solide/feuillage_acacia.png");
        itemToImage.put("GRES",            "/img/blocs/solide/gres.png");
        itemToImage.put("FEU",             "/img/decors/feu.png");
        itemToImage.put("CACTUS",          "/img/blocs/solide/cactus.png");
        itemToImage.put("NUAGE",           "/img/blocs/traversable/nuage.png");
        itemToImage.put("CIEL_VIOLET",     "/img/blocs/traversable/ciel_violet.png");
        itemToImage.put("NUAGE_PARTIE1",   "/img/blocs/traversable/nuage_partie1.png");
        itemToImage.put("NUAGE_PARTIE2",   "/img/blocs/traversable/nuage_partie2.png");
        itemToImage.put("NUAGE_PARTIE3",   "/img/blocs/traversable/nuage_partie3.png");
        // --- ITEMS ---
        itemToImage.put("ARC",            "/img/items/arc.png");
        itemToImage.put("EPEE",       "/img/items/epee.png");
        itemToImage.put("MOUTON_CUIT", "/img/items/mouton_cuit.png");
        itemToImage.put("PELLE", "/img/items/pelle.png");
        itemToImage.put("PIOCHE", "/img/items/pioche.png");
    }

    public static String getCheminImage(Item item) {
        String clef = null;
        if (item.getTypeItem() == Item.TypeItem.BLOC && item.getBloc() != null)
            clef = item.getBloc().name();
        else if (item.getTypeItem() == Item.TypeItem.OBJET && item.getObjet() != null)
            clef = item.getObjet().name();
        else
            clef = item.getNom().toUpperCase();

        return itemToImage.getOrDefault(clef, "/img/default.jpg");
    }

}
