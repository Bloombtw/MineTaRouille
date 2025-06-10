package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.RecettesCraft;

import java.util.Arrays;

public class GestionnaireCraft {

    Inventaire inventaire;
    private final Item[][] grille;

    public GestionnaireCraft(Inventaire inventaire) {
        grille = new Item[3][3];
        this.inventaire = inventaire;
    }

    public void placerItem(int ligne, int colonne, Item item) {
        grille[ligne][colonne] = item;
    }

    public void retirerItem(int ligne, int colonne) {
        grille[ligne][colonne] = null;
    }

    public ResultatCraft tenterCraft() {
        for (RecettesCraft recette : RecettesCraft.values()) {
            if (recette.correspond(grille)) {
                // Vérifier que l'inventaire contient les items nécessaires
                if (peutCrafter(recette)) {
                    retirerItemsPourRecette(recette);
                    // Ajouter le résultat à l'inventaire
                    inventaire.ajouterItem(recette.getResultat());
                    viderGrille();
                    return new ResultatCraft(recette.getResultat(), recette.getQuantiteResultat());
                }
            }
        }
        return null;
    }

    private boolean peutCrafter(RecettesCraft recette) {
        if (recette == RecettesCraft.PLANCHE) {
            // 1 bûche n'importe où
            return inventaire.getQuantite(new Item(Bloc.TRONC)) >= 1;
        }
        // Pour d'autres recettes, adapter ici
        return false;
    }

    private void retirerItemsPourRecette(RecettesCraft recette) {
        if (recette == RecettesCraft.PLANCHE) {
            inventaire.retirer(new Item(Bloc.TRONC), 1);
        }
        // Pour d'autres recettes, adapter ici
    }

    private void viderGrille() {
        for (Item[] items : grille) {
            Arrays.fill(items, null); // Vide toute la grille
        }
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

}