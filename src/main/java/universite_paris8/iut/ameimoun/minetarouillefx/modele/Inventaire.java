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
        int quantiteRestante = nouvelItem.getQuantite();

        //Compléter les stacks existants du même type
        for (Item slot : slots) {
            if (slot != null && slot.equals(nouvelItem) && slot.getQuantite() < slot.getStackMax()) {
                int place = slot.getStackMax() - slot.getQuantite();
                int aAjouter = Math.min(place, quantiteRestante);
                slot.ajouterQuantite(aAjouter);
                quantiteRestante -= aAjouter;
                if (quantiteRestante == 0) return;
            }
        }

        //Mettre le reste dans des slots vides
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null && quantiteRestante > 0) {
                int aMettre = Math.min(nouvelItem.getStackMax(), quantiteRestante);
                Item itemAAjouter = (nouvelItem.getTypeItem() == Item.TypeItem.BLOC)
                        ? new Item(nouvelItem.getBloc(), aMettre)
                        : new Item(nouvelItem.getObjet(), aMettre);
                slots.set(i, itemAAjouter);
                quantiteRestante -= aMettre;
                if (quantiteRestante == 0) return;
            }
        }
        // Si l'inventaire est plein c'est perdu.
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

    public boolean aDeLaPlacePour(Item item) {
        // Retourne true si un slot vide existe OU si un slot du même type peut stacker l'item
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