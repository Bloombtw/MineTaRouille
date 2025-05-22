package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;

import java.io.InputStream;

public class VueItem {
    public static final int TAILLE_ITEM = 30;
    private ImageView itemView;

    public VueItem(Item item) {
        String nomFichier = item.getNom()
                .toLowerCase()
                .replace("é", "e")
                .replace("è", "e")
                .replace("à", "a")
                .replace("ù", "u")
                .replace(" ", "_");

        String[] cheminsPossibles = {
                "/img/items/" + nomFichier + ".png",
                "/img/blocs/solide/" + nomFichier + ".png"
        };

        Image img = null;
        for (String chemin : cheminsPossibles) {
            InputStream stream = getClass().getResourceAsStream(chemin);
            if (stream != null) {
                img = new Image(stream);
                break;
            }
        }

        // Image par défaut si rien trouvé
        if (img == null) {
            img = new Image(getClass().getResource("/img/items/item.png").toExternalForm());
        }

        itemView = new ImageView(img);
        itemView.setFitWidth(20); // taille plus petite pour l'affichage au sol
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