package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob;

import javafx.scene.Group;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory.MobFactory;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.factory.MobHostileFactory;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.loot.LootHostileStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.loot.LootStrategy;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import java.util.ArrayList;
import java.util.List;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;

/**
 * Gère les mobs hostiles dans le jeu, leur création, mise à jour et suppression.
 * S'occupe également de générer le loot via le GestionnaireItem lorsqu'un mob meurt.
 * Maintient la liste des mobs hostiles et de leurs vues pour l'affichage.
 */
public class GestionnaireMobHostile extends GestionnaireMob {

    private final List<MobHostile> mobsHostiles = new ArrayList<>();
    private final List<VueMobHostile> vuesMobsHostiles = new ArrayList<>();
    private static final double MAP_WIDTH = 1920.0;
    private GestionnaireItem gestionnaireItem;
    private MobFactory mobFactory = new MobHostileFactory();
    private LootStrategy lootStrategy = new LootHostileStrategy();

    // Constructeur avec injection du gestionnaire d'item
    public GestionnaireMobHostile(GestionnaireItem gestionnaireItem) {
        this.gestionnaireItem = gestionnaireItem;
    }

    @Override
    public MobHostile ajouterMob(Joueur cible, double y, Group worldGroup) {
        if (this.rootPane == null) {
            this.rootPane = worldGroup;
        }
        MobHostile nouveauMob = (MobHostile) mobFactory.creerMob(cible);
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
    public List<? extends Mob> getListeMobs() {
        return mobsHostiles;
    }

    @Override
    public void mettreAJourMob(Mob mob) {
        ((MobHostile) mob).mettreAJour();
    }

    @Override
    public void retirerVue(int index) {
        if (index < vuesMobsHostiles.size()) {
            rootPane.getChildren().remove(vuesMobsHostiles.get(index).getNode());
            vuesMobsHostiles.remove(index);
        }
    }

    @Override
    public void retirerMob(int index) {
        mobsHostiles.remove(index);
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