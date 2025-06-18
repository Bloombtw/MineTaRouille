package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import static universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader.loadAnimation;

/**
 * Gestionnaire d'animations simples à base d'images individuelles.
 * Fonctionne uniquement avec plusieurs fichiers image (image1.png, image2.png, ...).
 * Pour les spritesheets, utiliser AnimationJoueur ou AnimationBloc.
 */
public class GestionnaireAnimation {

    /**
     * Ajoute une animation à une cellule (Pane) en affichant une suite d'images à intervalle régulier.
     *
     * @param cellule             Pane dans lequel l'animation sera affichée.
     * @param cheminAnimation     Chemin de base des images d'animation (sans numéro ni extension).
     * @param nbFrames            Nombre d'images dans l'animation.
     * @param frameDurationMillis Durée d'affichage de chaque image en millisecondes.
     */
    public static void ajouterAnimation(Pane cellule, String cheminAnimation, int nbFrames, int frameDurationMillis) {
        Image[] frames = loadAnimation(cheminAnimation, nbFrames);

        ImageView imgView = new ImageView(frames[0]);
        imgView.setFitWidth(Constantes.TAILLE_TUILE);
        imgView.setFitHeight(Constantes.TAILLE_TUILE);
        cellule.getChildren().add(imgView);

        final int frameCount = frames.length;
        final int[] frameIndex = {0};

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long frameDuration = frameDurationMillis * 1_000_000L; // conversion ms -> ns

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= frameDuration) {
                    frameIndex[0] = (frameIndex[0] + 1) % frameCount;
                    imgView.setImage(frames[frameIndex[0]]);
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    /**
     * Découpe une planche de sprites horizontale en plusieurs frames distinctes.
     *
     * @param plancheSprite Image source contenant toutes les frames côte à côte.
     * @param largeurFrame  Largeur d'une frame.
     * @param hauteurFrame  Hauteur d'une frame.
     * @param nbFrames      Nombre total de frames à extraire.
     * @return Un tableau d'images correspondant à chaque frame.
     */
    public static Image[] decouperSpriteSheet(Image plancheSprite, int largeurFrame, int hauteurFrame, int nbFrames) {
        Image[] frames = new Image[nbFrames];
        for (int indice = 0; indice < nbFrames; indice++) {
            frames[indice] = new WritableImage(plancheSprite.getPixelReader(), indice * largeurFrame, 0, largeurFrame, hauteurFrame);
        }
        return frames;
    }
}
