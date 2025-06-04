package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;

    public Mob() {
        super(30, 50, 10, "MOB");
    }

    public void mettreAJour() {
        gravite();
        if (doitChangerDirection()) {
            changerDirection();
        }
        deplacerDansDirection();
    }

    private boolean doitChangerDirection() {
        double prochaineX = getX() + (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT : -Constantes.VITESSE_DEPLACEMENT);
        return collision(prochaineX, getY());
    }

    private void changerDirection() {
        mouvementDirection = (mouvementDirection == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
        sauter();
    }

    private void deplacerDansDirection() {
        if (mouvementDirection == Direction.DROITE) {
            deplacerDroite();
        } else {
            deplacerGauche();
        }
    }

    public double getVitesseX() {
        return (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT : -Constantes.VITESSE_DEPLACEMENT);
    }
}