package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;

    public Mob() {
        super(200, 10, 10, "MOB");
    }

    public void mettreAJour() {
        gravite();
        double prochaineX = getX() + (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT_MOB : -Constantes.VITESSE_DEPLACEMENT_MOB);
        double prochaineY = getY() +getVitesseY();

        boolean collisionVerticale = collision(prochaineX,prochaineY-Constantes.FORCE_SAUT);
        boolean collisionDroite = collision(prochaineX + Constantes.VITESSE_DEPLACEMENT_MOB,getY());
        boolean collisionGauche = collision(prochaineX - Constantes.VITESSE_DEPLACEMENT_MOB,getY());

        if(collisionDroite && collisionGauche){
            sauter();
        }
        else if (collisionDroite || collisionGauche){
            if(collisionVerticale){
                sauter();
                mouvementDirection=(mouvementDirection==Direction.DROITE)? Direction.GAUCHE : Direction.DROITE;
            }
            else{
                sauter();
            }
        }
        if(mouvementDirection==Direction.DROITE){
            deplacerDroite();
        }
        else{
            deplacerGauche();
        }
    }

    public double getVitesseX() {
        return (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT_MOB : -Constantes.VITESSE_DEPLACEMENT_MOB);
    }
}