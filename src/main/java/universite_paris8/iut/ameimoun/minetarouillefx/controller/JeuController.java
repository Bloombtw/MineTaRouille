package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane; // Utilise AnchorPane comme rootPane
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color; // Peut être supprimé si Color.RED n'est plus utilisé ici
import javafx.scene.shape.Rectangle; // Peut être supprimé si Rectangle n'est plus utilisé ici
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import javafx.application.Platform;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;

import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane; // C'est le nœud racine de ta scène
    @FXML
    private AnchorPane overlayDeMort;

    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private Clavier clavier;
    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private VueVie vueVie;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;
    private DebugManager debugManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserJoueur();
        initialiserInventaire();
        initialiserControles();
        initialiserBarreDeVie();
        demarrerBoucleDeJeu();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur();
        debugManager = new DebugManager(rootPane, joueurModele);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
    }

    private void initialiserBarreDeVie() {
        vueVie = new VueVie(joueurModele.getVie(), rootPane);
        rootPane.getChildren().add(vueVie.getNoeudBarreVie());
        rootPane.getChildren().add(0, vueVie.getOverlayDegatsGlobal()); // Ajoute-le en première position (arrière-plan)
    }

    private void initialiserInventaire() {
        inventaire = new Inventaire();
        inventaire.ajouterItem(new Item(1, "Épée", 1, "Une épée basique", Type.ARME, Rarete.COMMUN));
        inventaire.ajouterItem(new Item(2, "Pioche", 1, "Pioche", Type.ARME, Rarete.RARE));
        vueInventaire = new VueInventaire(inventaire);
        vueInventaire.setLayoutX(20);
        vueInventaire.setLayoutY(950);
        rootPane.getChildren().add(vueInventaire);
    }

    private void initialiserControles() {
        clavier = new Clavier(joueurModele, inventaire, vueInventaire, debugManager);
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        Platform.runLater(() -> tileMap.requestFocus());
    }

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }

    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourJeu();
            }
        };
        gameLoop.start();
    }

    private void gererFinDeJeu() {
        if (joueurModele.getVie().estMort()) {
            gameLoop.stop();
            clavier.desactiverClavier(tileMap);
            Platform.runLater(() -> overlayDeMort.setVisible(true));
        }
    }

    private void mettreAJourJeu() {
        joueurModele.gravite();
        joueurModele.getVie().verifierDegats(joueurModele, Carte.getInstance());
        gererFinDeJeu();
        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }
    }

    @FXML
    private void handleQuitter() {
        Platform.exit();
    }
}