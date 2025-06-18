package universite_paris8.iut.ameimoun.minetarouillefx.vue;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Chemin;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.AnimationVue.AnimationMob;

public class VueMobHostile extends VueMob {
    private final AnimationMob animationMobHostile;

    public VueMobHostile(MobHostile mobHostile) {
        super(mobHostile);
        animationMobHostile = new AnimationMob(
                getMobImage(),
                Chemin.ANIMATION_MOB_HOSTILE_IDLE,
                Chemin.ANIMATION_MOB_HOSTILE_SAUT,
                128,
                8,
                13
        );
        animationMobHostile.mettreAJourAnimation(mobHostile);
    }
}