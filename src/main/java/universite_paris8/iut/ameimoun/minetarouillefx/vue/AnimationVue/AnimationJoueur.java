package universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

/**
 * Classe qui gère l'animation du joueur en fonction de son état (idle, gauche, droite, saut).
 * Utilise un AnimationTimer pour mettre à jour l'image du joueur à chaque frame.
 */
public class AnimationJoueur {
    private Image[] framesIdle, framesGauche, framesDroite, framesSaut;
    private int frameIdleDuration, frameGaucheDuration, frameDroiteDuration, frameSautDuration;

    private Image[] framesActuelles;
    private int frameActuelleDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private final ImageView perso;
    private AnimationTimer animationTimer;

    private void initialiserAnimations() {
        framesIdle = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(Chemin.ANIMATION_JOUEUR_IDLE), 32, 32, 4);
        frameIdleDuration = 150;
        framesGauche = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(Chemin.ANIMATION_JOUEUR_GAUCHE), 32, 32, 6);
        frameGaucheDuration = 120;
        framesDroite = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(Chemin.ANIMATION_JOUEUR_DROITE), 32, 32, 6);
        frameDroiteDuration = 120;
        framesSaut = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage(Chemin.ANIMATION_JOUEUR_SAUT), 32, 32, 8);
        frameSautDuration = 180;
        setAnimation(framesIdle, frameIdleDuration);
    }

    /**
     * Initialise les différentes animations du joueur (idle, gauche, droite, saut).
     */
    public AnimationJoueur(ImageView perso) {
        this.perso = perso;
        initialiserAnimations();
        // AnimationTimer qui s'occupe d'animer le joueur
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
     * Met à jour l'animation du joueur en fonction de son état actuel.
     *
     * @param joueur Le joueur dont l'état est utilisé pour déterminer l'animation.
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
                perso.setImage(frames[0]);
        }
    }


}
