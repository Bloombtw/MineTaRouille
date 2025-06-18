package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCraft;

/**
 * Contrôleur gérant les interactions de la fenêtre de craft (clics, clavier).
 * Fait le lien entre VueCraft et GestionnaireCraft.
 * Permet d'ajouter/retirer des items dans la grille, de tenter un craft,
 * d'ouvrir/fermer la fenêtre et de gérer les raccourcis clavier.
 */
public class CraftController {
    private final GestionnaireCraft gestionnaireCraft;
    private final VueCraft vueCraft;
    private ClavierListener clavierListener;

    /**
     * Construit un contrôleur de craft.
     * @param gestionnaireCraft le gestionnaire de craft associé
     * @param vueCraft la vue de la fenêtre de craft
     */
    public CraftController(GestionnaireCraft gestionnaireCraft, VueCraft vueCraft) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.vueCraft = vueCraft;
    }

    /**
     * Gère le clic sur une case de la grille de craft.
     * Ajoute ou retire un item selon l'état de la case.
     * @param ligne la ligne de la case cliquée
     * @param colonne la colonne de la case cliquée
     */
    public void clicCaseCraft(int ligne, int colonne) {
        gestionnaireCraft.ajouterOuRetirerItem(ligne, colonne);
    }

    /**
     * Tente de réaliser le craft selon la grille et l'inventaire.
     */
    public void clicBoutonCrafter() {
        gestionnaireCraft.tenterCraft();
    }

    /**
     * Ouvre la fenêtre de craft (met à jour l'état dans le gestionnaire).
     */
    public void ouvrirFenetreCraft() {
        gestionnaireCraft.ouvrirFenetreCraft();
    }

    /**
     * Ferme la fenêtre de craft : remet les items de la grille dans l'inventaire,
     * puis ferme la fenêtre (met à jour l'état dans le gestionnaire).
     */
    public void fermerFenetreCraft() {
        gestionnaireCraft.remettreItemsGrilleDansInventaire();
        gestionnaireCraft.fermerFenetreCraft();
    }

    /**
     * Définit le listener clavier pour la gestion de la sélection d'inventaire.
     * @param clavierListener le listener à utiliser
     */
    public void setClavierListener(ClavierListener clavierListener) {
        this.clavierListener = clavierListener;
    }

    /**
     * Initialise les listeners sur la vue de craft :
     * - clic souris pour focus
     * - gestion des touches clavier (fermeture, sélection inventaire)
     */
    public void initialiserListeners() {
        AnchorPane overlay = vueCraft.getOverlayPane();
        if (overlay != null) {
            overlay.setOnMousePressed(event -> {
                overlay.requestFocus();
                event.consume();
            });
            overlay.setOnKeyPressed(event -> {
                String keyText = event.getText();
                if (event.getCode() == KeyCode.E) {
                    fermerFenetreCraft();
                } else if (keyText.matches("[&é\"'(-è_]")) {
                    if (clavierListener != null) {
                        clavierListener.gererSelectionInventaire(keyText);
                    }
                }
                event.consume();
            });
        }
    }
}