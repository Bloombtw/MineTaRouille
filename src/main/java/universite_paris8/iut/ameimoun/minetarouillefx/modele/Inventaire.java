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
            slots.add(null);
        }
    }

    public void ajouterItem(Item item) {
        // 1. Cherche une case contenant le même type d’item (par nom)
        for (int i = 0; i < slots.size(); i++) {
            Item existant = slots.get(i);
            if (existant != null && existant.getNom().equals(item.getNom())) {
                // empile
                existant.incrementeQuantite(item.getQuantite());
                return;
            }
        }

        // 2. Sinon, ajoute dans une case vide
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i) == null) {
                slots.set(i, item);
                return;
            }
        }
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
