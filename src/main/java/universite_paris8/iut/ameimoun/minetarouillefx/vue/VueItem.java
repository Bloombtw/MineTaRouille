

package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class VueItem {
    private final ImageView imageView;

    public VueItem(Item item) {
        Image img = Loader.loadImage(GestionnaireImage.getCheminImage(item));
        imageView = new ImageView(img);
        imageView.setFitWidth(Constantes.TAILLE_ITEM);
        imageView.setFitHeight(Constantes.TAILLE_ITEM);

        // Plus de centrage ici
        imageView.xProperty().bind(item.xProperty());
        imageView.yProperty().bind(item.yProperty());
    }

    public ImageView getImageView() {
        return imageView;
    }
}