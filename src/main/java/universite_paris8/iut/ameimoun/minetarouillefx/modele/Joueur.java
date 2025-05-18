package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Joueur extends Personnage {
//debogguer la fluidité des déplacements (voir la branche deplacements)
    public static final int TAILLE_PERSO = 30;
    private static final double FORCE_SAUT = -10;
    private double vitesseX = 0;
    private double vitesseY = 0;
    private boolean peutSauter = false;
    private final double vitesseDeplacement = 5; // Vitesse de déplacement constante

    public Joueur(Carte carte) {
        super(30, 50, 100, "Joueur", 5, carte);
    }

    public double getVitesseX() {
        return vitesseX;
    }

    public void setVitesseX(double vitesseX) {
        this.vitesseX = vitesseX;
    }

    public double getVitesseY() {
        return vitesseY;
    }

    public void setVitesseY(double vitesseY) {
        this.vitesseY = vitesseY;
    }

    public boolean peutSauter() {
        return peutSauter;
    }

    public void setPeutSauter(boolean peutSauter) {
        this.peutSauter = peutSauter;
    }

    public double getVitesseDeplacement() {
        return vitesseDeplacement;
    }


    public void arreterMouvementX() {
        this.vitesseX = 0;
    }

    public void sauter() {
        if (onGround()) {
            this.vitesseY = FORCE_SAUT;
            this.setY(getY() + vitesseY); // Appliquer le saut immédiatement
            this.peutSauter = false; // Empêcher les sauts multiples en l'air
        }
    }


/*la gestion des gestions des collisions était en protected*/

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

    public void gravite() {
        double nouvelleY = getY() + vitesseY;

        if (!collision(getX(), nouvelleY)) {
            this.setY(nouvelleY);
            this.vitesseY += GRAVITE;
        } else {
            this.vitesseY = 0;
            this.setY(Math.floor(getY() / TAILLE_PERSO) * TAILLE_PERSO);
            this.peutSauter = true;
        }
    }
    public boolean onGround() {
        return getY() >= carte.getHauteur() - TAILLE_PERSO;
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
}