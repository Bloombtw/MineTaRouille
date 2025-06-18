package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class GestionnaireBloc {


    /** Casse un bloc à la position (x, y) dans la couche spécifiée.
     * Si le bloc est solide, il est cassé et un Item de type Bloc est créé.
     * @param couche La couche du bloc à casser.
     * @param x La coordonnée x du bloc.
     * @param y La coordonnée y du bloc.
     * @param joueur Le joueur qui casse le bloc.
     * @return Un Item de type Bloc si le bloc a été cassé, sinon null.
     */
    public static Item casserBlocEtDonnerItem(int couche, int x, int y, Joueur joueur) {
        if (!estADistanceAutorisee(joueur, x, y)) return null;
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
            // On crée un Item de type Bloc, quantité 1 (un bloc tombé au sol)
            return new Item(blocCasse, 1);
        }
        return null;
    }

    /** Vérifie si le joueur peut casser un bloc à la position (x, y) dans la couche spécifiée.
     * @param joueur Le joueur qui tente de casser le bloc.
     * @param x La coordonnée x du bloc.
     * @param y La coordonnée y du bloc.
     * @return true si le joueur peut casser le bloc, false sinon.
     */
    public static boolean estADistanceAutorisee(Joueur joueur, int x, int y) {
        // Distance euclidienne entre le joueur et le bloc
        int joueurX = (int) ((joueur.getX() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        int joueurY = (int) ((joueur.getY() + Constantes.TAILLE_PERSO / 2) / Constantes.TAILLE_TUILE);
        double distance = Math.sqrt(Math.pow(joueurX - x, 2) + Math.pow(joueurY - y, 2));
        return distance <= Constantes.DISTANCE_MAX_CASSAGE_BLOC;
    }


    /** Place un bloc à la position (x, y) dans la couche spécifiée.
     * @param carte La carte sur laquelle placer le bloc.
     * @param inventaire L'inventaire du joueur.
     * @param indexItem L'index de l'item dans l'inventaire.
     * @param couche La couche où placer le bloc.
     * @param x La coordonnée x où placer le bloc.
     * @param y La coordonnée y où placer le bloc.
     * @param joueur Le joueur qui place le bloc.
     * @return true si le bloc a été placé, false sinon.
     */
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

    /** Vérifie si le joueur peut placer un bloc à la position (x, y) dans la couche spécifiée.
     * @param carte La carte sur laquelle placer le bloc.
     * @param inventaire L'inventaire du joueur.
     * @param indexItem L'index de l'item dans l'inventaire.
     * @param couche La couche où placer le bloc.
     * @param x La coordonnée x où placer le bloc.
     * @param y La coordonnée y où placer le bloc.
     * @param joueur Le joueur qui place le bloc.
     * @return true si le bloc peut être placé, false sinon.
     */
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
        if (!estADistanceAutorisee(joueur, x, y)) return false;

        Item itemSelectionne = inventaire.getItem(indexItem);
        if (itemSelectionne == null) return false;
        if (itemSelectionne.getTypeItem() != Item.TypeItem.BLOC) return false;
        if (itemSelectionne.getQuantite() <= 0) return false;
        if (itemSelectionne.getBloc() == null) return false;
        return true;
    }

    /** Métode empéchant de placer un bloc sur un joueur.
     * @param joueur Le joueur dont on vérifie la hitbox.
     * @param x La coordonnée x du bloc.
     * @param y La coordonnée y du bloc.
     * @return true si la hitbox du joueur est sur le bloc, false sinon.
     */
    public static boolean hitboxSurBloc(Joueur joueur, int x, int y) {
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
