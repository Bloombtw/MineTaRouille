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
            if (mettreAJourPositionEtDistance(fleche, it)) continue;
            if (gererCollisionAvecMobsPassifs(fleche, it)) continue;
            if (gererCollisionAvecMobsHostiles(fleche, it)) continue;
        }
    }

    private boolean mettreAJourPositionEtDistance(Fleche fleche, Iterator<Fleche> it) {
        boolean depassee = fleche.mettreAJourEtVerifierDistance();
        if (depassee || fleche.estHorsJeu()) {
            supprimerFleche(fleche, it);
            return true;
        }
        return false;
    }

    private boolean gererCollisionAvecMobsPassifs(Fleche fleche, Iterator<Fleche> it) {
        // Copie pour éviter modification concurrente
        List<Mob> mobs = new ArrayList<>(gestionnaireMob.getMobs());
        for (Mob mob : mobs) {
            double dist = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
            if (dist < Constantes.DISTANCE_ATTAQUE_ARC) {
                gestionnaireMob.supprimerMobEtGetLoot(mob);
                supprimerFleche(fleche, it);
                return true;
            }
        }
        return false;
    }

    private boolean gererCollisionAvecMobsHostiles(Fleche fleche, Iterator<Fleche> it) {
        if (gestionnaireMobHostile != null) {
            List<MobHostile> mobs = new ArrayList<>(gestionnaireMobHostile.getMobsHostiles());
            for (MobHostile mob : mobs) {
                double dist = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
                if (dist < Constantes.DISTANCE_ATTAQUE_ARC) {
                    gestionnaireMobHostile.supprimerMob(mob);
                    supprimerFleche(fleche, it);
                    return true;
                }
            }
        }
        return false;
    }

    private void supprimerFleche(Fleche fleche, Iterator<Fleche> it) {
        VueFleche vue = vueFleches.get(fleche);
        if (vue != null) {
            worldGroup.getChildren().remove(vue.getNode());
        }
        it.remove();
        vueFleches.remove(fleche);
    }
}