package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gestionnaire dédié aux mobs hostiles (plusieurs entités).
 * Gère : création, mise à jour (IA+gravité) et “one-shot kill” en fonction de la distance euclidienne au joueur.
 */
public class GestionnaireMobHostile {
    private final List<MobHostile> mobsHostiles = new ArrayList<>();
    private final List<VueMobHostile> vuesMobsHostiles = new ArrayList<>();
    private final Random random = new Random();
    private final static double MAP_WIDTH = 1920.0;

    // Pane parent pour retirer la vue lorsqu’on tue un mob
    private Pane rootPane;

    public void ajouterMobHostile(Personnage cible, double y, Pane rootPane) {
        if (this.rootPane == null) {
            this.rootPane = rootPane;
        }

        double randomX = genererPositionAleatoire();
        MobHostile mobHostile = new MobHostile(cible);
        mobHostile.setX(randomX);
        mobHostile.setY(y);
        mobsHostiles.add(mobHostile);

        VueMobHostile vueMobHostile = new VueMobHostile(mobHostile);
        vuesMobsHostiles.add(vueMobHostile);
        rootPane.getChildren().add(vueMobHostile.getNode());
    }

    private double genererPositionAleatoire() {
        return random.nextDouble() * MAP_WIDTH;
    }

    public void mettreAJour() {
        for (MobHostile mobHostile : mobsHostiles) {
            mobHostile.mettreAJour();
        }
    }

    /**
     * TUE les mobs hostiles dont la distance euclidienne entre leur centre et
     * la position (playerCenterX, playerCenterY) du joueur est ≤ DISTANCE_ATTAQUE.
     */
    public void tuerMobSiProximite(double playerCenterX, double playerCenterY) {
        double seuil = Constantes.DISTANCE_ATTAQUE;

        for (int i = mobsHostiles.size() - 1; i >= 0; i--) {
            MobHostile mobHostile = mobsHostiles.get(i);

            // Centre du mob hostile
            double mobCenterX = mobHostile.getX() + (Constantes.TAILLE_TUILE / 2.0);
            double mobCenterY = mobHostile.getY() + (Constantes.TAILLE_TUILE / 2.0);

            // Calcul de la distance euclidienne
            double dx = playerCenterX - mobCenterX;
            double dy = playerCenterY - mobCenterY;
            double distanceTotale = Math.sqrt(dx * dx + dy * dy);

            if (distanceTotale <= seuil) {
                // Suppression du modèle
                mobsHostiles.remove(i);
                // Suppression de la vue
                if (rootPane != null) {
                    VueMobHostile vue = vuesMobsHostiles.get(i);
                    rootPane.getChildren().remove(vue.getNode());
                    System.out.println("Mob hostile tué (distance)");
                }
                vuesMobsHostiles.remove(i);
            }
        }
    }

    // Getters nécessaires pour SourisListener
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
