package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationMob;

public class VueMob {
    private final Mob mob;
    private final ImageView mobImage;
    private final Group container;
    private final AnimationMob animationMob;

    public VueMob(Mob mob) {
        this.mob = mob;

        mobImage = new ImageView();
        mobImage.setFitWidth(Constantes.TAILLE_TUILE);
        mobImage.setFitHeight(Constantes.TAILLE_TUILE);

        container = new Group(mobImage);
        lierPositionContainer();

        animationMob = new AnimationMob(mobImage);
        animationMob.mettreAJourAnimation(mob);
    }

    private void lierPositionContainer() {
        container.translateXProperty().bind(mob.xProperty());
        container.translateYProperty().bind(mob.yProperty());
    }

    public ImageView getMobImage() {
        return mobImage;
    }

    public void mettreAJourPosition() {
        mobImage.setTranslateX(mob.getX());
        mobImage.setTranslateY(mob.getY());
    }

    public Group getNode() {
        return container;
    }
}