package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.Souris;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;
import javafx.application.Platform;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.musique.MusiqueManager;

import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;
    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private ClavierListener clavierListene;
    private Vie vie;
    private VueVie vueVie;
    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;
    private DebugManager debugManager;
    private MusiqueManager musiqueManager;
    private Souris souris;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserInventaire();
        initialiserControles();
        demarrerBoucleDeJeu();
        initialiserMusique();
        initialiserSouris();
    }

    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourJeu();
                gererVie();
            }
        };
        gameLoop.start();
    }

    private void mettreAJourJeu() {
        joueurModele.gravite();
        gererVie();
        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }
    }

    private void gererVie() {
        vie.verifierDegats(joueurModele, Carte.getInstance());
        if (joueurModele.getVie().estMort() && vie.estMort()) {
            joueurModele.getVie().setEstEnVie(false);
            musiqueManager.arreterMusique();
            musiqueManager.jouerMusique("/mp3/GTA5_mort.mp3", 1);
            gameLoop.stop();
            clavierListene.desactiver(tileMap);
            Parent overlayDeMort = Loader.load("/fxml/EcranDeMort.fxml");
            if (overlayDeMort != null) {
                rootPane.getChildren().add(overlayDeMort);
            }
        }
    }

    private void initialiserMusique() {
        musiqueManager = MusiqueManager.getInstance();
        musiqueManager.jouerMusiqueFond();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur();
        debugManager = new DebugManager(rootPane, joueurModele);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
    }

    private void initialiserBarreDeVie() {
        vie = joueurModele.getVie();
        vueVie = new VueVie(vie, rootPane);
        rootPane.getChildren().add(vueVie.getNoeudBarreVie());
        rootPane.getChildren().add(0, vueVie.getOverlayDegatsGlobal());
    }

    private void initialiserInventaire() {
        inventaire = new Inventaire();
        inventaire.ajouterItem(new Item(1, "Épée", 1, "Une épée basique", Type.ARME, Rarete.COMMUN));
        inventaire.ajouterItem(new Item(2, "Pioche", 1, "Pioche", Type.ARME, Rarete.RARE));
        vueInventaire = new VueInventaire(inventaire);
        AnchorPane.setTopAnchor(vueInventaire, 10.0);
        AnchorPane.setRightAnchor(vueInventaire, 10.0);
        rootPane.getChildren().add(vueInventaire);
    }

    private void initialiserControles() {
        clavierListene = new ClavierListener(joueurModele, inventaire, vueInventaire, debugManager);
        clavierListene.lier(tileMap);
        tileMap.setFocusTraversable(true);
        Platform.runLater(() -> tileMap.requestFocus());
    }

    private void initialiserSouris() {
        souris = new Souris(tileMap, Carte.getInstance(), inventaire, vueCarte, vueInventaire, joueurModele);
    }

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }
}