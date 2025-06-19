package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

/**
 * La classe AnimationMob gère les animations graphiques des mobs.
 * Elle permet de changer l'animation en fonction de l'état du mob (idle, marche).
 */
public class AnimationMob {
    private final ImageView mobImage;
    private Image[] framesIdle, framesMarche;
    private int frameIdleDuration, frameMarcheDuration;
    private Image[] framesActuelles;
    private int frameActuelleDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private final AnimationTimer animationTimer;

    private void initialiserAnimations(String cheminIdle, String cheminMarche, int taille, int nbFramesIdle, int nbFramesMarche) {
        framesIdle = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(cheminIdle), taille, taille, nbFramesIdle
        );
        frameIdleDuration = 150; // ms

        framesMarche = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(cheminMarche), taille, taille, nbFramesMarche
        );
        frameMarcheDuration = 120; // ms

        setAnimation(framesIdle, frameIdleDuration);
    }

    public AnimationMob(ImageView mobImage, String cheminIdle, String cheminMarche, int taille, int nbFramesIdle, int nbFramesMarche) {
        this.mobImage = mobImage;
        initialiserAnimations(cheminIdle, cheminMarche, taille, nbFramesIdle, nbFramesMarche);

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

    /**
     * Met à jour l'animation du mob en fonction de son état actuel.
     *
     * @param mob Le mob dont l'état est utilisé pour déterminer l'animation.
     */
    public void mettreAJourAnimation(Mob mob) {
        if (mob.getVitesseX() != 0) {
            setAnimation(framesMarche, frameMarcheDuration);
        } else {
            setAnimation(framesIdle, frameIdleDuration);
        }
    }

    /**
     * Met à jour les frames et la durée de l'animation courante.
     *
     * @param frames Les frames de l'animation à afficher.
     * @param frameDuration La durée de chaque frame en millisecondes.
     */
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