package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

public interface CraftListener {
    void onGrilleChange(Item[][] grille);
    void onCraftResult(Item resultat, int quantite);
}