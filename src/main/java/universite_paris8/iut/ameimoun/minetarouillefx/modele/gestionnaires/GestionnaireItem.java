package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * La classe GestionnaireItem gère les interactions avec les items au sol.
 * Elle permet de mettre à jour leur position, de gérer les collisions, de les ramasser, et de les faire apparaître.
 */
public class GestionnaireItem {
    private final List<Item> itemsAuSol = new ArrayList<>();
    private final List<VueItem> vuesItemsAuSol = new ArrayList<>();
    private final AnchorPane rootPane;

    public GestionnaireItem(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }

    /**Met à jour la position des items au sol, applique la gravité et gère les collisions.
     *
     * Utilise les méthodes appliquerGravite, gererCollisionSol et detecterRamassage.*/
    public void miseAJourItemAuSol(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        Iterator<Item> itemIterator = itemsAuSol.iterator();
        Iterator<VueItem> vueIterator = vuesItemsAuSol.iterator();

        while (itemIterator.hasNext() && vueIterator.hasNext()) {
            Item item = itemIterator.next();
            VueItem vue = vueIterator.next();

            appliquerGravite(item);
            gererCollisionSol(item);
            vue.updatePosition(item);

            if (detecterRamassage(item, joueur)) {
                inventaire.ajouterItem(item);
                vueInventaire.mettreAJourAffichageInventaire();

                rootPane.getChildren().remove(vue.getImageView());
                itemIterator.remove();
                vueIterator.remove();
            }
        }
    }

    /** Gère la collision d'un item avec le sol*/
    private void gererCollisionSol(Item item) {
        int x = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int y = (int)((item.getY() + Constantes.TAILLE_ITEM) / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(x, y)) {
            item.setY((y * Constantes.TAILLE_TUILE) - (Constantes.TAILLE_ITEM / 2.0));
        }
    }

    /** Détecte si un item est ramassé par le joueur*/
    private boolean detecterRamassage(Item item, Joueur joueur) {
        double itemGauche = item.getX();
        double itemDroite = item.getX() + Constantes.TAILLE_ITEM;
        double itemHaut = item.getY();
        double itemBas = item.getY() + Constantes.TAILLE_ITEM;

        double joueurGauche = joueur.getX();
        double joueurDroite = joueur.getX() + Constantes.TAILLE_PERSO;
        double joueurHaut = joueur.getY();
        double joueurBas = joueur.getY() + Constantes.TAILLE_PERSO;

        boolean collisionX = itemDroite > joueurGauche && itemGauche < joueurDroite;
        boolean collisionY = itemBas > joueurHaut && itemHaut < joueurBas;

        return collisionX && collisionY;
    }

    /** Applique la gravité à un item, le fait tomber vers le bas*/
    private void appliquerGravite(Item item) {
        item.setY(item.getY() + Constantes.GRAVITE * 5);
    }

    /** fait spawn un item au sol à la position (x, y)*/
    public void spawnItemAuSol(Item item, int x, int y) {
        item.setX(x * Constantes.TAILLE_TUILE);
        item.setY(y * Constantes.TAILLE_TUILE);

        VueItem vue = new VueItem(item);
        vue.updatePosition(item);

        itemsAuSol.add(item);
        vuesItemsAuSol.add(vue);
        rootPane.getChildren().add(vue.getImageView());
    }

    /**
     * Calcule la tuile cible où un item doit être jeté en fonction de la position et de la direction du joueur.
     *
     * @param joueur Le joueur qui jette l'item.
     * @return Un tableau contenant les coordonnées x et y de la tuile cible.
     */
    private int[] calculerTuileCible(Joueur joueur) {
        double joueurCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        int centerTileX = (int) (joueurCenterX / Constantes.TAILLE_TUILE);

        double yPixelPieds = joueur.getY() + Constantes.TAILLE_PERSO - 1;
        int playerTileY = (int) (yPixelPieds / Constantes.TAILLE_TUILE);

        int direction = joueur.estRegardADroite() ? +1 : -1;
        int xTuileSpawn = centerTileX + direction;
        int yTuileSpawn = playerTileY - 1;

        return new int[]{xTuileSpawn, yTuileSpawn};
    }

    /**
     * Crée un nouvel item à partir de l'item sélectionné dans l'inventaire.
     *
     * @param item L'item sélectionné.
     * @return Un nouvel item correspondant à l'item sélectionné.
     */
    private Item creerItemDrop(Item item) {
        if (item.getTypeItem() == Item.TypeItem.BLOC) {
            return new Item(item.getBloc());
        } else {
            return new Item(item.getObjet());
        }
    }

    private void retirerItemInventaire(Inventaire inventaire, VueInventaire vueInventaire, int idx) {
        int quantite = inventaire.getItem(idx).getQuantite();
        Item item = inventaire.getItem(idx);
        inventaire.retirer(item,quantite);
        vueInventaire.mettreAJourAffichageInventaire();
    }

    /**
     * Jette l'item sélectionné par le joueur à une position calculée.
     *
     * @param joueur Le joueur qui jette l'item.
     * @param inventaire L'inventaire du joueur contenant l'item à jeter.
     * @param vueInventaire La vue de l'inventaire à mettre à jour après le jet.
     */
    public void jeterItemSelectionne(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);
        if (item == null) {
            return;
        }
        retirerItemInventaire(inventaire, vueInventaire, idx);
        Item dropItem = creerItemDrop(item);
        int[] tuileCible = calculerTuileCible(joueur);

        System.out.println("[DEBUG] centreTile = (" + tuileCible[0] + "," + tuileCible[1] + ")");
        spawnItemAuSol(dropItem, tuileCible[0], tuileCible[1]);
    }

    /**
     * Vérifie si l'item sélectionné est un objet consommable de type mouton cuit.
     *
     * @param item L'item sélectionné.
     * @return true si l'item est un mouton cuit, false sinon.
     */
    private boolean verifierItemSelectionne(Item item) {
        return item != null && item.getTypeItem() == Item.TypeItem.OBJET && item.getObjet() == Objet.MOUTON_CUIT;
    }

    /**Vérifie si le joueur peut consommer un objet en fonction de sa vie actuelle.*/
    private boolean verifierVieJoueur(Joueur joueur) {
        double vieActuelle = joueur.getVie().vieActuelleProperty().get();
        double vieMax = joueur.getVie().getVieMax();
        return vieActuelle < vieMax;
    }

    /**Soigne le joueur en consommant un objet et met à jour l'inventaire.*/
    private void soignerJoueur(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire, int idx) {
        joueur.getVie().soigner(20);
        inventaire.retirer(inventaire.getItem(idx), 1);
        vueInventaire.mettreAJourAffichageInventaire();
    }

    /**Consomme l'item sélectionné par le joueur si c'est un mouton cuit et soigne le joueur.*/
    public void consommerMoutonCuitSelectionne(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);

        if (!verifierItemSelectionne(item)) {
            return;
        }

        if (!verifierVieJoueur(joueur)) {
            return;
        }
        soignerJoueur(joueur, inventaire, vueInventaire, idx);
    }

}