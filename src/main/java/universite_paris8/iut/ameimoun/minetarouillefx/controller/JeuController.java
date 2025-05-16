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

    private static final int LARGEUR_FENETRE = 1680;
    private static final int HAUTEUR_FENETRE = 1050;
    public static final int TAILLE_TUILE = 30;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final int NB_LIGNES = HAUTEUR_FENETRE / TAILLE_TUILE;
        final int NB_COLONNES = (LARGEUR_FENETRE / TAILLE_TUILE) + 6;
        tileMap.setPrefColumns(NB_COLONNES);  // pour garder une grille alignée
        tileMap.setPrefTileWidth(TAILLE_TUILE);
        tileMap.setPrefTileHeight(TAILLE_TUILE);
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
                return new Image(getClass().getResource("/img/blocs/traversable/ciel_clair.png").toExternalForm());
            case PIERRE:
                return new Image(getClass().getResource("/img/blocs/solide/pierre.png").toExternalForm());
            case SABLE:
                return new Image(getClass().getResource("/img/blocs/solide/sable.png").toExternalForm());
            case TRONC:
                return new Image(getClass().getResource("/img/blocs/solide/tronc.png").toExternalForm());
            case FEUILLAGE:
                return new Image(getClass().getResource("/img/blocs/solide/feuillage.png").toExternalForm());
            case TERRE:
                return new Image(getClass().getResource("/img/blocs/solide/terre.png").toExternalForm());
            case TRANSPARENT:
                return new Image(getClass().getResource("/img/blocs/traversable/transparent.png").toExternalForm());
            case CIEL:
                return new Image(getClass().getResource("/img/blocs/traversable/ciel.png").toExternalForm());
            case GAY_CIEL:
                return new Image(getClass().getResource("/img/blocs/traversable/gayciel.png").toExternalForm());
            case SABLE_ROUGE:
                return new Image(getClass().getResource("/img/blocs/solide/sable_rouge.png").toExternalForm());
            case TERRE_STYLEE:
                return new Image(getClass().getResource("/img/blocs/solide/terre_stylee.png").toExternalForm());
            case TERRE_STYLEE_SOMBRE:
                return new Image(getClass().getResource("/img/blocs/solide/terre_stylee_sombre.png").toExternalForm());
            case CIEL_SOMBRE:
                return new Image(getClass().getResource("/img/blocs/traversable/ciel_sombre.png").toExternalForm());
            case CORBEAU:
                return new Image(getClass().getResource("/img/decors/corbeau.png").toExternalForm());
            case LUNE:
                return new Image(getClass().getResource("/img/decors/lune.png").toExternalForm());
            case LUNE_ZELDA:
                return new Image(getClass().getResource("/img/decors/lune_zelda.jpg").toExternalForm());
            case ETOILE:
                return new Image(getClass().getResource("/img/decors/etoile.png").toExternalForm());
            case ARBUSTE_MORT:
                return new Image(getClass().getResource("/img/decors/arbuste_mort.png").toExternalForm());
            default:
                return null;
        }
    }
}
