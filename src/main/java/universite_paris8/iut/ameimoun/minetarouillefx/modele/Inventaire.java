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
                slots.set(i, nouvelItem);
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