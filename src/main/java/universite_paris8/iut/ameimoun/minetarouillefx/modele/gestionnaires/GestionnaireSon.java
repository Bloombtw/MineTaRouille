package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.AudioManager;

public class GestionnaireSon {
    private final Joueur joueurModele;
    private boolean sonDegatJoue = false;

    public GestionnaireSon(Joueur joueurModele) {
        this.joueurModele = joueurModele;
    }

    // Joue une alerte sonore si la vie du joueur est basse, et arrête de jouer si la vie redevient normale.
    public void gererAlerteVieBasse() {
        boolean vieLow = joueurModele.getVie().estLow();
        if (vieLow && !sonDegatJoue) {
            sonDegatJoue = true;
            AudioManager.getInstance().jouerAlerteVieBasse();
        } else if (!vieLow) {
            sonDegatJoue = false;
        }
    }
}
