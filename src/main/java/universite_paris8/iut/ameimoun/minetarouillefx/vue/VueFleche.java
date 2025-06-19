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

        // Centrer l'image de la flèche sur sa position logique
        imageView.xProperty().bind(fleche.xProperty().subtract(Constantes.TAILLE_ITEM / 2.0));
        imageView.yProperty().bind(fleche.yProperty().subtract(Constantes.TAILLE_ITEM / 2.0));
    }

    public ImageView getNode() {
        return imageView;
    }
}