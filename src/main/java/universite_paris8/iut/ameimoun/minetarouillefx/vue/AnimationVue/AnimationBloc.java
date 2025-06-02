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

    private void mettreAJourAnimation(long now) {
        if (now - lastFrameTime > frameDuration * 1_000_000L) {
            frameIndex = (frameIndex + 1) % frames.length;
            imageView.setImage(frames[frameIndex]);
            lastFrameTime = now;
        }
    }

    public void changerAnimation(String spriteSheetPath, int frameWidth, int frameHeight, int nbFrames, int frameDurationMs) {
        this.frames = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(spriteSheetPath), frameWidth, frameHeight, nbFrames);
        this.frameDuration = frameDurationMs;
        this.frameIndex = 0;
        this.lastFrameTime = 0;
        if (frames.length > 0) {
            imageView.setImage(frames[0]);
        }
    }

    public void stop() {
        timer.stop();
    }
}
