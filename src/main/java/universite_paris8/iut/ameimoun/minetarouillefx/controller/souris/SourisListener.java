// java
package universite_paris8.iut.ameimoun.minetarouillefx.controller.souris;

import javafx.geometry.Point2D;
import javafx.scene.Group;
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
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobPassif;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobHostile;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.Constantes.Constantes;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.*;

/**
 * Classe SourisListener qui gère les interactions de la souris dans le jeu.
 * Conversion des coordonnées écran -> monde via worldGroup.sceneToLocal(...)
 */
public class SourisListener {

    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private GestionnaireItem gestionnaireItem;
    private GestionnaireMobHostile gestionnaireMobHostile;
    private GestionnaireMobPassif gestionnaireMobPassif;
    private GestionnaireFleche gestionnaireFleche;
    private VueCarte vueCarte;
    private VueCraft vueCraft;
    private VueJoueur vueJoueur;
    private CraftController craftController;
    private final Group worldGroup;

    public SourisListener(Joueur joueur,
                          Inventaire inventaire,
                          VueCarte vueCarte,
                          GestionnaireItem gestionnaireItem,
                          GestionnaireMobHostile gestionnaireMobHostile,
                          GestionnaireMobPassif gestionnaireMobPassif,
                          GestionnaireFleche gestionnaireFleche,
                          VueInventaire vueInventaire,
                          Group worldGroup) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.gestionnaireItem = gestionnaireItem;
        this.gestionnaireMobHostile = gestionnaireMobHostile;
        this.gestionnaireMobPassif = gestionnaireMobPassif;
        this.gestionnaireFleche = gestionnaireFleche;
        this.vueInventaire = vueInventaire;
        this.worldGroup = worldGroup;
    }

    public void setCraftController(CraftController craftController) {
        this.craftController = craftController;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnMousePressed(this::gererClicGauche);
        tilePane.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                gererClicDroit(event);
            }
        });
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

    private Point2D toWorld(MouseEvent event) {
        // conversion robuste écran -> coordonnées locales du worldGroup (inclut translation/scale)
        return worldGroup.sceneToLocal(event.getSceneX(), event.getSceneY());
    }

    private void gererClicDroit(MouseEvent event) {
        gererPlacementBloc(event);
        gererOuvertureLivre();
    }

    private void gererPlacementBloc(MouseEvent event) {
        Point2D world = toWorld(event);
        int px = (int) world.getX();
        int py = (int) world.getY();
        int couche = 1;
        if (gererInteractionBlocSpecial(couche, px, py)) return;
        placerBloc(couche, px, py);
    }

    private void gererOuvertureLivre() {
        Item itemSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (itemSelectionne != null && Objet.LIVRE.estUnLivre(itemSelectionne)) {
            VueLivre.getInstance().ouvrir();
        }
    }

    private void placerBloc(int couche, int px, int py) {
        boolean blocPlace = GestionnaireBloc.placerBloc(
                Carte.getInstance(),
                inventaire,
                inventaire.getSelectedIndex(),
                couche,
                px,
                py,
                joueur
        );

        if (blocPlace) {
            int tileX = px / Constantes.TAILLE_TUILE;
            int tileY = py / Constantes.TAILLE_TUILE;
            // vérification bornes avant mise à jour vue
            if (tileX >= 0 && tileY >= 0 && tileX < Carte.getInstance().getLargeur() && tileY < Carte.getInstance().getHauteur()) {
                vueCarte.mettreAJourAffichage(tileX, tileY);
            }
            vueInventaire.mettreAJourAffichageInventaire();
            if (vueJoueur != null) {
                vueJoueur.mettreAJourObjetTenu(inventaire.getItem(inventaire.getSelectedIndex()));
            }
        }
    }

    public void gererAttaqueProximite() {
        double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);

        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (objetSelectionne == null) return;

        if (Objet.EPEE.getNom().equals(objetSelectionne.getNom())) {
            if (gestionnaireMobHostile != null) {
                gestionnaireMobHostile.tuerMob(playerCenterX, playerCenterY, Constantes.DISTANCE_ATTAQUE);
            }

            if (gestionnaireMobPassif != null) {
                gestionnaireMobPassif.tuerMob(playerCenterX, playerCenterY, Constantes.DISTANCE_ATTAQUE);
            }
        }
    }

    public void gererAttaqueDistance(MouseEvent event) {
        double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (objetSelectionne == null) return;

        if (Objet.ARC.getNom().equals(objetSelectionne.getNom())) {
            Point2D world = toWorld(event);
            double worldMouseX = world.getX();
            double worldMouseY = world.getY();

            double dx = worldMouseX - playerCenterX;
            double dy = worldMouseY - playerCenterY;
            double norme = Math.sqrt(dx * dx + dy * dy);
            if (norme != 0) {
                dx /= norme;
                dy /= norme;
            }

            int tileX = (int) (worldMouseX / Constantes.TAILLE_TUILE);
            int tileY = (int) (worldMouseY / Constantes.TAILLE_TUILE);
            if (tileX >= 0 && tileY >= 0 && tileX < Carte.getInstance().getLargeur() && tileY < Carte.getInstance().getHauteur()) {
                Bloc blocVise = Carte.getInstance().getBloc(tileX, tileY, 1);
                if (blocVise == null || !blocVise.estSolide()) {
                    gestionnaireFleche.tirerFleche(playerCenterX, playerCenterY, dx * 2, dy * 2);
                }
            } else {
                // si hors carte, on peut tout de même tirer
                gestionnaireFleche.tirerFleche(playerCenterX, playerCenterY, dx * 2, dy * 2);
            }
        }
    }

    private boolean gererInteractionBlocSpecial(int couche, int px, int py) {
        Bloc blocClique = GestionnaireBloc.getBloc(couche, px, py);
        if (blocClique != null && blocClique.estBlocAction() && GestionnaireBloc.estADistanceAutorisee(joueur, px, py)) {
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

    private void dropItemEtMettreAJour(Item item, int tileX, int tileY, int couche) {
        if (item != null) {
            if (tileX >= 0 && tileY >= 0 && tileX < Carte.getInstance().getLargeur() && tileY < Carte.getInstance().getHauteur()) {
                vueCarte.mettreAJourAffichage(tileX, tileY);
                if (couche == 1 && tileY - 1 >= 0) {
                    vueCarte.mettreAJourAffichage(tileX, tileY - 1);
                }
            }
            gestionnaireItem.spawnItemAuSol(item, tileX, tileY);
        }
    }

    private void casserBloc(int couche, double clickX, double clickY) {
        int px = (int) clickX;
        int py = (int) clickY;
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (objetSelectionne == null) return;

        if (Objet.PIOCHE.getNom().equals(objetSelectionne.getNom())) {
            Item itemBloc = GestionnaireBloc.casserBlocEtDonnerItem(couche, px, py, joueur);
            if (itemBloc != null) {
                int tileX = px / Constantes.TAILLE_TUILE;
                int tileY = py / Constantes.TAILLE_TUILE;
                dropItemEtMettreAJour(itemBloc, tileX, tileY, couche);
                vueInventaire.mettreAJourAffichageInventaire();
            }
        }
    }

    private void gererClicGauche(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) return;
        Point2D world = toWorld(event);
        casserBloc(1, world.getX(), world.getY());
        casserBloc(2, world.getX(), world.getY());

        gererAttaqueProximite();
        gererAttaqueDistance(event);
    }

    public void setVueCraft(VueCraft vueCraft) {
        this.vueCraft = vueCraft;
    }

}