package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.CraftListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.RecettesCraft;

import java.util.Arrays;

public class GestionnaireCraft {

    Inventaire inventaire;
    private final Item[][] grille;
    private CraftListener craftListener;

    public GestionnaireCraft(Inventaire inventaire) {
        grille = new Item[3][3];
        this.inventaire = inventaire;
    }

    public void setCraftListener(CraftListener listener) {
        this.craftListener = listener;
    }

    public void placerItem(int ligne, int colonne, Item item) {
        grille[ligne][colonne] = item;
        notifierGrilleChange();
    }

    public void retirerItem(int ligne, int colonne) {
        grille[ligne][colonne] = null;
        notifierGrilleChange();
    }

    // Dans GestionnaireCraft.java, méthode tenterCraft()
    public void tenterCraft() {
        for (RecettesCraft recette : RecettesCraft.values()) {
            if (recette.correspondPattern(grille)) {
                Item resultat = recette.getResultat();
                int quantite = recette.getQuantiteResultat();
                Item resultatAvecQuantite = resultat.getBloc() != null
                        ? new Item(resultat.getBloc(), quantite)
                        : new Item(resultat.getObjet(), quantite);

                if (!inventaire.aDeLaPlacePour(resultatAvecQuantite)) {
                    notifierCraftResult(null, -1); // ou message "Inventaire plein"
                    return;
                }

                if (peutCrafter(recette)) {
                    retirerItemsPourRecette(recette);
                    inventaire.ajouterItem(resultatAvecQuantite);
                    viderGrille();
                    notifierCraftResult(resultat, quantite);
                    return;
                }
            }
        }
        notifierCraftResult(null, -2);
    }

    private boolean peutCrafter(RecettesCraft recette) {
         if (recette.getPattern() != null) {
            // Vérifie que chaque item du pattern est bien présent dans l'inventaire
            Item[][] pattern = recette.getPattern();
            int[][] compte = new int[3][3];
             for (Item[] items : pattern) {
                 for (Item item : items) {
                     if (item != null) {
                         if (inventaire.getQuantite(item) < 1) return false;
                     }
                 }
             }
            return true;
        }
        return false;
    }

    private void retirerItemsPourRecette(RecettesCraft recette) {
         if (recette.getPattern() != null) {
             Item[][] pattern = recette.getPattern();
             for (Item[] items : pattern) {
                 for (Item item : items) {
                     if (item != null) {
                         inventaire.retirer(item, 1);
                     }
                 }
             }
        }
    }

    private void viderGrille() {
        for (Item[] items : grille) {
            Arrays.fill(items, null);
        }
        notifierGrilleChange();
    }

    public Item[][] getGrille() {
        return grille;
    }

    public static class ResultatCraft {
        public final Item resultat;
        public final int quantite;

        public ResultatCraft(Item resultat, int quantite) {
            this.resultat = resultat;
            this.quantite = quantite;
        }
    }

    public void ajouterEcouteurs(AnchorPane overlayPane, ClavierListener clavierListener) {
        overlayPane.setOnMousePressed(event -> {
            overlayPane.requestFocus();
            event.consume();
        });

        overlayPane.setOnKeyPressed(event -> {
            if (event.getText().matches("[&é\"'(-è_]")) {
                clavierListener.gererSelectionInventaire(event.getText());
            }
            event.consume();
        });
    }

    public void gererClicCraft(int row, int col) {
        if (grille[row][col] == null) {
            Item item = inventaire.getItem(inventaire.getSelectedIndex());
            if (item == null) {
                return;
            }
            if (item.getBloc() != null) {
                inventaire.retirer(new Item(item.getBloc()), 1);
                grille[row][col] = new Item(item.getBloc(), 1);
            } else if (item.getObjet() != null) {
                inventaire.retirer(new Item(item.getObjet()), 1);
                grille[row][col] = new Item(item.getObjet(), 1);
            }
            placerItem(row, col, grille[row][col]);
        } else {
            Item item = grille[row][col];
            if (item.getBloc() != null) {
                inventaire.ajouterItem(new Item(item.getBloc(), 1));
            } else if (item.getObjet() != null) {
                inventaire.ajouterItem(new Item(item.getObjet(), 1));
            }
            grille[row][col] = null;
            retirerItem(row, col);
        }
    }

    private void notifierGrilleChange() {
        if (craftListener != null) {
            craftListener.onGrilleChange(grille);
        }
    }

    private void notifierCraftResult(Item resultat, int quantite) {
        if (craftListener != null) {
            craftListener.onCraftResult(resultat, quantite);
        }
    }

    public void remettreItemsGrilleDansInventaire() {
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                Item item = grille[i][j];
                if (item != null) {
                    inventaire.ajouterItem(item);
                    grille[i][j] = null;
                }
            }
        }
        notifierGrilleChange();
    }

}