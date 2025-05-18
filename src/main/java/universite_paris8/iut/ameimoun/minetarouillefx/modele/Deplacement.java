package universite_paris8.iut.ameimoun.minetarouillefx.modele;

public class Deplacement {
    private Joueur joueur;
    private Carte carte;
    private static final double FORCE_SAUT = -10;
    private static final double GRAVITE = 0.5; // Valeur ajustable pour fluidifier la chute

    public Deplacement(Joueur joueur, Carte carte) {
        this.joueur = joueur;
        this.carte = carte;
    }

    public void deplacerGauche() {
        double nouvelleX = joueur.getX() - joueur.getVitesseDeplacement();
        if (!collision(nouvelleX, joueur.getY())) {
            joueur.setX(nouvelleX);
        }
    }

    public void deplacerDroite() {
        double nouvelleX = joueur.getX() + joueur.getVitesseDeplacement();
        if (!collision(nouvelleX, joueur.getY())) {
            joueur.setX(nouvelleX);
        }
    }

    public void sauter() {
        if (joueur.onGround()) {
            joueur.setVitesseY(FORCE_SAUT);
            joueur.setY(joueur.getY() + joueur.getVitesseY());
            joueur.setPeutSauter(false); // Empêcher les sauts multiples en l'air
        }
    }

    public void gravite() {
        double nouvelleY = joueur.getY() + joueur.getVitesseY();
        if (!collision(joueur.getX(), nouvelleY)) {
            joueur.setY(nouvelleY);
            joueur.setVitesseY(joueur.getVitesseY() + GRAVITE);
        } else {
            joueur.setVitesseY(0);
            joueur.setY(Math.floor(joueur.getY() / Joueur.TAILLE_PERSO) * Joueur.TAILLE_PERSO);
            joueur.setPeutSauter(true);
        }
    }

    private boolean collision(double x, double y) {
        int left = (int) (x / Joueur.TAILLE_PERSO);
        int right = (int) ((x + Joueur.TAILLE_PERSO - 1) / Joueur.TAILLE_PERSO);
        int top = (int) (y / Joueur.TAILLE_PERSO);
        int bottom = (int) ((y + Joueur.TAILLE_PERSO - 1) / Joueur.TAILLE_PERSO);

        for (int tx = left; tx <= right; tx++) {
            for (int ty = top; ty <= bottom; ty++) {
                if (carte.estBlocSolide(tx, ty)) return true;
            }
        }
        return false;
    }
}