package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireMobHostile extends GestionnaireMobA {
    private final List<MobHostile> mobsHostiles = new ArrayList<>();
    private final List<VueMobHostile> vuesMobsHostiles = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;

    @Override
    public Mob ajouterMob(Joueur cible, double y, Pane rootPane) {
        if (this.rootPane == null) {
            this.rootPane = rootPane;
        }
        MobHostile nouveauMob = new MobHostile(cible);
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobsHostiles.add(nouveauMob);

        VueMobHostile vue = new VueMobHostile(nouveauMob);
        vuesMobsHostiles.add(vue);
        rootPane.getChildren().add(vue.getNode());
        return nouveauMob;
    }

    @Override
    public void mettreAJour() {
        for (MobHostile mobHostile : mobsHostiles) {
            mobHostile.mettreAJour();
        }
    }

    private double[] calculerCentreMob(MobHostile mob) {
        double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
        double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);
        return new double[]{mobCenterX, mobCenterY};
    }

    @Override
    public void tuerMob(double playerCenterX, double playerCenterY, double distanceMax) {
        for (int i = mobsHostiles.size() - 1; i >= 0; i--) {
            MobHostile mobHostile = mobsHostiles.get(i);
            double[] mobCenter = calculerCentreMob(mobHostile);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);
            if (distanceTotale <= distanceMax) {
                mobsHostiles.remove(i);
                if (rootPane != null) {
                    VueMobHostile vue = vuesMobsHostiles.get(i);
                    rootPane.getChildren().remove(vue.getNode());
                }
                vuesMobsHostiles.remove(i);
            }
        }
    }

    public void supprimerMob(Mob mob) {
        int index = mobsHostiles.indexOf(mob);
        if (index != -1) {
            // Retirer la vue du mob hostile
            if (rootPane != null && index < vuesMobsHostiles.size()) {
                rootPane.getChildren().remove(vuesMobsHostiles.get(index).getNode());
                vuesMobsHostiles.remove(index);
            }
            // Supprimer le mob de la liste
            mobsHostiles.remove(index);

        }
    }
    public List<MobHostile> getMobsHostiles() {
        return mobsHostiles;
    }

    public List<VueMobHostile> getVuesMobsHostiles() {
        return vuesMobsHostiles;
    }

    public Pane getRootPane() {
        return rootPane;
    }
}