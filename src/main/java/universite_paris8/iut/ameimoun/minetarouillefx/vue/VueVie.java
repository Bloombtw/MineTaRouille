package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;

public class VueVie extends StackPane {
    private final Rectangle fond;
    private final Rectangle barreVie;

    public VueVie(int largeur, int hauteur) {
        fond = new Rectangle(largeur, hauteur);
        fond.setFill(Color.GRAY);
        fond.setStroke(Color.BLACK);

        barreVie = new Rectangle(largeur, hauteur);
        barreVie.setFill(Color.GREEN);
        barreVie.setStroke(Color.BLACK);

        this.getChildren().addAll(fond, barreVie);
    }

    public void lierAuJoueur(Joueur joueur) {
        // Conversion explicite en double pour éviter l'erreur
        barreVie.widthProperty().bind(
                joueur.vieActuelleProperty()
                        .multiply(fond.getWidth())
                        .divide(joueur.getVieMax())
        );
        joueur.vieActuelleProperty().addListener((obs, oldVal, newVal) -> {
            double pourcentage = newVal.doubleValue() / joueur.getVieMax();
            if (pourcentage < 0.3) {
                barreVie.setFill(Color.RED);
            } else if (pourcentage < 0.6) {
                barreVie.setFill(Color.ORANGE);
            } else {
                barreVie.setFill(Color.GREEN);
            }
        });
    }
}


