package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireBloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireMob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

public class SourisListener {
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private JeuController jeuController;
    private GestionnaireItem gestionnaireItem;
    private VueCarte vueCarte;
    private VueJoueur vueJoueur;

    public SourisListener(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire, GestionnaireItem gestionnaireItem) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.vueInventaire = vueInventaire;
        this.gestionnaireItem = gestionnaireItem;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnMousePressed(this::gererClicSouris);
        tilePane.setOnMouseClicked(this::gererPlacementBloc);
    }

    public void desactiver(TilePane tilePane) {
        tilePane.setOnMousePressed(null);
        tilePane.setOnMouseReleased(null);
        tilePane.setOnMouseMoved(null);
    }

    public void lierScrollInventaire(Scene scene) {
        scene.addEventFilter(ScrollEvent.SCROLL, event -> {
            int index = inventaire.getSelectedIndex();
            int max = inventaire.getSlots().size();
            if (event.getDeltaY() < 0) {
                index = (index + 1) % max;
            } else if (event.getDeltaY() > 0) {
                index = (index - 1 + max) % max;
            }
            inventaire.setSelectedIndex(index);
            event.consume();
        });
    }

    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }

    private void gererPlacementBloc(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) { // clic droit
            int x = (int) event.getX() / Constantes.TAILLE_TUILE;
            int y = (int) event.getY() / Constantes.TAILLE_TUILE;
            int couche = 1;

            boolean blocPlace = GestionnaireBloc.placerBloc(
                    Carte.getInstance(),
                    inventaire,
                    inventaire.getSelectedIndex(),
                    couche,
                    x,
                    y,
                    joueur
            );

            if (blocPlace) {
                vueCarte.mettreAJourAffichage(x, y);
                vueInventaire.mettreAJourAffichage();
                if (vueJoueur != null) {
                    vueJoueur.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
                }
            }
        }
    }


    private void gererClicSouris(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }

        // 1) Casser un bloc si applicable
        double clickX = event.getX();
        double clickY = event.getY();
        int tx = (int) (clickX / Constantes.TAILLE_TUILE);
        int ty = (int) (clickY / Constantes.TAILLE_TUILE);
        Item itemBloc1 = GestionnaireBloc.casserBlocEtDonnerItem(1, tx, ty, joueur);
        Item itemBloc2 = GestionnaireBloc.casserBlocEtDonnerItem(2, tx, ty, joueur);
        if (itemBloc1 != null || itemBloc2 != null) {
            dropItemEtMettreAJour(itemBloc1, tx, ty, 1);
            dropItemEtMettreAJour(itemBloc2, tx, ty, 2);
            vueInventaire.mettreAJourAffichage();
        }

        // 2) One‐shot kill par test de distance euclidienne
        if (jeuController != null) {
            // Position centrée du joueur
            double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
            double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);

            // 2a) Mobs hostiles
            GestionnaireMobHostile gestionHostile = jeuController.getGestionnaireMobHostile();
            if (gestionHostile != null) {
                gestionHostile.tuerMobSiProximite(playerCenterX, playerCenterY);
            }

            // 2b) Mob passif (via GestionnaireMob)
            GestionnaireMob gestionPassif = jeuController.getGestionnaireMob();
            if (gestionPassif != null) {
                gestionPassif.tuerMobSiProximite(playerCenterX, playerCenterY);
            }
        }
    }



    private void dropItemEtMettreAJour(Item item, int x, int y, int couche) {
        if (item != null) {
            vueCarte.mettreAJourAffichage(x, y); // le bloc cassé
            if (couche == 1 && y - 1 >= 0) {
                vueCarte.mettreAJourAffichage(x, y - 1); // décor au-dessus si sol cassé
            }
            gestionnaireItem.spawnItemAuSol(item, x, y);
        }
    }

}

