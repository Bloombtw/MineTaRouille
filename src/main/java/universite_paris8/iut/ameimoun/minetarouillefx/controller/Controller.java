package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private GridPane tileMap;

    private Joueur joueurModele;
    private VueJoueur joueurVue;
    private Carte carte;
    private Clavier clavier;
    private AnimationTimer gameLoop;

    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;
    public static final int TAILLE_TUILE = 30;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final int NB_LIGNES = HAUTEUR_FENETRE / TAILLE_TUILE;
        final int NB_COLONNES = (LARGEUR_FENETRE / TAILLE_TUILE) + 6;
        carte = new Carte(NB_LIGNES, NB_COLONNES);

        Bloc[][][] terrain = carte.getTerrain();
        int nbCouches = carte.getNbCouches();

        for (int y = 0; y < terrain[0].length; y++) {
            for (int x = 0; x < terrain[0][0].length; x++) {
                StackPane cellule = new StackPane();
                for (int layer = 0; layer < nbCouches; layer++) {
                    Bloc bloc = terrain[layer][y][x];
                    if (bloc != null && bloc != Bloc.CIEL) {
                        Image image = getImageAssociee(bloc);
                        if (image != null) {
                            ImageView iv = new ImageView(image);
                            iv.setFitWidth(TAILLE_TUILE);
                            iv.setFitHeight(TAILLE_TUILE);
                            iv.setPreserveRatio(false);
                            cellule.getChildren().add(iv);
                        }
                    }
                }
                tileMap.add(cellule, x, y);
            }
        }

        initialiserJoueur();
        initialiserControles();
        demarrerBoucleDeJeu();
    }


    private void initialiserJoueur() {
        joueurModele = new Joueur(carte);
        joueurVue = new VueJoueur(joueurModele);
        joueurVue.mettreAJourPosition(); // Place l’image du joueur à sa position initiale
        tileMap.getChildren().add(joueurVue.getPerso()); // Ajout à la scène
    }

    private void initialiserControles() {
        clavier = new Clavier(joueurModele);
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();
    }

    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                joueurModele.gravite();
                joueurVue.mettreAJourPosition();
            }
        };
        gameLoop.start();
    }

    private Image getImageAssociee(Bloc bloc) {
        switch (bloc) {
            case CIEL_CLAIR:
                return new Image(getClass().getResource("/img/ciel_clair.png").toExternalForm());
            case PIERRE:
                return new Image(getClass().getResource("/img/pierre.png").toExternalForm());
            case SABLE:
                return new Image(getClass().getResource("/img/sable.png").toExternalForm());
            case TRONC:
                return new Image(getClass().getResource("/img/tronc.png").toExternalForm());
            case FEUILLAGE:
                return new Image(getClass().getResource("/img/feuillage.png").toExternalForm());
            case TERRE:
                return new Image(getClass().getResource("/img/terreAvecHerbe.png").toExternalForm());
            default:
                return null;
        }
    }
}
