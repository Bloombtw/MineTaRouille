package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private GridPane tileMap;
    @FXML
    private StackPane racine;
    @FXML
    private AnchorPane overlayDeMort;

    private Joueur joueur;
    private Carte carte;
    private Clavier clavier;
    private AnimationTimer gameLoop;
    private VueVie vueVie;

    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;
    private static final int TAILLE_TUILE = 20; // Doit correspondre à TAILLE_PERSO

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserControles();
        demarrerBoucleDeJeu();
    }

    private void initialiserCarte() {
        carte = new Carte();
        int[][] terrain = carte.creerTerrain(HAUTEUR_FENETRE/TAILLE_TUILE, LARGEUR_FENETRE/TAILLE_TUILE);

        tileMap.setPrefWidth(LARGEUR_FENETRE);
        tileMap.setPrefHeight(HAUTEUR_FENETRE);

        for (int i = 0; i < terrain.length; i++) {
            for (int j = 0; j < terrain[i].length; j++) {
                ImageView tile = new ImageView(getImageAssociee(terrain[i][j]));
                tile.setFitWidth(TAILLE_TUILE);
                tile.setFitHeight(TAILLE_TUILE);
                tileMap.add(tile, j, i);
            }
        }
    }

    private void initialiserJoueur() {
        // Position initiale au milieu de l'écran
        double startX = (LARGEUR_FENETRE/2) - (Joueur.TAILLE_PERSO/2);
        double startY = HAUTEUR_FENETRE - 100; // Un peu au-dessus du bas de l'écran

        joueur = new Joueur(startX, startY);
        joueur.ajouterAGrille(tileMap);
    }

    private void initialiserBarreDeVie() {
        // Création de la barre de vie (200x20 pixels)
        vueVie = new VueVie(200, 20);

        // Ajout à la GridPane (position en haut à gauche)
        tileMap.add(vueVie, 0, 0);
        GridPane.setColumnSpan(vueVie, 10); // Prend 10 colonnes de large

        // Lier la vue au modèle
        vueVie.lierAuJoueur(joueur);

        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 2_000_000_000) {
                    joueur.recevoirDegat(10);
                    lastUpdate = now;
                }
            }
        }.start();

    }

    private void initialiserControles() {
        clavier = new Clavier(joueur);
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();
    }

    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                joueur.appliquerPhysique();
                gererCollisions();
                gererVie();
                // Ici vous pourriez ajouter d'autres updates (ennemis, etc.)
            }
        };
        gameLoop.start();
    }

    private void gererCollisions() {
        // À implémenter selon votre système de collision
        // Exemple simple avec le bas de l'écran :
        if (joueur.getY() > HAUTEUR_FENETRE - Joueur.TAILLE_PERSO) {
            joueur.setVitesseX(HAUTEUR_FENETRE - Joueur.TAILLE_PERSO);
            joueur.setVitesseY(0);
            joueur.setPeutSauter(true);
        }
    }

    private void gererVie() {
        if (joueur.estMort()) {
            gameLoop.stop(); // Stopper le jeu
            overlayDeMort.setVisible(true); // Afficher l’overlay
        }
    }

    @FXML
    private void handleQuitter() {
        Platform.exit();
    }

    private Image getImageAssociee(int id) {
        switch(id) {
            case 0: return new Image(getClass().getResourceAsStream("/img/ciel.png"));
            case 1: return new Image(getClass().getResourceAsStream("/img/pierre.png"));
            case 2: return new Image(getClass().getResourceAsStream("/img/sable.png"));
            default: return null;
        }
    }
}