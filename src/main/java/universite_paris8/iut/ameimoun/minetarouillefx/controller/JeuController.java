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
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
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
    private DebugManager debugManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        tileMap.setPrefColumns(Constantes.NB_COLONNES);
        tileMap.setPrefTileWidth(Constantes.TAILLE_TUILE);
        tileMap.setPrefTileHeight(Constantes.TAILLE_TUILE);
        carte = Carte.getInstance();

        Bloc[][][] terrain = carte.getTerrain();
        int nbCouches = carte.getNbCouches();

        for (int y = 0; y < terrain[0].length; y++) {
            for (int x = 0; x < terrain[0][0].length; x++) {
                StackPane cellule = new StackPane();
                for (int layer = 0; layer < nbCouches; layer++) {
                    Bloc bloc = terrain[layer][y][x];
                    if (bloc != null && bloc != Bloc.CIEL) {
                        Image image = getImageAssociee(bloc);
                            ImageView iv = new ImageView(image);
                            iv.setFitWidth(Constantes.TAILLE_TUILE);
                            iv.setFitHeight(Constantes.TAILLE_TUILE);
                            iv.setPreserveRatio(false);
                            cellule.getChildren().add(iv);
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
        joueur = new Joueur();
        debugManager = new DebugManager(rootPane, carte, joueur);
        joueur.getPerso().setTranslateX(joueur.getX());
        joueur.getPerso().setTranslateY(joueur.getY());

        rootPane.getChildren().add(joueur.getPerso());
    }

    private void initialiserControles() {
        clavier = new Clavier(joueur, debugManager);
        clavier.gestionClavier(tileMap);
        tileMap.setFocusTraversable(true);
        tileMap.requestFocus();
    }

    private void demarrerBoucleDeJeu() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                joueur.gravite();  // applique gravité au joueur

                // MAJ la position visuelle
                joueur.getPerso().setTranslateX(joueur.getX());
                joueur.getPerso().setTranslateY(joueur.getY());
                debugManager.update();
            }
        };
        gameLoop.start();
    }



    private Image getImageAssociee(Bloc bloc) {
        String chemin = switch (bloc) {
            case CIEL_CLAIR -> "/img/blocs/traversable/ciel_clair.png";
            case PIERRE -> "/img/blocs/solide/pierre.png";
            case SABLE -> "/img/blocs/solide/sable.png";
            case TRONC -> "/img/blocs/solide/tronc.png";
            case FEUILLAGE -> "/img/blocs/solide/feuillage.png";
            case TERRE -> "/img/blocs/solide/terre.png";
            case TRANSPARENT -> "/img/blocs/traversable/transparent.png";
            case CIEL -> "/img/blocs/traversable/ciel.png";
            case GAY_CIEL -> "/img/blocs/traversable/gayciel.png";
            case SABLE_ROUGE -> "/img/blocs/solide/sable_rouge.png";
            case TERRE_STYLEE -> "/img/blocs/solide/terre_stylee.png";
            case TERRE_STYLEE_SOMBRE -> "/img/blocs/solide/terre_stylee_sombre.png";
            case CIEL_SOMBRE -> "/img/blocs/traversable/ciel_sombre.png";
            case CORBEAU -> "/img/decors/corbeau.png";
            case LUNE -> "/img/decors/lune.png";
            case LUNE_ZELDA -> "/img/decors/lune_zelda.jpg";
            case ETOILE -> "/img/decors/etoile.png";
            case ARBUSTE_MORT -> "/img/decors/arbuste_mort.png";
            case ECHELLE -> "/img/decors/echelle.png";
            case FLECHE_VERS_LA_DROITE -> "/img/decors/flecheVersDroite.png";
            case ESCALIER_DROITE -> "/img/decors/escalier_Droite.png";
            default -> "/img/default.png"; // Dans le cas ou le bloc n'est pas trouvé
        };
        return Loader.loadImage(chemin);
    }
}
