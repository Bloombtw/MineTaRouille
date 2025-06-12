package universite_paris8.iut.ameimoun.minetarouillefx.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier.ClavierListener;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireCraft;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueCraft;

public class CraftController {
    private final GestionnaireCraft gestionnaireCraft;
    private final VueCraft vueCraft;
    private ClavierListener clavierListener;

    public CraftController(GestionnaireCraft gestionnaireCraft, VueCraft vueCraft) {
        this.gestionnaireCraft = gestionnaireCraft;
        this.vueCraft = vueCraft;
    }

    public void clicCaseCraft(int ligne, int colonne) {
        gestionnaireCraft.ajouterOuRetirerItem(ligne, colonne);
    }

    public void clicBoutonCrafter() {
        gestionnaireCraft.tenterCraft();
        // VueCraft se met à jour automatiquement grâce aux propriétés observables
    }

    public void ouvrirFenetreCraft() {
        gestionnaireCraft.ouvrirFenetreCraft();
    }

    public void fermerFenetreCraft() {
        gestionnaireCraft.fermerFenetreCraft();
    }

    public void setClavierListener(ClavierListener clavierListener) {
        this.clavierListener = clavierListener;
    }

    public void gererTouche(KeyCode code) {
        if (code == KeyCode.E) {
            fermerFenetreCraft();
        }
    }

    public void initialiserListeners() {
        AnchorPane overlay = vueCraft.getOverlayPane();
        if (overlay != null) {
            overlay.setOnMousePressed(event -> {
                overlay.requestFocus();
                event.consume();
            });

            overlay.setOnKeyPressed(event -> {
                String keyText = event.getText();
                if (keyText.matches("[&é\"'(-è_]")) {
                    if (clavierListener != null) {
                        clavierListener.gererSelectionInventaire(keyText);
                    }
                } else if (event.getCode() == KeyCode.E) {
                    gererTouche(KeyCode.E);
                }
                event.consume();
            });
        }
    }
}