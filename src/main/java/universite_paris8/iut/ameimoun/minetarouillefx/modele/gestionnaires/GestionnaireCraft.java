package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.RecettesCraft;


import static javafx.collections.FXCollections.observableArrayList;

/**
 * Gère la grille de craft du joueur et l'inventaire associé.
 * Permet d'ajouter ou retirer des items dans la grille, de tenter un craft
 * selon les recettes disponibles, et de gérer le résultat et la quantité du craft.
 * Met à jour les propriétés observables pour l'interface utilisateur.
 */
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
            if (!grilleCorrespondA(recette)) continue;

            Item resultat = construireItemResultat(recette);
            if (!inventaire.aDeLaPlacePour(resultat)) {
                signalerInventairePlein();
                return;
            }

            if (inventaireContientIngredients(recette)) {
                effectuerCraft(recette, resultat);
                return;
            }
        }
        signalerCraftImpossible();
    }


    /**
     * Ajoute ou retire un item de la grille de craft quand on clique sur une case.
     * @param row La ligne de la grille où l'item doit être ajouté ou retiré.
     * @param col La colonne de la grille où l'item doit être ajouté ou retiré.
     */
    public void ajouterOuRetirerItem(int row, int col) {
        Item itemGrille = grille.get(row).get(col);

        if (itemGrille == null) {
            ajouterItemDepuisInventaire(row, col);
        } else {
            retirerItemVersInventaire(row, col, itemGrille);
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


    private boolean grilleCorrespondA(RecettesCraft recette) {
        return recette.correspondPattern(convertirGrille(grille));
    }

    private Item construireItemResultat(RecettesCraft recette) {
        Item base = recette.getResultat();
        int quantite = recette.getQuantiteResultat();
        return base.getBloc() != null
                ? new Item(base.getBloc(), quantite)
                : new Item(base.getObjet(), quantite);
    }

    private boolean inventaireContientIngredients(RecettesCraft recette) {
        for (Item[] ligne : recette.getPattern()) {
            for (Item item : ligne) {
                if (item != null && inventaire.getQuantite(item) < 1) {
                    return false;
                }
            }
        }
        return true;
    }

    //Méthode privées extraites
    private void effectuerCraft(RecettesCraft recette, Item resultat) {
        inventaire.ajouterItem(resultat);
        viderGrille();
        resultatCraft.set(recette.getResultat());
        quantiteCraft.set(recette.getQuantiteResultat());
    }

    private void signalerInventairePlein() {
        quantiteCraft.set(-1);
        resultatCraft.set(null);
    }

    private void signalerCraftImpossible() {
        quantiteCraft.set(-3);
        resultatCraft.set(null);
    }

    private void ajouterItemDepuisInventaire(int row, int col) {
        Item item = inventaire.getItem(inventaire.getSelectedIndex());
        if (item == null) return;

        Item itemUnitaire = item.getBloc() != null
                ? new Item(item.getBloc(), 1)
                : new Item(item.getObjet(), 1);

        inventaire.retirer(itemUnitaire, 1);
        grille.get(row).set(col, itemUnitaire);
    }

    private void retirerItemVersInventaire(int row, int col, Item itemGrille) {
        Item itemUnitaire = itemGrille.getBloc() != null
                ? new Item(itemGrille.getBloc(), 1)
                : new Item(itemGrille.getObjet(), 1);

        inventaire.ajouterItem(itemUnitaire);
        grille.get(row).set(col, null);
    }
}
