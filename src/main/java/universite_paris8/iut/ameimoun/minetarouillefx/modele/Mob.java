package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.comportement.ComportementDeplacement;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.comportement.DeplacementBasique;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Représente un mob générique du jeu.
 * Utilise une stratégie pour gérer son comportement de déplacement.
 */
public class Mob extends Personnage {

    private Direction directionMouvement = Direction.DROITE;
    private ComportementDeplacement comportementDeplacement;

    public Mob() {
        super(200, 10, 5, "MOB");
        this.comportementDeplacement = new DeplacementBasique(); // stratégie par défaut
    }

    public void mettreAJour() {
        comportementDeplacement.deplacer(this);
    }

    public void setComportementDeplacement(ComportementDeplacement comportement) {
        this.comportementDeplacement = comportement;
    }

    public Direction getDirectionMouvement() {
        return directionMouvement;
    }

    public void setDirectionMouvement(Direction direction) {
        this.directionMouvement = direction;
    }

    public void inverserDirection() {
        this.directionMouvement = (directionMouvement == Direction.DROITE)
                ? Direction.GAUCHE
                : Direction.DROITE;
    }

    public double getVitesseX() {
        return (directionMouvement == Direction.DROITE)
                ? Constantes.VITESSE_DEPLACEMENT_MOB
                : -Constantes.VITESSE_DEPLACEMENT_MOB;
    }

    public double getLargeur() {
        return Constantes.TAILLE_PERSO;
    }

    public double getHauteur() {
        return Constantes.TAILLE_PERSO;
    }

    /**
     * Proxy public pour accéder à la méthode collision() protégée de Personnage.
     * Permet aux comportements externes de vérifier les collisions.
     */

    public boolean verifierCollision(double x, double y) {
        return super.collision(x, y);
    }

}
