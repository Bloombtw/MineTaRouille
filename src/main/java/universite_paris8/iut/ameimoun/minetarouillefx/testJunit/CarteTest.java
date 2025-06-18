package universite_paris8.iut.ameimoun.minetarouillefx.testJunit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;

/**
 * Classe de tests unitaires pour {@link Carte}.
 * Vérifie les principales fonctionnalités de la carte :
 * - Casse de blocs solides et non solides
 * - Accès et bornes du terrain
 * - Gestion des couches et de la solidité
 * - Intégrité du terrain et des méthodes d'accès
 */
public class CarteTest {

    private Carte carte;
    private Bloc[][][] terrain;

    /**
     * Initialise une carte de test avec une couche de blocs solides avant chaque test.
     */
    @Before
    public void setUp() {
        terrain = new Bloc[3][5][5];
        // Remplir la couche 0 de blocs solides
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++)
                terrain[0][y][x] = Bloc.TERRE;
        carte = new Carte(terrain);
    }

    /**
     * Teste la casse d'un bloc solide.
     */
    @Test
    public void testCasserBlocSolide() {
        Bloc bloc = carte.casserBloc(0, 2, 2);
        assertEquals(Bloc.TERRE, bloc);
        assertEquals(Bloc.CIEL_VIOLET, carte.getBloc(2, 2, 0));
    }

    /**
     * Teste la casse d'un bloc hors de la carte.
     */
    @Test
    public void testCasserBlocHorsMap() {
        Bloc bloc = carte.casserBloc(0, -1, 0);
        assertNull(bloc);
    }

    /**
     * Teste l'accès à un bloc et la gestion des bornes.
     */
    @Test
    public void testGetBloc() {
        assertEquals(Bloc.TERRE, carte.getBloc(1, 1, 0));
        assertNull(carte.getBloc(10, 10, 0));
    }

    /**
     * Vérifie si une position est bien dans la carte.
     */
    @Test
    public void testEstDansLaMap() {
        assertTrue(carte.estDansLaMap(0, 0));
        assertFalse(carte.estDansLaMap(-1, 0));
        assertFalse(carte.estDansLaMap(0, 5));
    }

    /**
     * Teste la solidité d'un bloc sur la carte.
     */
    @Test
    public void testEstBlocSolide() {
        assertTrue(carte.estBlocSolide(1, 1));
        terrain[0][1][1] = null;
        assertFalse(carte.estBlocSolide(1, 1));
    }

    /**
     * Teste la casse d'un bloc non solide.
     */
    @Test
    public void testCasserBlocNonSolide() {
        terrain[0][3][3] = Bloc.CIEL_VIOLET; // non solide
        Bloc bloc = carte.casserBloc(0, 3, 3);
        assertNull(bloc);
    }

    /**
     * Teste la casse de blocs sur les bords de la carte.
     */
    @Test
    public void testCasserBlocBordCarte() {
        Bloc bloc = carte.casserBloc(0, 0, 0);
        assertEquals(Bloc.TERRE, bloc);
        assertEquals(Bloc.CIEL_VIOLET, carte.getBloc(0, 0, 0));
        bloc = carte.casserBloc(0, 4, 4);
        assertEquals(Bloc.TERRE, bloc);
        assertEquals(Bloc.CIEL_VIOLET, carte.getBloc(4, 4, 0));
    }

    /**
     * Vérifie que le terrain retourné est bien celui d'origine.
     */
    @Test
    public void testGetTerrain() {
        assertSame(terrain, carte.getTerrain());
    }

    /**
     * Vérifie le nombre de couches de la carte.
     */
    @Test
    public void testGetNbCouches() {
        assertEquals(3, carte.getNbCouches());
    }

    /**
     * Teste la solidité sur toutes les couches.
     */
    @Test
    public void testEstBlocSolideToutesCouches() {
        terrain[1][2][2] = Bloc.TERRE;
        terrain[0][2][2] = null;
        assertTrue(carte.estBlocSolide(2, 2));
        terrain[1][2][2] = null;
        assertFalse(carte.estBlocSolide(2, 2));
    }
}