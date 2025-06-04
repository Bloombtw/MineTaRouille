package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireImage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

import java.io.InputStream;

public class VueItem {
    public static final int TAILLE_ITEM = 30;
    private ImageView itemView;

    public VueItem(Item item) {
        Image img = Loader.loadImage(GestionnaireImage.getCheminImage(item));
        itemView = new ImageView(img);
        itemView.setFitWidth(20);
        itemView.setFitHeight(20);
    }


    public ImageView getImageView() {
        return itemView;
    }

    public void updatePosition(Item item) {
        itemView.setLayoutX(item.getX());
        itemView.setLayoutY(item.getY());
    }
}