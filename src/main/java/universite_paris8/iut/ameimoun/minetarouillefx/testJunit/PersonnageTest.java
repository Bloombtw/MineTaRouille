package universite_paris8.iut.ameimoun.minetarouillefx.testJunit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class PersonnageTest {

    private Personnage personnage;

    @BeforeMethod
    public void setUp() {
        personnage = new Personnage(0, 0, 100, "TestPersonnage");
    }

    @Test
    public void testSauter() {
        personnage.sauter();
        assertEquals(Constantes.FORCE_SAUT, personnage.getVitesseY());
    }

    @Test
    public void testDeplacerGauche() {
        double initialX = personnage.getX();
        personnage.deplacerGauche();
        assertEquals(initialX - Constantes.VITESSE_DEPLACEMENT, personnage.getX());
    }

    @Test
    public void testDeplacerDroite() {
        double initialX = personnage.getX();
        personnage.deplacerDroite();
        assertEquals(initialX + Constantes.VITESSE_DEPLACEMENT, personnage.getX());
    }

    @Test
    public void testArreterMouvementX() {
        personnage.arreterMouvementX();
        assertEquals(0.0, personnage.getVitesseX());
    }

    @Test
    public void testGravite() {
        double initialY = personnage.getY();
        personnage.gravite();
        assertTrue(personnage.getY() > initialY);
    }

    @Test
    public void testCollision() {
        boolean collisionDetected = personnage.collision(10, 10);
        assertFalse(collisionDetected);
    }
}