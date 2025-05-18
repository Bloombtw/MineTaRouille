package universite_paris8.iut.ameimoun.minetarouillefx.modele;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;

public class Personnage {
    private double x;
    private double y;
    private double vitesseX = 0;
    private double vitesseY = 0;
    public final double GRAVITE = 0.1;
    private double vitesseDeplacement;
    private final double FORCE_SAUT = -10;
    private boolean peutSauter = true;
    public Carte carte;

    public static final int TAILLE_PERSO = JeuController.TAILLE_TUILE;

    public Personnage(double x, double y, double pointsDeVieMax, String nom, double vitesseDeplacement, Carte carte) {
        this.x = x;
        this.y = y;
        this.vitesseDeplacement = vitesseDeplacement;
        this.carte = carte;
    }

    public void deplacerGauche() {
        double nouvelleX = getX() - vitesseDeplacement;
        if (!collision(nouvelleX, getY())) {
            this.setX(nouvelleX);
        }
    }

    public void deplacerDroite() {
        double nouvelleX = getX() + vitesseDeplacement;
        if (!collision(nouvelleX, getY())) {
            this.setX(nouvelleX);
        }
    }

    public void sauter() {
        if (peutSauter) {
            vitesseY = FORCE_SAUT;
            peutSauter = false;
        }
    }

    public void gravite() {
        this.vitesseY += GRAVITE; // Applique seulement la gravité, sans mise à jour de position
    }

    public boolean onGround() {
        return getY() >= carte.getHauteur() - TAILLE_PERSO;
    }
    public void arreterMouvementX() {
        vitesseX = 0;
    }

    public boolean collision(double x, double y) {
        int left = (int) (x / TAILLE_PERSO);
        int right = (int) ((x + TAILLE_PERSO - 1) / TAILLE_PERSO);
        int top = (int) (y / TAILLE_PERSO);
        int bottom = (int) ((y + TAILLE_PERSO - 1) / TAILLE_PERSO);

        for (int tx = left; tx <= right; tx++) {
            for (int ty = top; ty <= bottom; ty++) {
                if (carte.estBlocSolide(tx, ty)) return true;
            }
        }
        return false;
    }

    public boolean getPeutSauter() {
        return peutSauter;
    }

    public void setPeutSauter(boolean peutSauter) {
        this.peutSauter = peutSauter;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }

    public double getVitesseY(){return vitesseY;}

    public void setVitesseY(double vitesseDeplacement){}

    public int getX() { return (int) x; }
    public void setX(double x) { this.x = x; }

    public int getY() { return (int) y; }
    public void setY(double y) { this.y = y; }

    public Carte getCarte() {return carte;}
}