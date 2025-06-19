package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireMobPassif extends GestionnaireMob {
    private final List<Mob> mobSimple = new ArrayList<>();
    private final List<VueMob> vuesMob = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;
    private GestionnaireItem gestionnaireItem;

    /**
     * Constructeur de la classe GestionnaireMob.
     *
     * @param gestionnaireItem Le gestionnaire d'items utilisé pour gérer les loots.
     */
    public GestionnaireMobPassif(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem = gestionnaireItem;
    }

    @Override
    public Mob ajouterMob(Joueur mob, double y, Group worldGroup) {
        if (this.rootPane == null) {
            this.rootPane = worldGroup;
        }
        Mob nouveauMob = new Mob();
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobSimple.add(nouveauMob);

        VueMob vue = new VueMob(nouveauMob);
        vuesMob.add(vue);
        worldGroup.getChildren().add(vue.getNode());
        return nouveauMob;
    }

    @Override
    public void mettreAJour() {
        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            mob.mettreAJour();
            if (mob.estMort()) {
                if (rootPane != null && i < vuesMob.size()) {
                    javafx.scene.Node node = vuesMob.get(i).getNode();
                    if (rootPane.getChildren().contains(node)) {
                        rootPane.getChildren().remove(node);
                    }
                    vuesMob.remove(i);
                }
                mobSimple.remove(i);
                if (gestionnaireItem != null) {
                    Item loot = new Item(Objet.FIL, 3);
                    int tileX = (int) (mob.getX() / Constantes.TAILLE_TUILE);
                    int tileY = (int) (mob.getY() / Constantes.TAILLE_TUILE);
                    gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
                }
            }
        }
    }

    /**
     * Calcule le centre d'un Mob.
     *
     * @param mob Le Mob dont le centre doit être calculé.
     * @return Un tableau contenant les coordonnées X et Y du centre du Mob.
     */
    private double[] calculerCentreMob(Mob mob) {
        double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
        double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);
        return new double[]{mobCenterX, mobCenterY};
    }

    /**
     * Supprime un Mob et génère son loot.
     *
     * @param mob Le Mob à supprimer.
     */
    public void supprimerMobEtGetLoot(Mob mob) {
        int index = mobSimple.indexOf(mob);
        if (index != -1) {
            if (rootPane != null && index < vuesMob.size()) {
                rootPane.getChildren().remove(vuesMob.get(index).getNode());
                vuesMob.remove(index);
            }
            mobSimple.remove(index);

            if (gestionnaireItem != null) {
                Item loot = new Item(Objet.MOUTON_CUIT, 3);
                int tileX = (int) (mob.getX() / Constantes.TAILLE_TUILE);
                int tileY = (int) (mob.getY() / Constantes.TAILLE_TUILE);
                gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
            }
        }
    }

    /**
     * Tue les Mobs proches d'un joueur en fonction d'une distance maximale.
     *
     * @param playerCenterX La position X du centre du joueur.
     * @param playerCenterY La position Y du centre du joueur.
     * @param distanceMax   La distance maximale pour tuer les Mobs.
     */
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

    public List<VueMob> getVuesMob() {
        return vuesMob;
    }

    public List<Mob> getMobs() {
        return mobSimple;
    }

    public Group getRootPane() {
        return this.rootPane;
    }
}