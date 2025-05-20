package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

public class VueItem {
    public static final int TAILLE_ITEM = 30;
    private ImageView itemView;

    public VueItem(Item item) {
        Image img = new Image(getClass().getResource(
                "/img/items/item.png").toExternalForm());
        itemView = new ImageView(img);
        itemView.setFitWidth(TAILLE_ITEM);
        itemView.setFitHeight(TAILLE_ITEM);
    }

    public ImageView getImageView() {
        return itemView;
    }

    public void updatePosition(Item item) {
        itemView.setLayoutX(item.getX());
        itemView.setLayoutY(item.getY());
    }
}