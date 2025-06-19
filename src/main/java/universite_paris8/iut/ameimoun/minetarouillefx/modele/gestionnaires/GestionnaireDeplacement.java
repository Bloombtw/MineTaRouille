package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.animation.AnimationTimer;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class GestionnaireDeplacement {
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;
    private final Joueur joueur;
    private AnimationTimer boucleDeplacement;

    public GestionnaireDeplacement(Joueur joueur) {
        this.joueur = joueur;
        initialiserBoucleDeplacement();
    }


    /** Initialise la boucle d'animation pour gérer le déplacement du joueur.
     * La boucle vérifie en continu si le joueur doit se déplacer à gauche ou à droite
     * et appelle les méthodes appropriées sur l'objet Joueur.
     */
    private void initialiserBoucleDeplacement() {
        boucleDeplacement = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (enDeplacementGauche) joueur.deplacerGauche();
                if (enDeplacementDroite) joueur.deplacerDroite();
            }
        };
        boucleDeplacement.start();
    }

    public void setEnDeplacementGauche(boolean actif) {
        enDeplacementGauche = actif;
        if (!enDeplacementGauche && !enDeplacementDroite) {
            joueur.arreterMouvementX();
        }
    }

    public void setEnDeplacementDroite(boolean actif) {
        enDeplacementDroite = actif;
        if (!enDeplacementGauche && !enDeplacementDroite) {
            joueur.arreterMouvementX();
        }
    }

    public void stop() {
        boucleDeplacement.stop();
    }

    public boolean isEnDeplacementGauche() {
        return enDeplacementGauche;
    }
    public boolean isEnDeplacementDroite() {
        return enDeplacementDroite;
    }
}
