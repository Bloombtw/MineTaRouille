// Dans VueJoueur.java
package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;

public class VueJoueur {

    private Joueur joueur;
    private GraphicsContext gc;
    private Image sprite = Loader.loadImage("/joueur.png");

    public VueJoueur(Joueur joueur, GraphicsContext gc) {
        this.joueur = joueur;
        this.gc = gc;
    }

    public void mettreAJour() {
        gc.clearRect(0, 0, 800, 600); // si nécessaire
        gc.drawImage(sprite, joueur.getX(), joueur.getY(), 20, 20);
    }

}