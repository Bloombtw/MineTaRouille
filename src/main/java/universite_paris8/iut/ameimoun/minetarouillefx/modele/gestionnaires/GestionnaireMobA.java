package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;

import java.util.Random;

public abstract class GestionnaireMobA {
    protected final Random random = new Random();
    protected Pane rootPane;

    public abstract Mob ajouterMob(Joueur cible, double y, Pane rootPane);
    public abstract void mettreAJour();
    public abstract void tuerMob(double playerCenterX, double playerCenterY, double distanceMax);

    protected double calculerDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}