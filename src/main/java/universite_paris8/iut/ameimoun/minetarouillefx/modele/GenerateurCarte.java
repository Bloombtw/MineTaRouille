package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.Random;

public class GenerateurCarte {

    public static Carte genererCarte() {
        Bloc[][][] terrain = new Bloc[Constantes.NB_COUCHES][Constantes.NB_LIGNES][Constantes.NB_COLONNES];
        Random rand = new Random();

        int[] hauteurSol = genererHauteurSol(rand);
        remplirTerrain(hauteurSol, rand, terrain);
        ajouterNuages(rand, terrain);
        ajouterArbre(hauteurSol, terrain);
        ajouterEtoiles(rand, terrain);
        ajouterObjetsSpeciaux(hauteurSol, terrain);

        return new Carte(terrain);
    }

    /**
     * Génère la hauteur du sol pour chaque colonne de la carte.
     * La hauteur varie légèrement d'une colonne à l'autre pour créer un terrain naturel.
     *
     * @param rand    L'instance de Random pour générer des variations aléatoires.
     * @return Un tableau contenant la hauteur du sol pour chaque colonne.
     */
    private static int[] genererHauteurSol(Random rand) {
        int[] hauteurSol = new int[Constantes.NB_COLONNES];
        for (int x = 0; x < Constantes.NB_COLONNES; x++) {
            if (x > 0) {
                int variation = rand.nextInt(3) - 1; // -1, 0 ou 1
                hauteurSol[x] = Math.min(Math.max(hauteurSol[x - 1] + variation, 20), Constantes.NB_LIGNES - 5);
            } else {
                hauteurSol[x] = Constantes.BASE_SOL;
            }
        }
        return hauteurSol;
    }

    /**
     * Remplit le terrain avec des blocs en fonction de la hauteur du sol générée.
     * Place des blocs de ciel, de surface et de profondeur selon les règles définies.
     *
     * @param hauteurSol Le tableau contenant la hauteur du sol pour chaque colonne.
     * @param rand       L'instance de Random pour générer des blocs aléatoires.
     * @param terrain    Le tableau 3D représentant le terrain de la carte.
     */
    private static void remplirTerrain(int[] hauteurSol, Random rand, Bloc[][][] terrain) {
        for (int x = 0; x < Constantes.NB_COLONNES; x++) {
            int h = hauteurSol[x];
            for (int y = 0; y < Constantes.NB_LIGNES; y++) {
                if (y < h) {
                    terrain[0][y][x] = Bloc.CIEL_VIOLET;
                } else if (y == h) {
                    placerBlocSurface(x, y, rand, terrain);
                } else {
                    placerBlocProfondeur(x, y, rand, terrain);
                }
            }
        }
    }

    /**
     * Place un bloc de surface à la position (x, y) dans le terrain.
     * Selon une probabilité, place du sable ou du sable rouge.
     * Ajoute également des décors superficiels comme des arbustes ou des cactus.
     *
     * @param x       La coordonnée x du bloc.
     * @param y       La coordonnée y du bloc.
     * @param rand    L'instance de Random pour générer des éléments aléatoires.
     * @param terrain Le tableau 3D représentant le terrain de la carte.
     */
    private static void placerBlocSurface(int x, int y, Random rand, Bloc[][][] terrain) {

        if (rand.nextDouble() < 0.7) {
            terrain[1][y][x] = Bloc.SABLE;
        } else {
            terrain[1][y][x] = Bloc.SABLE_ROUGE;
        }

        // Décors superficiels si le bloc est du sable
        if (rand.nextDouble() < Constantes.PROBA_ARBUSTE && terrain[1][y][x] == Bloc.SABLE) {
            if (rand.nextDouble() < 0.3) {
                terrain[2][y - 1][x] = Bloc.FEU;
            } else if (rand.nextDouble() < 0.6) {
                terrain[2][y - 1][x] = Bloc.ARBUSTE_MORT;
            } else {
                terrain[2][y - 1][x] = Bloc.POUSSE_ACACIA;
            }
        }

        // Cactus
        if (rand.nextDouble() < Constantes.PROBA_CACTUS && y - 2 >= 0) {
            terrain[2][y - 1][x] = Bloc.CACTUS;
            terrain[2][y - 2][x] = Bloc.CACTUS;
        }
    }

    /**
     * Place un bloc de profondeur à la position (x, y) dans le terrain.
     * Selon une probabilité, place du grès coupé, ciselé ou normal.
     *
     * @param x       La coordonnée x du bloc.
     * @param y       La coordonnée y du bloc.
     * @param rand    L'instance de Random pour générer des éléments aléatoires.
     * @param terrain Le tableau 3D représentant le terrain de la carte.
     */
    private static void placerBlocProfondeur(int x, int y, Random rand, Bloc[][][] terrain) {
        if (rand.nextDouble() < 0.3) {
            terrain[1][y][x] = Bloc.GRES_COUPE;
        } else if (rand.nextDouble() < 0.6) {
            terrain[1][y][x] = Bloc.GRES_CISELE;
        } else {
            terrain[1][y][x] = Bloc.GRES;
        }
    }

    private static void ajouterNuages(Random rand, Bloc[][][] terrain) {
        for (int x = 0; x < Constantes.NB_COLONNES - 2; x++) {
            if (rand.nextDouble() < Constantes.PROBA_VARIATION) {
                int y = 2 + rand.nextInt(5);
                terrain[2][y][x] = Bloc.NUAGE_PARTIE1;
                terrain[2][y][x + 1] = Bloc.NUAGE_PARTIE2;
                terrain[2][y][x + 2] = Bloc.NUAGE_PARTIE3;
            }
        }
    }

    /**
     * Ajoute un arbre à une position fixe dans le terrain.
     * Place une table de craft et un tronc avec des feuillages d'acacia.
     *
     * @param hauteurSol Le tableau contenant la hauteur du sol pour chaque colonne.
     * @param terrain    Le tableau 3D représentant le terrain de la carte.
     */
    private static void ajouterArbre(int[] hauteurSol, Bloc[][][] terrain) {
        int arbreX = 10;
        int arbreY = hauteurSol[arbreX];
        terrain[1][arbreY][arbreX] = Bloc.TERRE;
        terrain[2][arbreY - 1][arbreX] = Bloc.TRONC;
        terrain[2][arbreY - 2][arbreX] = Bloc.TRONC;
        terrain[2][arbreY - 3][arbreX] = Bloc.TRONC;
        terrain[2][arbreY - 4][arbreX] = Bloc.FEUILLAGE_ACACIA;
        terrain[2][arbreY - 4][arbreX - 1] = Bloc.FEUILLAGE_ACACIA;
        terrain[2][arbreY - 4][arbreX + 1] = Bloc.FEUILLAGE_ACACIA;
        terrain[2][arbreY - 3][arbreX - 1] = Bloc.FEUILLAGE_ACACIA;
        terrain[2][arbreY - 3][arbreX + 1] = Bloc.FEUILLAGE_ACACIA;
    }

    private static void ajouterEtoiles(Random rand, Bloc[][][] terrain) {
        for (int i = 0; i < Constantes.NB_ETOILES; i++) {
            int xEtoile = rand.nextInt(Constantes.NB_COLONNES);
            int yEtoile = rand.nextInt(7);
            if (terrain[2][yEtoile][xEtoile] == null) {
                terrain[2][yEtoile][xEtoile] = Bloc.ETOILE;
            }
        }
    }

    private static void ajouterObjetsSpeciaux(int[] hauteurSol, Bloc[][][] terrain) {
        terrain[2][10][15] = Bloc.CORBEAU;
        terrain[2][6][59] = Bloc.LUNE;
    }
}