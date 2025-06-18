package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;


import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.CraftController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireFleche;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.*;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireBloc;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMob;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCarte;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueJoueur;

/**
 /**
 * Classe SourisListener qui gère les interactions de la souris dans le jeu.
 * Elle permet de lier les événements de clic, de défilement et de mouvement de la souris
 * pour effectuer des actions telles que placer des blocs, casser des blocs, attaquer des mobs
 * et gérer l'inventaire.
 */
public class SourisListener {

    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private GestionnaireItem gestionnaireItem;
    private VueCarte vueCarte;
    private VueCraft vueCraft;
    private VueJoueur vueJoueur;
    private CraftController craftController;

    public SourisListener(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, VueInventaire vueInventaire, GestionnaireItem gestionnaireItem, GestionnaireMobHostile gestionnaireMobHostile, GestionnaireMob gestionnaireMobPassif, GestionnaireFleche gestionnaireFleche) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.vueInventaire = vueInventaire;
        this.gestionnaireItem = gestionnaireItem;
        this.vueCraft = vueCraft;
    }

    public void setCraftController(CraftController craftController) {
        this.craftController = craftController;
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
        if (scene == null) {
            return;
        }
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

    private void gererPlacementBloc(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            int x = (int) event.getX() / Constantes.TAILLE_TUILE;
            int y = (int) event.getY() / Constantes.TAILLE_TUILE;
            int couche = 1;
            if (gererInteractionBlocSpecial(couche, x, y)) return;
            placerBloc(couche, x, y);
        }
    }

    private void placerBloc(int couche, int x, int y) {
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
            vueInventaire.mettreAJourAffichageInventaire();
            if (vueJoueur != null) {
                vueJoueur.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
            }
        }
    }

    private boolean gererInteractionBlocSpecial(int couche, int x, int y) {
        Bloc blocClique = GestionnaireBloc.getBloc(couche, x, y);
        if (blocClique != null && blocClique.estBlocAction() && GestionnaireBloc.estADistanceAutorisee(joueur, x, y)) {
            switch (blocClique) {
                case TABLE_CRAFT -> {
                    if (craftController != null) {
                        craftController.ouvrirFenetreCraft();
                    }
                    return true;
                }
                default -> {
                    System.err.println("Bloc spécial non géré : " + blocClique.getNom());
                    return false;
                }
            }
        }
        return false;
    }

    private void gererClicSouris(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        int x = (int) event.getX() / Constantes.TAILLE_TUILE;
        int y = (int) event.getY() / Constantes.TAILLE_TUILE;
        int couche1 = 1;
        int couche2 = 2;

        Item itemBloc1 = GestionnaireBloc.casserBlocEtDonnerItem(couche1, x, y, joueur);
        Item itemBloc2 = GestionnaireBloc.casserBlocEtDonnerItem(couche2, x, y, joueur);
        if (itemBloc1 != null || itemBloc2 != null) {
            gestionnaireItem.spawnItemAuSol(itemBloc1, x, y);
            gestionnaireItem.spawnItemAuSol(itemBloc2, x, y);
            vueInventaire.mettreAJourAffichageInventaire();
        }
    }

    public void setVueCraft(VueCraft vueCraft) {
        this.vueCraft = vueCraft;
    }


}