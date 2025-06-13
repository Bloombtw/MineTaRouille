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
        // Stocker rootPane si ce n'est pas déjà fait
        if (this.rootPane == null) {
            this.rootPane = rootPane;
        }

        // 1) Création et positionnement du modèle Mob
        Mob nouveauMob = new Mob();
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobSimple.add(nouveauMob);

        // 2) Création de la vue associée et ajout au Pane
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
    public void tuerMobSiProximite(double playerCenterX, double playerCenterY) {
        double seuil = Constantes.DISTANCE_ATTAQUE;

        // On itère de la fin vers le début pour pouvoir supprimer des éléments sans problèmes d'index
        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);
            VueMob vue = vuesMob.get(i); // <-- On récupère la vue AVANT de potentiellement retirer le modèle

            // Centre du mob
            double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
            double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);

            // Calcul de la distance euclidienne
            double dx = playerCenterX - mobCenterX;
            double dy = playerCenterY - mobCenterY;
            double distanceTotale = Math.sqrt(dx * dx + dy * dy);

            if (distanceTotale <= seuil) {
                // 1. Suppression de la vue du Pane (visuellement)
                if (rootPane != null && vue != null) { // Vérifier que rootPane et vue existent
                    rootPane.getChildren().remove(vue.getNode());
                    vuesMob.remove(i);

                    // 2. Drop d'item (si gestionnaireItem est disponible)
                    if (gestionnaireItem != null) {
                        Item loot = new Item(Objet.MOUTON_CUIT, 1);
                        // Convertir les coordonnées pixels en indices de tuiles
                        int tileX = (int) (mobCenterX / Constantes.TAILLE_TUILE);
                        int tileY = (int) (mobCenterY / Constantes.TAILLE_TUILE);
                        gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
                    }
                }

                // 3. Suppression du modèle de la liste
                mobSimple.remove(i);
                System.out.println("Mob passif supprimé du modèle.");

            }
        }
    }
          public Pane getRootPane() {
        return rootPane;
    }
}
