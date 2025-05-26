package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

public class Souris {
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;

    private VueCarte vueCarte;

    public Souris(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire ) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.vueInventaire = vueInventaire;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnMousePressed(this::gererClicSouris);
    }

    public void desactiver(TilePane tilePane) {
        tilePane.setOnMousePressed(null);
        tilePane.setOnMouseReleased(null);
        tilePane.setOnMouseMoved(null);
    }

    private void gererClicSouris(MouseEvent event) {
        int x = (int) event.getX() / Constantes.TAILLE_TUILE;
        int y = (int) event.getY() / Constantes.TAILLE_TUILE;
        int couche = 1;

        Item itemBloc = GestionnaireBloc.casserBlocEtDonnerItem(couche, x, y);
        if (itemBloc != null) {
            vueCarte.mettreAJourAffichage(x, y, couche);
            inventaire.ajouterItem(itemBloc);
            vueInventaire.mettreAJourAffichage();
        }

    }
}

