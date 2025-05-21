package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.beans.binding.Bindings; // Importer ceci
import javafx.beans.value.ChangeListener; // Importer ceci
import javafx.beans.value.ObservableValue; // Importer ceci
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Vie; // Importer la classe Vie

public class VueVie {

    private static final double LARGEUR_BARRE_MAX = 200; // Renommé pour plus de clarté
    private static final double HAUTEUR = 20;

    private Rectangle fond;
    private Rectangle barre;
    private StackPane container;

    public VueVie(Vie vieModele) {
        fond = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        fond.setFill(Color.DARKGRAY);

        barre = new Rectangle(LARGEUR_BARRE_MAX, HAUTEUR);
        barre.setFill(Color.LIMEGREEN);

        container = new StackPane(fond, barre);
        container.setTranslateX(20);
        container.setTranslateY(20);

        barre.widthProperty().bind(
                vieModele.vieActuelleProperty().divide(vieModele.vieMaxProperty()).multiply(LARGEUR_BARRE_MAX)
        );
        vieModele.vieActuelleProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double vieActuelle = newValue.doubleValue();
                double vieMax = vieModele.getVieMax(); // Ou vieModele.vieMaxProperty().get()

                double ratio = vieActuelle / vieMax;

                if (ratio > 0.6) {
                    barre.setFill(Color.LIMEGREEN);
                } else if (ratio > 0.3) {
                    barre.setFill(Color.ORANGE);
                } else {
                    barre.setFill(Color.RED);
                }
            }
        });
        mettreAJourCouleurInitiale(vieModele.getVieActuelle(), vieModele.getVieMax());
    }

    // Petite méthode utilitaire pour la couleur initiale, appelée une fois dans le constructeur
    private void mettreAJourCouleurInitiale(double vie, double vieMax) {
        double ratio = vie / vieMax;
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