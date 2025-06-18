package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Carte {
    private final Bloc[][][] terrain;

    private static Carte instance;
    public Carte(Bloc[][][] terrain) {
        this.terrain = terrain;
    }


    /**
     * Casse un bloc à la position (x, y) dans la couche spécifiée.
     * Si le bloc est solide, il est remplacé par Bloc.CIEL_VIOLET -> Le bloc de fond par défaut.
     * Si le bloc est un décor nécessitant un support, il est supprimé si plus de support.
     *
     * @param couche La couche du bloc à casser (0, 1 ou 2).
     * @param x     La coordonnée x du bloc.
     * @param y     La coordonnée y du bloc.
     * @return Le bloc cassé ou null si le bloc n'est pas solide ou hors de la carte.
     */
    public Bloc casserBloc(int couche, int x, int y) {
        if (!estDansLaMap(x, y)) return null;
        Bloc bloc = terrain[couche][y][x];
        if (bloc == null || !bloc.estSolide()) return null;

        terrain[couche][y][x] = Bloc.CIEL_VIOLET;


        if (couche == 1 && y - 1 >= 0) {
            casserDecorSiPlusDeSupport(x, y);
        }

        return bloc;
    }


    private void casserDecorSiPlusDeSupport(int x, int y) {
        Bloc decor = terrain[2][y - 1][x];
        if (decor != null && decor.necessiteSupport() && decor != Bloc.CACTUS) {
            terrain[2][y - 1][x] = null;
        }
    }

    public boolean estDansLaMap(int x, int y) {
        return x >= 0 && y >= 0 && y < getHauteur() && x < getLargeur();
    }

    /**
     * Retourne l'instance unique de la carte.
     * Si l'instance n'existe pas, elle est générée.
     *
     * @return L'instance de la carte.
     */
    public static Carte getInstance() {
        if (instance == null) {
            instance = GenerateurCarte.genererCarte();
        }
        return instance;
    }

    /**
     * Vérifie si un bloc à la position (x, y) est solide.
     * Parcourt toutes les couches pour vérifier si un bloc solide existe.
     *
     * @param x La coordonnée x du bloc.
     * @param y La coordonnée y du bloc.
     * @return true si le bloc est solide, false sinon.
     */
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



}