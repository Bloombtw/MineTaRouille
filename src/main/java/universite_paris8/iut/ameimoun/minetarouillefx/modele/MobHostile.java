package universite_paris8.iut.ameimoun.minetarouillefx.modele;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * La classe MobHostile représente un mob hostile dans le jeu.
 * Ce mob suit le joueur, attaque lorsqu'il est à portée, et saute pour éviter les obstacles.
 */
public class MobHostile extends Mob {
    private final Personnage joueur; // Référence au joueur

    public MobHostile(Personnage joueur) {
        super();
        this.joueur = joueur;
    }

    /**
     * Met à jour l'état du mob hostile.
     * Applique la gravité, fait sauter le mob en cas d'obstacle, le fait suivre le joueur, et attaque si à portée.
     */
    @Override
    public void mettreAJour() {
        gravite();
        sauterSiObstacle();
        suivreJoueur();
        attaquerJoueur();
    }

    /**
     * Fait suivre le joueur par le mob hostile.
     * Le mob se déplace vers la gauche ou la droite en fonction de la position du joueur.
     */
    private void suivreJoueur() {
        double joueurX = joueur.getX();
        double mobX = getX();

        if (joueurX < mobX) {
            deplacerGauche();
        } else if (joueurX > mobX) {
            deplacerDroite();
        }
    }

    /**
     * Attaque le joueur si la distance entre le mob et le joueur est inférieure ou égale à la portée d'attaque.
     * Inflige des dégâts au joueur.
     */
    private void attaquerJoueur() {
        double distanceX = Math.abs(this.getX() - joueur.getX());

        double distanceY = Math.abs(this.getY() - joueur.getY());
        double distanceTotale = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distanceTotale <= Constantes.DISTANCE_ATTAQUE) {
            joueur.getVie().subirDegats(Constantes.DEGATS_MOB_HOSTILE);
        }
    }

    /**Fait sauter le mob hostile si un obstacle est détecté devant lui.*/
    private void sauterSiObstacle() {
        double prochaineX = getX() + (getVitesseX());
        if (collision(prochaineX, getY())) {
            sauter();
        }
    }
}