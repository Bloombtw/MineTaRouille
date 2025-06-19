package universite_paris8.iut.ameimoun.minetarouillefx.modele;
import javafx.scene.paint.Color;

/**
 * L'énumération Rarete représente les différents niveaux de rareté des objets dans le jeu.
 * Chaque rareté est associée à un nom lisible et une couleur spécifique.
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

    public String getNomLisible() {
        return nomRarete;
    }

    public Color getCouleurHex() {
        return couleurRarete;
    }

    @Override
    public String toString() {
        return nomRarete;
    }
}