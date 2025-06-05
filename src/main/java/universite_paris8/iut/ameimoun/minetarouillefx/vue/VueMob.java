package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationMob;

public class VueMob {
    private final ImageView mobImage;
    private final Group container;
    private final AnimationMob animationMob;

    public VueMob(Mob mob) {
        mobImage = new ImageView();
        mobImage.setFitWidth(Constantes.TAILLE_TUILE);
        mobImage.setFitHeight(Constantes.TAILLE_TUILE);

        container = new Group(mobImage);
        lierPositionContainer(mob);


        animationMob = new AnimationMob(mobImage); // animationTimer démarre tout seul
        animationMob.mettreAJourAnimation(mob);    // mets à jour le bon état d’anim (au début)
    }

    private void lierPositionContainer(Mob mob) {
        container.translateXProperty().bind(mob.xProperty());
        container.translateYProperty().bind(mob.yProperty());
    }

    public ImageView getMobImage() {
        return mobImage;
    }


    public Group getNode() {
        return container;
    }//si pas de superposition, pas de group
}