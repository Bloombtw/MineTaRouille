package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;

public class GestionnaireMobHostile extends GestionnaireMob {

    private final List<MobHostile> mobsHostiles = new ArrayList<>();
    private final List<VueMobHostile> vuesMobsHostiles = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;
    private GestionnaireItem gestionnaireItem;

    // Constructeur avec injection du gestionnaire d'item
    public GestionnaireMobHostile(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem = gestionnaireItem;
    }

    @Override
    public MobHostile ajouterMob(Joueur cible, double y, Group worldGroup) {
        if (this.rootPane == null) {
            this.rootPane = worldGroup; // Group hérite de Pane, c'est OK
        }
        MobHostile nouveauMob = new MobHostile(cible);
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobsHostiles.add(nouveauMob);

        VueMobHostile vue = new VueMobHostile(nouveauMob);
        vuesMobsHostiles.add(vue);
        worldGroup.getChildren().add(vue.getNode());
        return nouveauMob;
    }

    @Override
    public void mettreAJour() {
        for (int i = mobsHostiles.size() - 1; i >= 0; i--) {
            MobHostile mobHostile = mobsHostiles.get(i);
            mobHostile.mettreAJour();
            if (mobHostile.estMort()) {
                if (rootPane != null && i < vuesMobsHostiles.size()) {
                    javafx.scene.Node node = vuesMobsHostiles.get(i).getNode();
                    if (rootPane.getChildren().contains(node)) {
                        rootPane.getChildren().remove(node);
                    }
                    vuesMobsHostiles.remove(i);
                }
                mobsHostiles.remove(i);
                // loot si besoin
            }
        }
    }

    private double[] calculerCentreMob(MobHostile mob) {
        double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
        double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);
        return new double[]{mobCenterX, mobCenterY};
    }

    public void supprimerMobEtLoot(MobHostile mob) {
        int index = mobsHostiles.indexOf(mob);
        if (index != -1) {
            if (rootPane != null && index < vuesMobsHostiles.size()) {
                rootPane.getChildren().remove(vuesMobsHostiles.get(index).getNode());
                vuesMobsHostiles.remove(index);
            }
            mobsHostiles.remove(index);

            // Gérer le loot
            if (gestionnaireItem != null) {
                Item loot = new Item(Objet.FIL, 1);
                int tileX = (int) (mob.getX() / Constantes.TAILLE_TUILE);
                int tileY = (int) (mob.getY() / Constantes.TAILLE_TUILE);
                gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
            }
        }
    }
    public void tuerMob(double playerCenterX, double playerCenterY, double distanceMax) {
        for (int i = mobsHostiles.size() - 1; i >= 0; i--) {
            MobHostile mobHostile = mobsHostiles.get(i);
            double[] mobCenter = calculerCentreMob(mobHostile);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);
            if (distanceTotale <= distanceMax) {
                // Supprimer le mob
                supprimerMobEtLoot(mobHostile);
            }
        }
    }

    public List<MobHostile> getMobsHostiles() {
        return mobsHostiles;
    }

    public List<VueMobHostile> getVuesMobsHostiles() {
        return vuesMobsHostiles;
    }

    public Group getRootPane() {
        return rootPane;
    }
}