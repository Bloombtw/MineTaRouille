package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class AnimationBloc {
    private final ImageView imageView;
    private Image[] frames;
    private int frameDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private final AnimationTimer timer;


    /**
     * Crée une animation pour un bloc à partir d'une sprite sheet.
     *
     * @param imageView        L'ImageView où afficher l'animation.
     * @param spriteSheetPath  Le chemin vers la sprite sheet à utiliser.
     * @param frameWidth       Largeur d'une frame dans la sprite sheet.
     * @param frameHeight      Hauteur d'une frame dans la sprite sheet.
     * @param nbFrames         Nombre total de frames dans la sprite sheet.
     * @param frameDurationMs  Durée d'affichage de chaque frame en millisecondes.
     */

    public AnimationBloc(ImageView imageView, String spriteSheetPath, int frameWidth, int frameHeight, int nbFrames, int frameDurationMs) {
        this.imageView = imageView;
        this.frames = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(spriteSheetPath), frameWidth, frameHeight, nbFrames);
        this.frameDuration = frameDurationMs;

        if (frames.length > 0) {
            imageView.setImage(frames[0]);
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourAnimation(now);
            }
        };
        timer.start();
    }

    /**
     *  Met à jour l'animation du bloc en changeant l'image affichée si la durée d'une frame est écoulée.
     *
     * @param now Le temps actuel généralement fourni par AnimationTimer.
     */
    private void mettreAJourAnimation(long now) {
        if (now - lastFrameTime > frameDuration * 1_000_000L) {
            frameIndex = (frameIndex + 1) % frames.length;
            imageView.setImage(frames[frameIndex]);
            lastFrameTime = now;
        }
    }

}
