package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
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

    // Met à jour la position des items au sol, applique la gravité et gère les collisions.
    // Utilise les méthodes appliquerGravite, gererCollisionSol et detecterRamassage.
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
                vueInventaire.mettreAJourAffichage();

                rootPane.getChildren().remove(vue.getImageView());
                itemIterator.remove();
                vueIterator.remove();
            }
        }
    }

    // Gère la collision d'un item avec le sol
    private void gererCollisionSol(Item item) {
        int x = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int y = (int)((item.getY() + Constantes.TAILLE_ITEM) / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(x, y)) {
            item.setY((y * Constantes.TAILLE_TUILE) - (Constantes.TAILLE_ITEM / 2.0));
        }
    }

    // Détecte si un item est ramassé par le joueur
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

    // fait spawn un item au sol à la position (x, y)
    public void spawnItemAuSol(Item item, int x, int y) {
        item.setX(x * Constantes.TAILLE_TUILE);
        item.setY(y * Constantes.TAILLE_TUILE);

        VueItem vue = new VueItem(item);
        vue.updatePosition(item);

        itemsAuSol.add(item);
        vuesItemsAuSol.add(vue);
        rootPane.getChildren().add(vue.getImageView());
    }

}
