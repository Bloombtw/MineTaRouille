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

    private void gererCollisionSol(Item item) {
        double futureY = item.getY() + Constantes.TAILLE_ITEM;
        int xBloc = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int yBloc = (int)(futureY / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(xBloc, yBloc)) {
            item.setY(yBloc * Constantes.TAILLE_TUILE - Constantes.TAILLE_ITEM);
        }
    }

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

    private void appliquerGravite(Item item) {
        item.setY(item.getY() + Constantes.GRAVITE * 5);
    }

    public void spawnItemAuSol(Item item, double px, double py) {
        item.setX(px);
        item.setY(py);
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

        double joueurCenterX = joueur.getX() + Constantes.TAILLE_PERSO / 2.0;
        double joueurBasY = joueur.getY() + Constantes.TAILLE_PERSO;
        int direction = joueur.estRegardADroite() ? 1 : -1;

        double px = joueurCenterX + direction * Constantes.TAILLE_TUILE;
        double py = joueurBasY - Constantes.TAILLE_ITEM;

        spawnItemAuSol(dropItem, px, py);
    }

    public void consommerMoutonCuitSelectionne(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire) {
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
}