package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

public class VueItem {

    public static void afficherItem(Pane mapPane, Item item) {
        Image image = new Image(VueItem.class.getResourceAsStream("/img/items"));
        ImageView imageView = new ImageView(image);

        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        imageView.setLayoutX(item.getX());
        imageView.setLayoutY(item.getY());

        mapPane.getChildren().add(imageView);
    }
}
