package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.paint.Color;

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