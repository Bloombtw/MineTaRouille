package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.RecettesCraft;

import java.util.Collections;

public class GestionnaireCraft {

    private final Inventaire inventaire;
    private final ObservableList<ObservableList<Item>> grille;
    private final ObjectProperty<Item> resultatCraft = new SimpleObjectProperty<>(null);
    private final IntegerProperty quantiteCraft = new SimpleIntegerProperty(-1);
    private final BooleanProperty fenetreCraftOuverte = new SimpleBooleanProperty(false);

    public BooleanProperty fenetreCraftOuverteProperty() {
        return fenetreCraftOuverte;
    }

    public void ouvrirFenetreCraft() {
        fenetreCraftOuverte.set(true);
    }

    public void fermerFenetreCraft() {
        fenetreCraftOuverte.set(false);
    }

    public GestionnaireCraft(Inventaire inventaire) {
        this.inventaire = inventaire;
        this.grille = FXCollections.observableArrayList();
        for (int i = 0; i < 3; i++) {
            ObservableList<Item> ligne = FXCollections.observableArrayList();
            for (int j = 0; j < 3; j++) {
                ligne.add(null);
            }
            grille.add(ligne);
        }
    }

    public static Item[][] convertirGrille(ObservableList<ObservableList<Item>> grille) {
        int rows = grille.size();
        int cols = rows > 0 ? grille.get(0).size() : 0;
        Item[][] array = new Item[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = grille.get(i).get(j);
            }
        }
        return array;
    }

    public ObservableList<ObservableList<Item>> getGrille() {
        return grille;
    }

    public ObjectProperty<Item> resultatCraftProperty() {
        return resultatCraft;
    }

    public IntegerProperty quantiteCraftProperty() {
        return quantiteCraft;
    }

    public void placerItem(int ligne, int colonne, Item item) {
        grille.get(ligne).set(colonne, item);
    }

    public void retirerItem(int ligne, int colonne) {
        grille.get(ligne).set(colonne, null);
    }

    public void tenterCraft() {
        for (RecettesCraft recette : RecettesCraft.values()) {
            if (recette.correspondPattern(convertirGrille(grille))) {
                Item resultat = recette.getResultat();
                int quantite = recette.getQuantiteResultat();
                Item resultatAvecQuantite = resultat.getBloc() != null
                        ? new Item(resultat.getBloc(), quantite)
                        : new Item(resultat.getObjet(), quantite);

                if (!inventaire.aDeLaPlacePour(resultatAvecQuantite)) {
                    quantiteCraft.set(-1);
                    resultatCraft.set(null);
                    return;
                }

                if (peutCrafter(recette)) {
                    retirerItemsPourRecette(recette);
                    inventaire.ajouterItem(resultatAvecQuantite);
                    viderGrille();
                    resultatCraft.set(resultat);
                    quantiteCraft.set(quantite);
                    return;
                }
            }
        }
        resultatCraft.set(null);
        quantiteCraft.set(-2);
    }

    public void ajouterOuRetirerItem(int row, int col) {
        Item currentItem = grille.get(row).get(col);

        if (currentItem == null) {
            Item item = inventaire.getItem(inventaire.getSelectedIndex());
            if (item == null) return;

            if (item.getBloc() != null) {
                inventaire.retirer(new Item(item.getBloc()), 1);
                grille.get(row).set(col, new Item(item.getBloc(), 1));
            } else if (item.getObjet() != null) {
                inventaire.retirer(new Item(item.getObjet()), 1);
                grille.get(row).set(col, new Item(item.getObjet(), 1));
            }
        } else {
            if (currentItem.getBloc() != null) {
                inventaire.ajouterItem(new Item(currentItem.getBloc(), 1));
            } else if (currentItem.getObjet() != null) {
                inventaire.ajouterItem(new Item(currentItem.getObjet(), 1));
            }
            grille.get(row).set(col, null);
        }
    }


    private boolean peutCrafter(RecettesCraft recette) {
        for (Item[] ligne : recette.getPattern()) {
            for (Item item : ligne) {
                if (item != null && inventaire.getQuantite(item) < 1) {
                    return false;
                }
            }
        }
        return true;
    }

    private void retirerItemsPourRecette(RecettesCraft recette) {
        for (Item[] ligne : recette.getPattern()) {
            for (Item item : ligne) {
                if (item != null) {
                    inventaire.retirer(item, 1);
                }
            }
        }
    }

    private void viderGrille() {
        for (ObservableList<Item> ligne : grille) {
            ligne.clear();
        }
    }

    public void remettreItemsGrilleDansInventaire() {
        for (ObservableList<Item> ligne : grille) {
            for (int i = 0; i < ligne.size(); i++) {
                Item item = ligne.get(i);
                if (item != null) {
                    inventaire.ajouterItem(item);
                    ligne.set(i, null);
                }
            }
        }
    }
}
