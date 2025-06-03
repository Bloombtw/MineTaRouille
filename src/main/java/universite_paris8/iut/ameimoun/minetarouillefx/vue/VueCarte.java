package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationBloc;

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

    private void ajouterBloc(Bloc bloc, Pane cellule) {
        if (bloc != null) {
            switch (bloc) {
                case FEU -> {
                    GestionnaireAnimation.ajouterAnimation(cellule, Chemin.ANIMATION_DECOUPEE_EXPLOSION_FEU, 6, 60);
                    break;
                }
                case CORBEAU -> {
                    ImageView corbeauView = new ImageView();
                    corbeauView.setFitWidth(TAILLE_TUILE);
                    corbeauView.setFitHeight(TAILLE_TUILE);
                    cellule.getChildren().add(corbeauView);
                    new AnimationBloc(corbeauView, Chemin.ANIMATION_CORBEAU_VOLE, 32, 32, 6, 120);
                    break;
                }
                default -> {
                    Image image = getImageAssociee(bloc);
                    ImageView img = new ImageView(image);
                    img.setFitWidth(TAILLE_TUILE);
                    img.setFitHeight(TAILLE_TUILE);
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
            if (bloc != null ) {
                ajouterBloc(bloc, cellule);
            }
        }
    }




    public TilePane getTileMap() {
        return tileMap;
    }

    private Image getImageAssociee(Bloc bloc) {
        String chemin = switch (bloc) {
            case CIEL_CLAIR -> Chemin.IMAGE_CIEL_CLAIR;
            case PIERRE -> Chemin.IMAGE_PIERRE;
            case SABLE -> Chemin.IMAGE_SABLE;
            case TRONC -> Chemin.IMAGE_TRONC;
            case FEUILLAGE -> Chemin.IMAGE_FEUILLAGE;
            case TERRE -> Chemin.IMAGE_TERRE;
            case TRANSPARENT -> Chemin.IMAGE_TRANSPARENT;
            case CIEL -> Chemin.IMAGE_CIEL;
            case GAY_CIEL -> Chemin.IMAGE_GAY_CIEL;
            case SABLE_ROUGE -> Chemin.IMAGE_SABLE_ROUGE;
            case CIEL_SOMBRE -> Chemin.IMAGE_CIEL_SOMBRE;
            case CORBEAU -> Chemin.IMAGE_DECORS_CORBEAU;
            case LUNE -> Chemin.IMAGE_DECORS_LUNE;
            case LUNE_ZELDA -> Chemin.IMAGE_DECORS_LUNE_ZELDA;
            case ETOILE -> Chemin.IMAGE_DECORS_ETOILE;
            case ARBUSTE_MORT -> Chemin.IMAGE_DECORS_ARBUSTE_MORT;
            case ECHELLE -> Chemin.IMAGE_DECORS_ECHELLE;
            case FLECHE_VERS_LA_DROITE -> Chemin.IMAGE_DECORS_FLECHE_VERS_LA_DROITE;
            case ESCALIER_DROITE -> Chemin.IMAGE_DECORS_ESCALIER_DROITE;
            case GRES_CISELE -> Chemin.IMAGE_GRES_CISELE;
            case GRES_COUPE -> Chemin.IMAGE_GRES_COUPE;
            case POUSSE_ACACIA -> Chemin.IMAGE_DECORS_POUSSE_ACACIA;
            case FEUILLAGE_ACACIA -> Chemin.IMAGE_FEUILLAGE_ACACIA;
            case GRES -> Chemin.IMAGE_GRES;
            case FEU -> Chemin.IMAGE_DECORS_FEU;
            case CACTUS -> Chemin.IMAGE_CACTUS;
            case NUAGE -> Chemin.IMAGE_NUAGE;
            case CIEL_VIOLET -> Chemin.IMAGE_CIEL_VIOLET;
            case NUAGE_PARTIE1 -> Chemin.IMAGE_NUAGE_PARTIE1;
            case NUAGE_PARTIE2 -> Chemin.IMAGE_NUAGE_PARTIE2;
            case NUAGE_PARTIE3 -> Chemin.IMAGE_NUAGE_PARTIE3;
            default -> Chemin.IMAGE_DEFAULT;
        };
        return Loader.loadImage(chemin);
    }
}
