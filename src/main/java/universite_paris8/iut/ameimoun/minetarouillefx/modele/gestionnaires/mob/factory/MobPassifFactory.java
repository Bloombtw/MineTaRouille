package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;

public class MobPassifFactory implements MobFactory {
    @Override
    public Mob creerMob(Joueur cible) {
        return new Mob();
    }
}