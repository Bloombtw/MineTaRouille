package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Joueur extends Personnage {
//debogguer la fluidité des déplacements (voir la branche deplacements)
    public static final int TAILLE_PERSO = 64;
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

    public void deplacerGauche() {
        this.vitesseX = -vitesseDeplacement;
        this.setX(getX() + vitesseX);
    }

    public void deplacerDroite() {
        this.vitesseX = vitesseDeplacement;
        this.setX(getX() + vitesseX);
    }

    public void arreterMouvementX() {
        this.vitesseX = 0;
    }

    public void sauter() {
        //if (onGround()) {
            this.vitesseY = FORCE_SAUT;
            this.setY(getY() + vitesseY); // Appliquer le saut immédiatement
            this.peutSauter = false; // Empêcher les sauts multiples en l'air
        //}
    }

    @Override
    public void gravite() {
        super.gravite(); // Applique la gravité de la classe Personnage
        this.setX(getX() + vitesseX);
        this.setY(getY() + vitesseY);
        this.vitesseY += GRAVITE; // GRAVITE doit être une constante définie dans Personnage ou Joueur

        //Utiliser estBlocSoldie()
        if (carte.estBlocSolide((int) getX(), (int) (getY() + vitesseY + TAILLE_PERSO))) {
            this.setY(Math.floor(getY() / TAILLE_PERSO) * TAILLE_PERSO); // Ajuster pile sur le sol
            this.vitesseY = 0;
            this.peutSauter = true; // Permettre de sauter à nouveau
        }
    }

    public boolean onGround() {
        return getY() >= carte.getHauteur() - TAILLE_PERSO;
    }
}