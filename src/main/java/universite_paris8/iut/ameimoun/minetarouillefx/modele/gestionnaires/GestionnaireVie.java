package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.animation.AnimationTimer;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;

public class GestionnaireVie {
    private final Joueur joueurModele;
    private final GestionnaireSon gestionnaireSon;
    private final GestionnaireMort gestionnaireMort;
    private final Vie vie;

    public GestionnaireVie(Joueur joueurModele, GestionnaireSon gestionnaireSon, GestionnaireMort gestionnaireMort, Vie vie) {
        this.joueurModele = joueurModele;
        this.gestionnaireSon = gestionnaireSon;
        this.gestionnaireMort = gestionnaireMort;
        this.vie = vie;
    }

    // Gère la vie du joueur, vérifie les dégâts, joue les alertes de vie basse et gère la mort.
    public void mettreAJour(AnimationTimer gameLoop) {
        vie.verifierDegats(joueurModele, Carte.getInstance());
       // gestionnaireSon.gererAlerteVieBasse();
        gestionnaireMort.gererMort(gameLoop);
    }
}
