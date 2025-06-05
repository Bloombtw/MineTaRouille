package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class Mob extends Personnage {
    private Direction mouvementDirection = Direction.DROITE;
    private boolean enVie = true;

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
        double prochaineX = getX() + (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT_MOB : -Constantes.VITESSE_DEPLACEMENT_MOB);
        return collision(prochaineX, getY());
    }

    private void changerDirection() {
        mouvementDirection = (mouvementDirection == Direction.DROITE) ? Direction.GAUCHE : Direction.DROITE;
        sauter();
    }

    private void deplacerDansDirection() {
        if (mouvementDirection == Direction.DROITE) {
            setX(getX() + Constantes.VITESSE_DEPLACEMENT_MOB);
        } else {
            setX(getX() - Constantes.VITESSE_DEPLACEMENT_MOB);
        }
    }

    public double getVitesseX() {
        return (mouvementDirection == Direction.DROITE ? Constantes.VITESSE_DEPLACEMENT_MOB : -Constantes.VITESSE_DEPLACEMENT_MOB);
    }

    public boolean enVie() {
        return enVie;
    }
}