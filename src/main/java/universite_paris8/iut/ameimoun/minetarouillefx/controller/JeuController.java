package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

import java.net.URL;
import java.util.ResourceBundle;

public class JeuController implements Initializable {
    @FXML
    private TilePane tileMap;

    @FXML
    private AnchorPane rootPane;

    private Joueur joueur;
    private Carte carte;
    private Clavier clavier;
    private AnimationTimer gameLoop;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        tileMap.setPrefColumns(Constantes.NB_COLONNES);  // pour garder une grille alignée
        tileMap.setPrefTileWidth(Constantes.TAILLE_TUILE);
        tileMap.setPrefTileHeight(Constantes.TAILLE_TUILE);
        carte = new Carte(Constantes.NB_LIGNES, Constantes.NB_COLONNES);

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
                            iv.setFitWidth(Constantes.TAILLE_TUILE);
                            iv.setFitHeight(Constantes.TAILLE_TUILE);
                            iv.setPreserveRatio(false);
                            cellule.getChildren().add(iv);
                        }
                    }
                }
                tileMap.getChildren().add(cellule);
            }
        }

        initialiserJoueur();
        initialiserControles();
        demarrerBoucleDeJeu();
    }

    private void initialiserJoueur() {
        joueur = new Joueur(carte);
        joueur.getPerso().setTranslateX(joueur.getX());
        joueur.getPerso().setTranslateY(joueur.getY());

        rootPane.getChildren().add(joueur.getPerso());
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
                joueur.gravite();  // applique la gravité au joueur

                // Met à jour la position visuelle du joueur à l'écran
                joueur.getPerso().setTranslateX(joueur.getX());
                joueur.getPerso().setTranslateY(joueur.getY());
            }
        };
        gameLoop.start();
    }


    private Image getImageAssociee(Bloc bloc) {
        switch (bloc) {
            case CIEL_CLAIR:
                return Loader.loadImage("/img/blocs/traversable/ciel_clair.png");
            case PIERRE:
                return Loader.loadImage("/img/blocs/solide/pierre.png");
            case SABLE:
                return Loader.loadImage("/img/blocs/solide/sable.png");
            case TRONC:
                return Loader.loadImage("/img/blocs/solide/tronc.png");
            case FEUILLAGE:
                return Loader.loadImage("/img/blocs/solide/feuillage.png");
            case TERRE:
                return Loader.loadImage("/img/blocs/solide/terre.png");
            case TRANSPARENT:
                return Loader.loadImage("/img/blocs/traversable/transparent.png");
            case CIEL:
                return Loader.loadImage("/img/blocs/traversable/ciel.png");
            case GAY_CIEL:
                return Loader.loadImage("/img/blocs/traversable/gayciel.png");
            case SABLE_ROUGE:
                return Loader.loadImage("/img/blocs/solide/sable_rouge.png");
            case TERRE_STYLEE:
                return Loader.loadImage("/img/blocs/solide/terre_stylee.png");
            case TERRE_STYLEE_SOMBRE:
                return Loader.loadImage("/img/blocs/solide/terre_stylee_sombre.png");
            case CIEL_SOMBRE:
                return Loader.loadImage("/img/blocs/traversable/ciel_sombre.png");
            case CORBEAU:
                return Loader.loadImage("/img/decors/corbeau.png");
            case LUNE:
                return Loader.loadImage("/img/decors/lune.png");
            case LUNE_ZELDA:
                return Loader.loadImage("/img/decors/lune_zelda.jpg");
            case ETOILE:
                return Loader.loadImage("/img/decors/etoile.png");
            case ARBUSTE_MORT:
                return Loader.loadImage("/img/decors/arbuste_mort.png");
            default:
                return null;
        }
    }
}
