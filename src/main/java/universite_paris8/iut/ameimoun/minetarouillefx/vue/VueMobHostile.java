package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationMob;

public class VueMobHostile extends VueMob {
    private final AnimationMob animationMobHostile;

    public VueMobHostile(MobHostile mobHostile) {
        super(mobHostile);

        // Initialize specific animation for MobHostile
        animationMobHostile = new AnimationMob(getMobImage());
        animationMobHostile.mettreAJourAnimation(mobHostile);
    }

    @Override
    public Group getNode() {
        return super.getNode();
    }
}