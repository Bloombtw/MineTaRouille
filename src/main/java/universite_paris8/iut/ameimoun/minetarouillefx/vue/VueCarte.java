package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueCarte {
    private final TilePane tileMap;
    private final Carte carte;
    private static final int TAILLE_TUILE = 30;

    public VueCarte(Carte carte) {
        this.carte = carte;
        tileMap = new TilePane();
        tileMap.setPrefColumns(carte.getLargeur());
        tileMap.setPrefRows(carte.getHauteur());
        initialiserCarte();
    }

    private void ajouterBloc(Bloc bloc, Pane cellule) {
        // Now, we also check if the block is not CIEL_VIOLET
        if (bloc != null && bloc != Bloc.CIEL && bloc != Bloc.CIEL_VIOLET) { // <-- MODIFIED LINE
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
                Pane cellule = new Pane();
                for (int layer = 0; layer < nbCouches; layer++) {
                    ajouterBloc(terrain[layer][y][x], cellule);
                }
                tileMap.getChildren().add(cellule);
            }
        }
    }


    public void mettreAJourAffichage() {
        tileMap.getChildren().clear(); // Efface toutes les tuiles existantes
        Bloc[][][] terrain = carte.getTerrain();
        int nbCouches = carte.getNbCouches();

        for (int y = 0; y < terrain[0].length; y++) {
            for (int x = 0; x < terrain[0][0].length; x++) {
                Pane cellule = new Pane(); // Chaque cellule est un Pane pour empiler les couches
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
            case CIEL_SOMBRE -> "/img/blocs/traversable/ciel_sombre.png";
            case CORBEAU -> "/img/decors/corbeau.png";
            case LUNE -> "/img/decors/lune.png";
            case LUNE_ZELDA -> "/img/decors/lune_zelda.jpg";
            case ETOILE -> "/img/decors/etoile.png";
            case ARBUSTE_MORT -> "/img/decors/arbuste_mort.png";
            case ECHELLE -> "/img/decors/echelle.png";
            case FLECHE_VERS_LA_DROITE -> "/img/decors/flecheVersDroite.png";
            case ESCALIER_DROITE -> "/img/decors/escalier_Droite.png";
            case GRES_CISELE -> "/img/blocs/solide/gres_cisele.png";
            case GRES_COUPE -> "/img/blocs/solide/gres_coupe.png";
            case POUSSE_ACACIA -> "/img/decors/pousse_acacia.png";
            case FEUILLAGE_ACACIA -> "/img/blocs/solide/feuillage_acacia.png";
            case GRES -> "/img/blocs/solide/gres.png";
            case FEU -> "/img/decors/feu.png";
            case CACTUS -> "/img/blocs/solide/cactus.png";
            case NUAGE -> "/img/blocs/traversable/nuage.png";
            case CIEL_VIOLET -> "/img/blocs/traversable/ciel_violet.png";
            case NUAGE_PARTIE1 -> "/img/blocs/traversable/nuage_partie1.png";
            case NUAGE_PARTIE2 -> "/img/blocs/traversable/nuage_partie2.png";
            case NUAGE_PARTIE3 -> "/img/blocs/traversable/nuage_partie3.png";
            default -> "/img/default.png"; // Dans le cas ou le bloc n'est pas trouvé
        };
        return Loader.loadImage(chemin);

    }
}