package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class MobHostile extends Mob {
    private final Personnage joueur; // Référence au joueur

    public MobHostile(Personnage joueur) {
        super();
        this.joueur = joueur;
    }

    @Override
    public void mettreAJour() {
        gravite();
        suivreJoueur();
        attaquerJoueur();
    }

    private void suivreJoueur() {
        double joueurX = joueur.getX();
        double mobX = getX();

        if (joueurX < mobX) {
            deplacerGauche();
        } else if (joueurX > mobX) {
            deplacerDroite();
        }
    }

    private void attaquerJoueur() {
        double distanceX = Math.abs(this.getX() - joueur.getX());
        double distanceY = Math.abs(this.getY() - joueur.getY());
        double distanceTotale = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if (distanceTotale <= Constantes.DISTANCE_ATTAQUE) {
            joueur.getVie().subirDegats(Constantes.DEGATS_MOB_HOSTILE);
        }
    }
}