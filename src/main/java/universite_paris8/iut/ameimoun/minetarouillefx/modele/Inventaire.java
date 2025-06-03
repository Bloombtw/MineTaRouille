package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

public class Inventaire {

    private final ObservableList<Item> slots = FXCollections.observableArrayList();
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

    public Inventaire() {
        for (int i = 0; i < 9; i++) {
            slots.add(null); // 9 emplacements vides
        }
    }
    public void ajouterItem(Item nouvelItem) {
        for (Item slot : slots) {
            if (slot != null && slot.getId() == nouvelItem.getId()) {
                slot.ajouterQuantite(nouvelItem.getQuantite());
                return;
            }
        }

        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null) {
                slots.set(i, nouvelItem);
                return;
            }
        }
    }

    public void retirerItem(int index) {
        if (index < 0 || index >= slots.size()) return;
        Item item = slots.get(index);

        if (item == null) return;

        if (item.getQuantite() > 1)
            item.ajouterQuantite(-1);
        else
            slots.set(index, null);

    }

    public ObservableList<Item> getSlots() {
        return slots;
    }

    public Item getItem(int index) {
        return (index >= 0 && index < slots.size()) ? slots.get(index) : null;
    }

    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    public void setSelectedIndex(int index) {
        if (index >= 0 && index < slots.size()) {
            selectedIndex.set(index);
        }
    }

    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }

    public void jeterItemSelectionne(Joueur joueur, VueInventaire vueInventaire, JeuController jeuController) {
        int idx = getSelectedIndex();
        Item item = getItem(idx);
        if (item == null) {
            // pas d’item dans le slot sélectionné → on ne fait rien
            return;
        }

        // 1. Créer une nouvelle instance d’Item (une seule unité) selon le Type
        Item dropItem;
        if (item.getTypeItem() == Item.TypeItem.BLOC) {
            dropItem = new Item(item.getBloc());
        } else {
            dropItem = new Item(item.getObjet());
        }

        // 2. Retirer/décrémenter l’item du slot du modèle Inventaire
        retirerItem(idx);
        vueInventaire.mettreAJourAffichage();

        // 3. Mettre à jour la vue graphique
        vueInventaire.mettreAJourAffichage();

        // 1. Tuile sous les pieds :
        int playerTileX = (int) (joueur.getX() / Constantes.TAILLE_TUILE);
        double yPixelPieds = joueur.getY() + Constantes.TAILLE_PERSO - 1;
        int playerTileY = (int) (yPixelPieds / Constantes.TAILLE_TUILE);

        // 2. Tuile devant le joueur (X) et tuile d’air (Y)
        int direction = joueur.estRegardADroite() ? +1 : -1;
        int xTuileSpawn = playerTileX + direction;
        int yTuileSpawn = playerTileY - 1;  // juste au-dessus du sol

        System.out.println("[DEBUG] playerTile = (" + playerTileX + "," + playerTileY + "), " +
                "spawnTile = (" + xTuileSpawn + "," + yTuileSpawn + ")");

        jeuController.spawnItemAuSol(dropItem, xTuileSpawn, yTuileSpawn);

    }
}