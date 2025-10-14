package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategiesdeplacement.StrategieAttaque;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategiesdeplacement.StrategieDeplacement;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.strategiesdeplacement.StrategieFuite;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Représente un mob générique du jeu.
 * Gère son déplacement, sa gravité et ses collisions avec la carte.
 * Fournit des méthodes pour obtenir sa vitesse et sa taille.
 */
public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;
    private StrategieDeplacement strategieCourante;

    public Mob() {
        super(200, 10, 5, "MOB");
        this.strategieCourante = new StrategieAttaque();
    }

    public void mettreAJour(Joueur joueur) {
        gravite();

        if (getVie().estLow()) {
            setStrategie(new StrategieFuite());
        } else {
            setStrategie(new StrategieAttaque());
        }

        if (strategieCourante != null) {
            strategieCourante.deplacer(this, joueur);
        }

        // Logique de saut en cas de blocage
        double prochaineX = getX() + getVitesseX();
        double prochaineY = getY() + getVitesseY();

        boolean collisionVerticale = collision(prochaineX, prochaineY - Constantes.FORCE_SAUT);
        boolean collisionDroite = collision(prochaineX + Constantes.VITESSE_DEPLACEMENT_MOB, getY());
        boolean collisionGauche = collision(prochaineX - Constantes.VITESSE_DEPLACEMENT_MOB, getY());

        if (collisionDroite && collisionGauche) {
            sauter();
        } else if (collisionDroite || collisionGauche) {
            if (collisionVerticale) {
                sauter();
                direction = (direction == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
            } else {
                sauter();
            }
        }
    }

    public void setStrategie(StrategieDeplacement strategie) {
        this.strategieCourante = strategie;
    }

    public double getVitesseX() {
        return (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT_MOB : -Constantes.VITESSE_DEPLACEMENT_MOB);
    }

    public double getLargeur() {
        return Constantes.TAILLE_PERSO;
    }

    public double getHauteur() {
        return Constantes.TAILLE_PERSO;
    }
}