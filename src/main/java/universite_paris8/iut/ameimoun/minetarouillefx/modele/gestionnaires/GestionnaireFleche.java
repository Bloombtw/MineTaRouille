package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Fleche;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobPassif;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueFleche;

import java.util.*;

/**
 * Gère les flèches tirées par le joueur, leur déplacement et leur durée de vie.
 * Vérifie les collisions avec les mobs passifs et hostiles, et supprime les flèches
 * lorsqu'elles dépassent la distance maximale ou touchent un mob.
 */
public class GestionnaireFleche {
    private final Group worldGroup;
    private final List<Fleche> fleches = new ArrayList<>();
    private final Map<Fleche, VueFleche> vueFleches = new HashMap<>();
    private final GestionnaireMobPassif gestionnaireMob;
    private final GestionnaireMobHostile gestionnaireMobHostile;

    public GestionnaireFleche(Group worldGroup, GestionnaireMobPassif gestionnaireMob, GestionnaireMobHostile gestionnaireMobHostile) {
        this.worldGroup = worldGroup;
        this.gestionnaireMob = gestionnaireMob;
        this.gestionnaireMobHostile = gestionnaireMobHostile;
    }

    /**
     * Tire une flèche à partir des coordonnées spécifiées avec une direction donnée.
     */
    public void tirerFleche(double x, double y, double dx, double dy) {
        Fleche fleche = new Fleche(x, y, dx, dy, Constantes.DISTANCE_ATTAQUE_ARC);
        fleches.add(fleche);
        VueFleche vueFleche = new VueFleche(fleche);
        vueFleches.put(fleche, vueFleche);
        worldGroup.getChildren().add(vueFleche.getNode());
    }

    public void mettreAJour() {
        Iterator<Fleche> it = fleches.iterator();
        while (it.hasNext()) {
            Fleche fleche = it.next();
            if (flecheDoitEtreSupprimee(fleche, it)) continue;
            if (collisionAvecMobPassif(fleche, it)) continue;
            if (collisionAvecMobHostile(fleche, it)) continue;
        }
    }

    private boolean flecheDoitEtreSupprimee(Fleche fleche, Iterator<Fleche> it) {
        if (fleche.mettreAJourEtVerifierDistance() || fleche.estHorsJeu()) {
            supprimerFleche(fleche, it);
            return true;
        }
        return false;
    }
    private boolean collisionAvecMobPassif(Fleche fleche, Iterator<Fleche> it) {
        for (Mob mob : copieDesMobsPassifs()) {
            if (flecheEstProcheDuMob(fleche, mob)) {
                gestionnaireMob.supprimerMobEtGetLoot(mob);
                supprimerFleche(fleche, it);
                return true;
            }
        }
        return false;
    }

    private boolean collisionAvecMobHostile(Fleche fleche, Iterator<Fleche> it) {
        if (gestionnaireMobHostile == null) return false;

        for (MobHostile mob : copieDesMobsHostiles()) {
            if (flecheEstProcheDuMob(fleche, mob)) {
                gestionnaireMobHostile.supprimerMobEtLoot(mob);
                supprimerFleche(fleche, it);
                return true;
            }
        }
        return false;
    }

    private List<MobHostile> copieDesMobsHostiles() {
        return new ArrayList<>(gestionnaireMobHostile.getMobsHostiles());
    }


    private List<Mob> copieDesMobsPassifs() {
        return new ArrayList<>(gestionnaireMob.getMobs());
    }

    private boolean flecheEstProcheDuMob(Fleche fleche, Mob mob) {
        double distance = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
        return distance < Constantes.DISTANCE_ATTAQUE_ARC;
    }



    private void supprimerFleche(Fleche fleche, Iterator<Fleche> it) {
        VueFleche vue = vueFleches.remove(fleche);
        if (vue != null) {
            worldGroup.getChildren().remove(vue.getNode());
        }
        it.remove();
    }

}