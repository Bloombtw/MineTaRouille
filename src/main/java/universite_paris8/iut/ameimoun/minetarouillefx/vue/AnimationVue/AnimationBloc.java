package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;
import javafx.scene.image.ImageView;

public class AnimationBloc {
    private final ImageView imageView;
    private final Image[] frames;
    private final int frameDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private long startTime = 0;
    private boolean mouvementActif = false;
    private double vitesseX = 0.5;
    private double positionXInitiale;
    private double positionYInitiale;
    private final AnimationTimer timer;


    public AnimationBloc(ImageView imageView, String spriteSheetPath, int frameWidth, int frameHeight, int nbFrames, int frameDurationMs, boolean avecMouvement) {
        this.imageView = imageView;
        this.frames = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(spriteSheetPath), frameWidth, frameHeight, nbFrames);
        this.frameDuration = frameDurationMs;
        this.mouvementActif = avecMouvement;

        this.positionXInitiale = imageView.getTranslateX();
        this.positionYInitiale = imageView.getTranslateY();
        this.startTime = System.nanoTime();

        if (frames.length > 0) {
            imageView.setImage(frames[0]);
        }

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                mettreAJourAnimation(now);
                if (mouvementActif) {
                    deplacerBloc(now);
                }
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

    private void deplacerBloc(long now) {
        double tempsEcouleSec = (now - startTime) / 1_000_000_000.0;
        double y = positionYInitiale + Math.sin(tempsEcouleSec * 2) * 5; // 5 pixels d'oscillation
        double x = positionXInitiale + tempsEcouleSec * vitesseX * 60;  // 60 fps estimé

        imageView.setTranslateX(x);
        imageView.setTranslateY(y);
    }
}
