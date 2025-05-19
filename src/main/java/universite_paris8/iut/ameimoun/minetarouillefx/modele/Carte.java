package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

import java.util.ArrayList;
import java.util.Random;

public class Carte {
    private final Bloc[][][] terrain;

    private static Carte instance;

    private Carte() {
        terrain = new Bloc[Constantes.NB_COUCHES][Constantes.NB_LIGNES][Constantes.NB_COLONNES];

        // Couche 0 : sol
        for (int y = 0; y < Constantes.NB_LIGNES; y++) {
            for (int x = 0; x < Constantes.NB_COLONNES; x++) {
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
            int xEtoile = rand.nextInt(Constantes.NB_COLONNES);
            int yEtoile = rand.nextInt(7); // Choisir le nb de couches (en partant du haut)
            if (terrain[2][yEtoile][xEtoile] == null) {
                terrain[2][yEtoile][xEtoile] = Bloc.ETOILE;
            }
        }

        int arbreX = 10;
        // Le layer [ ][24][ ] représente le sol

        // L'escalier pour monter sur l'arbre des deux côtés
        terrain[2][22][arbreX - 2] = Bloc.ESCALIER_DROITE;
        terrain[2][23][arbreX - 3] = Bloc.ESCALIER_DROITE;
        terrain[2][24][arbreX - 4] = Bloc.ESCALIER_DROITE;


        // L'arbre
        terrain[2][24][arbreX] = Bloc.TRONC;
        terrain[2][23][arbreX] = Bloc.TRONC;
        terrain[2][22][arbreX] = Bloc.TRONC;
        terrain[2][21][arbreX] = Bloc.FEUILLAGE;
        terrain[2][21][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[2][21][arbreX + 1] = Bloc.FEUILLAGE;
        terrain[2][22][arbreX - 1] = Bloc.FEUILLAGE;
        terrain[2][22][arbreX + 1] = Bloc.FEUILLAGE;


        // Décors en plus
        terrain[2][10][15] = Bloc.CORBEAU;
        terrain[2][6][59] = Bloc.LUNE_ZELDA;
        terrain[2][24][31] = Bloc.ARBUSTE_MORT;
        terrain[2][24][45] = Bloc.ARBUSTE_MORT;
        terrain[2][24][12] = Bloc.ARBUSTE_MORT;
        terrain[2][24][23] = Bloc.ARBUSTE_MORT;

        System.out.println("Carte générée");
    }

/*
    public Carte() {
    terrain = new Bloc[Constantes.NB_COUCHES][Constantes.NB_LIGNES][Constantes.NB_COLONNES];
    Random rand = new Random();

    // Génère un sol avec hauteur variable
    int[] hauteurSol = new int[Constantes.NB_COLONNES];
    int base = 25;
    for (int x = 0; x < Constantes.NB_COLONNES; x++) {
        // Variation de la hauteur du sol entre -1 et +1 par rapport à la colonne précédente
        if (x > 0) {
            int variation = rand.nextInt(3) - 1; // -1, 0 ou 1
            hauteurSol[x] = Math.min(Math.max(hauteurSol[x - 1] + variation, 20), Constantes.NB_LIGNES - 5);
        } else {
            hauteurSol[x] = base;
        }

        int h = hauteurSol[x];

        // Remplit les couches
        for (int y = 0; y < Constantes.NB_LIGNES; y++) {
            if (y < h) {
                terrain[0][y][x] = Bloc.CIEL_SOMBRE;
            } else if (y == h) {
                Bloc surfaceBloc = rand.nextDouble() < 0.5 ? Bloc.SABLE_ROUGE : Bloc.TERRE_STYLEE;
                terrain[1][y][x] = surfaceBloc;

                // Décoration : chance d’ajouter un arbuste mort ou une pierre
                if (rand.nextDouble() < 0.05) terrain[2][y][x] = Bloc.ARBUSTE_MORT;
                else if (rand.nextDouble() < 0.05) terrain[2][y][x] = Bloc.PIERRE;

            } else {
                terrain[1][y][x] = Bloc.TERRE_STYLEE_SOMBRE;
            }
        }
    }

    // Décoration céleste
    for (int i = 0; i < 6; i++) {
        int x = rand.nextInt(Constantes.NB_COLONNES);
        int y = rand.nextInt(10);
        terrain[2][y][x] = Bloc.ETOILE;
    }

    terrain[2][6][Constantes.NB_COLONNES - 5] = Bloc.LUNE;

    // Exemple d’arbre
    int arbreX = 10;
    int arbreY = hauteurSol[arbreX];

    terrain[2][arbreY - 1][arbreX] = Bloc.TRONC;
    terrain[2][arbreY - 2][arbreX] = Bloc.TRONC;
    terrain[2][arbreY - 3][arbreX] = Bloc.TRONC;

    terrain[2][arbreY - 4][arbreX] = Bloc.FEUILLAGE;
    terrain[2][arbreY - 4][arbreX - 1] = Bloc.FEUILLAGE;
    terrain[2][arbreY - 4][arbreX + 1] = Bloc.FEUILLAGE;
    terrain[2][arbreY - 3][arbreX - 1] = Bloc.FEUILLAGE;
    terrain[2][arbreY - 3][arbreX + 1] = Bloc.FEUILLAGE;

    terrain[2][10][15] = Bloc.CORBEAU;
}
*/
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

    public static Carte getInstance() {
        if (instance == null) {
            instance = new Carte();
        }
        return instance;
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