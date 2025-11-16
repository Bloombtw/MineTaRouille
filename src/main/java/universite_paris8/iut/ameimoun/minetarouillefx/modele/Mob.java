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
        appliquerGravite();
        mettreAJourStrategie(joueur);
        appliquerStrategieDeplacement(joueur);
        gererBlocageEtSaut();
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


    private void appliquerGravite() {
        gravite();
    }

    private void mettreAJourStrategie(Joueur joueur) {
        if (getVie().estLow()) {
            setStrategie(new StrategieFuite());
        } else {
            setStrategie(new StrategieAttaque());
        }
    }

    private void appliquerStrategieDeplacement(Joueur joueur) {
        if (strategieCourante != null) {
            strategieCourante.deplacer(this, joueur);
        }
    }

    private void gererBlocageEtSaut() {
        double prochaineX = getX() + getVitesseX();
        double prochaineY = getY() + getVitesseY();
        boolean collisionVerticale = Carte.getInstance().collision(prochaineX, prochaineY - Constantes.FORCE_SAUT);
        boolean collisionDroite = Carte.getInstance().collision(prochaineX + Constantes.VITESSE_DEPLACEMENT_MOB,getY());
        boolean collisionGauche = Carte.getInstance().collision(prochaineX - Constantes.VITESSE_DEPLACEMENT_MOB,getY());

        if (collisionDroite && collisionGauche) {
            sauter();
        } else if (collisionDroite || collisionGauche) {
            sauter();
            if (collisionVerticale) {
                direction = (direction == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
            }
        }
    }
}