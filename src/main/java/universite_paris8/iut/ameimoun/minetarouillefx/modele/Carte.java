package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import java.util.Random;

public class Carte {
    private final Bloc[][][] terrain;

    private static Carte instance;

    /**
     * Constructeur de la carte.
     * Initialise le terrain avec des blocs.
     * La carte est toujours la même.
     */

        public Carte() {
        terrain = new Bloc[Constantes.NB_COUCHES][Constantes.NB_LIGNES][Constantes.NB_COLONNES];
        Random rand = new Random();

        // Génère un sol avec hauteur variable
            int[] hauteurSol = new int[Constantes.NB_COLONNES];
            int base = 25;

            for (int x = 0; x < Constantes.NB_COLONNES; x++) {
                if (x > 0) {
                    int variation = rand.nextInt(3) - 1; // -1, 0 ou 1
                    hauteurSol[x] = Math.min(Math.max(hauteurSol[x - 1] + variation, 20), Constantes.NB_LIGNES - 5);
                } else {
                    hauteurSol[x] = base;
                }

                int h = hauteurSol[x];


                for (int y = 0; y < Constantes.NB_LIGNES; y++) {
                    if (y < h) {
                        terrain[0][y][x] = Bloc.CIEL_VIOLET;
                    } else if (y == h) {
                        if (rand.nextDouble() < 0.7) {
                            terrain[1][y][x] = Bloc.SABLE;
                        } else {
                            terrain[1][y][x] = Bloc.SABLE_ROUGE;
                        }


                        if (rand.nextDouble() < 0.2 && terrain[1][y][x] == Bloc.SABLE) {
                            double r = Math.random();
                            // une chance sur trois d'avoir un arbuste ou un feu ou une pousse d'acacia
                            if (r < 0.3) {
                                terrain[2][y - 1][x] = Bloc.FEU;
                            } else if (r < 0.6) {
                                terrain[2][y - 1][x] = Bloc.ARBUSTE_MORT;
                            } else {
                                terrain[2][y - 1][x] = Bloc.POUSSE_ACACIA;
                            }
                        }

                        // 10% de chance d'avoir un cactus de 2 de hauteur
                        if (rand.nextDouble() < 0.05 && y - 2 >= 0) {
                            terrain[2][y - 1][x] = Bloc.CACTUS;
                            terrain[2][y - 2][x] = Bloc.CACTUS;
                        }

                    } else {
                        double r = Math.random();
                        if (r < 0.3) {
                            terrain[1][y][x] = Bloc.GRES_COUPE;
                        } else if (r < 0.6) {
                            terrain[1][y][x] = Bloc.GRES_CISELE;
                        } else {
                            terrain[1][y][x] = Bloc.GRES;
                        }
                    }
                }
            }
            // Nuages
            for (int x = 0; x < Constantes.NB_COLONNES - 2; x++) {
                if (rand.nextDouble() < 0.05) {
                    int y = 2 + rand.nextInt(5);

                    // Nuage horizontal
                    terrain[2][y][x] = Bloc.NUAGE_PARTIE1;
                    terrain[2][y][x + 1] = Bloc.NUAGE_PARTIE2;
                    terrain[2][y][x + 2] = Bloc.NUAGE_PARTIE3;
                }
            }


            // Mise en place de l'arbre
            int arbreX = 10;
            int arbreY = hauteurSol[arbreX];

            // Force le bloc sous l'arbre à être de la terre
            terrain[1][arbreY][arbreX] = Bloc.TERRE;

            // Tronc
            terrain[2][arbreY - 1][arbreX] = Bloc.TRONC;
            terrain[2][arbreY - 2][arbreX] = Bloc.TRONC;
            terrain[2][arbreY - 3][arbreX] = Bloc.TRONC;

            // Feuillage
            terrain[2][arbreY - 4][arbreX] = Bloc.FEUILLAGE_ACACIA;
            terrain[2][arbreY - 4][arbreX - 1] = Bloc.FEUILLAGE_ACACIA;
            terrain[2][arbreY - 4][arbreX + 1] = Bloc.FEUILLAGE_ACACIA;
            terrain[2][arbreY - 3][arbreX - 1] = Bloc.FEUILLAGE_ACACIA;
            terrain[2][arbreY - 3][arbreX + 1] = Bloc.FEUILLAGE_ACACIA;


            int nbEtoiles = 6;
            for (int i = 0; i < nbEtoiles; i++) {
                int xEtoile = rand.nextInt(Constantes.NB_COLONNES);
                int yEtoile = rand.nextInt(7); // Choisir le nb de couches (en partant du haut)
                if (terrain[2][yEtoile][xEtoile] == null) {
                    terrain[2][yEtoile][xEtoile] = Bloc.ETOILE;
                }
            }

            terrain[2][10][15] = Bloc.CORBEAU;
            terrain[2][6][59] = Bloc.LUNE;
        }



    public Bloc casserBloc(int couche, int x, int y) {
        if (!estDansLaMap(x, y)) return null;
        Bloc bloc = terrain[couche][y][x];
        if (bloc == null || !bloc.estSolide()) return null;
        terrain[couche][y][x] = Bloc.CIEL_VIOLET;
        return bloc; // Nécessite un constructeur dans Item
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