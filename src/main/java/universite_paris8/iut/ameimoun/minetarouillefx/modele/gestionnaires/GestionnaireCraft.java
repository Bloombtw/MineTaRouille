package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.RecettesCraft;


import static javafx.collections.FXCollections.observableArrayList;

public class GestionnaireCraft {

    private final Inventaire inventaire;
    private final ObservableList<ObservableList<Item>> grille;
    private final ObjectProperty<Item> resultatCraft = new SimpleObjectProperty<>(null);
    private final IntegerProperty quantiteCraft = new SimpleIntegerProperty(-1);
    private final BooleanProperty fenetreCraftOuverte = new SimpleBooleanProperty(false);
    public BooleanProperty fenetreCraftOuverteProperty() {
        return fenetreCraftOuverte;
    }

    /**
=     * Met à jour la propriété fenetreCraftOuverte pour indiquer que la fenêtre est ouverte.
     */
    public void ouvrirFenetreCraft() {
        fenetreCraftOuverte.set(true);
    }

    public void fermerFenetreCraft() {
        fenetreCraftOuverte.set(false);
    }

    /**
     * Constructeur de la classe GestionnaireCraft.
     * Initialise la grille de craft avec des cases vides et la quantité de craft à -3
     * -> toutes les quantités négatives sont interprétées comme des messages à afficher dans le label de résultat du craft.
     *
     * @param inventaire L'inventaire associé au gestionnaire de craft.
     */
    public GestionnaireCraft(Inventaire inventaire) {
        this.inventaire = inventaire;
        this.grille = observableArrayList();
        for (int i = 0; i < 3; i++) {
            ObservableList<Item> ligne = observableArrayList();
            for (int j = 0; j < 3; j++) {
                ligne.add(null);
            }
            grille.add(ligne);
        }
        quantiteCraft.set(-3);
    }

    /**
     * Convertit une grille d'ObservableList<Item> en un tableau 2D d'Item.
     *
     * @param grille La grille à convertir.
     * @return Un tableau 2D d'Item représentant la grille.
     */
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

    /**
     *
     * @return La propriété de l'item résultant du craft.
     */
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

    /**
     * Tente de crafter un item en fonction des recettes disponibles.
     * Vérifie si la grille correspond à une recette, si l'inventaire a de la place pour le résultat,
     * et si les items nécessaires sont présents dans l'inventaire.
     * Si le craft est possible, ajoute l'item résultant à l'inventaire et vide la grille.
     */
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
                    inventaire.ajouterItem(resultatAvecQuantite);
                    viderGrille();
                    resultatCraft.set(resultat);
                    quantiteCraft.set(quantite);
                    return;
                }
            }
        }
        resultatCraft.set(null);
        quantiteCraft.set(-3);
    }

    /**
     * Ajoute ou retire un item de la grille de craft quand on clique sur une case.
     * @param row La ligne de la grille où l'item doit être ajouté ou retiré.
     * @param col La colonne de la grille où l'item doit être ajouté ou retiré.
     */
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

    /**
     * Vérifie si l'inventaire contient tous les items nécessaires pour crafter la recette.
     *
     * @param recette La recette à vérifier.
     * @return true si tous les items nécessaires sont présents, false sinon.
     */
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

    /**
     * Vide la grille de craft en remplaçant tous les items par null.
     */
    private void viderGrille() {
        for (ObservableList<Item> ligne : grille) {
            for (int i = 0; i < ligne.size();i++) {
               ligne.set(i, null);
            }
        }
    }

    /**
     * Remet tous les items de la grille de craft dans l'inventaire.
     * Utilisé lors de la fermeture de la fenêtre de craft pour récupérer les items.
     */
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
