package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class Deplacement {

    private Personnage joueur;
    private Carte carte;
    private boolean enMouvementGauche = false;
    private boolean enMouvementDroite = false;


    public Deplacement(Joueur joueur, Carte carte) {
        this.joueur = joueur;
        this.carte = carte;
    }

    public void sauter() {
        if (joueur.onGround()) {
            joueur.setVitesseY(Constantes.FORCE_SAUT);
            joueur.setPeutSauter(false); // Empêcher plusieurs sauts
        }
    }

    public void miseAJourDeplacement() {
        System.out.println("Mise à jour du déplacement... X=" + joueur.getX() + ", Y=" + joueur.getY());

        double nouvelleX = joueur.getX();
        double nouvelleY = joueur.getY() + joueur.getVitesseY();

        if (enMouvementGauche) nouvelleX -= joueur.getVitesseDeplacement();
        if (enMouvementDroite) nouvelleX += joueur.getVitesseDeplacement();

        if (!collision(nouvelleX, joueur.getY())) {
            joueur.setX(nouvelleX);
        }
        if (!collision(joueur.getX(), nouvelleY)) {
            joueur.setY(nouvelleY);
            joueur.setVitesseY(joueur.getVitesseY() + Constantes.GRAVITE);
        } else {
            joueur.setVitesseY(0);
            joueur.setPeutSauter(true);
        }
    }

    private boolean collision(double x, double y) {
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
}