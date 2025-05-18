package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import java.util.ArrayList;
import java.util.Random;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;

    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private Carte carte;
    private Clavier clavier;
    private AnimationTimer gameLoop;

    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;
    public static final int TAILLE_TUILE = 30;
    private ArrayList<Item> listeDesItems;


    private VueCarte vueCarte;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialiserCarte();
        //initialiserItems(); à debogguer
        initialiserJoueur();
        initialiserControles();
        demarrerBoucleDeJeu();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur(carte);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getImageView());
    }

    private void initialiserControles() {
        clavier = new Clavier(joueurModele,joueurVue);
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();
    }

    private void initialiserCarte() {
        final int NB_LIGNES = HAUTEUR_FENETRE / TAILLE_TUILE;
        final int NB_COLONNES = (LARGEUR_FENETRE / TAILLE_TUILE) + 6;

        carte = new Carte(NB_LIGNES, NB_COLONNES);
        vueCarte = new VueCarte(carte);
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
            }
        };
        gameLoop.start();
    }

    private void mettreAJourJeu() {
        joueurModele.gravite();
        joueurVue.updatePosition(joueurModele);

    }
}
