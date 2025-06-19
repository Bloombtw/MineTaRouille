package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GestionnaireItem {
    private final List<Item> itemsAuSol = new ArrayList<>();
    private final List<VueItem> vuesItemsAuSol = new ArrayList<>();
    private final Group worldGroup;

    public GestionnaireItem(Group worldGroup) {
        this.worldGroup = worldGroup;
    }

    // Met à jour la position des items au sol, applique la gravité et gère les collisions.
    public void update(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        Iterator<Item> itemIterator = itemsAuSol.iterator();
        Iterator<VueItem> vueIterator = vuesItemsAuSol.iterator();

        while (itemIterator.hasNext() && vueIterator.hasNext()) {
            Item item = itemIterator.next();
            VueItem vue = vueIterator.next();

            appliquerGravite(item);
            gererCollisionSol(item);

            if (detecterRamassage(item, joueur)) {
                inventaire.ajouterItem(item);
                vueInventaire.mettreAJourAffichageInventaire();

                worldGroup.getChildren().remove(vue.getImageView());
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

    public void spawnItemAuSol(Item item, int tuileX, int tuileY) {
        double x = tuileX * Constantes.TAILLE_TUILE + (Constantes.TAILLE_TUILE - Constantes.TAILLE_ITEM) / 2.0;
        double y = (tuileY + 1) * Constantes.TAILLE_TUILE - Constantes.TAILLE_ITEM - Constantes.TAILLE_TUILE / 2.0;

        item.setX(x);
        item.setY(y);

        VueItem vue = new VueItem(item);

        itemsAuSol.add(item);
        vuesItemsAuSol.add(vue);
        worldGroup.getChildren().add(vue.getImageView());
    }

    public void jeterItemSelectionne(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);
        if (item == null) return;

        inventaire.retirerItem(idx);
        vueInventaire.mettreAJourAffichageInventaire();

        Item dropItem = (item.getTypeItem() == Item.TypeItem.BLOC)
                ? new Item(item.getBloc())
                : new Item(item.getObjet());

        // Position du centre du joueur
        double joueurCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double joueurBasY = joueur.getY() + Constantes.TAILLE_PERSO;

        // Direction : +1 à droite, -1 à gauche
        int direction = joueur.estRegardADroite() ? 1 : -1;

        // Calcul de la tuile devant le joueur
        int xTuileSpawn = (int) ((joueurCenterX + direction * Constantes.TAILLE_TUILE) / Constantes.TAILLE_TUILE);
        int yTuileSpawn = (int) (joueurBasY / Constantes.TAILLE_TUILE) - 1;

        spawnItemAuSol(dropItem, xTuileSpawn, yTuileSpawn);
    }

    public void consommerMoutonCuitSelectionne(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);
        if (item == null) {
            return;
        }

        if (item.getTypeItem() != Item.TypeItem.OBJET || item.getObjet() != Objet.MOUTON_CUIT) {
            return;
        }

        double vieActuelle = joueur.getVie().vieActuelleProperty().get();
        double vieMax = joueur.getVie().getVieMax();

        if (vieActuelle >= vieMax) {
            return;
        }

        joueur.getVie().soigner(20);
        inventaire.retirerItem(idx);
        vueInventaire.mettreAJourAffichageInventaire();
    }
}