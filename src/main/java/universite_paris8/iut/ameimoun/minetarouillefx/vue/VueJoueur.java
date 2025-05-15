// Dans VueJoueur.java
package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class VueJoueur {

    private Joueur joueur;
    private GraphicsContext gc;
    private Image sprite = new Image(getClass().getResourceAsStream("/joueur.png"));

    public VueJoueur(Joueur joueur, GraphicsContext gc) {
        this.joueur = joueur;
        this.gc = gc;
    }

    public void mettreAJour() {
        gc.clearRect(0, 0, 800, 600); // si nécessaire
        gc.drawImage(sprite, joueur.getX(), joueur.getY(), 20, 20);
    }

}