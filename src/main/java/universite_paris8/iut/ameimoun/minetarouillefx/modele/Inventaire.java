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


    // Ajoute un nouvel item dans l'inventaire
    public void ajouterItem(Item nouvelItem) {
        int quantiteRestante = empilerDansStacksExistants(nouvelItem);
        if (quantiteRestante > 0) {
            ajouterDansSlotsVides(nouvelItem, quantiteRestante);
        }
        // Si l'inventaire est plein on fait r
    }

    // Empile l'item ds les stacks existants et retourne la qtité restante à add
    private int empilerDansStacksExistants(Item nouvelItem) {
        int quantiteRestante = nouvelItem.getQuantite();
        for (Item slot : slots) {
            if (slot != null && slot.equals(nouvelItem) && slot.getQuantite() < slot.getStackMax()) {
                int place = slot.getStackMax() - slot.getQuantite();
                int aAjouter = Math.min(place, quantiteRestante);
                slot.ajouterQuantite(aAjouter);
                quantiteRestante -= aAjouter;
                if (quantiteRestante == 0) break;
            }
        }
        return quantiteRestante;
    }

    // Ajoute l'item dans les slots vides
    private void ajouterDansSlotsVides(Item nouvelItem, int quantiteRestante) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null && quantiteRestante > 0) {
                int aMettre = Math.min(nouvelItem.getStackMax(), quantiteRestante);
                Item itemAAjouter = (nouvelItem.getTypeItem() == Item.TypeItem.BLOC)
                        ? new Item(nouvelItem.getBloc(), aMettre)
                        : new Item(nouvelItem.getObjet(), aMettre);
                slots.set(i, itemAAjouter);
                quantiteRestante -= aMettre;
                if (quantiteRestante == 0) break;
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

    // Retourne true si un slot vide existe OU si un slot du même type peut stacker l'item
    public boolean aDeLaPlacePour(Item item) {
        for (Item slot : slots) {
            if (slot == null) return true;
            if (slot.equals(item) && slot.getQuantite() + item.getQuantite() <= item.getStackMax()) return true;
        }
        return false;
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