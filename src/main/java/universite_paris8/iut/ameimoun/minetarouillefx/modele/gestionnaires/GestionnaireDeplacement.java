package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.animation.AnimationTimer;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class GestionnaireDeplacement {
    private boolean enDeplacementGauche = false;
    private boolean enDeplacementDroite = false;
    private boolean doitSauter = false;
    private final Joueur joueur;
    private AnimationTimer boucleDeplacement;

    public GestionnaireDeplacement(Joueur joueur) {
        this.joueur = joueur;
        boucleDeplacement = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (enDeplacementGauche) joueur.deplacerGauche();
                if (enDeplacementDroite) joueur.deplacerDroite();
                if (doitSauter) {
                    joueur.sauter();
                    doitSauter = false; // ✅ le saut ne se refait pas en boucle
                }
            }
        };
        boucleDeplacement.start();
    }

    public void sauter() {
        if (joueur.getVitesseY() == 0) {
            doitSauter = true;
        }
    }

    public void setEnDeplacementGauche(boolean actif) {
        enDeplacementGauche = actif;
        if (!actif && !enDeplacementDroite) joueur.arreterMouvementX();
    }

    public void setEnDeplacementDroite(boolean actif) {
        enDeplacementDroite = actif;
        if (!actif && !enDeplacementGauche) joueur.arreterMouvementX();
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
