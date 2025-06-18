package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();

    private Vie vie;

    private final String nom;
    public Direction direction;
    private double vitesseX = 0;
    private double vitesseY = 0;
    private boolean peutSauter = true;
    private Carte carte;

    public Personnage(double x, double y, double pointsDeVie, String nom) {
        this.x.set(x);
        this.y.set(y);
        this.vie = new Vie(pointsDeVie);
        this.nom = nom;
        this.direction = Direction.DROITE;
        this.carte = Carte.getInstance();
    }

    public void sauter() {
        if (peutSauter) {
            vitesseY = Constantes.FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void deplacerGauche() {
        double futurX = getX() - Constantes.VITESSE_DEPLACEMENT;
        if (!collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.GAUCHE;
    }

    public void deplacerDroite() {
        double futurX = getX() + Constantes.VITESSE_DEPLACEMENT;
        if (!collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.DROITE;
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    /**
     * Applique la gravité au personnage.
     * Si le personnage est en l'air, il tombe vers le bas.
     * Si une collision est détectée avec un bloc solide, le personnage s'arrête et peut sauter à nouveau.
     */
    public void gravite() {
        vitesseY += Constantes.GRAVITE;
        double futurY = getY() + vitesseY;

        if (!collision(getX(), futurY)) {
            setY(futurY);
        } else {
            vitesseY = 0;
            peutSauter = true;
        }
    }

    /**
     * Vérifie si le personnage entre en collision avec un bloc solide.
     * La collision est déterminée par la position du personnage et la taille des blocs.
     *
     * @param x Position horizontale du personnage.
     * @param y Position verticale du personnage.
     * @return true si une collision est détectée, false sinon.
     */
    boolean collision(double x, double y) {
        int left = (int) (x / Constantes.TAILLE_PERSO);
        int right = (int) ((x + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_PERSO);
        int top = (int) (y / Constantes.TAILLE_PERSO);
        int bottom = (int) ((y + Constantes.TAILLE_PERSO - 1) / Constantes.TAILLE_PERSO);

        for (int tx = left; tx <= right; tx++) {
            for (int ty = top; ty <= bottom; ty++) {
                if (carte.estBlocSolide(tx, ty)) return true;
            }
        }
        return false;
    }

    public Vie getVie() { return vie; }
    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }
    public double getX() { return x.get(); }
    public void setX(double val) { x.set(val); }
    public double getY() { return y.get(); }
    public void setY(double val) { y.set(val); }
    public double getVitesseY() { return vitesseY; }

}