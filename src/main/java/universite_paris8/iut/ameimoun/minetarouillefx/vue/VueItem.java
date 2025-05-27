package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

import java.io.InputStream;

public class VueItem {
    public static final int TAILLE_ITEM = 30;
    private ImageView itemView;

    public VueItem(Item item) {
        String nomFichier = item.getNom().toLowerCase()
                .replace("é", "e")
                .replace("è", "e")
                .replace("à", "a")
                .replace("ù", "u")
                .replace(" ", "_");

        String cheminImage = "/img/items/" + nomFichier + ".png";
        InputStream stream = getClass().getResourceAsStream(cheminImage);

        if (stream == null) {
            cheminImage = "/img/blocs/solide/" + nomFichier + ".png";
            stream = getClass().getResourceAsStream(cheminImage);
        }

        Image img;
        if (stream != null) {
            img = new Image(stream);
        } else {
            img = new Image(getClass().getResource("/img/items/item.png").toExternalForm()); // fallback
            System.out.println("Image non trouvée pour item au sol : " + nomFichier);
        }

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