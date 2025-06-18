package universite_paris8.iut.ameimoun.minetarouillefx.testJunit;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Direction;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Classe de tests unitaires pour {@link Personnage}.
 * Vérifie les comportements principaux du personnage :
 * déplacement, saut, gravité, collision, gestion de la vie et propriétés.
 */
public class PersonnageTest {

    private Personnage personnage;

    /**
     * Initialise un personnage avant chaque test.
     */
    @Before
    public void setUp() {
        personnage = new Personnage(0, 0, 100, "TestPersonnage");
    }

    /**
     * Teste le saut du personnage.
     */
    @Test
    public void testSauter() {
        personnage.sauter();
        assertEquals(Constantes.FORCE_SAUT, personnage.getVitesseY(), 0.0001);
    }

    /**
     * Teste le déplacement vers la gauche.
     */
    @Test
    public void testDeplacerGauche() {
        double initialX = personnage.getX();
        personnage.deplacerGauche();
        assertEquals(initialX - Constantes.VITESSE_DEPLACEMENT, personnage.getX(), 0.0001);
        assertEquals(Direction.GAUCHE, personnage.direction);
    }

    /**
     * Teste le déplacement vers la droite.
     */
    @Test
    public void testDeplacerDroite() {
        double initialX = personnage.getX();
        personnage.deplacerDroite();
        assertEquals(initialX + Constantes.VITESSE_DEPLACEMENT, personnage.getX(), 0.0001);
        assertEquals(Direction.DROITE, personnage.direction);
    }

    /**
     * Teste l'arrêt du mouvement horizontal.
     */
    @Test
    public void testArreterMouvementX() {
        personnage.arreterMouvementX();
        assertEquals(0.0, personnage.getVitesseX(), 0.0001);
    }

    /**
     * Teste l'effet de la gravité sur le personnage.
     */
    @Test
    public void testGravite() {
        double initialY = personnage.getY();
        personnage.gravite();
        assertTrue(personnage.getY() > initialY);
    }

    /**
     * Teste la détection de collision (aucun bloc solide attendu).
     */
    @Test
    public void testCollision() {
        boolean collisionDetected = personnage.collision(10, 10);
        assertFalse(collisionDetected);
    }

    /**
     * Vérifie qu'un double saut n'est pas possible sans atterrir.
     */
    @Test
    public void testSauterDeuxFois() {
        personnage.sauter();
        double vitesseApresPremierSaut = personnage.getVitesseY();
        personnage.sauter(); // Ne doit pas resauter tant qu'il n'a pas atterri
        assertEquals(vitesseApresPremierSaut, personnage.getVitesseY(), 0.0001);
    }

    /**
     * Teste la gravité avec collision (le personnage doit pouvoir resauter).
     */
    @Test
    public void testGraviteAvecCollision() {
        double yAvant = personnage.getY();
        personnage.gravite();
        assertTrue(personnage.getVitesseY() == 0 || personnage.getY() > yAvant);
    }

    /**
     * Vérifie la valeur initiale des points de vie.
     */
    @Test
    public void testVieInitiale() {
        assertEquals(100, personnage.getVie().getVieMax(), 0.0001);
    }
}