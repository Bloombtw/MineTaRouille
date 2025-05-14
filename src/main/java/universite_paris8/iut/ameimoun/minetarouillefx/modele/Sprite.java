package universite_paris8.iut.ameimoun.minetarouillefx.modele;
import javafx.scene.image.Image;
import java.io.InputStream;

public class Sprite {

    private Image image;
    private String nom; // Optionnel : pour identifier le sprite

    public Sprite(String chemin) {
        try {
            InputStream is = getClass().getResourceAsStream(chemin);
            if (is == null) {
                throw new IllegalArgumentException("Image non trouvée : " + chemin);
            }
            this.image = new Image(is);
            this.nom = chemin;
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());

        }
    }

    public Image getImage() {
        return image;
    }

    public String getNom() {
        return nom;
    }
}