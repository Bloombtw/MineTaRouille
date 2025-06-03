package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.lang.reflect.GenericArrayType;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;

    public Mob() {
        super(30, 50, 10, "MOB");
    }

    public void mettreAJour() {
        gravite();
        double prochaineX = getX() + (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT : -Constantes.VITESSE_DEPLACEMENT);
        double prochaineY = getY() +getVitesseY();

        boolean collisionVerticale = collision(prochaineX,prochaineY-Constantes.FORCE_SAUT);
        boolean collisionDroite = collision(prochaineX + Constantes.VITESSE_DEPLACEMENT,getY());
        boolean collisionGauche = collision(prochaineX - Constantes.VITESSE_DEPLACEMENT,getY());

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
        return (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT : -Constantes.VITESSE_DEPLACEMENT);
    }
}