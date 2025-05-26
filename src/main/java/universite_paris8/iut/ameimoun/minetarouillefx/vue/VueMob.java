package universite_paris8.iut.ameimoun.minetarouillefx.vue;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Loader;




public class VueMob {
    private final ImageView mobImage;
    private final Group container;
    private Animation animIdle;
    private Animation animMarche;
    private Animation animActuelle;

    public VueMob(Mob mob) {
        mobImage = new ImageView();
        mobImage.setFitWidth(128);
        mobImage.setFitHeight(128);

        container = new Group(mobImage);
        lierPositionContainer(mob);
        chargerAnimations();
        jouerAnimation(animIdle);
    }

    private void lierPositionContainer(Mob mob) {
        container.translateXProperty().bind(mob.xProperty());
        container.translateYProperty().bind(mob.yProperty());
    }

    private void chargerAnimations() {
        Image spriteIdle = Loader.loadImage("/img/mob/idle.png");
        Image spriteMarche = Loader.loadImage("/img/mob/saut.png");

        if (spriteIdle == null) {
            System.out.println("Erreur : impossible de charger idle.png");
        }
        if (spriteMarche == null) {
            System.out.println("Erreur : impossible de charger marche.png");
        }

        animIdle = creerAnimation("/img/mob/idle.png", 128, 128, 8, 200);
        animMarche = creerAnimation("/img/mob/saut.png", 128, 128, 13, 200);

    }


    private Animation creerAnimation(String path, int width, int height, int frames, int duree) {
        Image sprite = Loader.loadImage(path);
        if (sprite == null) {
            System.err.println("Erreur : Impossible de charger l'image du sprite pour l'animation : " + path);
            return null;
        }

        int frameWidth = (int) sprite.getWidth() / frames;
        System.out.println("FrameWidth calculé : " + frameWidth);

        Image[] framesArray = Animation.decouperSpriteSheet(sprite, frameWidth, height, frames);
        return new Animation(mobImage, framesArray, duree);
    }



    public void mettreAJourAnimation(Mob mob) {
        if (mob.getSeDeplace()) {
            jouerAnimation(animMarche);
            System.out.println("Animation marche activée");
        } else {
            jouerAnimation(animIdle);
            System.out.println("Animation idle activée");
        }
    }


    private void jouerAnimation(Animation animation) {
        if (animActuelle != animation) {
            if (animActuelle != null) animActuelle.stop();
            animActuelle = animation;
            animActuelle.start();
        }
    }

    public Group getNode() {
        return container;
    }
}
