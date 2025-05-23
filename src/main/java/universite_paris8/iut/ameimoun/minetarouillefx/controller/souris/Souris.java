package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Bloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Carte;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Item;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

public class Souris {

    private final TilePane tileMap;
    private final Carte carte; // Added reference to Carte
    private final Inventaire inventaire; // Added reference to Inventaire
    private final VueCarte vueCarte; // Added reference to VueCarte
    private final VueInventaire vueInventaire; // Added reference to VueInventaire
    private final Joueur joueurModele;

    public Souris(TilePane tileMap, Carte carte, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire, Joueur joueurModele) {
        this.tileMap = tileMap;
        this.carte = carte;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.vueInventaire = vueInventaire;
        this.joueurModele = joueurModele;
        initialiserClicSouris();
    }

    private void initialiserClicSouris() {
    }

    private double distanceEntre(int x1, int y1, int x2, int y2) {
        double dx = x1 - x2;
        double dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}