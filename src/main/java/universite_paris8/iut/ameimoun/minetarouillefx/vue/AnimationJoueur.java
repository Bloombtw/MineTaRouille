package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.GestionnaireAnimation;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader;

public class AnimationJoueur {
    private Image[] framesIdle, framesGauche, framesDroite, framesSaut;
    private int frameIdleDuration, frameGaucheDuration, frameDroiteDuration, frameSautDuration;

    private Image[] framesActuelles;
    private int frameActuelleDuration;
    private int frameIndex = 0;
    private long lastFrameTime = 0;
    private final ImageView perso;
    private AnimationTimer animationTimer;


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


    private void initialiserAnimations() {
        framesIdle = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage("/img/joueur/idle.png"), 32, 32, 4);
        frameIdleDuration = 150;
        framesGauche = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage("/img/joueur/gauche.png"), 32, 32, 6);
        frameGaucheDuration = 100;
        framesDroite = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage("/img/joueur/droite.png"), 32, 32, 6);
        frameDroiteDuration = 100;
        framesSaut = GestionnaireAnimation.decouperSpriteSheet(Loader.loadImage("/img/joueur/saut.png"), 32, 32, 8);
        frameSautDuration = 120;

        // On démarre en idle
        setAnimation(framesIdle, frameIdleDuration);
    }

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

    /** Met à jour les frames à utiliser pour l’animation courante */
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
