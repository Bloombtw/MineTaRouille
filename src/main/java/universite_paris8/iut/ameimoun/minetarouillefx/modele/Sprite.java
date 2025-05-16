package universite_paris8.iut.ameimoun.minetarouillefx.modele;

import javafx.scene.image.Image;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class Sprite {

    private Image image;
    private String nom;

    public Sprite(String chemin) {
            this.image = Loader.loadImage(chemin);
            this.nom = chemin;
    }

}
