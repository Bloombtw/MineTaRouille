package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Fleche;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueFleche;

import java.util.*;

public class GestionnaireFleche {
    private final AnchorPane rootPane;
    private final List<Fleche> fleches = new ArrayList<>();
    private final Map<Fleche, VueFleche> vueFleches = new HashMap<>();
    private final GestionnaireMob gestionnaireMob;

    public GestionnaireFleche(AnchorPane rootPane, GestionnaireMob gestionnaireMob) {
        this.rootPane = rootPane;
        this.gestionnaireMob = gestionnaireMob;
    }

    public void tirerFleche(double x, double y, double dx, double dy) {
        Fleche fleche = new Fleche(x, y, dx, dy);
        fleches.add(fleche);
        VueFleche vueFleche = new VueFleche(fleche);
        vueFleches.put(fleche, vueFleche);
        rootPane.getChildren().add(vueFleche.getNode());
    }

    public void mettreAJour() {
        Iterator<Fleche> it = fleches.iterator();
        while (it.hasNext()) {
            Fleche fleche = it.next();

            // Vérifier collision avec chaque mob
            Mob mobTouche = null;
            for (Mob mob : gestionnaireMob.getMobs()) {
                double dist = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
                if (dist < Constantes.DISTANCE_ATTAQUE_ARC) {
                    mobTouche = mob;
                    break;
                }
            }
            if (mobTouche != null) {
                gestionnaireMob.supprimerMobEtGetLoot(mobTouche);
                VueFleche vue = vueFleches.get(fleche);
                if (vue != null) {
                    rootPane.getChildren().remove(vue.getNode());
                }
                it.remove();
                vueFleches.remove(fleche);
                continue;
            }

            if (fleche.estHorsJeu()) {
                VueFleche vue = vueFleches.get(fleche);
                if (vue != null) {
                    rootPane.getChildren().remove(vue.getNode());
                }
                it.remove();
                vueFleches.remove(fleche);
            }
        }
    }
}