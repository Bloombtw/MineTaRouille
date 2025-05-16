package universite_paris8.iut.ameimoun.minetarouillefx.utils;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;

public class DebugOverlay {


    public static Canvas genererGrille(Carte carte) {
        int largeur = carte.getLargeur();
        int hauteur = carte.getHauteur();
        int taille = Constantes.TAILLE_TUILE;

        Canvas overlay = new Canvas(largeur * taille, hauteur * taille);
        GraphicsContext gc = overlay.getGraphicsContext2D();

        // Grille
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);
        for (int x = 0; x <= largeur; x++) {
            gc.strokeLine(x * taille, 0, x * taille, hauteur * taille);
        }
        for (int y = 0; y <= hauteur; y++) {
            gc.strokeLine(0, y * taille, largeur * taille, y * taille);
        }

        gc.setFill(Color.color(1, 0, 0, 0.3));
        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                for (int couche = 0; couche < carte.getNbCouches(); couche++) {
                    Bloc bloc = carte.getTerrain()[couche][y][x];
                    if (bloc != null && bloc.estSolide()) {
                        gc.fillRect(x * taille, y * taille, taille, taille);
                        break;
                    }
                }
            }
        }

        return overlay;
    }
}
