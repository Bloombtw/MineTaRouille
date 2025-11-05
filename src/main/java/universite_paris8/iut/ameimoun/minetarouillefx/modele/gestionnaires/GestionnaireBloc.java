package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
public class GestionnaireBloc {

    public static Item casserBlocEtDonnerItem(int couche, int px, int py, Joueur joueur) {
        if (!estADistanceAutorisee(joueur, px, py)) return null;

        Bloc blocCasse = getBlocCasse(couche, px, py);
        if (blocCasse != null && blocCasse.estSolide()) {
            return new Item(blocCasse, 1);
        }
        return null;
    }

    public static boolean placerBloc(
            Carte carte,
            Inventaire inventaire,
            int indexItem,
            int couche,
            int px,
            int py,
            Joueur joueur
    ) {
        if (!peutPlacerBloc(carte, inventaire, indexItem, couche, px, py, joueur)) return false;

        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;

        Item item = inventaire.getItem(indexItem);
        Bloc bloc = item.getBloc();

        carte.getTerrain()[couche][y][x] = bloc;
        item.ajouterQuantite(-1);
        if (item.getQuantite() <= 0) {
            inventaire.getSlots().set(indexItem, null);
        }
        return true;
    }

    private static boolean peutPlacerBloc(
            Carte carte,
            Inventaire inventaire,
            int indexItem,
            int couche,
            int px,
            int py,
            Joueur joueur
    ) {
        return estPositionValidePourPlacement(carte, couche, px, py)
                && !hitboxSurBloc(joueur, px, py)
                && estADistanceAutorisee(joueur, px, py)
                && estItemBlocValide(inventaire, indexItem);
    }

    // 🔍Méthodes de vérification🔍

    private static boolean estPositionValidePourPlacement(Carte carte, int couche, int px, int py) {
        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;
        if (!carte.estDansLaMap(x, y)) return false;
        Bloc blocExistant = carte.getTerrain()[couche][y][x];
        return blocExistant == null || !blocExistant.estSolide();
    }

    private static boolean estItemBlocValide(Inventaire inventaire, int indexItem) {
        Item item = inventaire.getItem(indexItem);
        return item != null
                && item.getTypeItem() == Item.TypeItem.BLOC
                && item.getQuantite() > 0
                && item.getBloc() != null;
    }

    public static boolean estADistanceAutorisee(Joueur joueur, int px, int py) {
        double joueurCentreX = joueur.getX() + Constantes.TAILLE_PERSO / 2.0;
        double joueurCentreY = joueur.getY() + Constantes.TAILLE_PERSO / 2.0;
        double distance = Math.hypot(joueurCentreX - px, joueurCentreY - py);
        return distance <= Constantes.DISTANCE_MAX_CASSAGE_BLOC * Constantes.TAILLE_TUILE;
    }

    public static boolean hitboxSurBloc(Joueur joueur, int pxBloc, int pyBloc) {
        double pxJoueur = joueur.getX();
        double pyJoueur = joueur.getY();
        double taille = Constantes.TAILLE_PERSO;
        return pxJoueur + taille > pxBloc && pxJoueur < pxBloc + Constantes.TAILLE_TUILE
                && pyJoueur + taille > pyBloc && pyJoueur < pyBloc + Constantes.TAILLE_TUILE;
    }

    public static Bloc getBloc(int couche, int px, int py) {
        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;
        return Carte.getInstance().getTerrain()[couche][y][x];
    }

    private static Bloc getBlocCasse(int couche, int px, int py) {
        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;
        return Carte.getInstance().casserBloc(couche, x, y);
    }
}
