package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory.MobFactory;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory.MobPassifFactory;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.loot.LootPassifStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.loot.LootStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMob;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;

/**
 * Gère les mobs passifs dans le jeu, leur création, mise à jour et suppression.
 * S'occupe de générer le loot via le GestionnaireItem lorsqu'un mob meurt.
 * Maintient la liste des mobs passifs et de leurs vues pour l'affichage.
 */
public class GestionnaireMobPassif extends GestionnaireMob {
    private final List<Mob> mobSimple = new ArrayList<>();
    private final List<VueMob> vuesMob = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;
    private GestionnaireItem gestionnaireItem;
    private MobFactory mobFactory = new MobPassifFactory();
    private LootStrategy lootStrategy = new LootPassifStrategy();
    /**
     * Constructeur de la classe GestionnaireMob.
     *
     * @param gestionnaireItem Le gestionnaire d'items utilisé pour gérer les loots.
     */
    public GestionnaireMobPassif(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem = gestionnaireItem;
    }

    @Override
    public Mob ajouterMob(Joueur joueur, double y, Group worldGroup) {
        if (this.rootPane == null) {
            this.rootPane = worldGroup;
        }
        Mob nouveauMob = mobFactory.creerMob(joueur);
        double randomX = random.nextDouble() * MAP_WIDTH;
        nouveauMob.setX(randomX);
        nouveauMob.setY(y);
        mobSimple.add(nouveauMob);

        VueMob vue = new VueMob(nouveauMob);
        vuesMob.add(vue);
        worldGroup.getChildren().add(vue.getNode());
        return nouveauMob;
    }

    // Implémentation des méthodes du Template Method

    @Override
    public List<? extends Mob> getListeMobs() {
        return mobSimple;
    }

    @Override
    public void mettreAJourMob(Mob mob) {
        mob.mettreAJour(new Joueur());
    }

    @Override
    public void retirerVue(int index) {
        if (index < vuesMob.size()) {
            rootPane.getChildren().remove(vuesMob.get(index).getNode());
            vuesMob.remove(index);
        }
    }

    @Override
    public void retirerMob(int index) {
        mobSimple.remove(index);
    }

    @Override
    public void genererLoot(Mob mob) {
        if (gestionnaireItem != null) {
            Item loot = lootStrategy.genererLoot(mob);
            int tileX = (int) (mob.getX() / Constantes.TAILLE_TUILE);
            int tileY = (int) (mob.getY() / Constantes.TAILLE_TUILE);
            gestionnaireItem.spawnItemAuSol(loot, tileX, tileY);
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
