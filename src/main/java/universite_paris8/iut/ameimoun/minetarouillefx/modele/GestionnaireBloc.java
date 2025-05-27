package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class GestionnaireBloc {

    // Renvoie un Item (Bloc) correspondant au bloc cassé (ou null si rien à casser)
    public static Item casserBlocEtDonnerItem(int couche, int x, int y) {
        Bloc blocCasse = Carte.getInstance().casserBloc(couche, x, y);
        if (blocCasse != null && blocCasse.estSolide()) {
            // On crée un Item de type Bloc, quantité 1 (un bloc tombé au sol)
            return new Item(blocCasse, 1);
        }
        return null;
    }

    // Place un bloc à la position (x, y) dans la couche spécifiée
    public static boolean placerBloc(
            Carte carte,
            Inventaire inventaire,
            int indexItem,
            int couche,
            int x,
            int y,
            Joueur joueur // Pour avoir la hitbox de facon à ne pas placer un bloc sur le joueur
    ) {
        if (!carte.estDansLaMap(x, y)) return false;
        if (carte.getTerrain()[couche][y][x] != null && carte.getTerrain()[couche][y][x].estSolide()) return false;
        if (hitboxSurBloc(joueur, x, y)) return false; // Ne pas placer un bloc sur le joueur

        Item itemSelectionne = inventaire.getItem(indexItem);

        if (itemSelectionne == null || itemSelectionne.getTypeItem() != Item.TypeItem.BLOC || itemSelectionne.getQuantite() <= 0)
            return false;

        Bloc bloc = itemSelectionne.getBloc();
        if (bloc == null) return false; // Sécurité

        carte.getTerrain()[couche][y][x] = bloc;
        itemSelectionne.ajouterQuantite(-1);
        if (itemSelectionne.getQuantite() <= 0) {
            inventaire.getSlots().set(indexItem, null);
        }
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
