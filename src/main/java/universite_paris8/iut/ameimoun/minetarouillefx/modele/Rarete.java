package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.paint.Color;

/**
 * Enumération représentant les différentes raretés d'objets dans le jeu.
 * Chaque rareté a un nom et une couleur associée.
 * TODO : Utiliser les couleurs pour l'affichage graphique dans l'inventaire.
 */
public enum Rarete {
    COMMUN("Commun", Color.GREEN),
    RARE("Rare", Color.BLUE),
    EPIQUE("Epique", Color.PURPLE),
    LEGENDAIRE("Legendaire", Color.GOLD);



    private final String nomRarete;
    private final Color couleurRarete;

    Rarete(String nomRarete, Color couleurRarete) {
        this.nomRarete = nomRarete;
        this.couleurRarete = couleurRarete;
    }

    @Override
    public String toString() {
        return nomRarete;
    }
}