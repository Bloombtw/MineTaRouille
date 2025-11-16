package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;

public interface MobFactory {
    Mob creerMob(Joueur cible);
}