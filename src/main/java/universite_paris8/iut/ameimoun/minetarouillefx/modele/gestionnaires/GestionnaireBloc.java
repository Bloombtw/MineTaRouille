package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class GestionnaireBloc {

    public static Item casserBlocEtDonnerItem(int couche, int px, int py, Joueur joueur) {
        if (!estADistanceAutorisee(joueur, px, py)) return null;
        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
            return new Item(blocCasse, 1);
        }
        return null;
    }

    public static boolean estADistanceAutorisee(Joueur joueur, int px, int py) {
        double joueurCentreX = joueur.getX() + Constantes.TAILLE_PERSO / 2.0;
        double joueurCentreY = joueur.getY() + Constantes.TAILLE_PERSO / 2.0;
        double distance = Math.sqrt(Math.pow(joueurCentreX - px, 2) + Math.pow(joueurCentreY - py, 2));
        return distance <= Constantes.DISTANCE_MAX_CASSAGE_BLOC * Constantes.TAILLE_TUILE;
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
            int px,
            int py,
            Joueur joueur
    ) {
        int x = px / Constantes.TAILLE_TUILE;
        int y = py / Constantes.TAILLE_TUILE;

        if (!carte.estDansLaMap(x, y)) return false;
        Bloc blocExistant = carte.getTerrain()[couche][y][x];
        if (blocExistant != null && blocExistant.estSolide()) return false;
        if (hitboxSurBloc(joueur, px, py)) return false;
        if (!estADistanceAutorisee(joueur, px, py)) return false;

        Item itemSelectionne = inventaire.getItem(indexItem);
        if (itemSelectionne == null) return false;
        if (itemSelectionne.getTypeItem() != Item.TypeItem.BLOC) return false;
        if (itemSelectionne.getQuantite() <= 0) return false;
        if (itemSelectionne.getBloc() == null) return false;
        return true;
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
}
