package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import javafx.application.Platform;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane overlayDeMort;

    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private Clavier clavier;
    private Vie vie;
    private VueVie vueVie;

    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        //initialiserItems(); à debogguer
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserInventaire();
        initialiserControles();
        demarrerBoucleDeJeu();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur(Carte.getInstance());
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getImageView());
    }

    private void initialiserBarreDeVie() {

        vie = new Vie( joueurModele.getPointsDeVie());
        vueVie = new VueVie();

        // Liaison modèle vue via
        vie.setObservateur(() -> Platform.runLater(() ->
                vueVie.mettreAJour(vie.getVieActuelle(), vie.getVieMax())
        ));

        vueVie.mettreAJour(vie.getVieActuelle(), vie.getVieMax());
        rootPane.getChildren().add(vueVie.getNoeud());


        //degat tout 2sec
        new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate > 2_000_000_000L) {
                    vie.subirDegats(10);
                    lastUpdate = now;
                }
            }
        }.start();
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
        clavier = new Clavier(joueurModele, joueurVue, inventaire, vueInventaire); // Passe les instances ici
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        Platform.runLater(() -> tileMap.requestFocus());
    }

    private void initialiserCarte() {
        vueCarte = new VueCarte(Carte.getInstance());
        tileMap.getChildren().add(vueCarte.getTileMap());
    }

   /* private void initialiserItems() {
        listeDesItems = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 5; i++) {
            Item item = new Item(i, "Item_" + i, 1, "Description", Type.CONSOMMABLE, Rarete.COMMUN);
            item.ajouterItem(carte, item);
            listeDesItems.add(item);

            VueItem vueItem = new VueItem(item);
            rootPane.getChildren().add(vueItem.getImageView()); // 🆕 Ajout visuel
        }
    }*/

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
        joueurVue.miseAJourPosition(joueurModele);
        gererVie();
    }


    private void gererVie() {
        if (vie.estMort()) {
            gameLoop.stop();
            clavier.desactiverClavier(tileMap);
            Platform.runLater(() -> overlayDeMort.setVisible(true));
        }
    }

    @FXML
    private void handleQuitter() {
        Platform.exit();
    }


}