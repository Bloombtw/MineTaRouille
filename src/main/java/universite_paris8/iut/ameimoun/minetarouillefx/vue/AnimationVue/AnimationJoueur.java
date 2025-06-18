package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

/**
 * Gère l’animation du joueur (idle, marche gauche/droite, saut) via un AnimationTimer.
 * Sélectionne la bonne animation selon l’état et la direction du joueur.
 */
public class AnimationJoueur {
    /** Frames pour l’animation idle. */
    private Image[] framesIdle;
    /** Frames pour l’animation vers la gauche. */
    private Image[] framesGauche;
    /** Frames pour l’animation vers la droite. */
    private Image[] framesDroite;
    /** Frames pour l’animation de saut. */
    private Image[] framesSaut;

    /** Durée d’une frame pour chaque animation (en ms). */
    private int frameIdleDuration, frameGaucheDuration, frameDroiteDuration, frameSautDuration;

    /** Frames et durée de l’animation courante. */
    private Image[] framesActuelles;
    private int frameActuelleDuration;

    /** Index de la frame courante et temps de la dernière frame. */
    private int frameIndex = 0;
    private long lastFrameTime = 0;

    /** ImageView du joueur à animer. */
    private final ImageView perso;

    /** Timer qui anime le joueur. */
    private final AnimationTimer animationTimer;

    /**
     * Construit l’animation du joueur et démarre l’animation idle.
     * @param perso l’ImageView du joueur à animer
     */
    public AnimationJoueur(ImageView perso) {
        this.perso = perso;
        initialiserAnimations();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (framesActuelles != null && frameActuelleDuration > 0) {
                    if (now - lastFrameTime > frameActuelleDuration * 1_000_000L) {
                        frameIndex = (frameIndex + 1) % framesActuelles.length;
                        perso.setImage(framesActuelles[frameIndex]);
                        lastFrameTime = now;
                    }
                }
            }
        };
        animationTimer.start();
    }

    /**
     * Initialise les frames et durées pour chaque animation du joueur.
     */
    private void initialiserAnimations() {
        framesIdle = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_JOUEUR_IDLE), 32, 32, 4);
        frameIdleDuration = 150;

        framesGauche = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_JOUEUR_GAUCHE), 32, 32, 6);
        frameGaucheDuration = 100;

        framesDroite = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_JOUEUR_DROITE), 32, 32, 6);
        frameDroiteDuration = 100;

        framesSaut = GestionnaireAnimation.decouperSpriteSheet(
                Loader.loadImage(Chemin.ANIMATION_JOUEUR_SAUT), 32, 32, 8);
        frameSautDuration = 120;

        setAnimation(framesIdle, frameIdleDuration);
    }

    /**
     * Met à jour l’animation du joueur selon son état (saut, direction).
     * @param joueur le modèle du joueur
     */
    public void mettreAJourAnimation(Joueur joueur) {
        boolean enSaut = joueur.getVitesseY() != 0;
        if (enSaut) {
            setAnimation(framesSaut, frameSautDuration);
        } else {
            switch (joueur.direction) {
                case GAUCHE -> setAnimation(framesGauche, frameGaucheDuration);
                case DROITE -> setAnimation(framesDroite, frameDroiteDuration);
                default -> setAnimation(framesIdle, frameIdleDuration);
            }
        }
    }

    /**
     * Change les frames et la durée de l’animation courante si besoin.
     * @param frames tableau de frames à utiliser
     * @param frameDuration durée d’une frame (ms)
     */
    private void setAnimation(Image[] frames, int frameDuration) {
        if (this.framesActuelles != frames) {
            this.framesActuelles = frames;
            this.frameActuelleDuration = frameDuration;
            this.frameIndex = 0;
            this.lastFrameTime = System.nanoTime();
            if (frames != null && frames.length > 0)
                perso.setImage(frames[0]);
        }
    }
}