package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;

import java.util.List;
import java.util.Random;

/**
 * Classe abstraite GestionnaireMobA qui définit les méthodes de base pour gérer les entités de type Mob.
 * Elle fournit des fonctionnalités communes telles que l'ajout, la mise à jour et la suppression des Mobs.
 */
public abstract class GestionnaireMob {

    /**
     * Générateur de nombres aléatoires utilisé pour positionner les Mobs.
     */
    protected final Random random = new Random();

    /**
     * Conteneur graphique racine où les Mobs sont affichés.
     */
    Group rootPane;

    /**
     * Ajoute un nouveau Mob au jeu.
     *

     */

    public abstract Mob ajouterMob(Joueur mob, double y, Group worldGroup);

    /**
     * Met à jour les Mobs gérés par le gestionnaire.
     */
    public void mettreAJour() {
        for (int i = getListeMobs().size() - 1; i >= 0; i--) {
            Mob mob = getListeMobs().get(i);
            mettreAJourMob(mob);
            if (mob.estMort()) {
                retirerVue(i);
                retirerMob(i);
                genererLoot(mob);
            }
        }
    }

    /**
     * Tue les mobs à une distance maximale spécifiée du joueur.
     * @param playerCenterX
     * @param playerCenterY
     * @param distanceMax
     */
    public void tuerMob(double playerCenterX, double playerCenterY, double distanceMax) {
        for (int i = getListeMobs().size() - 1; i >= 0; i--) {
            Mob mob = getListeMobs().get(i);
            double[] mobCenter = calculerCentreMob(mob);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);
            if (distanceTotale <= distanceMax) {
                retirerVue(i);
                retirerMob(i);
                genererLoot(mob);
            }
        }
    }

    protected abstract List<? extends Mob> getListeMobs();
    protected abstract void mettreAJourMob(Mob mob);
    protected abstract void retirerVue(int index);
    protected abstract void retirerMob(int index);
    protected abstract void genererLoot(Mob mob);
    /**
     * Calcule la distance entre deux points.
     *
     * @param x1 La coordonnée X du premier point.
     * @param y1 La coordonnée Y du premier point.
     * @param x2 La coordonnée X du second point.
     * @param y2 La coordonnée Y du second point.
     * @return La distance entre les deux points.
     */
    protected double calculerDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calcule le centre d'un Mob.
     * @param mob
     * @return
     */
    protected double[] calculerCentreMob(Mob mob) {
        double mobCenterX = mob.getX() + 0.5;
        double mobCenterY = mob.getY() + 0.5;
        return new double[]{mobCenterX, mobCenterY};
    }
}