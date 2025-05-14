// Dans VueJoueur.java
package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class VueJoueur {

    private Joueur joueur;
    private GraphicsContext gc;

    public VueJoueur(Joueur joueur, GraphicsContext gc) {
        this.joueur = joueur;
        this.gc = gc;
    }

    public void mettreAJour() {
        gc.setFill(Color.BLUE);
        gc.fillRect(joueur.getX(),joueur.getY(), 20, 20);
    }
}