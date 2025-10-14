package universite_paris8.iut.ameimoun.minetarouillefx.modele.strategiesdeplacement;

import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;

public class StrategieAttaque implements StrategieDeplacement {

    @Override
    public void deplacer(Mob mob, Joueur joueur) {
            if (joueur.getX() > mob.getX()){
                mob.deplacerDroite();
            }
            else{
                mob.deplacerGauche();
            }
    }

}
