package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueItem {

    public static void afficherItem(Pane mapPane, Item item) {
        Image image = Loader.loadImage("/img/items");
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(Constantes.TAILLE_ITEM);
        imageView.setFitHeight(Constantes.TAILLE_ITEM);
        imageView.setLayoutX(item.getX());
        imageView.setLayoutY(item.getY());

        mapPane.getChildren().add(imageView);
    }
}
