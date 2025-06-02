package universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import static universite_paris8.iut.ameimoun.minetarouillefx.utils.gestionnaire.Loader.loadAnimation;

/*
    Le gestionnaire d'animations marche seulement si on à plusieurs fichiers distincts.
    Sinon, il faut utiliser AnimationJoueur ou AnimationBloc qui permettent de découper l'image
    et de l'afficher.
 */
public class GestionnaireAnimation {

    // Utilisation : GestionnaireAnimation.ajouterAnimation(pane, "/img/animations/Explosion_", 6, 60);

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
            private final long frameDuration = frameDurationMillis * 1_000_000L; // ms -> ns

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


// Découpe en n frames.
    public static Image[] decouperSpriteSheet(Image plancheSprite, int largeurFrame, int hauteurFrame, int nbFrames) {
        Image[] frames = new Image[nbFrames];
        for (int indice = 0; indice < nbFrames; indice++) {
            frames[indice] = new WritableImage(plancheSprite.getPixelReader(), indice * largeurFrame, 0, largeurFrame, hauteurFrame);
        }
    return frames;
    }

}
