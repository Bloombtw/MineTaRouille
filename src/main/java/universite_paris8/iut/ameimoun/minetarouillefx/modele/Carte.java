package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

import java.util.ArrayList;
import java.util.Random;

public class Carte {
    private final Bloc[][][] terrain;


    public Carte(int hauteur, int largeur) {
        terrain = new Bloc[Constantes.NB_COUCHES][hauteur][largeur];

        // Couche 0 : sol
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < largeur; x++) {
                if (y < 25) {
                    terrain[0][y][x] = Bloc.CIEL_SOMBRE;
                } else if (y == 25) {
                    terrain[1][y][x] = Bloc.SABLE_ROUGE;
                } else {
                    terrain[1][y][x] = Bloc.TERRE_STYLEE_SOMBRE;
                }
            }
        }
        Random rand = new Random();
        int nbEtoiles = 6;
        for (int i = 0; i < nbEtoiles; i++) {
            int xEtoile = rand.nextInt(largeur);
            int yEtoile = rand.nextInt(7); // Choisir le nb de couches (en partant du haut)
            if (terrain[2][yEtoile][xEtoile] == null) {
                terrain[2][yEtoile][xEtoile] = Bloc.ETOILE;
            }
        }

        int arbreX = 10;
        // Le layer [ ][24][ ] représente le sol
        terrain[1][25][arbreX] = Bloc.TERRE_STYLEE;

        terrain[2][24][arbreX] = Bloc.TRONC;
        terrain[2][23][arbreX] = Bloc.TRONC;
        terrain[2][22][arbreX] = Bloc.TRONC;

        terrain[2][21][arbreX] = Bloc.FEUILLAGE;
        terrain[2][21][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[2][21][arbreX + 1] = Bloc.FEUILLAGE;
        terrain[2][22][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[2][22][arbreX + 1] = Bloc.FEUILLAGE;
        terrain[2][10][15] = Bloc.CORBEAU;
        terrain[2][6][59] = Bloc.LUNE;
        terrain[2][24][31] = Bloc.ARBUSTE_MORT;
        terrain[2][24][45] = Bloc.ARBUSTE_MORT;
        terrain[2][24][12] = Bloc.ARBUSTE_MORT;
        terrain[2][24][23] = Bloc.ARBUSTE_MORT;
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
        return Constantes.NB_COUCHES;
    }

    public boolean estDansLaMap(int x, int y) {
        return x >= 0 && y >= 0 && y < getHauteur() && x < getLargeur();
    }


    public boolean estBlocSolide(int x, int y) {
        if (!estDansLaMap(x, y)) return true;

        for (int couche = 0; couche < Constantes.NB_COUCHES; couche++) {
            Bloc bloc = terrain[couche][y][x];
            if (bloc != null && bloc.estSolide()) {
                return true;
            }
        }
        return false;
    }

}