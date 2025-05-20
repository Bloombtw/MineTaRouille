package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class VueVie {

    private static final double LARGEUR = 200;
    private static final double HAUTEUR = 20;

    private Rectangle fond;
    private Rectangle barre;
    private StackPane container;

    public VueVie() {
        fond = new Rectangle(LARGEUR, HAUTEUR);
        fond.setFill(Color.DARKGRAY);

        barre = new Rectangle(LARGEUR, HAUTEUR);
        barre.setFill(Color.LIMEGREEN);

        container = new StackPane(fond, barre);
        container.setTranslateX(20); // En haut à gauche
        container.setTranslateY(20);
    }

    public void mettreAJour(double vie, double vieMax) {
        double ratio = vie / vieMax;
        barre.setWidth(LARGEUR * ratio);

        if (ratio > 0.6) {
            barre.setFill(Color.LIMEGREEN);
        } else if (ratio > 0.3) {
            barre.setFill(Color.ORANGE);
        } else {
            barre.setFill(Color.RED);
        }
    }

    public StackPane getNoeud() {
        return container;
    }
}


