package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Gestionnaire pour les mobs « passifs » (mob normal unique ou plusieurs).
 * Crée et affiche chaque mob, met à jour leur logique, et peut les supprimer si le joueur s'en approche.
 */
public class GestionnaireMob {
    private final List<Mob> mobSimple = new ArrayList<>();
    private final List<VueMob> vuesMob = new ArrayList<>();
    private final Random random = new Random();
    private static final double MAP_WIDTH = 1920.0;
    private Pane rootPane;
    private GestionnaireItem gestionnaireItem;

    public GestionnaireMob(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem= gestionnaireItem;
    }

    /**
     * Ajoute un nouveau mob « passif » :
     * - Crée le modèle Mob et le positionne (X aléatoire, Y passé en paramètre)
     * - Crée sa VueMob associée et l'ajoute au rootPane
     * - Conserve les références dans les listes pour mise à jour + suppression ultérieure
     *
     * @param cible    (inutilisé pour un mob passif, mais gardé pour symétrie avec MobHostile)
     * @param y        position verticale en pixels où placer le mob
     * @param rootPane le Pane JavaFX qui contiendra la vue du mob
     */
    public void ajouterMob(Personnage cible, double y, Pane rootPane) {
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
    }

    /**
     * Met à jour l'ensemble des mobs passifs (gravité, IA, etc.).
     * À appeler à chaque frame depuis JeuController.mettreAJourJeu().
     */
    public void mettreAJour() {
        for (Mob m : mobSimple) {
            m.mettreAJour();
        }
    }
    private double calculerDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    private double[] calculerCentreMob(Mob mob) {
        double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
        double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);
        return new double[]{mobCenterX, mobCenterY};
    }

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
    }

    private void attaquerMobsDistance(double playerCenterX, double playerCenterY) {
        double seuilDistance = Constantes.DISTANCE_ATTAQUE_DISTANCE; // Define a constant for ranged attack distance

        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            double[] mobCenter = calculerCentreMob(mob);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);

            if (distanceTotale <= seuilDistance) {
                supprimerMobEtGerLoot(i, mobCenter[0], mobCenter[1]);
            }
        }
    }

    private void attaquerMobsDistance(double playerCenterX, double playerCenterY) {
        double seuilDistance = Constantes.DISTANCE_ATTAQUE_ARC; // Define a constant for ranged attack distance

        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            double[] mobCenter = calculerCentreMob(mob);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);

            if (distanceTotale <= seuilDistance) {
                supprimerMobEtGerLoot(i, mobCenter[0], mobCenter[1]);
            }
        }
    }



    public void tuerMobSiProximite(double playerCenterX, double playerCenterY) {
        double seuil = Constantes.DISTANCE_ATTAQUE;
        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            double[] mobCenter = calculerCentreMob(mob);
            double distanceTotale = calculerDistance(playerCenterX, playerCenterY, mobCenter[0], mobCenter[1]);

            if (distanceTotale <= seuil) {
                supprimerMobEtGerLoot(i, mobCenter[0], mobCenter[1]);
            }
        }
    }
    public Pane getRootPane() {
        return rootPane;
    }
}
