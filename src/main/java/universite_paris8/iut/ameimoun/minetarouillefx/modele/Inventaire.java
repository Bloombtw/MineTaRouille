package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Classe représentant l'inventaire du joueur.
 * Gère les emplacements d'objets (slots), l'ajout, le retrait, la sélection et la quantité d'objets.
 */
public class Inventaire {

    /**
     * Liste observable des slots d'inventaire (9 emplacements par défaut).
     */
    private final ObservableList<Item> slots = FXCollections.observableArrayList();

    /**
     * Index de l'emplacement sélectionné (barre d'accès rapide).
     */
    private final IntegerProperty selectedIndex = new SimpleIntegerProperty(0);

    /**
     * Constructeur : initialise l'inventaire avec 9 emplacements vides.
     */
    public Inventaire() {
        for (int i = 0; i < 9; i++) {
            slots.add(null); // 9 emplacements vides
        }
    }

    /**
     * Ajoute un nouvel item dans l'inventaire.
     * Empile d'abord dans les stacks existants, puis dans les slots vides si besoin.
     * @param nouvelItem l'item à ajouter
     */
    public void ajouterItem(Item nouvelItem) {
        int quantiteRestante = empilerDansStacksExistants(nouvelItem);
        if (quantiteRestante > 0) {
            ajouterDansSlotsVides(nouvelItem, quantiteRestante);
        }
        // Si l'inventaire est plein on fait r
    }

    /**
     * Empile l'item dans les stacks existants et retourne la quantité restante à ajouter.
     * @param nouvelItem l'item à empiler
     * @return la quantité restante à ajouter dans des slots vides
     */
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

    /**
     * Ajoute l'item dans les slots vides de l'inventaire.
     * Si l'item est un bloc, on crée une nouvelle instance pour chaque stack.
     * Si l'item est un objet, on utilise la même instance pour éviter de créer des doublons.
     * @param nouvelItem l'item à ajouter
     * @param quantiteRestante la quantité à répartir dans les slots vides
     */
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

    /**
     * Retire une quantité d'un item de l'inventaire.
     * Si la quantité restante est négative, l'item est retiré du slot.
     * Si la quantité restante est positive, on met à jour la quantité dans le slot.
     * @param item l'item à retirer
     * @param quantite la quantité à retirer
     */
    public void retirer(Item item, int quantite) {
        for (int i = 0; i < slots.size(); i++) {
            Item slot = slots.get(i);
            if (slot != null && slot.getId() == item.getId()) {
                int reste = slot.getQuantite() - quantite;
                if (reste > 0) {
                    slot.setQuantite(reste);
                    return;
                } else {
                    slots.set(i, null);
                    quantite = -reste; // On continue à retirer sur les autres slots si besoin
                }
            }
        }
    }

    /**
     * Retourne la quantité totale d'un item présent dans l'inventaire.
     * @param item l'item recherché
     * @return la quantité totale présente
     */
    public int getQuantite(Item item) {
        int total = 0;
        for (Item slot : slots) {
            if (slot != null && slot.getId() == item.getId()) {
                total += slot.getQuantite();
            }
        }
        return total;
    }

    /**
     * Indique si l'inventaire a de la place pour un item donné (slot vide ou stackable).
     * @param item l'item à tester
     * @return true si une place est disponible, false sinon
     */
    public boolean aDeLaPlacePour(Item item) {
        for (Item slot : slots) {
            if (slot == null) return true;
            if (slot.equals(item) && slot.getQuantite() + item.getQuantite() <= item.getStackMax()) return true;
        }
        return false;
    }

    /**
     * Retourne la liste observable des slots d'inventaire.
     * @return la liste des items (slots)
     */
    public ObservableList<Item> getSlots() {
        return slots;
    }

    /**
     * Retourne l'item à l'index donné.
     * @param index l'index du slot
     * @return l'item à cet index, ou null si vide ou hors limites
     */
    public Item getItem(int index) {
        return (index >= 0 && index < slots.size()) ? slots.get(index) : null;
    }

    /**
     * Retourne l'index sélectionné dans la barre d'accès rapide.
     * @return l'index sélectionné
     */
    public int getSelectedIndex() {
        return selectedIndex.get();
    }

    /**
     * Définit l'index sélectionné dans la barre d'accès rapide.
     * @param index le nouvel index sélectionné
     */
    public void setSelectedIndex(int index) {
        if (index >= 0 && index < slots.size()) {
            selectedIndex.set(index);
        }
    }

    /**
     * Retourne la propriété observable de l'index sélectionné.
     * @return la propriété selectedIndex
     */
    public IntegerProperty selectedIndexProperty() {
        return selectedIndex;
    }
}
