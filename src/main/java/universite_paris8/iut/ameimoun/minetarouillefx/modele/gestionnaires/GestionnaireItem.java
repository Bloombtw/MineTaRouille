package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireItem {
    private final List<Item> itemsAuSol = new ArrayList<>();
    private final List<VueItem> vuesItemsAuSol = new ArrayList<>();
    private final AnchorPane rootPane;

    public GestionnaireItem(AnchorPane rootPane) {
        this.rootPane = rootPane;
    }


    /**
     * Met à jour la position des items au sol, gère la gravité, les collisions avec le sol,
     * et détecte si un joueur ramasse un item.
     *
     * @param joueur Le joueur qui peut ramasser les items.
     * @param inventaire L'inventaire du joueur pour ajouter les items ramassés.
     * @param vueInventaire La vue de l'inventaire pour mettre à jour l'affichage.
     */
    public void update(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
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

    /**
     * Gère la collision d'un item avec le sol, en ajustant sa position si nécessaire.
     *
     * @param item L'item à vérifier pour la collision avec le sol.
     */
    private void gererCollisionSol(Item item) {
        int x = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int y = (int)((item.getY() + Constantes.TAILLE_ITEM) / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(x, y)) {
            item.setY((y * Constantes.TAILLE_TUILE) - (Constantes.TAILLE_ITEM / 2.0));
        }
    }


    /**
     * Détecte si un item est ramassé par un joueur en vérifiant les collisions.
     *
     * @param item L'item à vérifier.
     * @param joueur Le joueur qui peut ramasser l'item.
     * @return true si l'item est ramassé, false sinon.
     */
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

    // Applique la gravité à un item, le fait tomber vers le bas
    private void appliquerGravite(Item item) {
        item.setY(item.getY() + Constantes.GRAVITE * 5);
    }

    /**
     * Fait apparaître un item au sol à une position donnée.
     * @param item L'item à faire apparaître.
     * @param x La position x de l'item.
     * @param y La position y de l'item.
     */
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
     * Gère la chute d'un item et met à jour la vue de la carte.
     * Si l'item est non nul, il est ajouté au sol à la position spécifiée.
     * Met à jour l'affichage du bloc cassé et du décor au-dessus si nécessaire.
     *
     * @param item L'item à faire tomber.
     * @param x La position x de l'item.
     * @param y La position y de l'item.
     * @param couche La couche du bloc (0 pour sol, 1 pour décor).
     * @param vueCarte La vue de la carte pour mettre à jour l'affichage.
     */
    public void dropItemEtMettreAJour(Item item, int x, int y, int couche, VueCarte vueCarte) {
        if (item != null) {
            vueCarte.mettreAJourAffichage(x, y); // le bloc cassé
            if (couche == 1 && y - 1 >= 0) {
                vueCarte.mettreAJourAffichage(x, y - 1); // décor au-dessus si sol cassé
            }
            spawnItemAuSol(item, x, y);
        }
    }
}
