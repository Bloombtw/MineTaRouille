package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class AnimationMob {
    private final ImageView mobImage;
    private Image[] framesIdle, framesMarche;
    private int frameIdleDuration, frameMarcheDuration;
    private Image[] framesActuelles;
    private int frameActuelleDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private final AnimationTimer animationTimer;

    public AnimationMob(ImageView mobImage) {
        this.mobImage = mobImage;
        initialiserAnimations();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (framesActuelles != null && frameActuelleDuration > 0) {
                    if (now - lastFrameTime > frameActuelleDuration * 1_000_000L) {
                        frameIndex = (frameIndex + 1) % framesActuelles.length;
                        mobImage.setImage(framesActuelles[frameIndex]);
                        lastFrameTime = now;
                    }
                }
            }
        };
        animationTimer.start();
    }

    private void initialiserAnimations() {
        // Adapte les chemins si besoin
        framesIdle = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_MOB_IDLE), 128, 128, 8
        );
        frameIdleDuration = 200; // ms

        framesMarche = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_MOB_SAUT), 128, 128, 13
        );
        frameMarcheDuration = 120; // ms

        setAnimation(framesIdle, frameIdleDuration);
    }

    /** À appeler à chaque frame ou lors d’un changement d’état */
    public void mettreAJourAnimation(Mob mob) {
        if (mob.getVitesseX() != 0) {
            setAnimation(framesMarche, frameMarcheDuration);
        } else {
            setAnimation(framesIdle, frameIdleDuration);
        }
    }

    private void setAnimation(Image[] frames, int frameDuration) {
        if (this.framesActuelles != frames) {
            this.framesActuelles = frames;
            this.frameActuelleDuration = frameDuration;
            this.frameIndex = 0;
            this.lastFrameTime = System.nanoTime();
            if (frames != null && frames.length > 0)
                mobImage.setImage(frames[0]);
        }
    }

}