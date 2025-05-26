package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;

public class Mob extends Personnage {
    private boolean seDeplace = false; // Initialisation par défaut à false
    private Direction mouvementDirection = Direction.DROITE; // Le mob commence par aller à droite
    private final double vitesseMob = 1.0; // Vitesse de déplacement du mob

    public Mob() {
        super(50, 50, 10, "poulet");
    }

    /**
     * Met à jour la position du mob en fonction de sa logique de déplacement
     * et gère la gravité et les collisions.
     * @param vueMob La vue associée pour mettre à jour l'animation.
     */
    public void mettreAJour(VueMob vueMob) {
        gravite();
        double futurX = getX();

        if (mouvementDirection == Direction.DROITE) {
            futurX = getX() + vitesseMob;
        } else {
            futurX = getX() - vitesseMob;
        }

        if (!collision(futurX, getY())) {
            setX(futurX);
            this.direction = mouvementDirection;
            setSeDeplace(true);
        } else {
            System.out.println("Collision détectée pour le mob. Changement de direction.");
            if (mouvementDirection == Direction.DROITE) {
                mouvementDirection = Direction.GAUCHE;
            } else {
                mouvementDirection = Direction.DROITE;
            }
            setSeDeplace(false);
        }
        vueMob.mettreAJourAnimation(this);
    }

    public boolean getSeDeplace() {
        return seDeplace;
    }

    public void setSeDeplace(boolean seDeplace) {
        this.seDeplace = seDeplace;
    }
}