package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Mob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;

import java.util.List;
import java.util.Random;

/**
 * Classe abstraite GestionnaireMobA qui définit les méthodes de base pour gérer les entités de type Mob.
 * Elle fournit des fonctionnalités communes telles que l'ajout, la mise à jour et la suppression des Mobs.
 */
public abstract class GestionnaireMobA {

    /**
     * Générateur de nombres aléatoires utilisé pour positionner les Mobs.
     */
    protected final Random random = new Random();

    /**
     * Conteneur graphique racine où les Mobs sont affichés.
     */
    protected Pane rootPane;

    /**
     * Ajoute un nouveau Mob au jeu.
     *
     * @param cible    Le joueur ou entité associée au Mob.
     * @param y        La position verticale du Mob.
     * @param rootPane Le conteneur graphique où le Mob sera affiché.
     * @return Le Mob nouvellement ajouté.
     */
    public abstract Mob ajouterMob(Joueur cible, double y, Pane rootPane);

    /**
     * Met à jour les Mobs gérés par le gestionnaire.
     */
    public abstract void mettreAJour();

    /**
     * Tue les Mobs proches d'un joueur en fonction d'une distance maximale.
     *
     * @param playerCenterX La position X du centre du joueur.
     * @param playerCenterY La position Y du centre du joueur.
     * @param distanceMax   La distance maximale pour tuer les Mobs.
     */
    public abstract void tuerMob(double playerCenterX, double playerCenterY, double distanceMax);

    /**
     * Calcule la distance entre deux points.
     *
     * @param x1 La coordonnée X du premier point.
     * @param y1 La coordonnée Y du premier point.
     * @param x2 La coordonnée X du second point.
     * @param y2 La coordonnée Y du second point.
     * @return La distance entre les deux points.
     */
    protected double calculerDistance(double x1, double y1, double x2, double y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}