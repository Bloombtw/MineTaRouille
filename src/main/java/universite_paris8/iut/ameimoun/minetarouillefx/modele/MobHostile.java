package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Représente un mob hostile qui attaque le joueur.
 * Se déplace vers le joueur, saute par-dessus les obstacles
 * et inflige des dégâts lorsque le joueur est à portée.
 */
public class MobHostile extends Mob {
    private final Joueur joueur; // Référence au joueur

    public MobHostile(Joueur joueur) {
        super();
        this.joueur = joueur;
    }

    public void mettreAJour() {
        super.mettreAJour(joueur); // gravité + stratégie de déplacement
        sauterSiObstacle();
        attaquerJoueur();
    }

    private void attaquerJoueur() {
        double distanceX = Math.abs(getX() - joueur.getX());
        double distanceY = Math.abs(getY() - joueur.getY());
        double distanceTotale = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distanceTotale <= Constantes.DISTANCE_ATTAQUE) {
            joueur.getVie().subirDegats(Constantes.DEGATS_MOB_HOSTILE);
        }
    }

    private void sauterSiObstacle() {
        double prochaineX = getX() + getVitesseX();
        if (collision(prochaineX, getY())) {
            sauter();
        }
    }
}
