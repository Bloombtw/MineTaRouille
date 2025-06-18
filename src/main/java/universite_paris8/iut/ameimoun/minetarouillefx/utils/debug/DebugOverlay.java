package universite_paris8.iut.ameimoun.minetarouillefx.utils.debug;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

/**
 * Classe utilitaire pour générer un canevas d'overlay de debug.
 */
public class DebugOverlay {

    /**
     * Génère un canevas contenant la grille et les blocs solides de la carte.
     *
     * @param carte Carte du jeu.
     * @return Un Canvas prêt à être affiché en overlay.
     */
    public static Canvas genererGrille(Carte carte) {
        int largeur = carte.getLargeur();
        int hauteur = carte.getHauteur();
        int taille = Constantes.TAILLE_TUILE;

        Canvas overlay = new Canvas(largeur * taille, hauteur * taille);
        GraphicsContext gc = overlay.getGraphicsContext2D();

        dessinerGrille(gc, largeur, hauteur, taille);
        surlignerBlocsSolides(gc, carte, taille);

        return overlay;
    }

    /**
     * Dessine la grille grise sur le canevas.
     */
    private static void dessinerGrille(GraphicsContext gc, int largeur, int hauteur, int taille) {
        gc.setStroke(Color.GRAY);
        gc.setLineWidth(0.5);

        for (int x = 0; x <= largeur; x++) {
            gc.strokeLine(x * taille, 0, x * taille, hauteur * taille);
        }
        for (int y = 0; y <= hauteur; y++) {
            gc.strokeLine(0, y * taille, largeur * taille, y * taille);
        }
    }

    /**
     * Surligne les blocs solides de la carte avec un rouge transparent.
     */
    private static void surlignerBlocsSolides(GraphicsContext gc, Carte carte, int taille) {
        int largeur = carte.getLargeur();
        int hauteur = carte.getHauteur();
        int nbCouches = carte.getNbCouches();

        gc.setFill(Color.color(1, 0, 0, 0.3));

        for (int x = 0; x < largeur; x++) {
            for (int y = 0; y < hauteur; y++) {
                for (int couche = 0; couche < nbCouches; couche++) {
                    Bloc bloc = carte.getTerrain()[couche][y][x];
                    if (bloc != null && bloc.estSolide()) {
                        gc.fillRect(x * taille, y * taille, taille, taille);
                        break;
                    }
                }
            }
        }
    }
}
