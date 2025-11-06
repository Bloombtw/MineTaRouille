package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Gère les items présents au sol dans le monde.
 * S'occupe de la gravité, des collisions avec le sol, du ramassage par le joueur,
 * et de l'affichage des items via VueItem.
 */
public class GestionnaireItem {
    private final List<Item> itemsAuSol = new ArrayList<>();
    private final List<VueItem> vuesItemsAuSol = new ArrayList<>();
    private final Group worldGroup;

    public GestionnaireItem(Group worldGroup) {
        this.worldGroup = worldGroup;
    }

    public void update(Joueur joueur, VueInventaire vueInventaire) {
        // Appliquer gravité et collisions
        for (Item item : itemsAuSol) {
            appliquerGravite(item);
            gererCollisionSol(item);
        }

        // Ramassage délégué au joueur
        List<Item> itemsARamasser = new ArrayList<>();
        for (Item item : itemsAuSol) {
            double distance = Math.hypot(joueur.getX() - item.getX(), joueur.getY() - item.getY());
            if (distance <= 30 && joueur.ramasserItem(item)) {
                itemsARamasser.add(item);
            }
        }

        // Suppression des items ramassés
        for (Item item : itemsARamasser) {
            int index = itemsAuSol.indexOf(item);
            if (index >= 0) {
                worldGroup.getChildren().remove(vuesItemsAuSol.get(index).getImageView());
                itemsAuSol.remove(index);
                vuesItemsAuSol.remove(index);
            }
        }

        vueInventaire.mettreAJourAffichageInventaire();
    }

    private void gererCollisionSol(Item item) {
        int x = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int y = (int)((item.getY() + Constantes.TAILLE_ITEM) / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(x, y)) {
            item.setY((y * Constantes.TAILLE_TUILE) - (Constantes.TAILLE_ITEM / 2.0));
        }
    }

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

    public void jeterItemSelectionne(Joueur joueur, VueInventaire vueInventaire) {
        Inventaire inventaire = joueur.getInventaire();
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);
        if (item == null) return;

        inventaire.retirerItem(idx);
        vueInventaire.mettreAJourAffichageInventaire();

        Item dropItem = (item.getTypeItem() == Item.TypeItem.BLOC)
                ? new Item(item.getBloc())
                : new Item(item.getObjet());

        double joueurCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double joueurBasY = joueur.getY() + Constantes.TAILLE_PERSO;
        int direction = joueur.estRegardADroite() ? 1 : -1;

        int xTuileSpawn = (int) ((joueurCenterX + direction * Constantes.TAILLE_TUILE) / Constantes.TAILLE_TUILE);
        int yTuileSpawn = (int) (joueurBasY / Constantes.TAILLE_TUILE) - 1;

        spawnItemAuSol(dropItem, xTuileSpawn, yTuileSpawn);
    }

    public void consommerMoutonCuitSelectionne(Joueur joueur, VueInventaire vueInventaire) {
        Inventaire inventaire = joueur.getInventaire();
        int idx = inventaire.getSelectedIndex();
        Item item = inventaire.getItem(idx);
        if (item == null) return;

        if (item.getTypeItem() != Item.TypeItem.OBJET || item.getObjet() != Objet.MOUTON_CUIT) return;

        double vieActuelle = joueur.getVie().vieActuelleProperty().get();
        double vieMax = joueur.getVie().getVieMax();

        if (vieActuelle >= vieMax) return;

        joueur.getVie().soigner(20);
        inventaire.retirerItem(idx);
        vueInventaire.mettreAJourAffichageInventaire();
    }

    public List<Item> getItemsAuSol() {
        return itemsAuSol;
    }

}
