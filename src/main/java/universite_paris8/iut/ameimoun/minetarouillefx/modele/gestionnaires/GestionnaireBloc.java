package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Fournit des méthodes pour casser et placer des blocs dans la carte.
 * Vérifie les distances autorisées pour les actions du joueur et les collisions
 * avec la hitbox du joueur. Permet de récupérer les blocs ou de placer des items
 * au sol.
 */
public class GestionnaireBloc {

    // Renvoie un Item (Bloc) correspondant au bloc cassé (ou null si rien à casser)
    public static Item casserBlocEtDonnerItem(int couche, int x, int y, Joueur joueur) {
        if (!estADistanceAutorisee(joueur, x, y)) return null;
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
            // On crée un Item de type Bloc, quantité 1 (un bloc tombé au sol)
            return new Item(blocCasse, 1);
        }
        return null;
    }

    public static boolean estADistanceAutorisee(Joueur joueur, int x, int y) {
        // Distance euclidienne entre le joueur et le bloc
        int joueurX = (int) ((joueur.getX() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        double distance = Math.sqrt(Math.pow(joueurX - x, 2) + Math.pow(joueurY - y, 2));
        return distance <= Constantes.DISTANCE_MAX_CASSAGE_BLOC;
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
        Bloc bloc = BlocFactory.creerBloc(itemSelectionne.getBloc().getId());

        carte.getTerrain()[couche][y][x] = bloc;
        itemSelectionne.ajouterQuantite(-1);
        if (itemSelectionne.getQuantite() <= 0) {
            inventaire.getSlots().set(indexItem, null);
        }
        return true;
    }

    private static boolean estBlocPlacable(Carte carte, int couche, int x, int y) {
        if (!carte.estDansLaMap(x, y)) return false;
        Bloc blocExistant = carte.getTerrain()[couche][y][x];
        return blocExistant == null || !blocExistant.estSolide();
    }

    private static boolean estItemValidePourPlacement(Item item) {
        return item != null
                && item.getTypeItem() == Item.TypeItem.BLOC
                && item.getQuantite() > 0
                && item.getBloc() != null;
    }

    private static boolean peutPlacerBloc(Carte carte, Inventaire inventaire, int indexItem, int couche, int x, int y, Joueur joueur) {
        if (!estBlocPlacable(carte, couche, x, y)) return false;
        if (hitboxSurBloc(joueur, x, y)) return false;
        if (!estADistanceAutorisee(joueur, x, y)) return false;

        Item item = inventaire.getItem(indexItem);
        return estItemValidePourPlacement(item);
    }


    public static boolean hitboxSurBloc(Joueur joueur, int x, int y) {
        int tailleTuile = Constantes.TAILLE_TUILE;
        double px = joueur.getX();
        double py = joueur.getY();
        double taille = Constantes.TAILLE_PERSO;
        double blocX = x * Constantes.TAILLE_TUILE;
        double blocY = y * Constantes.TAILLE_TUILE;
        return px + taille > blocX && px < blocX + Constantes.TAILLE_TUILE
                && py + taille > blocY && py < blocY + Constantes.TAILLE_TUILE;
    }

    public static Bloc getBloc(int couche, int x, int y) {
        return Carte.getInstance().getTerrain()[couche][y][x];
    }
}
