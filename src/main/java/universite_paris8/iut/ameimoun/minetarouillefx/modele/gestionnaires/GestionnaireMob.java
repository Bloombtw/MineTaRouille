package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireMob extends GestionnaireMobA {
    private final List<Mob> mobSimple = new ArrayList<>();
    private final List<VueMob> vuesMob = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;
    private GestionnaireItem gestionnaireItem;

    public GestionnaireMob(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem = gestionnaireItem;
    }

    @Override
    public Mob ajouterMob(Joueur mob, double y, Pane rootPane) {
        if (this.rootPane == null) {
            this.rootPane = rootPane;
        }
        Mob nouveauMob = new Mob();
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobSimple.add(nouveauMob);

        VueMob vue = new VueMob(nouveauMob);
        vuesMob.add(vue);
        rootPane.getChildren().add(vue.getNode());
        return nouveauMob;
    }

    @Override
    public void mettreAJour() {
        for (Mob m : mobSimple) {
            m.mettreAJour();
        }
    }

    private double[] calculerCentreMob(Mob mob) {
        double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
        double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);
        return new double[]{mobCenterX, mobCenterY};
    }

    public void supprimerMobEtGetLoot(Mob mob) {
        int index = mobSimple.indexOf(mob);
        if (index != -1) {
            if (rootPane != null && index < vuesMob.size()) {
                rootPane.getChildren().remove(vuesMob.get(index).getNode());
                vuesMob.remove(index);
            }
            // Supprimer le mob de la liste
            mobSimple.remove(index);

            // Gérer le loot ici si besoin
            if (gestionnaireItem != null) {
                Item loot = new Item(Objet.MOUTON_CUIT, 1);
                int tileX = (int) (mob.getX() / Constantes.TAILLE_TUILE);
                int tileY = (int) (mob.getY() / Constantes.TAILLE_TUILE);
                gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
            }
        }
    }

    /*
    private void supprimerMobEtGerLoot(int index, double mobCenterX, double mobCenterY) {
        VueMob vue = vuesMob.get(index);
        if (rootPane != null && vue != null) {
            rootPane.getChildren().remove(vue.getNode());
            vuesMob.remove(index);

            if (gestionnaireItem != null) {
                Item loot = new Item(Objet.MOUTON_CUIT, 1);
                int tileX = (int) (mobCenterX / Constantes.TAILLE_TUILE);
                int tileY = (int) (mobCenterY / Constantes.TAILLE_TUILE);
                gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
            }
        }
        mobSimple.remove(index);
    }*/

    @Override
    public void tuerMob(double playerCenterX, double playerCenterY, double distanceMax) {
        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            double[] mobCenter = calculerCentreMob(mob);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);
            if (distanceTotale <= distanceMax) {
                supprimerMobEtGetLoot(mob);
            }
        }
    }

    public List<Mob> getMobs() {
        return mobSimple;
    }



    public Pane getRootPane() {
        return rootPane;
    }
}