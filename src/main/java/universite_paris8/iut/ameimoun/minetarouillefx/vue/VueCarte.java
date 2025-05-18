package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;

public class VueCarte {
    private final TilePane tileMap;
    private final Carte carte;
    private static final int TAILLE_TUILE = 30;

    public VueCarte(Carte carte) {
        this.carte = carte;
        tileMap = new TilePane();
        tileMap.setPrefColumns(carte.getLargeur());//pour bien adapter la map à la fenêtre
        tileMap.setPrefRows(carte.getHauteur());

        initialiserCarte();
    }

    private void ajouterBloc(Bloc bloc, StackPane cellule) {
        if (bloc != null && bloc != Bloc.CIEL) {
            Image image = getImageAssociee(bloc);
            if (image != null) {
                ImageView img = new ImageView(image);
                img.setFitWidth(TAILLE_TUILE);
                img.setFitHeight(TAILLE_TUILE);
                cellule.getChildren().add(img);
            }
        }
    }

    private void initialiserCarte() {
        Bloc[][][] terrain = carte.getTerrain();
        int nbCouches = carte.getNbCouches();

        for (int y = 0; y < terrain[0].length; y++) {
            for (int x = 0; x < terrain[0][0].length; x++) {
                StackPane cellule = new StackPane();
                for (int layer = 0; layer < nbCouches; layer++) {
                    ajouterBloc(terrain[layer][y][x], cellule);
                }
                tileMap.getChildren().add(cellule);
            }
        }
    }

    public TilePane getTileMap() {
        return tileMap;
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
            case ETOILE:
                return new Image(getClass().getResource("/img/decors/etoile.png").toExternalForm());
            case ARBUSTE_MORT:
                return new Image(getClass().getResource("/img/decors/arbuste_mort.png").toExternalForm());
            default:
                return null;
        }
    }
}