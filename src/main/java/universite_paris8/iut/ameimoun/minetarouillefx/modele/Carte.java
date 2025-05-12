package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import java.util.ArrayList;

public class Carte {
    private int[][] terrain;

    public Carte() {
    }

    public int[][] creerTerrain(int hauteur, int largeur)  {
        terrain = new int[hauteur][largeur];
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                terrain[y][x] = (y == hauteur - 1) ? 1 : 0;
            }
        }
        return this.terrain;
    }

    public int getTile(int x, int y) {
        return terrain[x][y];
    }

    public int getLargeur() {
        return terrain[0].length;
    }

    public int getHauteur() {
        return terrain.length;
    }

    public int[][] getTerrain() {
        return terrain;
    }







//    public static final String[][] PROTOTYPE_CARTE = {
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c", "c"},
//            {"t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t"},
//            {"t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t"},
//            {"t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t"},
//            {"t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t", "t"}
//    };
}

