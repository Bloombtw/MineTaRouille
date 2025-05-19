package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueItem;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;
import java.util.ArrayList;
import java.util.Random;
import javafx.application.Platform;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;
    @FXML
    private AnchorPane rootPane;

    private Inventaire inventaire;
    private VueInventaire vueInventaire;
    private Clavier clavier;

    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private Carte carte;
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
        initialiserInventaire();
        initialiserControles();
        demarrerBoucleDeJeu();
    }

    private void initialiserJoueur() {
        joueurModele = new Joueur(carte);
        joueurVue = new VueJoueur(joueurModele);
        rootPane.getChildren().add(joueurVue.getImageView());
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
        joueurVue.miseAJourPosition(joueurModele);
    }
}
