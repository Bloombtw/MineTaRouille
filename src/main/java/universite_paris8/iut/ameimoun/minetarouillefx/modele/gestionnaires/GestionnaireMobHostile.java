package universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires;

import javafx.scene.layout.Pane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.MobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Personnage;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueMobHostile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GestionnaireMobHostile {
    private final List<MobHostile> mobsHostiles = new ArrayList<>();
    private final List<VueMobHostile> vuesMobsHostiles = new ArrayList<>();
    private final Random random = new Random();
    private static final double MAP_WIDTH = 1920.0;

    public void ajouterMobHostile(Personnage joueur, double y, Pane rootPane) {
        double randomX = genererPositionAleatoire();
        MobHostile mobHostile = new MobHostile(joueur);
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

    public List<MobHostile> getMobsHostiles() {
        return mobsHostiles;
    }
}