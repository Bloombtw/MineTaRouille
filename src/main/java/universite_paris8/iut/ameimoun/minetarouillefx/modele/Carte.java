package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import java.util.ArrayList;

public class Carte {
    private final Bloc[][][] terrain;
    private final int NB_COUCHES = 2;

    public Carte(int hauteur, int largeur) {
        terrain = new Bloc[NB_COUCHES][hauteur][largeur];

        // Couche 0 : sol
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (y < 25) {
                    terrain[0][y][x] = Bloc.CIEL_CLAIR;
                } else if (y == 25) {
                    terrain[0][y][x] = Bloc.PIERRE;
                } else {
                    terrain[0][y][x] = Bloc.SABLE;
                }
            }
        }

        int arbreX = 10;
        terrain[0][25][arbreX] = Bloc.TERRE;

        terrain[1][24][arbreX] = Bloc.TRONC;
        terrain[1][23][arbreX] = Bloc.TRONC;
        terrain[1][22][arbreX] = Bloc.TRONC;

        terrain[1][21][arbreX] = Bloc.FEUILLAGE;
        terrain[1][21][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[1][21][arbreX + 1] = Bloc.FEUILLAGE;
        terrain[1][22][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[1][22][arbreX + 1] = Bloc.FEUILLAGE;
    }

    public Bloc[][][] getTerrain() {
        return terrain;
    }

    public int getLargeur() {
        return terrain[0][0].length;
    }

    public int getHauteur() {
        return terrain[0].length;
    }

    public int getNbCouches() {
        return NB_COUCHES;
    }
}