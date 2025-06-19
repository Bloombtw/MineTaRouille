package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationBloc;

public class VueCarte {
    private final TilePane tileMap;
    private final Carte carte;

    public VueCarte(Carte carte) {
        this.carte = carte;
        tileMap = new TilePane();
        tileMap.setPrefColumns(carte.getLargeur());
        tileMap.setPrefRows(carte.getHauteur());
        tileMap.setPrefTileWidth(Constantes.TAILLE_TUILE);
        tileMap.setPrefTileHeight(Constantes.TAILLE_TUILE);
        tileMap.setHgap(0);
        tileMap.setVgap(0);
        initialiserCarte();
    }

    private void ajouterBloc(Bloc bloc, Pane cellule) {
        if (bloc != null) {
            switch (bloc) {
                case FEU -> GestionnaireAnimation.ajouterAnimation(cellule, Chemin.ANIMATION_DECOUPEE_EXPLOSION_FEU, 6, 60);
                case CORBEAU -> {
                    ImageView corbeauView = new ImageView();
                    corbeauView.setFitWidth(Constantes.TAILLE_TUILE);
                    corbeauView.setFitHeight(Constantes.TAILLE_TUILE);
                    cellule.getChildren().add(corbeauView);
                    new AnimationBloc(corbeauView, Chemin.ANIMATION_CORBEAU_VOLE, 32, 32, 6, 120);
                }
                default -> {
                    Image image = getImageAssociee(bloc);
                    ImageView img = new ImageView(image);
                    img.setFitWidth(Constantes.TAILLE_TUILE);
                    img.setFitHeight(Constantes.TAILLE_TUILE);
                    cellule.getChildren().add(img);
                }
            }
        }
    }

    private void initialiserCarte() {
        Bloc[][][] terrain = carte.getTerrain();
        int nbCouches = carte.getNbCouches();

        for (int y = 0; y < terrain[0].length; y++) {
            for (int x = 0; x < terrain[0][0].length; x++) {
                Pane cellule = new Pane();
                cellule.setPrefSize(Constantes.TAILLE_TUILE, Constantes.TAILLE_TUILE);
                for (int layer = 0; layer < nbCouches; layer++) {
                    ajouterBloc(terrain[layer][y][x], cellule);
                }
                tileMap.getChildren().add(cellule);
            }
        }
    }

    public void mettreAJourAffichage(int x, int y) {
        int index = y * carte.getLargeur() + x;
        Pane cellule = (Pane) tileMap.getChildren().get(index);
        cellule.getChildren().clear();
        for (int couche = 0; couche < carte.getNbCouches(); couche++) {
            Bloc bloc = carte.getTerrain()[couche][y][x];
            if (bloc != null) {
                ajouterBloc(bloc, cellule);
            }
        }
    }

    public TilePane getTileMap() {
        return tileMap;
    }

    private Image getImageAssociee(Bloc bloc) {
        String chemin = GestionnaireImage.getCheminImage(bloc);
        return Loader.loadImage(chemin);
    }
}
