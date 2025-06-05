package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;

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
        // Si vous n'avez pas lié la position de la vue automatiquement,
        // il faudrait appeler ici : vuesMob.get(i).mettreAJourPosition() pour chaque mob.
    }

    /**
     * TUE tous les mobs passifs dont la distance euclidienne entre leur centre
     * et la position (playerCenterX, playerCenterY) du joueur ≤ DISTANCE_ATTAQUE.
     *
     * @param playerCenterX coordonnée X (en pixels) du centre du joueur
     * @param playerCenterY coordonnée Y (en pixels) du centre du joueur
     */
    public void tuerMobSiProximite(double playerCenterX, double playerCenterY) {
        double seuil = Constantes.DISTANCE_ATTAQUE;

        for (int i = mobSimple.size() - 1; i >= 0; i--) {
            Mob mob = mobSimple.get(i);

            // On estime que mob.getX()/getY() sont le coin haut-gauche ; on ajoute TAILLE_TUILE/2 pour avoir le centre
            double mobCenterX = mob.getX() + (Constantes.TAILLE_TUILE / 2.0);
            double mobCenterY = mob.getY() + (Constantes.TAILLE_TUILE / 2.0);

            // Calcul de la distance euclidienne
            double dx = playerCenterX - mobCenterX;
            double dy = playerCenterY - mobCenterY;
            double distanceTotale = Math.sqrt(dx * dx + dy * dy);

            if (distanceTotale <= seuil) {
                // Suppression du modèle de la liste
                mobSimple.remove(i);
                // Suppression de la vue du Pane
                if (rootPane != null) {
                    VueMob vue = vuesMob.get(i);
                    rootPane.getChildren().remove(vue.getNode());
                    System.out.println("Mob passif tué (distance ≤ " + seuil + ").");
                }
                // Suppression de la vue dans la liste
                vuesMob.remove(i);
            }
        }
    }

    /** Retourne la liste des mobs passifs (modèles). */
    public List<Mob> getMobSimple() {d
        return mobSimple;
    }

    /** Retourne la liste des vues correspondantes aux mobs passifs. */
    public List<VueMob> getVuesMob() {
        return vuesMob;
    }

    /** Retourne le Pane parent dans lequel les mobs sont ajoutés. */
    public Pane getRootPane() {
        return rootPane;
    }
}
