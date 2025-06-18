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
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.mob.GestionnaireMobPassif;
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
    private GestionnaireMobHostile gestionnaireMobHostile;
    private GestionnaireMobPassif gestionnaireMobPassif;
    private GestionnaireFleche gestionnaireFleche;
    private VueCarte vueCarte;
    private VueCraft vueCraft;
    private VueJoueur vueJoueur;
    private CraftController craftController;

    public SourisListener(Joueur joueur, Inventaire inventaire, VueCarte vueCarte, GestionnaireItem gestionnaireItem, GestionnaireMobHostile gestionnaireMobHostile, GestionnaireMobPassif gestionnaireMobPassif, GestionnaireFleche gestionnaireFleche, VueInventaire vueInventaire)   {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueCarte = vueCarte;
        this.gestionnaireItem = gestionnaireItem;
        this.gestionnaireMobHostile = gestionnaireMobHostile;
        this.gestionnaireMobPassif = gestionnaireMobPassif;
        this.gestionnaireFleche = gestionnaireFleche;
        this.vueInventaire = vueInventaire;
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

    /**
     * Gère les attaques de proximité contre les mobs.
     */
    public void gererAttaqueProximite() {
        double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);

        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());
        if (Objet.EPEE.getNom().equals(objetSelectionne.getNom())) {
            if (gestionnaireMobHostile != null) {
                gestionnaireMobHostile.tuerMob(playerCenterX, playerCenterY, Constantes.DISTANCE_ATTAQUE);
            }

            if (gestionnaireMobPassif != null) {
                gestionnaireMobPassif.tuerMob(playerCenterX, playerCenterY, Constantes.DISTANCE_ATTAQUE);
            }
        }
    }

    /**
     * Gère les attaques à distance avec des flèches.
     *
     * @param event L'événement de clic de la souris.
     */
    public void gererAttaqueDistance(MouseEvent event) {
        double playerCenterX = joueur.getX() + (Constantes.TAILLE_PERSO / 2.0);
        double playerCenterY = joueur.getY() + (Constantes.TAILLE_PERSO / 2.0);
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());

        if (Objet.ARC.getNom().equals(objetSelectionne.getNom())) {
            // Calcul de la direction vers la souris
            double dx = event.getX() - playerCenterX;
            double dy = event.getY() - playerCenterY;
            double norme = Math.sqrt(dx * dx + dy * dy);
            if (norme != 0) {
                dx /= norme;
                dy /= norme;
            }

            // Vérification du bloc visé
            int tileX = (int) (event.getX() / Constantes.TAILLE_TUILE);
            int tileY = (int) (event.getY() / Constantes.TAILLE_TUILE);
            Bloc blocVise = Carte.getInstance().getBloc(tileX, tileY, 1);
            if (blocVise == null || !blocVise.estSolide()) {
                gestionnaireFleche.tirerFleche(playerCenterX, playerCenterY, dx * 2, dy * 2);
            }
        }
    }

    /**
     * Gère les interactions avec les blocs spéciaux (comme la table de craft).
     *
     * @param couche Le numéro de la couche du bloc.
     * @param x     La position X du bloc.
     * @param y     La position Y du bloc.
     * @return true si l'interaction a été gérée, false sinon.
     */

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
    /**
     * Dépose un item au sol et met à jour l'affichage.
     *
     * @param item   L'item à déposer.
     * @param x      La position X où déposer l'item.
     * @param y      La position Y où déposer l'item.
     * @param couche La couche où déposer l'item.
     */
    private void dropItemEtMettreAJour(Item item, int x, int y, int couche) {
        if (item != null) {
            vueCarte.mettreAJourAffichage(x, y); // le bloc cassé
            if (couche == 1 && y - 1 >= 0) {
                vueCarte.mettreAJourAffichage(x, y - 1); // décor au-dessus si sol cassé
            }
            gestionnaireItem.spawnItemAuSol(item, x, y);
        }
    }

    /**
     * Casse un bloc dans la carte et met à jour l'affichage.
     *
     * @param couche La couche du bloc à casser.
     * @param clickX La position X du clic de la souris.
     * @param clickY La position Y du clic de la souris.
     */
    private void casserBloc(int couche, double clickX, double clickY) {
        int tx = (int) (clickX / Constantes.TAILLE_TUILE);
        int ty = (int) (clickY / Constantes.TAILLE_TUILE);
        Item objetSelectionne = inventaire.getItem(inventaire.getSelectedIndex());

        if (Objet.PIOCHE.getNom().equals(objetSelectionne.getNom())) {
            Item itemBloc = GestionnaireBloc.casserBlocEtDonnerItem(couche, tx, ty, joueur);
            if (itemBloc != null) {
                dropItemEtMettreAJour(itemBloc, tx, ty, couche);
                vueInventaire.mettreAJourAffichageInventaire();
            }
        }
    }


    /**
     * Gère les clics de la souris pour casser des blocs et attaquer des mobs.
     *
     * @param event L'événement de clic de la souris.
     */
    private void gererClicSouris(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY) {
            return;
        }
        double clickX = event.getX();
        double clickY = event.getY();
        casserBloc(1, clickX, clickY);
        casserBloc(2, clickX, clickY);

        gererAttaqueProximite();
        gererAttaqueDistance(event);
    }
    public void setVueCraft(VueCraft vueCraft) {
        this.vueCraft = vueCraft;
    }


}