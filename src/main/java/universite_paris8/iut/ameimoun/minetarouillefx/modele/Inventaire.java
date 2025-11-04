package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Représente l'inventaire du joueur.
 * Gère l'ajout, le retrait et le stockage des items dans des slots.
 * Permet de vérifier la quantité d'un item et de gérer la sélection active.
 */
public class Inventaire {
    private final ObservableList<Slot> slots = FXCollections.observableArrayList();
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

    public Inventaire() {
        for (int i = 0; i < 9; i++) {
            slots.add(new Slot()); // 9 emplacements vides
        }
    }

    // Ajoute un nouvel item dans l'inventaire
    public void ajouterItem(Item nouvelItem) {
        int quantiteRestante = nouvelItem.getQuantite();

        for (Slot slot : slots) {
            quantiteRestante = slot.empiler(nouvelItem);
            if (quantiteRestante == 0) return;
        }

        for (Slot slot : slots) {
            if (slot.isEmpty()) {
                int aMettre = Math.min(nouvelItem.getStackMax(), quantiteRestante);
                slot.setItem(nouvelItem.dupliquerAvecQuantite(aMettre));
                quantiteRestante -= aMettre;
                if (quantiteRestante == 0) return;
            }
        }
        // Si l'inventaire plein l'item est restant
    }


    public void retirerItem(int index) {
        if (index < 0 || index >= slots.size()) return;
        slots.get(index).retirerUn();
    }

    // Retire une quantité d’un item donné (par id)
    public void retirer(Item item, int quantite) {
        for (Slot slot : slots) {
            quantite = slot.retirerQuantite(item.getId(), quantite);
            if (quantite <= 0) break;
        }
    }

    public int getQuantite(Item item) {
        int total = 0;
        for (Slot slot : slots) {
            Item contenu = slot.getItem();
            if (contenu != null && contenu.getId() == item.getId()) {
                total += contenu.getQuantite();
            }
        }
        return total;
    }

    // Retourne true si un slot vide existe OU si un slot du même type peut stacker l'item
    public boolean aDeLaPlacePour(Item item) {
        for (Slot slot : slots) {
            if (slot.isEmpty()) return true;
            Item contenu = slot.getItem();
            if (contenu.equals(item) && contenu.getQuantite() < contenu.getStackMax()) {
                return true;
            }
        }
        return false;
    }

    public ObservableList<Slot> getSlots() {
        return slots;
    }

    public Item getItem(int index) {
        if (index < 0 || index >= slots.size()) return null;
        Slot slot = slots.get(index);
        return (slot != null) ? slot.getItem() : null;
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