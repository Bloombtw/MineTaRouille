package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import java.util.Random;

public class Carte {
    private final Bloc[][][] terrain;

    private static Carte instance;


    public Carte(Bloc[][][] terrain) {
        this.terrain = terrain;
    }


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

    public static Carte getInstance() {
        if (instance == null) {
            instance = GenerateurCarte.genererCarte();
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