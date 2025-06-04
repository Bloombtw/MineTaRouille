package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.Parent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.GestionnaireControles;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.AudioManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.audio.MusiqueManager;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.AnchorPane;

public class GestionnaireMort {
    private final Joueur joueurModele;
    private final Vie vie;
    private final MusiqueManager musiqueManager;
    private final AnchorPane rootPane;
    private final GestionnaireControles gestionnaireControles;
    private final VueCarte vueCarte;

    public GestionnaireMort(Joueur joueurModele, Vie vie, MusiqueManager musiqueManager, AnchorPane rootPane, GestionnaireControles gestionnaireControles, VueCarte vueCarte) {
        this.joueurModele = joueurModele;
        this.vie = vie;
        this.musiqueManager = musiqueManager;
        this.rootPane = rootPane;
        this.gestionnaireControles = gestionnaireControles;
        this.vueCarte = vueCarte;
    }


    // Vérifie si le joueur est mort, arrête la musique et affiche l'écran de mort.
    public void gererMort(AnimationTimer gameLoop) {
        TilePane tileMap = vueCarte.getTileMap();
        if (joueurModele.getVie().estMort() && vie.estMort()) {
            joueurModele.getVie().setEstEnVie(false);
            musiqueManager.arreterMusique();
            AudioManager.getInstance().arreterTousLesSons();
            AudioManager.getInstance().jouerAlerteMort();
            gameLoop.stop();
            gestionnaireControles.getSourisListener().desactiver(tileMap);
            gestionnaireControles.getClavierListener().desactiver(tileMap);
            afficherEcranDeMort();
        }
    }

    // Affiche l'écran de mort en chargeant le FXML.
    private void afficherEcranDeMort() {
        Parent overlayDeMort = Loader.load(Chemin.FXML_ECRAN_MORT);
        if (overlayDeMort != null) {
            rootPane.getChildren().add(overlayDeMort);
        }
    }
}
