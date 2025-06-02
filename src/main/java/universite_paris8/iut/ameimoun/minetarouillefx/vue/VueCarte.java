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
            case CIEL_CLAIR -> Chemin.CIEL_CLAIR;
            case PIERRE -> Chemin.PIERRE;
            case SABLE -> Chemin.SABLE;
            case TRONC -> Chemin.TRONC;
            case FEUILLAGE -> Chemin.FEUILLAGE;
            case TERRE -> Chemin.TERRE;
            case TRANSPARENT -> Chemin.TRANSPARENT;
            case CIEL -> Chemin.CIEL;
            case GAY_CIEL -> Chemin.GAY_CIEL;
            case SABLE_ROUGE -> Chemin.SABLE_ROUGE;
            case CIEL_SOMBRE -> Chemin.CIEL_SOMBRE;
            case CORBEAU -> Chemin.CORBEAU;
            case LUNE -> Chemin.LUNE;
            case LUNE_ZELDA -> Chemin.LUNE_ZELDA;
            case ETOILE -> Chemin.ETOILE;
            case ARBUSTE_MORT -> Chemin.ARBUSTE_MORT;
            case ECHELLE -> Chemin.ECHELLE;
            case FLECHE_VERS_LA_DROITE -> Chemin.FLECHE_VERS_LA_DROITE;
            case ESCALIER_DROITE -> Chemin.ESCALIER_DROITE;
            case GRES_CISELE -> Chemin.GRES_CISELE;
            case GRES_COUPE -> Chemin.GRES_COUPE;
            case POUSSE_ACACIA -> Chemin.POUSSE_ACACIA;
            case FEUILLAGE_ACACIA -> Chemin.FEUILLAGE_ACACIA;
            case GRES -> Chemin.GRES;
            case FEU -> Chemin.FEU;
            case CACTUS -> Chemin.CACTUS;
            case NUAGE -> Chemin.NUAGE;
            case CIEL_VIOLET -> Chemin.CIEL_VIOLET;
            case NUAGE_PARTIE1 -> Chemin.NUAGE_PARTIE1;
            case NUAGE_PARTIE2 -> Chemin.NUAGE_PARTIE2;
            case NUAGE_PARTIE3 -> Chemin.NUAGE_PARTIE3;
            default -> Chemin.DEFAULT;
        };
        return Loader.loadImage(chemin);
    }
}
