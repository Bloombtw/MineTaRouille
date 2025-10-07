package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Représente un personnage du jeu (joueur ou mob).
 * Gère les déplacements horizontaux, la gravité, les sauts,
 * la collision avec le terrain et la vie du personnage.
 */
public class Personnage {

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();
    protected boolean enDeplacementGauche = false;
    protected boolean enDeplacementDroite = false;
    private boolean doitSauter = false;

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
        if (!Carte.getInstance().collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.GAUCHE;
    }

    public void deplacerDroite() {
        double futurX = getX() + Constantes.VITESSE_DEPLACEMENT;
        if (!Carte.getInstance().collision(futurX, getY())) {
            setX(futurX);
        }
        direction = Direction.DROITE;
    }

    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public void gravite() {
        vitesseY += Constantes.GRAVITE;
        double futurY = getY() + vitesseY;

        if (!Carte.getInstance().collision(getX(), futurY)) {
            setY(futurY);
        } else {
            vitesseY = 0;
            peutSauter = true;
        }
    }

    public boolean estMort() {
        return vie.vieActuelleProperty().get() <= 0;
    }

    public Vie getVie() {
        return vie;
    }

    public DoubleProperty xProperty() { return x; }
    public DoubleProperty yProperty() { return y; }

    public double getX() { return x.get(); }
    public void setX(double val) { x.set(val); }

    public double getY() { return y.get(); }
    public void setY(double val) { y.set(val); }

    public double getVitesseY() { return vitesseY; }


    public void setEnDeplacementGauche(boolean actif) {
        this.enDeplacementGauche = actif;
        if (!actif && !enDeplacementDroite) arreterMouvementX();
    }

    public void setEnDeplacementDroite(boolean actif) {
        this.enDeplacementDroite = actif;
        if (!actif && !enDeplacementGauche) arreterMouvementX();
    }

    public void sauterUneFois() {
        if (getVitesseY() == 0) {
            doitSauter = true;
        }
    }

    public void mettreAJourDeplacement() {
        if (enDeplacementGauche) deplacerGauche();
        if (enDeplacementDroite) deplacerDroite();
        if (doitSauter) {
            sauter();
            doitSauter = false;
        }
    }

}