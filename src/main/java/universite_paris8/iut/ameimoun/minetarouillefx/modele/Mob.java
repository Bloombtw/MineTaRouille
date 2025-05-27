package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;

    public Mob() {
        super(200, 10, 10, "poulet");
    }

    public void mettreAJour() {
        gravite();
        double prochaineX = getX() + Constantes.VITESSE_DEPLACEMENT;

        if (collision(prochaineX, getY())) {//si il y a collision trop haut, qu'il aille à gauche
            sauter();
            deplacerDroite();
        } else {
            deplacerDroite();
        }
    }

}