package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.souris.SourisListener;
import universite_paris8.iut.ameimoun.minetarouillefx.environnement.Environnement;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Contrôleur principal du jeu, initialise carte, joueur, caméra,
 * mobs, inventaire, sons, craft et boucle de jeu.
 */

    public class JeuController implements Initializable {
        @FXML private AnchorPane rootPane;
        @FXML private TilePane tileMap;

        private Pane cameraPane;
        private Environnement environnement;
        private AnimationTimer gameLoop;
        private boolean jeuEstEnPause = false;

        @Override
        public void initialize(URL url, ResourceBundle rb) {
            Group worldGroup = new Group();
            cameraPane = new Pane(worldGroup);
            cameraPane.prefWidthProperty().bind(rootPane.widthProperty());
            cameraPane.prefHeightProperty().bind(rootPane.heightProperty());
            rootPane.getChildren().add(cameraPane);

            environnement = new Environnement(worldGroup, rootPane);
            rootPane.getChildren().add(environnement.getVueVie().getNoeudBarreVie());
            rootPane.getChildren().add(environnement.getVueVie().getOverlayDegatsGlobal());
            rootPane.getChildren().add(environnement.getVueInventaire());

            initialiserClavier();
            initialiserSouris();
            demarrerBoucleDeJeu();

        }

        private void demarrerBoucleDeJeu() {
            gameLoop = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (!jeuEstEnPause) {
                        environnement.update();
                        mettreAJourCamera();
                    }
                }
            };
            gameLoop.start();
        }

    private void initialiserClavier() {
        ClavierListener clavierListener = new ClavierListener(
                environnement.getJoueur(),
                environnement.getGestionnaireInventaire().getInventaire(),
                environnement.getGestionnaireInventaire().getVueInventaire(),
                environnement.getDebugManager(),
                environnement.getGestionnaireItem()
        );

        clavierListener.setJeuController(this);
        clavierListener.lier(tileMap);

        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();

        tileMap.setOnMouseClicked(e -> rootPane.requestFocus());
    }

    private void initialiserSouris() {
        SourisListener sourisListener = new SourisListener(
                environnement.getJoueur(),
                environnement.getGestionnaireInventaire().getInventaire(),
                environnement.getVueCarte(),
                environnement.getGestionnaireItem(),
                environnement.getGestionnaireMobHostile(),
                environnement.getGestionnaireMobPassif(),
                environnement.getGestionnaireFleche(),
                environnement.getGestionnaireInventaire().getVueInventaire()
        );

        // Si tu as un CraftController à lier
       // sourisListener.setCraftController(craftController);
        sourisListener.lier(tileMap);
        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();

        tileMap.setOnMouseClicked(e -> rootPane.requestFocus());
    }


    private void mettreAJourCamera() {
            Joueur joueur = environnement.getJoueur();
            Group worldGroup = environnement.getWorldGroup();
            double largeurEcran = cameraPane.getWidth();
            double hauteurEcran = cameraPane.getHeight();
            double cibleX = joueur.getX();
            double cibleY = joueur.getY();

            double offsetX = largeurEcran / 2 - cibleX;
            double offsetY = hauteurEcran / 2 - cibleY;

            double largeurCarte = Constantes.NB_COLONNES * Constantes.TAILLE_TUILE;
            double hauteurCarte = Constantes.NB_LIGNES * Constantes.TAILLE_TUILE;

            offsetX = Math.max(-(largeurCarte - largeurEcran), Math.min(0, offsetX));
            offsetY = Math.max(-(hauteurCarte - hauteurEcran), Math.min(0, offsetY));

            worldGroup.setTranslateX(offsetX);
            worldGroup.setTranslateY(offsetY);
        }

        public void reprendreJeu() {
            jeuEstEnPause = false;
        }

        public void mettreEnPauseJeu() {
            jeuEstEnPause = true;
        }

    public Environnement getEnvironnement() {return environnement;}

    public boolean isEnPause() {
        return jeuEstEnPause;
    }
    }
