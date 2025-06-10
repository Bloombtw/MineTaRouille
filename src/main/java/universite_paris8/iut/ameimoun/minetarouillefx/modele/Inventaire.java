package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
                // Crée un nouvel item avec la bonne quantité
                Item itemAAjouter;
                if (nouvelItem.getTypeItem() == Item.TypeItem.BLOC) {
                    itemAAjouter = new Item(nouvelItem.getBloc(), nouvelItem.getQuantite());
                } else {
                    itemAAjouter = new Item(nouvelItem.getObjet(),nouvelItem.getQuantite());
                }
                slots.set(i, itemAAjouter);
                return;
            }
        }
    }

    // Retire une quantité d’un item donné (par id)
    public void retirer(Item item, int quantite) {
        for (int i = 0; i < slots.size(); i++) {
            Item slot = slots.get(i);
            if (slot != null && slot.getId() == item.getId()) {
                int reste = slot.getQuantite() - quantite;
                if (reste > 0) {
                    slot.setQuantite(reste);
                } else {
                    slots.set(i, null);
                }
                return;
            }
        }
    }


    public int getQuantite(Item item) {
        int total = 0;
        for (Item slot : slots) {
            if (slot != null && slot.getId() == item.getId()) {
                total += slot.getQuantite();
            }
        }
        return total;
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

}