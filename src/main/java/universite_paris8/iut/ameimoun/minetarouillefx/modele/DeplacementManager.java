package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.animation.AnimationTimer;

public class DeplacementManager {
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;
    private final Personnage joueur;
    private AnimationTimer boucleDeplacement;

    public DeplacementManager(Personnage joueur) {
        this.joueur = joueur;
        initialiserBoucleDeplacement();
    }

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
    }//à utiliser dans collisions
    public boolean isEnDeplacementDroite() {
        return enDeplacementDroite;
    }
}
