package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import javafx.application.Platform;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueVie;
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
    @FXML
    private AnchorPane overlayDeMort;

    private MusiqueManager musiqueManager;
    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private Clavier clavier;
    private Vie vie;
    private VueVie vueVie;

    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private AnimationTimer gameLoop;
    private VueCarte vueCarte;
    private DebugManager debugManager;
    private Rectangle overlayDegats;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserMusique();
        initialiserCarte();
        //initialiserItems(); à debogguer
        initialiserJoueur();
        initialiserBarreDeVie();
        initialiserInventaire();
        initialiserControles();
        initialiserEffetDegats();
        demarrerBoucleDeJeu();
    }
;

    private void initialiserMusique() {
        musiqueManager = new MusiqueManager();
        musiqueManager.jouerMusiqueEnBoucle("/mp3/Maes_magie.mp3");
        musiqueManager.setVolume(0.5);
    }
    private void initialiserJoueur() {
        joueurModele = new Joueur();
        debugManager = new DebugManager(rootPane, joueurModele);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getNode());
    }

    private void initialiserBarreDeVie() {

        vie = new Vie( joueurModele.getPointsDeVie());
        vie.setControleur(this);
        vueVie = new VueVie();

        // Liaison modèle vue via
        vie.setObservateur(() -> Platform.runLater(() ->
                vueVie.mettreAJour(vie.getVieActuelle(), vie.getVieMax())
        ));

        vueVie.mettreAJour(vie.getVieActuelle(), vie.getVieMax());
        rootPane.getChildren().add(vueVie.getNoeud());

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
        clavier = new Clavier(joueurModele, joueurVue, inventaire, vueInventaire,debugManager); // Passe les instances ici
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
        gererVie();
        if (debugManager.isDebugVisible()) {
            debugManager.update();
        }
    }

    private void initialiserEffetDegats() {
        overlayDegats = new Rectangle(Constantes.NB_COLONNES * Constantes.TAILLE_TUILE,
                Constantes.NB_LIGNES * Constantes.TAILLE_TUILE);
        overlayDegats.setFill(Color.rgb(255, 0, 0, 0.3));
        overlayDegats.setVisible(false);
        rootPane.getChildren().add(overlayDegats);
    }

    public void afficherDegats() {
        Platform.runLater(() -> {
            overlayDegats.setVisible(true);
            new AnimationTimer() {
                private long start = -1;

                @Override
                public void handle(long now) {
                    if (start < 0) start = now;
                    if (now - start > 100_000_000L) { // ~100ms
                        overlayDegats.setVisible(false);
                        stop();
                    }
                }
            }.start();
        });
    }


    private void gererVie() {
        vie.verifierDegats(joueurModele, Carte.getInstance());
        if (vie.estMort()) {
            musiqueManager.arreterMusique();
            musiqueManager.setVolume(1);
            musiqueManager.jouerMusique("/mp3/GTA5_mort.mp3", 1);
            gameLoop.stop();
            clavier.desactiverClavier(tileMap);
            Platform.runLater(() -> overlayDeMort.setVisible(true));
        }
    }

    @FXML
    private void handleQuitter() {
        musiqueManager.arreterMusique();
        Platform.exit();
    }
}