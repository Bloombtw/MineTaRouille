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

            appliquerPhysique(item);

            if (itemEstRamasseParJoueur(item, joueur)) {
                ramasserItem(item, vue, itemIterator, vueIterator, inventaire, vueInventaire);
            }
        }
    }

    private void appliquerPhysique(Item item) {
        appliquerGravite(item);
        gererCollisionSol(item);
    }

    private void appliquerGravite(Item item) {
        item.setY(item.getY() + Constantes.GRAVITE * 5);
    }

    private void gererCollisionSol(Item item) {
        double futureY = item.getY() + Constantes.TAILLE_ITEM;
        int xBloc = (int)(item.getX() / Constantes.TAILLE_TUILE);
        int yBloc = (int)(futureY / Constantes.TAILLE_TUILE);
        if (Carte.getInstance().estBlocSolide(xBloc, yBloc)) {
            item.setY(yBloc * Constantes.TAILLE_TUILE - Constantes.TAILLE_ITEM);
        }
    }

    /**
     * Ajout : surcharge acceptant des coordonnées en tuiles.
     * Convertit en pixels puis délègue à la méthode existante.
     */
    public void spawnItemAuSol(Item item, int tileX, int tileY) {
        if (item == null) return;
        double px = tileX * Constantes.TAILLE_TUILE;
        double py = tileY * Constantes.TAILLE_TUILE;
        spawnItemAuSol(item, px, py);
    }

    /**
     * Méthode existante qui prend des coordonnées en pixels.
     */
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

    private boolean itemEstRamasseParJoueur(Item item, Joueur joueur) {
        return zonesSeChevauchent(
                item.getX(), item.getY(), Constantes.TAILLE_ITEM, Constantes.TAILLE_ITEM,
                joueur.getX(), joueur.getY(), Constantes.TAILLE_PERSO, Constantes.TAILLE_PERSO
        );
    }

    private boolean zonesSeChevauchent(double x1, double y1, double w1, double h1,
                                       double x2, double y2, double w2, double h2) {
        boolean chevaucheX = x1 < x2 + w2 && x1 + w1 > x2;
        boolean chevaucheY = y1 < y2 + h2 && y1 + h1 > y2;
        return chevaucheX && chevaucheY;
    }

    private void ramasserItem(Item item, VueItem vue, Iterator<Item> itemIterator,
                              Iterator<VueItem> vueIterator,
                              Inventaire inventaire, VueInventaire vueInventaire) {
        inventaire.ajouterItem(item);
        vueInventaire.mettreAJourAffichageInventaire();
        worldGroup.getChildren().remove(vue.getImageView());
        itemIterator.remove();
        vueIterator.remove();
    }

}
