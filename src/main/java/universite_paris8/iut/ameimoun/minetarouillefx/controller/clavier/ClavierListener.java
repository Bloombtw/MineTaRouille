package universite_paris8.iut.ameimoun.minetarouillefx.controller.clavier;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.TilePane;
import universite_paris8.iut.ameimoun.minetarouillefx.controller.JeuController;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Inventaire;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.Joueur;
import universite_paris8.iut.ameimoun.minetarouillefx.modele.gestionnaires.GestionnaireItem;
import universite_paris8.iut.ameimoun.minetarouillefx.utils.debug.DebugManager;
import universite_paris8.iut.ameimoun.minetarouillefx.vue.VueInventaire;

public class ClavierListener {
    private final Joueur joueur;
    private final Inventaire inventaire;
    private final VueInventaire vueInventaire;
    private final DebugManager debugManager;
    private JeuController jeuController;
    private final GestionnaireItem gestionnaireItem;

    public ClavierListener(Joueur joueur, Inventaire inventaire, VueInventaire vueInventaire, DebugManager debugManager, GestionnaireItem gestionnaireItem) {
        this.joueur = joueur;
        this.inventaire = inventaire;
        this.vueInventaire = vueInventaire;
        this.debugManager = debugManager;
        this.gestionnaireItem = gestionnaireItem;
    }

    public void lier(TilePane tilePane) {
        tilePane.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z, SPACE, UP -> joueur.sauterUneFois();
                case Q, LEFT -> joueur.setEnDeplacementGauche(true);
                case D, RIGHT -> joueur.setEnDeplacementDroite(true);
                case F3 -> debugManager.toggle();
                case A -> gestionnaireItem.jeterItemSelectionne(joueur, vueInventaire);
                case R -> gestionnaireItem.consommerMoutonCuitSelectionne(joueur, vueInventaire);
                case E -> joueur.ramasserItemsProches(gestionnaireItem.getItemsAuSol()); // 👈 Ramassage manuel
            }
            gererSelectionInventaire(event.getText());
            vueInventaire.mettreAJourAffichageInventaire();
        });

        tilePane.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case Q, LEFT -> joueur.setEnDeplacementGauche(false);
                case D, RIGHT -> joueur.setEnDeplacementDroite(false);
            }
        });
    }

    public void desactiver(TilePane tilePane) {
        tilePane.setOnKeyPressed(null);
        tilePane.setOnKeyReleased(null);
    }

    public void gererSelectionInventaire(String caractere) {
        switch (caractere) {
            case "&" -> inventaire.setSelectedIndex(0);
            case "é" -> inventaire.setSelectedIndex(1);
            case "\"" -> inventaire.setSelectedIndex(2);
            case "'" -> inventaire.setSelectedIndex(3);
            case "(" -> inventaire.setSelectedIndex(4);
            case "-" -> inventaire.setSelectedIndex(5);
            case "è" -> inventaire.setSelectedIndex(6);
            case "_" -> inventaire.setSelectedIndex(7);
            case "ç" -> inventaire.setSelectedIndex(8);
        }
    }

    private void ignorerToucheSiJeuEnPause(KeyEvent event) {
        if (jeuController != null && jeuController.isEnPause()) {
            event.consume();
        }
    }

    public void setJeuController(JeuController jeuController) {
        this.jeuController = jeuController;
    }
}
