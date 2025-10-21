package universite_paris8.iut.ameimoun.minetarouillefx.modele.comportement;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Direction;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Stratégie de déplacement basique pour un mob :
 * - Gravité
 * - Déplacement gauche/droite
 * - Gestion des collisions et sauts
 */
public class DeplacementBasique implements ComportementDeplacement {

    @Override
    public void deplacer(Mob mob) {
        mob.gravite();

        double vitesseX = (mob.getDirectionMouvement() == Direction.DROITE)
                ? Constantes.VITESSE_DEPLACEMENT_MOB
                : -Constantes.VITESSE_DEPLACEMENT_MOB;

        double prochaineX = mob.getX() + vitesseX;
        double prochaineY = mob.getY() + mob.getVitesseY();

        // Vérification des collisions via le proxy
        boolean collisionSol = mob.verifierCollision(prochaineX, prochaineY - Constantes.FORCE_SAUT);
        boolean collisionDroite = mob.verifierCollision(prochaineX + Constantes.VITESSE_DEPLACEMENT_MOB, mob.getY());
        boolean collisionGauche = mob.verifierCollision(prochaineX - Constantes.VITESSE_DEPLACEMENT_MOB, mob.getY());

        // Gestion des collisions et inversion de direction si nécessaire
        if (collisionDroite && collisionGauche) {
            mob.sauter();
        } else if (collisionDroite || collisionGauche) {
            if (collisionSol) {
                mob.sauter();
                mob.inverserDirection();
            } else {
                mob.sauter();
            }
        }

        // Déplacement horizontal
        if (mob.getDirectionMouvement() == Direction.DROITE) {
            mob.deplacerDroite();
        } else {
            mob.deplacerGauche();
        }
    }
}
