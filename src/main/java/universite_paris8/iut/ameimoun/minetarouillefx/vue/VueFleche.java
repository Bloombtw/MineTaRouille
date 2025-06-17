package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Fleche;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

public class VueFleche {
    private final ImageView imageView;

    public VueFleche(Fleche fleche) {
        Image image = new Image(getClass().getResourceAsStream("/img/items/fleche.png"));
        imageView = new ImageView(image);
        imageView.setFitWidth(Constantes.TAILLE_ITEM);
        imageView.setFitHeight(Constantes.TAILLE_ITEM);

        // Lier les propriétés x et y de la flèche à l'ImageView
        imageView.xProperty().bind(fleche.xProperty());
        imageView.yProperty().bind(fleche.yProperty());
    }

    public ImageView getNode() {
        return imageView;
    }
}