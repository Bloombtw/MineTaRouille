package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Fleche {
    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    private double distanceParcourue = 0;
    private final double distanceMax;
    private double vx, vy;

    public Fleche(double x, double y, double vx, double vy,double distanceMax) {
        this.x.set(x);
        this.y.set(y);
        this.vx = vx;
        this.vy = vy;
        this.distanceMax = distanceMax;
    }

    /**
     * Met à jour la position de la flèche et vérifie si elle a dépassé la distance maximale.
     *
     * @return true si la distance parcourue dépasse la distance maximale, sinon false.
     */
    public boolean mettreAJourEtVerifierDistance() {
        double oldX = x.get();
        double oldY = y.get();
        vy +=0.1;
        x.set(x.get() + vx);
        y.set(y.get() + vy);
        distanceParcourue += Math.hypot(x.get() - oldX, y.get() - oldY);
        return distanceParcourue > distanceMax;
    }

    public boolean estHorsJeu() {
        return x.get() < 0 || y.get() < 0
                || x.get() > Constantes.LARGEUR_FENETRE
                || y.get() > Constantes.HAUTEUR_FENETRE;
    }

    public double getX() { return x.get(); }
    public void setX(double x) { this.x.set(x); }
    public DoubleProperty xProperty() { return x; }

    public double getY() { return y.get(); }
    public void setY(double y) { this.y.set(y); }
    public DoubleProperty yProperty() { return y; }
}