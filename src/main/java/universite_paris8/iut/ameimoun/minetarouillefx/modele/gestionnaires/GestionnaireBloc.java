package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class GestionnaireBloc {

    // Renvoie un Item (Bloc) correspondant au bloc cassé (ou null si rien à casser)
    public static Item casserBlocEtDonnerItem(int couche, int x, int y, Joueur joueur) {
        if (!estADistanceAutorisee(joueur, x, y, 3)) return null;
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
            // On crée un Item de type Bloc, quantité 1 (un bloc tombé au sol)
            return new Item(blocCasse, 1);
        }
        return null;
    }

    public static boolean estADistanceAutorisee(Joueur joueur, int x, int y, int distanceMax) {//Type personnage pour pouvoir l'utilisé pour les attaques...
        // Distance euclidienne entre le joueur et le bloc
        int joueurX = (int) ((joueur.getX() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        double distance = Math.sqrt(Math.pow(joueurX - x, 2) + Math.pow(joueurY - y, 2));
        return distance <= distanceMax;
    }


    // Place un bloc à la position (x, y) dans la couche spécifiée
    public static boolean placerBloc(
            Carte carte,
            Inventaire inventaire,
            int indexItem,
            int couche,
            int x,
            int y,
            Joueur joueur
    ) {
        if (!peutPlacerBloc(carte, inventaire, indexItem, couche, x, y, joueur)) return false;

        Item itemSelectionne = inventaire.getItem(indexItem);
        Bloc bloc = itemSelectionne.getBloc();

        carte.getTerrain()[couche][y][x] = bloc;
        itemSelectionne.ajouterQuantite(-1);
        if (itemSelectionne.getQuantite() <= 0) {
            inventaire.getSlots().set(indexItem, null);
        }
        return true;
    }

    private static boolean peutPlacerBloc(
            Carte carte,
            Inventaire inventaire,
            int indexItem,
            int couche,
            int x,
            int y,
            Joueur joueur
    ) {
        if (!carte.estDansLaMap(x, y)) return false;
        Bloc blocExistant = carte.getTerrain()[couche][y][x];
        if (blocExistant != null && blocExistant.estSolide()) return false;
        if (hitboxSurBloc(joueur, x, y)) return false;
        if (!estADistanceAutorisee(joueur, x, y, 3)) return false;

        Item itemSelectionne = inventaire.getItem(indexItem);
        if (itemSelectionne == null) return false;
        if (itemSelectionne.getTypeItem() != Item.TypeItem.BLOC) return false;
        if (itemSelectionne.getQuantite() <= 0) return false;
        if (itemSelectionne.getBloc() == null) return false;
        return true;
    }

    public static boolean hitboxSurBloc(Joueur joueur, int x, int y) {
        double px = joueur.getX();
        double py = joueur.getY();
        double taille = Constantes.TAILLE_PERSO;
        double blocX = x * Constantes.TAILLE_TUILE;
        double blocY = y * Constantes.TAILLE_TUILE;
        return px + taille > blocX && px < blocX + Constantes.TAILLE_TUILE
                && py + taille > blocY && py < blocY + Constantes.TAILLE_TUILE;
    }
}
