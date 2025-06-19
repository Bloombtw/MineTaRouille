package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Fleche;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueFleche;

import java.util.*;

/**
 * La classe GestionnaireFleche gère les flèches tirées par le joueur.
 * Elle met à jour leur position, vérifie les collisions avec les mobs, et les supprime si nécessaire.
 */
public class GestionnaireFleche {
    private final AnchorPane rootPane;
    private final List<Fleche> fleches = new ArrayList<>();
    private final Map<Fleche, VueFleche> vueFleches = new HashMap<>();
    private final GestionnaireMob gestionnaireMob;
    private final GestionnaireMobHostile gestionnaireMobHostile;

    public GestionnaireFleche(AnchorPane rootPane, GestionnaireMob gestionnaireMob, GestionnaireMobHostile gestionnaireMobHostile) {
        this.rootPane = rootPane;
        this.gestionnaireMob = gestionnaireMob;
        this.gestionnaireMobHostile = gestionnaireMobHostile;
    }

    /**
     * Tire une flèche à partir des coordonnées spécifiées avec une direction donnée.
     *
     * @param x La position x de départ de la flèche.
     * @param y La position y de départ de la flèche.
     * @param dx La direction x de la flèche.
     * @param dy La direction y de la flèche.
     */
    public void tirerFleche(double x, double y, double dx, double dy) {
        Fleche fleche = new Fleche(x, y, dx, dy, Constantes.DISTANCE_ATTAQUE_ARC);
        fleches.add(fleche);
        VueFleche vueFleche = new VueFleche(fleche);
        vueFleches.put(fleche, vueFleche);
        rootPane.getChildren().add(vueFleche.getNode());
    }

    /**
     * Met à jour la position de la flèche et vérifie si elle dépasse sa distance maximale ou sort du jeu.
     *
     * @param fleche La flèche à mettre à jour.
     * @param it L'itérateur pour supprimer la flèche si nécessaire.
     */
    private void mettreAJourPositionEtDistance(Fleche fleche, Iterator<Fleche> it) {
        boolean depassee = fleche.mettreAJourEtVerifierDistance();
        if (depassee || fleche.estHorsJeu()) {
            supprimerFleche(fleche, it);
        }
    }

    /**
     * Gère les collisions entre une flèche et les mobs passifs.
     * Supprime le mob et la flèche en cas de collision.
     *
     * @param fleche La flèche à vérifier.
     * @param it L'itérateur pour supprimer la flèche si nécessaire.
     */
    private void gererCollisionAvecMobsPassifs(Fleche fleche, Iterator<Fleche> it) {
        for (Mob mob : gestionnaireMob.getMobs()) {
            double dist = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
            if (dist < Constantes.DISTANCE_ATTAQUE_ARC) {
                gestionnaireMob.supprimerMobEtGetLoot(mob);
                System.out.println("mob passif tuer");
                supprimerFleche(fleche, it);
                return;
            }
        }
    }

    /**
     * Gère les collisions entre une flèche et les mobs hostiles.
     * Supprime le mob et la flèche en cas de collision.
     *
     * @param fleche La flèche à vérifier.
     * @param it L'itérateur pour supprimer la flèche si nécessaire.
     */
    private void gererCollisionAvecMobsHostiles(Fleche fleche, Iterator<Fleche> it) {
        if (gestionnaireMobHostile != null) {
            for (MobHostile mob : gestionnaireMobHostile.getMobsHostiles()) {
                double dist = Math.hypot(fleche.getX() - mob.getX(), fleche.getY() - mob.getY());
                if (dist < Constantes.DISTANCE_ATTAQUE_ARC) {
                    gestionnaireMobHostile.supprimerMobEtLoot(mob);
                    supprimerFleche(fleche, it);
                    return;
                }
            }
        }
    }

    private void supprimerFleche(Fleche fleche, Iterator<Fleche> it) {
        VueFleche vue = vueFleches.get(fleche);
        if (vue != null) {
            rootPane.getChildren().remove(vue.getNode());
        }
        it.remove();
        vueFleches.remove(fleche);
    }

    public void mettreAJour() {
        Iterator<Fleche> it = fleches.iterator();
        while (it.hasNext()) {
            Fleche fleche = it.next();

            mettreAJourPositionEtDistance(fleche, it);
            gererCollisionAvecMobsPassifs(fleche, it);
            gererCollisionAvecMobsHostiles(fleche, it);
        }
    }
}